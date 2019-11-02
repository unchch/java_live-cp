package com.bh.live.task.component.issue.quartz;

import com.bh.live.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName TaskUtils
 * @description: TaskUtils
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
public class TaskUtils {

    public final static Logger LOGGER = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokeMethod(ScheduleJobInfo scheduleJob) {
        Object object = null;
        Class clazz = null;
        if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
            object = SpringUtils.getBean(scheduleJob.getSpringId());
        } else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (object == null) {
            LOGGER.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            if (clazz != Object.class) {
                clazz = clazz.getSuperclass();
            }
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName(), ScheduleJobInfo.class);
        } catch (NoSuchMethodException e) {
            LOGGER.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误（" + scheduleJob.getMethodName() + "）！！！");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object, scheduleJob);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
