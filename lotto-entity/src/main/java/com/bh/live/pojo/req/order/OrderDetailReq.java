package com.bh.live.pojo.req.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lgs
 * @title: OrderDetail
 * @projectName java_live-cp
 * @description: 订单明细
 * @date 2019/7/17  14:08
 */
@Data
@ApiModel(value = "订单明细", description = "订单明细")
@Accessors(chain = true)
public class OrderDetailReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "投注内容")
    private String content;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "投注位编号")
    private String bitNo;

    @ApiModelProperty(value = "投注项编号")
    private String itemNo;

    @ApiModelProperty(value = "投注位置", hidden = true)
    private String index;

    @ApiModelProperty(value = "投注注数", hidden = true)
    private int betNum;

    @ApiModelProperty(value = "赔率", hidden = true)
    private BigDecimal odds;

    @ApiModelProperty(value = "玩法名称", hidden = true)
    private String playName;
}
