package com.bh.live.task.component.issue.processer;

import com.bh.live.common.redislock.RedisLock;
import com.bh.live.common.redislock.lock.RedisLockKey;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import com.bh.live.task.service.lottery.IIssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName CreateProcessor
 * @description: 彩期创建process
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Component("issueCreateProcessor")
@Slf4j
public class CreateProcessor extends BaseTaskProcessor {

    @Autowired
    private IIssueService issueService;

    @Override
    @RedisLock(redisKey = RedisLockKey.ISSUE_TASK, retryTimes = 0, prop = "springId")
    @Order(9999)
    public void execute(ScheduleJobInfo jobInfo) {
        log.info("[*]>> 创建彩期 = " + jobInfo.getJobName() + " | " + DateUtils.getTime());
        try {
            issueService.createNewIssue();
        } catch (Exception e) {
            log.error("[*] 彩期生成异常 , cause:{} message:{}", e.getCause(), e.getMessage());
        }
    }

    @Override
    public void doExecute(ScheduleJobInfo jobInfo) {
        super.doExecute(jobInfo);
    }
}
