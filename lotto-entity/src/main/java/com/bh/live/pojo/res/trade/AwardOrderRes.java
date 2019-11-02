package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lgs
 * @title: AwardOrderRes
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/8/16  10:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="玩法投注位", description="玩法投注位")
public class AwardOrderRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "流水号")
    private String serialCode;

    @ApiModelProperty(value = "发单着用户ID")
    private Integer accountId;

    @ApiModelProperty(value = "购买者用户ID")
    private Integer userId;

    @ApiModelProperty(value = "订单购买金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "状态  - 1:待支付，2:待开奖，3:未中奖，4:已中奖，5:已派奖，6:已撤单")
    private Integer status;

    @ApiModelProperty(value = "输赢状态 0:默认，1:赢，2:输，3:和")
    private Integer awardState;

}
