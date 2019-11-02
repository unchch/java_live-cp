package com.bh.live.pojo.res.trade;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 方案列表
 * </p>
 *
 * @author ww
 * @since 2019-07-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("方案对象")
public class OrderFullRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单id")
	private Integer orderId;

	@ApiModelProperty(value = "专家id")
	private Integer userId;

	@ApiModelProperty(value = "订单编号")
	private String orderNo;

	@ApiModelProperty(value = "专家昵称")
	private String nickname;

	@ApiModelProperty(value = "专家图像")
	private String imageUrl;

	@ApiModelProperty(value = "盈利率")
	private BigDecimal profitrateValue;

	@ApiModelProperty(value = "当前连赢")
	private Integer wingingValue;

	@ApiModelProperty(value = "今日赢率")
	private Float winrateValue;

	@ApiModelProperty(value = "彩种名称")
	private String lotSeedName;

	@ApiModelProperty(value = "彩期")
	private String lotIssueNo;

	@ApiModelProperty(value = "号码")
	private String awardNumber;

	@ApiModelProperty(value = "近期竞猜结果   0输 1平 2赢")
	private String recentResult;

	@ApiModelProperty(value = "发布订单时间")
	private String createTime;

	@ApiModelProperty(value = "是否需要付费 0不付费 1付费")
	private String isPay;

	@ApiModelProperty(value = "竞猜内容")
	private String content;
	
	@ApiModelProperty(value = "支付钱")
	private Double bymoney;

	@ApiModelProperty(value = "是否购买  0未  1已")
	private Integer isBuy=0;

	@ApiModelProperty(value = "是否关注  0未  1已")
	private Integer isAttent=0;

}
