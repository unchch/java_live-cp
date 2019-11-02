package com.bh.live.task.component.issue.processer;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.component.issue.IssueConfig;
import com.bh.live.task.component.issue.IssueTemplateUtil;
import com.bh.live.task.component.issue.LotteryComponent;
import com.bh.live.task.component.issue.TemplateConfigDto;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import com.bh.live.task.component.issue.template.AbstractTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @ClassName ArgModifyProcessor
 * @description: 参数修改处理彩期process
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Component("argModifyProcessor")
public class ArgModifyProcessor extends BaseTaskProcessor {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryComponent lotteryComponent;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @Override
    public void execute(ScheduleJobInfo jobInfo) {
        System.out.println("Monitor-scheduler : " + schedulerFactoryBean.getScheduler());
        List<Seed> seedList = lotteryComponent.getAllSeed();
        if (CommonUtil.isEmpty(seedList)) {
            return;
        }
        /*
         * 1、获取所有彩种 2、获取每个彩种对应的彩期配置列表
         * 3、用配置列表与当前Quartz用到的配置列表进行比对，比对结果（db中的args有增加、减少、内容修改、无变动，4种可能性）
         * 4、针对不同结果做对应的操作
         */
        Map<Integer, List<IssueArgument>> argsMap = lotteryComponent.selectAllIssueArgumentToMap();
        for (Seed seed : seedList) {
            int seedNo = seed.getSeedNo();
            List<IssueArgument> dbArgs = argsMap.get(seedNo);
            Map<Integer, TemplateConfigDto> switchAndClosingTemplatePool = IssueConfig.seedForSwitchAndClosingTemplateMap.get(seedNo);

            if (switchAndClosingTemplatePool == null) {
                switchAndClosingTemplatePool = new ConcurrentHashMap<>(dbArgs.size());
                for (IssueArgument dbArg : dbArgs) {
                    switchAndClosingTemplatePool.put(dbArg.getSeedNo(),
                            IssueTemplateUtil.buildIssueSwitchAndClosingTemplates(schedulerFactoryBean.getScheduler(), seed, dbArg));
                }
                IssueConfig.seedForSwitchAndClosingTemplateMap.put(seed.getId(), switchAndClosingTemplatePool);
                continue;
            }

            // 浅复制一下
            Map<Integer, TemplateConfigDto> lastSwitchAndClosingTemplatePool = new ConcurrentHashMap<>(switchAndClosingTemplatePool.size());
            lastSwitchAndClosingTemplatePool.putAll(switchAndClosingTemplatePool);

            // 【Part 1】数据库中没有彩种的彩期args，拉停 “所有” 的任务template（删除job、trigger）
            if (dbArgs == null || dbArgs.size() <= 0) {

                for (Map.Entry<Integer, TemplateConfigDto> entry : switchAndClosingTemplatePool.entrySet()) {
                    // 停掉某个Arg Template
                    this.stopTemplate(entry.getValue());
                    lastSwitchAndClosingTemplatePool.remove(entry.getKey());
                }

                IssueConfig.seedForSwitchAndClosingTemplateMap.put(seed.getId(), lastSwitchAndClosingTemplatePool);
                continue;
            }
            // 【Part 2】以dbArgs作为参考，挑选出db中有，Memory中没有（新增job、trigger）
            for (IssueArgument dbArg : dbArgs) {
                boolean existMemory = false;
                for (int argId : switchAndClosingTemplatePool.keySet()) {
                    if (dbArg.getId().intValue() == argId) {
                        existMemory = true;
                        break;
                    }
                }

                // 如果不存在Memory中，则增加Template（增加job、trigger）
                if (!existMemory) {
                    // 增加调度
                    lastSwitchAndClosingTemplatePool.put(dbArg.getId(),
                            IssueTemplateUtil.buildIssueSwitchAndClosingTemplates(schedulerFactoryBean.getScheduler(), seed, dbArg));
                }
            }

            // 【Part 3】处理db中的arg与Memory中内容不一致的情况
            for (Map.Entry<Integer, TemplateConfigDto> entry : switchAndClosingTemplatePool.entrySet()) {
                int argId = entry.getKey();
                IssueArgument dbArg = null;
                for (IssueArgument tmpArg : dbArgs) {
                    if (argId == tmpArg.getId()) {
                        dbArg = tmpArg;
                        break;
                    }
                }

                if (dbArg == null) {
                    continue;
                }

                // 比对db与运行的arg差异化并执行对应QuartzTemplate的更新Trigger操作
                // 更新下extInfo
                TemplateConfigDto configDto = this.execComparisonAndUpdate(dbArg, entry.getValue());
                lastSwitchAndClosingTemplatePool.put(argId, configDto);
            }
            IssueConfig.seedForSwitchAndClosingTemplateMap.put(seed.getId(), lastSwitchAndClosingTemplatePool);
        }
    }


