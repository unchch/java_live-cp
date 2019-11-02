package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户流水详情
 * </p>
 *
 * @author ww
 * @since 2019-07-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户流水对象")
public class TradeTransExcelRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "订单编号")
	private String orderCode;

	@ApiModelProperty(value = "交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除")
	private Integer transType;

	@ApiModelProperty(value = "账户总余额,（现金总余额+中奖余额）")
	private BigDecimal totalCashBalance;

	@ApiModelProperty(value = "支出还是收入：1支出；2收入")
	private Integer inOut;

	@ApiModelProperty(value = "支出还是收入：1支出；2收入")
	private String inOutName;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "创建人")
	private String createBy;

	@ApiModelProperty
	public String transTypeName;


	@ApiModelProperty(value = "交易总金额")
	private BigDecimal transAmount;

	@ApiModelProperty(value = "交易总金额")
	private String transAmountStr;

}
