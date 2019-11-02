package com.bh.live.common.utils;

import cn.hutool.core.date.DateUtil;
import com.bh.live.pojo.res.anchor.AdvancesRes;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;
import com.bh.live.pojo.res.anchor.RoomLiveRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AdvanceUtil
 * @description: 处理直播预告数据
 * @author: Morphon
 * @date 2019-08-15
 */
public class AdvanceUtil {

    public static List<AdvancesRes> advanceHandler(List<HostAdvanceRes> list) {
        if (list == null || list.size() == 0) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.HH_MM_SS);
        //SimpleDateFormat sdf1 = new SimpleDateFormat(DateUtils.YYYY_MM_DD);
        int today = DateUtils.dayForWeek(new Date()); //今天星期几
        List<AdvancesRes> resList = new ArrayList<>();
        String str = "";
        for (HostAdvanceRes res : list) {
            AdvancesRes advance = new AdvancesRes();
            advance.setRoomId(res.getRoomId());
            advance.setSeedName(res.getSeedName());
            advance.setSeedNo(res.getSeedNo());
            advance.setNickname(res.getNickname());
            advance.setStatus(res.getHostStatus() == null ? null : res.getHostStatus());
            String openTime = res.getStartTime().substring(0, 5);
            String endTime = res.getEndTime().substring(0, 5);
            str = openTime + "-" + endTime;
            if (res.getLivePeriod().compareTo(0) == 0) {
                //周期性的预告
                //判断今天星期几并判断是否过了今天，过了返回false，不返回已经过了的直播预告
                //时当天时，还未判断时间
                Map<Integer, Boolean> judgeDate = JudgeDateUtil.judge(res.getLiveDate(), sdf.format(res.getStartTime()));
                for (Map.Entry<Integer, Boolean> entry : judgeDate.entrySet()) {
                    if (entry.getValue()) {
                        if (entry.getKey().compareTo(today) == 0) {
                            advance.setLiveDate(DateUtils.getDay(0));
                            advance.setAdvanceTime(DateUtils.getDay(0) + str);
                        } else {
                            advance.setLiveDate(DateUtils.getDay(entry.getKey() - today));
                            advance.setAdvanceTime(DateUtils.getDay(entry.getKey() - today) + str);
                        }
                        advance.setStartTime(DateUtils.parseDate(DateUtils.getDay(0) + sdf.format(res.getStartTime())));
                        advance.setEndTime(DateUtils.parseDate(DateUtils.getDay(0) + sdf.format(res.getEndTime())));

                        resList.add(advance);
                    }
                }
            } else {
                //天的预告
                boolean isTrue = JudgeDateUtil.isTrue(res.getStartTime(),
                        DateUtils.dayForWeek(DateUtil.parseDate(res.getLiveDate())) + 1 + "");  //判断是否大于今天的
                if (isTrue) {
                    advance.setLiveDate(res.getLiveDate());
                    advance.setAdvanceTime(res.getLiveDate() + " " + str);
                    advance.setStartTime(DateUtils.parseDate(res.getLiveDate() + " " + res.getStartTime()));
                    advance.setEndTime(DateUtils.parseDate(res.getLiveDate() + " " + res.getEndTime()));
                    resList.add(advance);
                }
            }
        }
        return resList;
    }

}
