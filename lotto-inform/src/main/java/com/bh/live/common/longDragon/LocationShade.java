package com.bh.live.common.longDragon;

import lombok.Data;

@Data
public class LocationShade {

    /**
     * 位置： ，例如:第一名（冠军）、第二名（亚军）、第三名。。。。
     */
    private int locationNum;

    /**
     * 形态名称：大、小、单双、龙虎
     */
    private String shadeName1;

    /**
     * 连续次数
     */
    private int continueTimes1;


    /**
     * 形态名称：大、小、单双、龙虎
     */
    private String shadeName2;

    /**
     * 连续次数
     */
    private int continueTimes2;

}
