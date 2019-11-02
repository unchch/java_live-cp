package com.bh.live.task.component.issue.cron;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 表达式quartz 模板组件
 * @author Y.
 */
@Component
public class CronExpTemplateComponent {

    private static Logger logger = LoggerFactory.getLogger(CronExpTemplateComponent.class);

    /**
     * 初始化表达式cron job template 集合
     *
     * @param scheduler
     */
    public void initJobs(Scheduler scheduler) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //获取所有匹配的文件
        try {
            Resource[] resources = resolver.getResources("/cron_template/*.properties");
            for (Resource resource : resources) {
                InputStream stream = resource.getInputStream();
                Properties prop = new Properties();
                prop.load(stream);
                String jobGroup = prop.getProperty("job.group");
                String jobName = prop.getProperty("job.name");
                String jobSpringId = prop.getProperty("job.springId");
                String jobDes = prop.getProperty("job.jobDes");
                String cronExp = prop.getProperty("job.cronExp");
                String enable = prop.getProperty("job.enable");
                if (enable.equals("true")) {
                    CronExpTemplate template = new CronExpTemplate(jobGroup, jobName, jobSpringId, jobDes, cronExp, scheduler, null);
                    template.schedule(null);
                    logger.info("调度：{}，加入调度中心成功！", jobDes);
                }
            }
        } catch (IOException e) {
            logger.error("{}", e);
        }
    }
}
