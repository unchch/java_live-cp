package com.bh.live.task.component.issue;

import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.component.issue.template.AbstractTemplate;
import com.bh.live.task.component.issue.template.ArgsModifyJobTemplate;
import com.bh.live.task.component.issue.template.ClosingJobTemplate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;

/**
 * @ClassName: IssueTemplateUtil
 * @description: IssueTemplateUtil
 * @author: yq.
 * @date: 2019-8-5 14:03:48
 */
@Slf4j
public class IssueTemplateUtil {
    /**
     * 创建彩期切换与封盘quartz 模板
     */
    public static TemplateConfigDto buildIssueSwitchAndClosingTemplates(Scheduler scheduler, Seed seed, IssueArgument arg) {
        String seedName = seed.getSeedName();

//        //彩期切期模板
//        AbstractTemplate switchIssueTemplate = new SwitchJobTemplate(scheduler, arg);
//        switchIssueTemplate.schedule(new Object[]{seed});
//
//        //彩期封盘模板
//        AbstractTemplate closing0IssueTemplate = new ClosingJobTemplate(scheduler, arg);
//        closing0IssueTemplate.schedule(new Object[]{seed, 0});

        //彩期封盘模板(提前60秒)
        AbstractTemplate closing60IssueTemplate = new ClosingJobTemplate(scheduler, arg);
        closing60IssueTemplate.schedule(new Object[]{seed, arg.getClosingSeconds()});
        log.info("[*]创建Quartz-job【彩期切换、封盘】,Seed:" + seedName + " ,ArgId=" + arg.getId() + " 完成!");

        TemplateConfigDto config = new TemplateConfigDto();
//        config.setSwitchIssueTemplate(switchIssueTemplate);
//        config.setClosing0IssueTemplate(closing0IssueTemplate);
        config.setClosing60IssueTemplate(closing60IssueTemplate);
        config.setArg(arg);

        return config;
    }

    /**
     * 监控彩期配置变化
     */
    public static void buildMonitorArgsChangeTemplate(Scheduler scheduler) {
        AbstractTemplate argsModifyJobTemplate = new ArgsModifyJobTemplate(scheduler, null);
        argsModifyJobTemplate.schedule(null);

        log.info("[*]创建Quartz-job【彩期args变更监控】完成");
    }
}
