package com.bh.live.model.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lgs
 * @title: OrderCount
 * @projectName java_live-cp
 * @description: 订单统计
 * @date 2019/8/1  20:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "竞猜统计对象", description = "竞猜统计对象")
public class OrderCount {

    @ApiModelProperty(value = "发布竞猜总数")
    private Integer orderTotal;

    @ApiModelProperty(value = "发布竞猜总金额")
    private Integer amountTotal;

    @ApiModelProperty(value = "平台收入")
    private Double amount;

    @ApiModelProperty(value = "首购用户数")
    private Integer firstUser;

    @ApiModelProperty(value = "重复用户数")
    private Integer repeatUser;

    @ApiModelProperty(value = "购买总用户数")
    private Integer userTotal;
}
