package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 方案列表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("实时竞猜方案返回前端对象")
public class GuessingRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "盈利率")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "竞猜内容")
    private String content;

    @ApiModelProperty(value = "是否需要付费；0不付费。1付费")
    private Integer isPay;
}
