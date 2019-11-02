package com.bh.live.task.task;

import com.bh.live.common.redislock.RedisLock;
import com.bh.live.common.redislock.lock.RedisLockKey;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.task.service.advance.IStatUserIncomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Morphon
 * @date 2019/8/2 11:32
 * @desc 主播收入统计定时任务
 * @Version 1.0
 */
@Component
@EnableScheduling
@Slf4j
public class StatUserIncomeTask {

    @Autowired
    private IStatUserIncomeService userIncomeService;

    //每天凌晨1点统计
    @Scheduled(cron = "0 0 1 * * ?")
    public void statUserGiftIncome() {
        try {
            log.info("statUserGiftIncome task start:" + System.currentTimeMillis());
            List<StatUserIncome> incomes = userIncomeService.statUserIncomes();
            if (incomes != null && incomes.size() > 0) {
                userIncomeService.saveBatch(incomes);
            }
            log.info("statUserGiftIncome task end date:{}", System.currentTimeMillis());
        } catch (Exception e) {
            log.warn("statUserGiftIncome task exception. cause:{} message:{}", e.getCause(), e.getMessage());
        }
    }
}
