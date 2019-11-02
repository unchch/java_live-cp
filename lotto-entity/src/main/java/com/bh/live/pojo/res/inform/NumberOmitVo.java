package com.bh.live.pojo.res.inform;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 号码遗漏
 *
 * @author mayn
 */
@Getter
@Setter
@ToString
public class NumberOmitVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 比对位置
     */
    private String total;


    /**
     * 当天出现次数
     */
    private Integer appearToday;

    /**
     * 当前遗漏次数
     */
    private Integer currentOmissions;


    /**
     * 当天遗漏次数
     */
    private Integer todayOmission;

    /**
     * 一周遗漏次数最大值
     */
    private Integer weekOmission;

    /**
     * 一月遗漏次数
     */
    private Integer missingThisMonth;

    /**
     * 历史遗漏次数最大值
     */
    private Integer historicalOmission;


}
