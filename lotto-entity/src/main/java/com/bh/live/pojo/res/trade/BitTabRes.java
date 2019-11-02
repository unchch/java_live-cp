package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgs
 * @title: PlayTabRes
 * @projectName java_live-cp
 * @description: 玩法投注位
 * @date 2019/8/6  20:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="玩法投注位", description="玩法投注位")
public class BitTabRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "位编号")
    private String bitNo;

    @ApiModelProperty(value = "位名称")
    private String content;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否显示投注位名称（0：不显示 1：显示）")
    private Integer showBitName;

    @ApiModelProperty(value = "竞猜位下竞猜项")
    private List<ItemTabRes> items;
}
