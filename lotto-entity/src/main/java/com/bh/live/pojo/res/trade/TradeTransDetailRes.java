package com.bh.live.pojo.res.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@ApiModel("流水详情对象")
public class TradeTransDetailRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "交易金额/价格")
	private BigDecimal amount;

	@ApiModelProperty(value = "交易类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除")
	private Integer type;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@ApiModelProperty(value = "时间/订单时间")
	private Date dateTime;

	@ApiModelProperty(value = "支出还是收入：1支出；2收入")
	private Integer inOut;

	@ApiModelProperty(value = "创建者/送赠者/购买者")
	private String createBy;

	@ApiModelProperty(value = "目标对象/受赠者/发布者")
	private String targetBy;

	@ApiModelProperty(value = "名称/礼物名")
	private String name;

	@ApiModelProperty(value = "数量/赠送数量")
	private String count="0";

	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "彩种")
	private String lottoSeed;

	@ApiModelProperty(value = "期号")
	private String referIssueNo;

	@ApiModelProperty(value = "竞猜内容")
	private String content;

	@ApiModelProperty(value = "开奖结果")
	private String result;
	
	@ApiModelProperty(value = "原因")
	private String resion;
}
