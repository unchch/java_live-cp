package com.bh.live.task.component.issue.template;

import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import org.quartz.*;

import java.util.Calendar;

/**
 * @ClassName ClosingJobTemplate
 * @description: ClosingJobTemplate
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
public class ClosingJobTemplate extends AbstractTemplate {

    private String jobGroupName = "issue_closing_job_group";

    private String jobName = "issue_closing_job_%s:%s-%s";

    private String jobSpringId = "issueClosingProcessor";

    private String jobDes = "彩期封盘job";

    public ClosingJobTemplate(Scheduler scheduler, Object ruleObject) {
        super(scheduler, ruleObject);
    }

    @Override
    protected void buildScheduleJobInfo(Object[] extInfo) {
        Seed seed = (Seed) extInfo[0];
        int preSeconds = ((int) extInfo[1]);
        IssueArgument arg = (IssueArgument) ruleObject;

        this.jobName = String.format(jobName, seed.getSeedNo(), arg.getId(), preSeconds);

        scheduleJobInfo.setJobGroup(jobGroupName);
        scheduleJobInfo.setJobName(jobName);
        scheduleJobInfo.setSpringId(jobSpringId);
        scheduleJobInfo.setMethodName(JOB_EXEC_METHOD);
        scheduleJobInfo.setIsConcurrent(ScheduleJobInfo.CONCURRENT_YES);
        scheduleJobInfo.setJobStatus(ScheduleJobInfo.STATUS_RUNNING);
        scheduleJobInfo.setDescription("[" + seed.getSeedName() + "]" + jobDes + ",Pre : " + extInfo[1] + "s");
        scheduleJobInfo.setExtInfo(extInfo);

    }

    @Override
    protected Trigger createTrigger() throws SchedulerException {
        IssueArgument arg = (IssueArgument) ruleObject;

        int preSeconds = (int) scheduleJobInfo.getExtInfo()[1];

        Calendar beginTime = DateUtils.getCalenderByDate(arg.getBeginTime());
        beginTime.add(Calendar.SECOND, 0 - preSeconds - arg.getClosingSeconds());
        int beginH = beginTime.get(Calendar.HOUR_OF_DAY);
        int beginM = beginTime.get(Calendar.MINUTE);
        int beginS = beginTime.get(Calendar.SECOND);

        TimeOfDay beginTimeOfDay = new TimeOfDay(beginH, beginM, beginS);

        DailyTimeIntervalScheduleBuilder scheduleBuilder =
                DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                        .startingDailyAt(beginTimeOfDay)
                        .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)
                        .withIntervalInSeconds(arg.getIssueTimeInterval());


        Calendar endTime = DateUtils.getCalenderByDate(arg.getEndTime());
        endTime.add(Calendar.SECOND, 0 - preSeconds - arg.getClosingSeconds());
        int endH = endTime.get(Calendar.HOUR_OF_DAY);
        int endM = endTime.get(Calendar.MINUTE);
        int endS = endTime.get(Calendar.SECOND);

        // 在同一天的情况
        if (endH >= beginH) {
            TimeOfDay endTimeOfDay = new TimeOfDay(endH, endM, endS);
            scheduleBuilder = scheduleBuilder.endingDailyAt(endTimeOfDay);
        }

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        DailyTimeIntervalTrigger trigger = (DailyTimeIntervalTrigger) scheduler.getTrigger(triggerKey);

        TriggerBuilder<DailyTimeIntervalTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder);

        trigger = triggerBuilder.build();

        return trigger;
    }


    @Override
    protected void doException(Exception e) {

    }

}
