package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 投注订单表
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "订单内容详情", description = "订单内容详情")
public class OrderInfoContentRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "玩法编号")
    private Integer playNo;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "玩法名称")
    private String guessName;
}
