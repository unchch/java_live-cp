package com.bh.live.pojo.req.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgs
 * @title: OrderVO
 * @projectName java_live-cp
 * @description: 订单入库VO
 * @date 2019/7/17  13:56
 */
@Data
@ApiModel(value = "订单", description = "订单对象")
@Accessors(chain = true)
public class OrderReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryCode;

    @ApiModelProperty(value = "订单来源(1:app，2:pc,，3:H5)")
    private Integer clientType;

    @ApiModelProperty(value = "彩期期号")
    private String issue;

    @ApiModelProperty(value = "投注内容")
    private List<OrderDetailReq> content;

    @ApiModelProperty(value = "查看需要支付金额")
    private Double price;

    @ApiModelProperty(value = "订单类别:1,竞猜.2投注",hidden = true)
    private Integer orderType;

    @ApiModelProperty(value = "销售截止时间",hidden = true)
    private Integer saleEndTime;
}
