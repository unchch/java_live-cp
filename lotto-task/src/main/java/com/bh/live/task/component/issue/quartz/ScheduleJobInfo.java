package com.bh.live.task.component.issue.quartz;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ScheduleJobInfo
 * @description: 计划任务信息:保存任务名称、组、执行者
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Data
public class ScheduleJobInfo implements Serializable {

    private static final long serialVersionUID = -999352601526739284L;

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    /**
     * 是否同时执行两个相同任务
     */
    public static final String CONCURRENT_YES = "1";
    public static final String CONCURRENT_NOT = "0";
    private Long jobId;

    private Date createTime;

    private Date updateTime;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务开始执行时间
     */
    private Date beginTime;

    /**
     * 任务结束时间
     */
    private Date endTime;

    /**
     * 间隔时间
     */
    private int repeatSeconds;

    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    /**
     * 任务是否有状态
     */
    private String isConcurrent;
    /**
     * spring bean
     */
    private String springId;
    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 生成新彩期数
     */
    private int issueCount;

    private Object[] extInfo;
    /**
     * 提前多少秒（用于封盘）
     */
    private int bringSeconds;
}