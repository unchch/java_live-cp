package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.model.rankingList.RankingList;
import com.bh.live.pojo.res.rankingList.RankingListRes;
import com.bh.live.user.dao.RankingListDao;
import com.bh.live.user.service.IRankingListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 排行表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
@Service
@Slf4j
public class RankingListServiceImpl extends ServiceImpl<RankingListDao, RankingList>
        implements IRankingListService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RankingListDao rankingListDao;

    /*原本排行查需要去查需redis缓存，但是房间状态需要实时去查询，所以改为实时去db查询*/
    @Override
    public List<RankingListRes> queryRankingListByPeriod(Integer rankPeriod, Integer rankType,
                                                         Integer userType, int size) {
      //  String key = String.format(UserRedisKey.USER_RANK_KEY, rankPeriod, userType, rankType);// 排行key
        try {
           /* long lsize = redisUtil.lGetListSize(key);// 获取当前缓存中排行的数量
            if (lsize > 0 && size <= lsize) {
                return cacheRankingList(key, size);// 满足要求，直接获取缓存信息返回
            }
            int rsize = rankingListDao.queryRankingCountByPeriod(rankPeriod, userType);// 查询该周期排行总数量
            if (size >= rsize && lsize > 0) {
                return cacheRankingList(key, size);// 满足要求，直接获取缓存信息返回
            }*/
           //实时去查询数据里面的排行榜数据
			List<RankingListRes> rankingList = rankingListDao.queryRankingListByPeriod(rankPeriod, rankType, userType, size);// 查询db,获取该周期的数据
            int index = 0;
            for (Iterator iterator = rankingList.iterator(); iterator.hasNext(); ) {
                RankingListRes RankingListRes = (RankingListRes) iterator.next();
                RankingListRes.setIndex(++index);// i构建排名
            }
            /*if (rankingList != null) {
            	   redisUtil.del(key);// 只失效对应key的数据
                redisUtil.lASet(key, rankingList, dayTimeToSecondsByType(rankPeriod));// 只更新对应key的缓存，并设置失效时间
            }*/
            return rankingList;
        //    return cacheRankingList(key, size);// 查询缓存返回信息
        } catch (Exception e) {
            log.error("查询排行版错误");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取缓存信息
     *
     * @param key
     * @param
     * @param size
     * @return
     */
    public List<RankingListRes> cacheRankingList(String key, int size) {
        List<RankingListRes> list = new LinkedList<RankingListRes>();// 定义返回集合
        redisUtil.lGet(key, 0, size).forEach(rankObj -> {
            RankingListRes res = new RankingListRes();
            BeanUtils.copyProperties(rankObj, res);// 类型转换
            list.add(res);
        });
        return list;
    }

    /**
     * 根据周期生成对应的秒
     *
     * @param period 周期 0天 1周 2月 3总
     * @return
     */
    public long dayTimeToSecondsByType(Integer period) {
        switch (period) {
            case 0:// 天
                return TimeUnit.DAYS.toSeconds(1);
            case 1:// 周
                return TimeUnit.DAYS.toSeconds(7);
            case 2:// 月
                return TimeUnit.DAYS.toSeconds(30);
            default:// 半小时
                return TimeUnit.MINUTES.toSeconds(30);
        }
    }

}
