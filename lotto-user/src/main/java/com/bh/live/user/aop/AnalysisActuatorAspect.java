package com.bh.live.user.aop;

import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.string.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 横切在线人数统计
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/15 16:09
 */
@Aspect
@Component
public class AnalysisActuatorAspect {
    final static Logger log = LoggerFactory.getLogger(AnalysisActuatorAspect.class);
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(analysisActuator)")
    public void serviceStatistics(AnalysisActuator analysisActuator) {
    }

    @AfterReturning(value = "serviceStatistics(analysisActuator)", returning = "result")
    public void doAfter(AnalysisActuator analysisActuator, Result result) {
        Map<String, Object> maps = (Map<String, Object>) result.getData();
        Integer userId = (Integer) maps.get("userId");
        if (userId != null) {
            // 记录用户在线人数
            String roomId = maps.get("roomId").toString();
            Set<Integer> userSet = redisUtil.getByFastJson(String.format(UserRedisKey.ROOM_ONLINE, roomId), Set.class);
            boolean flag=false;
            if (StringUtils.isNotEmpty(userSet)) {
                if (!userSet.contains(userId)) {
                    userSet.add(userId);
                    flag=true;
                }
            } else {
                userSet = new HashSet<>();
                userSet.add(userId);
                flag=true;
            }
            if (flag){
                //更新缓存
                redisUtil.setByFastJson(String.format(UserRedisKey.ROOM_ONLINE, roomId), userSet, 180, TimeUnit.MINUTES);
            }
        }
        log.info("====================================== statistic note:{}", analysisActuator.note());
    }

}