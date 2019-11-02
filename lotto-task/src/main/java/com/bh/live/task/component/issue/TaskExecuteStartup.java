package com.bh.live.task.component.issue;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.component.issue.cron.CronExpTemplateComponent;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TaskExecuteStartup
 * @description: TaskExecuteStartup
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Component
public class TaskExecuteStartup {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryComponent lotteryComponent;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private CronExpTemplateComponent cronExpTemplateComponent;

    private Scheduler scheduler;

    @PostConstruct
    public void execute() {

        this.scheduler = schedulerFactoryBean.getScheduler();

        try {
            if (!this.scheduler.isShutdown()) {
                logger.info("关闭所有任务.");
                this.scheduler = schedulerFactoryBean.getScheduler();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        List<Seed> seedList = lotteryComponent.getAllSeed();
        if (CommonUtil.isEmpty(seedList)) {
            logger.error("没有彩种信息.");
            return;
        }

        //2.构建彩种切期、封盘处理quartz模板
        //用于后续动态监控arg变动（add、delete、规则内容modify）
        this.scheduleIssueSwitchAndClosing(seedList);

        //3.构建{监控彩期配置变动}quartz模板
        this.scheduleMonitorArgsChange();

        //4.构建job状态监控quartz 模板
//		this.scheduleJobStatusMonitor();

        cronExpTemplateComponent.initJobs(scheduler);
    }

    /**
     * 创建彩期切换与封盘quartz 模板
     */
    private void scheduleIssueSwitchAndClosing(List<Seed> seedList) {
        Map<Integer, List<IssueArgument>> argsMap = lotteryComponent.selectAllIssueArgumentToMap();

        for (Seed seed : seedList) {
            // 获取seed的args
            List<IssueArgument> tmpArgs = argsMap.get(seed.getSeedNo());
            if (CommonUtil.isEmpty(tmpArgs)) {
                continue;
            }

            Map<Integer, TemplateConfigDto> switchAndClosingTemplatePool = new ConcurrentHashMap<Integer, TemplateConfigDto>(tmpArgs.size());

            for (IssueArgument arg : tmpArgs) {
                TemplateConfigDto config = IssueTemplateUtil.buildIssueSwitchAndClosingTemplates(scheduler, seed, arg);
                switchAndClosingTemplatePool.put(arg.getSeedNo(), config);
            }
            IssueConfig.seedForSwitchAndClosingTemplateMap.put(seed.getSeedNo(), switchAndClosingTemplatePool);
        }
    }

    /**
     * 监控彩期配置变化
     */
    private void scheduleMonitorArgsChange() {
        IssueTemplateUtil.buildMonitorArgsChangeTemplate(scheduler);
    }

//	/**
//	 * 监控所有正在执行的job
//	 */
//	private void scheduleJobStatusMonitor() {
//		IssueTemplateUtil.buildStatusMonitorTemplate(scheduler,redisDistributedLock);
//	}
}

