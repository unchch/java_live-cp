package com.bh.live.task.component.issue.template;

import com.bh.live.task.component.issue.quartz.QuartzJobFactory;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName AbstractTemplate
 * @description: AbstractTemplate
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
public abstract class AbstractTemplate {

    public static final Logger logger = LoggerFactory.getLogger(AbstractTemplate.class);

    /**
     * job执行的默认方法
     */
    protected static final String JOB_EXEC_METHOD = "doExecute";

    protected Scheduler scheduler;

    /**
     * 规则object Arg
     */
    protected Object ruleObject;

    private JobKey jobKey;

    protected TriggerKey triggerKey;

    protected ScheduleJobInfo scheduleJobInfo = new ScheduleJobInfo();

    public AbstractTemplate(Scheduler scheduler, Object ruleObject) {
        this.scheduler = scheduler;
        this.ruleObject = ruleObject;
    }

    /**
     * 执行quartz整体初始化及schedule过程
     *
     * @param extInfo 扩展信息（例如：[0]LotSeed seed、[1]提前N秒封盘的preSeconds）
     */
    public void schedule(Object[] extInfo) {
        buildScheduleJobInfo(extInfo);
        try {
            JobDetail jobDetail = buildJobDetail();
            Trigger trigger = createTrigger();
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info(scheduleJobInfo.getDescription() + "，加入scheduler成功");
        } catch (SchedulerException e) {
            doException(e);
        }
    }

    /**
     * 构建job信息
     *
     * @param extInfo
     */
    abstract protected void buildScheduleJobInfo(Object[] extInfo);


    /**
     * 构建trigger集合
     *
     * @return
     * @throws SchedulerException
     */
    abstract protected Trigger createTrigger() throws SchedulerException;

    /**
     * 处理异常信息，抛给子类实现
     *
     * @param e
     */
    abstract protected void doException(Exception e);

    /**
     * 停止所有job并删除trigger
     */
    public void stop() {

        try {
            if (!scheduler.isShutdown()) {
                // 停止触发器
                scheduler.pauseTrigger(triggerKey);
                // 移除触发器
                scheduler.unscheduleJob(triggerKey);
                // 删除任务
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            doException(e);
        }
    }

    /**
     * 更新trigger
     */
    public void updateTrigger(Object ruleObject) {
        try {

            //更新规则
            this.ruleObject = ruleObject;

            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJobInfo.getJobName(), scheduleJobInfo.getJobGroup());

            Trigger oldTrigger = this.scheduler.getTrigger(triggerKey);

            if (oldTrigger != null) {
                this.scheduler.resumeTrigger(triggerKey);
            }

            Trigger newTrigger = this.createTrigger();

            this.scheduler.rescheduleJob(triggerKey, newTrigger);

        } catch (SchedulerException e) {
            doException(e);
        }
    }

    /**
     * 构建job详情
     *
     * @return
     */
    protected JobDetail buildJobDetail() throws SchedulerException {

        jobKey = JobKey.jobKey(scheduleJobInfo.getJobName(), scheduleJobInfo.getJobGroup());

        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        try {
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
        }

        jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(scheduleJobInfo.getJobName(), scheduleJobInfo.getJobGroup()).build();
        jobDetail.getJobDataMap().put("scheduleJob", scheduleJobInfo);
        return jobDetail;
    }

    /**
     * 创建cronTrigger是统一的
     *
     * @return
     * @throws SchedulerException
     */
    protected Trigger createCrontabTrigger() throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJobInfo.getJobName(), scheduleJobInfo.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobInfo.getCronExpression());

        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        return trigger;
    }

}
