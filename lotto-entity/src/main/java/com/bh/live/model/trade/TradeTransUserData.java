package com.bh.live.model.trade;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 流水数据对象
 * </p>
 *
 * @author ww
 * @since 2019-07-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("流水数据对象")
public class TradeTransUserData implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单总数")
	private Long transAll;

	@ApiModelProperty(value = "充值总额")
	private Double rechargeAll;

	@ApiModelProperty(value = "打赏总额")
	private Double giveAll;
	
	@ApiModelProperty(value = "购买竞猜总额")
	private Double buyAll;

}
