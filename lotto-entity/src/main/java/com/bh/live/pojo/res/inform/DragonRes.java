package com.bh.live.pojo.res.inform;

import lombok.Data;

import java.io.Serializable;

/**
 * 每日长龙统计
 *
 * @author Administrator
 * @title: DragonRes
 * @projectName live
 * @description: TODO
 * @date 2019/6/17  11:02
 */
@Data
public class DragonRes implements Serializable {
    /**
     * 排名
     */
    private String rankName;
    /**
     * 长龙期数
     */
    private String ldPeriod;
    /**
     * 长龙类型
     */
    private String ldType;
    /**
     * 长龙出现次数
     */
    private Integer frequency;
    /**
     * 开奖时间
     */
    private String openDate;
//    /**
//     * 彩种(0-幸运飞艇 1-pk10 2-重时时彩 3-圣地彩 4-江苏快3 5-幸运农场  6-北京快乐8  7-新疆时时彩  8-广东快乐十分  9-广东十一选五  10-天津时时彩  11-香港彩)
//     */
//    private String varietyType;


}
