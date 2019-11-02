package com.bh.live.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.HostAdvanceReq;
import com.bh.live.pojo.req.anchor.SaveHostAdvance;
import com.bh.live.pojo.res.anchor.AdvanceRes;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;
import com.bh.live.pojo.res.anchor.HostUserLiveSeedAdvanceRes;
import com.bh.live.pojo.res.anchor.RecomHostRoomRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.rankingList.SearchLiveUserRes;
import com.bh.live.pojo.res.user.LiveHostStateRes;
import com.bh.live.user.dao.HostAdvanceDao;
import com.bh.live.user.service.IHostAdvanceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 主播预告表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-07-29
 */
@Service
public class HostAdvanceServiceImpl extends ServiceImpl<HostAdvanceDao, HostAdvance>
        implements IHostAdvanceService {

    protected final Logger logger = LoggerFactory.getLogger(HostAdvanceServiceImpl.class);

    @Resource
    private HostAdvanceDao hostAdvanceDao;

    @Transactional
    @Override
    public boolean addHostAdvance(Integer hostId, SaveHostAdvance saveHostAdvance) {
        try {

            HostAdvance hostAdvance = new HostAdvance();
            BeanUtils.copyProperties(saveHostAdvance, hostAdvance);
            hostAdvance.setHostId(hostId);
            hostAdvance.setCreatTime(DateUtils.getNowDate());
            hostAdvance.setUpdateTime(DateUtils.getNowDate());
            return super.save(hostAdvance);
        } catch (Exception e) {
            logger.error("创建预告失败", e);
            return false;
        }
    }

    @Override
    public boolean updateHostAdvance(HostAdvanceReq res) {
        HostAdvance hostAdvance = new HostAdvance();
        BeanUtils.copyProperties(res, hostAdvance);// 类型转换
//        hostAdvance.setUpdateTime(DateUtils.getNowDate());

        return super.updateById(hostAdvance);
    }

    @Override
    public List<HostAdvanceRes> queryHostAdvance(Integer hostId, Integer seedNo, Integer size) {
        return queryHostAdvanceBy(hostId, seedNo, 0, size);
    }

    @Override
    public List<HostUserLiveSeedAdvanceRes> queryHostAdvance(Integer hostId) {
        return hostAdvanceDao.queryHostAdvancesByUserId(hostId);
    }

    @Override
    public List<List<HostAdvanceRes>> queryClassifyHostAdvance(Integer hostId, Integer seedNo) {
        List<List<HostAdvanceRes>> list = new LinkedList<List<HostAdvanceRes>>();
        List<HostAdvanceRes> today = queryHostAdvanceBy(hostId, seedNo, 0, 1);
        List<HostAdvanceRes> tomorrow = queryHostAdvanceBy(hostId, seedNo, 1, 2);
        //today
        list.add(0, today != null && today.size() > 0 ? today : null);
        //tomorrow
        list.add(1, tomorrow != null && tomorrow.size() > 0 ? tomorrow : null);
        return list;
    }

    @Override
    public  List<AdvanceRes> queryAllHostAdvance() {
        List<HostAdvanceRes> today = queryHostAdvanceBy(null, null, 0, 1);
        List<HostAdvanceRes> tomorrow = queryHostAdvanceBy(null, null, 1, 2);
        List<HostAdvanceRes> diebTert = queryHostAdvanceBy(null, null, 2, 3);
        today.addAll(tomorrow);
        today.addAll(diebTert);

        for (HostAdvanceRes advanceRes : today) {
            String startTime = advanceRes.getStartTime();
            String endTime = advanceRes.getEndTime();
            if(startTime.lastIndexOf(":") > 0){
                startTime = StringUtils.substring(startTime,0, startTime.lastIndexOf(":"));
            }
            if(endTime.lastIndexOf(":") > 0){
                endTime =StringUtils.substring(endTime,0,endTime.lastIndexOf(":"));
            }
            advanceRes.setStartTime(startTime);
            advanceRes.setEndTime(endTime);
        }

        Map<Integer, String> hashedMap = new HashedMap();
        //获得彩种map
        for (HostAdvanceRes hostAdvanceRes : today) {
            hashedMap.put(hostAdvanceRes.getSeedNo(), hostAdvanceRes.getSeedName());
        }

         //按开播时间排序
        Collections.sort(today, new Comparator<HostAdvanceRes>() {
            @Override
            public int compare(HostAdvanceRes o1, HostAdvanceRes o2){
               return o1.getStartTime().compareTo(o2.getStartTime());

            }
        });
        //获得主播信息
        Map<Integer, List<HostAdvanceRes>> res = today.stream().collect(Collectors.groupingBy(HostAdvanceRes::getSeedNo));
        List<AdvanceRes> ret = Lists.newArrayList();
        res.forEach((k, v) -> {
            Map<String, List<HostAdvanceRes>> hosts = v.stream().collect(Collectors.groupingBy(HostAdvanceRes::getAdvanceTime));

            List<AdvanceRes.Live> lives = Lists.newArrayList();
            hosts.forEach((date, host) -> {
                AdvanceRes.Live live = new AdvanceRes.Live().setDate(date).setHostAdvanceRes(host);
                lives.add(live);

            });
            AdvanceRes ar = new AdvanceRes().setSeedNo(k).setSeedName(hashedMap.get(k)).setHost(lives);
            ret.add(ar);

        });
        return ret;
    }

    // 查询预告
    public List<HostAdvanceRes> queryHostAdvanceBy(Integer hostId, Integer seedNo, int i, int j) {

        return hostAdvanceDao.queryHostAdvanceResList(DateUtils.dayForWeek(new Date()),
                DateUtils.getDay(i), DateUtils.getDay(j), seedNo, hostId);
    }

    @Override
    public List<HostAdvanceRes> queryHostUserListBySize(Integer userId, Integer size) {
        // 查询推荐主播
        return hostAdvanceDao.queryHostUserRecommend(userId, size);
    }

    @Override
    public List<RecomHostRoomRes> queryHostRoomListBySize(Integer userId, Integer size) {

        return hostAdvanceDao.queryHostRoomListBySize(userId, size);
    }

    @Override
    public TableDataInfo homeSearch(Integer userId, String content, Integer userType, Integer pageNum, Integer pageSize) {
        Page<SearchLiveUserRes> page = new Page<>(pageNum, pageSize);
        page.setRecords(hostAdvanceDao.querySearchLiveUserInfo(userId, content, userType));
        return new TableDataInfo(page);
    }

    @Override
    public List<HostAdvanceRes> queryAdvanceByHostId(Integer hostId) {

        return hostAdvanceDao.queryAdvanceByHostId(hostId);
    }

    @Override
    public LiveHostStateRes queryLiveHostStateById(Integer hostId) {

        return hostAdvanceDao.queryLiveHostStateById(hostId);
    }

}
