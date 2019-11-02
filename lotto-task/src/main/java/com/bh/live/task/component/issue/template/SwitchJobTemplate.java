package com.bh.live.task.component.issue.template;

import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Calendar;

/**
 * @ClassName SwitchJobTemplate
 * @description: SwitchJobTemplate
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Slf4j
public class SwitchJobTemplate extends AbstractTemplate {

    private String jobGroupName = "issue_switch_job_group";

    private String jobName = "issue_switch_job_%s:%s";

    private String jobSpringId = "issueSwitchProcessor";

    private String jobDes = "彩期切换job";

    public SwitchJobTemplate(Scheduler scheduler, Object ruleObject) {
        super(scheduler, ruleObject);
    }

    @Override
    protected void buildScheduleJobInfo(Object[] extInfo) {
        Seed seed = (Seed) extInfo[0];

        if (ruleObject == null) {
            logger.error("彩种：{}，配置信息为空，请检查!", seed.getSeedName());
            return;
        }
        IssueArgument arg = (IssueArgument) ruleObject;

        this.jobName = String.format(this.jobName, seed.getSeedNo(), arg.getId());

        scheduleJobInfo.setJobGroup(jobGroupName);
        scheduleJobInfo.setJobName(jobName);
        scheduleJobInfo.setSpringId(jobSpringId);
        scheduleJobInfo.setMethodName(JOB_EXEC_METHOD);
        scheduleJobInfo.setIsConcurrent(ScheduleJobInfo.CONCURRENT_YES);
        scheduleJobInfo.setJobStatus(ScheduleJobInfo.STATUS_RUNNING);
        scheduleJobInfo.setDescription(jobDes);
        scheduleJobInfo.setExtInfo(extInfo);
    }


    @Override
    protected Trigger createTrigger() throws SchedulerException {

        IssueArgument arg = (IssueArgument) ruleObject;

        Calendar beginTime = DateUtils.getCalenderByDate(arg.getBeginTime());
        int beginH = beginTime.get(Calendar.HOUR_OF_DAY);
        int beginM = beginTime.get(Calendar.MINUTE);
        int beginS = beginTime.get(Calendar.SECOND);

        //彩期间隔
        int targetIntervalSeconds = arg.getIssueTimeInterval();

        TimeOfDay beginTimeOfDay = new TimeOfDay(beginH, beginM, beginS);


        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        DailyTimeIntervalTrigger trigger = (DailyTimeIntervalTrigger) scheduler.getTrigger(triggerKey);

        DailyTimeIntervalScheduleBuilder scheduleBuilder =
                DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                        .startingDailyAt(beginTimeOfDay)
                        .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)
                        .withIntervalInSeconds(targetIntervalSeconds);

        if (LotteryConstants.ISSUE_1440.compareTo(arg.getIssueCount()) != 0) {
            //对于（全天型1440期=1分钟/期）的彩种不配置endTime
            Calendar endTime = DateUtils.getCalenderByDate(arg.getEndTime());
            int endH = endTime.get(Calendar.HOUR_OF_DAY);
            int endM = endTime.get(Calendar.MINUTE);
            int endS = endTime.get(Calendar.SECOND);
            TimeOfDay endTimeOfDay = new TimeOfDay(endH, endM, endS);
            System.out.println("彩期job template，结束时间,H" + endH + ",M:" + endM + ",endS:" + endS);
            scheduleBuilder = scheduleBuilder.endingDailyAt(endTimeOfDay);
        }

        TriggerBuilder<DailyTimeIntervalTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(scheduleBuilder);

        trigger = triggerBuilder.build();

        return trigger;
    }

    @Override
    protected void doException(Exception e) {

    }
}
