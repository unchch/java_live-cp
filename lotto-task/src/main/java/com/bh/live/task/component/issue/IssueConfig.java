package com.bh.live.task.component.issue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName IssueConfig
 * @description: IssueConfig
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
public class IssueConfig {

    /**
     * 彩期对应的切期、封盘、关盘 template
     */
    public static Map<Integer, Map<Integer, TemplateConfigDto>> seedForSwitchAndClosingTemplateMap = new ConcurrentHashMap<Integer, Map<Integer, TemplateConfigDto>>();

}