    /**
     * 差异化比对 ps：比对内容是否有差异化为： 1、issue_time_interval 2、closing_seconds 3、begin_time
     * 4、end_time
     *
     * @param dbArg  数据库查询arg
     * @param config job运行中的arg
     */
    private TemplateConfigDto execComparisonAndUpdate(IssueArgument dbArg, TemplateConfigDto config) {
        IssueArgument runArg = config.getArg();
        boolean updateSwitch = false;
        boolean updateClosing = false;

        if (dbArg.getIssueTimeInterval().intValue() != runArg.getIssueTimeInterval().intValue()) {
            updateSwitch = true;
            updateClosing = true;
        }

        if (dbArg.getClosingSeconds().intValue() != runArg.getClosingSeconds().intValue()) {
            updateClosing = true;
        }

        if (!DateUtils.isEqual(dbArg.getBeginTime(), runArg.getBeginTime())
                || !DateUtils.isEqual(dbArg.getEndTime(), runArg.getEndTime())) {
            updateSwitch = true;
            updateClosing = true;
        }

        if (updateSwitch) {
            logger.info("[*]#Start# Arg modified , id = {} ( seedNo : {} ) -->> [SwitchIssue Trigger]", dbArg.getId(), dbArg.getSeedNo());

            AbstractTemplate switchIssueTemplate = config.getSwitchIssueTemplate();
            switchIssueTemplate.updateTrigger(dbArg);
            config.setSwitchIssueTemplate(switchIssueTemplate);

            logger.info("[*]#Finished# Arg modified , id = {} ( seedNo : {} ) -->> [SwitchIssue Trigger]", dbArg.getId(), dbArg.getSeedNo());
        }

        if (updateClosing) {
            logger.info("[*]#Start# Arg modified , id = {} ( seedNo : {} ) -->> [Closing0~60 Trigger]", dbArg.getId(), dbArg.getSeedNo());

            AbstractTemplate closing0IssueTemplate = config.getClosing0IssueTemplate();
            closing0IssueTemplate.updateTrigger(dbArg);

            AbstractTemplate closing60IssueTemplate = config.getClosing60IssueTemplate();
            closing60IssueTemplate.updateTrigger(dbArg);

            logger.info("[*]#Finished# Arg modified , id = {} ( seedNo : {} ) -->> [Closing0~60 Trigger]", dbArg.getId(), dbArg.getSeedNo());

            config.setClosing0IssueTemplate(closing0IssueTemplate);
            config.setClosing60IssueTemplate(closing60IssueTemplate);
        }

        config.setArg(dbArg);
        return config;

    }

    /**
     * 调用模板的停止方法（停止job）
     *
     * @param config
     */
    private TemplateConfigDto stopTemplate(TemplateConfigDto config) {
        IssueArgument runArg = config.getArg();
        config.getSwitchIssueTemplate().stop();
        config.getClosing0IssueTemplate().stop();
        config.getClosing60IssueTemplate().stop();
        logger.info("[*]#Finished# 关停所有切期、封盘、关盘job, id = {} ( seedNo : {} )", runArg.getId(), runArg.getSeedNo());
        return config;
    }

}
