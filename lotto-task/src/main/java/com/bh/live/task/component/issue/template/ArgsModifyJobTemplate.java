package com.bh.live.task.component.issue.template;

import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * @ClassName ArgsModifyJobTemplate
 * @description: ArgsModifyJobTemplate
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
public class ArgsModifyJobTemplate extends AbstractTemplate {

    private String jobGroupName = "issue_args_modify_monitor_job_group";

    private String jobName = "issue_args_modify_monitor_job";

    private String jobSpringId = "argModifyProcessor";

    private String jobDes = "彩期配置变更监控job";

    public ArgsModifyJobTemplate(Scheduler scheduler, Object ruleObject) {
        super(scheduler, ruleObject);
    }

    @Override
    protected void buildScheduleJobInfo(Object[] extInfo) {
        scheduleJobInfo.setJobGroup(jobGroupName);
        scheduleJobInfo.setJobName(jobName);
        scheduleJobInfo.setSpringId(jobSpringId);
        scheduleJobInfo.setMethodName(JOB_EXEC_METHOD);
        scheduleJobInfo.setCronExpression("0 0/3 * * * ? *");
        scheduleJobInfo.setIsConcurrent(ScheduleJobInfo.CONCURRENT_YES);
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
