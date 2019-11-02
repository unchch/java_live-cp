package com.bh.live.pojo.req.inform;

import lombok.Data;

/**
 * @author Administrator
 * @title: SpecialFormDto
 * @projectName live
 * @description: TODO
 * @date 2019/6/21  15:32
 */
@Data
public class SpecialFormDto {
    /**
     * 期数
     */
    private Integer expect;
    /**
     * ab形态大小次数
     */
    private Integer abBigSmallCount;
    /**
     * ab形态单双次数
     */
    private Integer abSingleDoubleCount;
    /**
     * ab形态龙虎次数
     */
    private Integer abDragonTigerCount;

    /**
     * abb形态大小次数
     */
    private Integer abbBigSmallCount;
    /**
     * abb形态单双次数
     */
    private Integer abbSingleDoubleCount;
    /**
     * abb形态龙虎次数
     */
    private Integer abbdragonTigerCount;

    /**
     * aabb形态大小次数
     */
    private Integer aabbBigSmallCount;
    /**
     * aabb形态单双次数
     */
    private Integer aabbSingleDoubleCount;
    /**
     * aabb形态龙虎次数
     */
    private Integer aabbdragonTigerCount;
}
