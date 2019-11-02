package com.bh.live.task.component.issue.cron;

import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import com.bh.live.task.component.issue.template.AbstractTemplate;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * @description: 活动奖励发放 template
 * @author:chenlang
 * @date:2018/6/16 14:11
 */
public class CronExpTemplate extends AbstractTemplate {

    /**
     * 任务组名称
     */
    private String jobGroupName;

    /**
     * 活动名称
     */
    private String jobName;

    /**
     * 活动processor service name
     */
    private String jobSpringId;

    /**
     * 活动描述
     */
    private String jobDes;

    private String cronExp;


    public CronExpTemplate(String jobGroupName, String jobName, String jobSpringId, String jobDes, String cronExp, Scheduler scheduler, Object ruleObject) {
        super(scheduler, ruleObject);
        this.jobGroupName = jobGroupName;
        this.jobName = jobName;
        this.jobSpringId = jobSpringId;
        this.jobDes = jobDes;
        this.cronExp = cronExp;
    }

    @Override
    protected void buildScheduleJobInfo(Object[] extInfo) {
        scheduleJobInfo.setJobGroup(jobGroupName);
        scheduleJobInfo.setJobName(jobName);
        scheduleJobInfo.setSpringId(jobSpringId);
        scheduleJobInfo.setMethodName(JOB_EXEC_METHOD);
        scheduleJobInfo.setCronExpression(cronExp);
        scheduleJobInfo.setIsConcurrent(ScheduleJobInfo.CONCURRENT_NOT);
        scheduleJobInfo.setJobStatus(ScheduleJobInfo.STATUS_RUNNING);
        scheduleJobInfo.setDescription(jobDes);
        scheduleJobInfo.setExtInfo(extInfo);
    }

    @Override
    protected Trigger createTrigger() throws SchedulerException {
        return super.createCrontabTrigger();
    }

    @Override
    protected void doException(Exception e) {

    }
}
