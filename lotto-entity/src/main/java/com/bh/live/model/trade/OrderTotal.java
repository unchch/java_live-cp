package com.bh.live.model.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lgs
 * @title: OrderTotal
 * @projectName java_live-cp
 * @description: 统计发单个数
 * @date 2019/8/1  20:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "彩种统计对象", description = "彩种统计对象")
public class OrderTotal {

    @ApiModelProperty(value = "彩种编号")
    private String lotSeedNo;

    @ApiModelProperty(value = "彩种名称")
    private String lotSeedName;

    @ApiModelProperty(value = "发布数量")
    private Integer total;

    @ApiModelProperty(value = "彩种发布竞猜金额")
    private Integer payAmount;
}
