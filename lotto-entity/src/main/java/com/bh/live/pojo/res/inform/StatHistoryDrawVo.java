package com.bh.live.pojo.res.inform;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StatHistoryDrawVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开奖期数
     */
    private String expect;

    /**
     * 开奖时间
     */
    private String statDate;

    /**
     * 显示号码
     */
    private List<Integer> displayNumber;

    /**
     * 显示大小
     */
    private List<String> displaySize;

    /**
     * 显示单双
     */
    private List<String> displaySingleAndDouble;

    /**
     * 冠亚军和
     */
    private List crownRunner;

    /**
     *  1~5龙虎
     */
    private List<String> dragonTiger;
}
