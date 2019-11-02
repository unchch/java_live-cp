package com.bh.live.task.component.issue.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @ClassName QuartzJobFactory
 * @description: 计划任务执行处 无状态
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
public class QuartzJobFactory implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        ScheduleJobInfo scheduleJob = (ScheduleJobInfo) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokeMethod(scheduleJob);
    }
}