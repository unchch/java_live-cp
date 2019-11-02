package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.dao.anchor.LiveRecordDao;
import com.bh.live.model.anchor.LiveRecord;
import com.bh.live.pojo.res.anchor.LiveRecordRes;
import com.bh.live.service.anchor.ILiveRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主播直播记录 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
@Service
public class LiveRecordServiceImpl extends ServiceImpl<LiveRecordDao, LiveRecord> implements ILiveRecordService {

    @Resource
    private LiveRecordDao liveRecordDao;

    @Override
    public Map<String, Object> getRecords(Integer userId, String begin, String end) {
        Map<String, Object> resMap = new HashMap<>();
        List<LiveRecordRes> resList = new ArrayList<>();
        QueryWrapper<LiveRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (StringUtils.isNotEmpty(begin) && StringUtils.isNotEmpty(end)) {
            wrapper.between("end_time", begin, end);
        }
        List<LiveRecord> liveRecords = liveRecordDao.selectList(wrapper);
        long totalTime = 0;
        for (LiveRecord item : liveRecords) {
            LiveRecordRes res = new LiveRecordRes();
            long min = DateUtils.getDatePoor(item.getEndTime(), item.getOpenTime(), "min"); //计算直播了多少分钟
            totalTime += min; //累计直播总时
            item.setTime(DateUtils.calTime(min));
            String timeStr = DateFormatUtils.format(item.getOpenTime(), "yyyy/MM/dd") + " " +
                    DateFormatUtils.format(item.getOpenTime(), DateUtils.HH_MM_SS) + "-" +
                    DateFormatUtils.format(item.getEndTime(), DateUtils.HH_MM_SS);

            res.setLiveTime(timeStr);
            BeanUtils.copyProperties(item, res);
            resList.add(res);
        }
        resMap.put("list", resList);
        resMap.put("totalTime", DateUtils.calTime(totalTime));
        return resMap;
    }


}
