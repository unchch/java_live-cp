package com.bh.live.task.component.issue.processer;

import com.bh.live.common.utils.DateUtils;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ArgModifyProcessor
 * @description: job基础处理器，主要用于统一入口按类型执行不同的processor，并提供分布式：try lock、unlock
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Slf4j
public abstract class BaseTaskProcessor {
    /**
     * 子类实现具体执行逻辑
     *
     * @param jobInfo
     * @return
     * @author yq.
     */
    abstract public void execute(ScheduleJobInfo jobInfo);

    /**
     * 执行processor入口(job自动调用)
     *
     * @param jobInfo
     */
    public void doExecute(ScheduleJobInfo jobInfo) {
        log.info("doExecute:{} now:{}", jobInfo.toString(), DateUtils.getTime());
        try {
            execute(jobInfo);
        } catch (Exception e) {
            log.error("doExecute exception. cause:{} message:{}", e.getCause(), e.getMessage());
        }
    }
}