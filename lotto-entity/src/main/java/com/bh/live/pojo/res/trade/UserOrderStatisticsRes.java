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
 * 用户方案信息统计
 * </p>
 *
 * @author ww
 * @since 2019-07-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户方案信息统计")
public class UserOrderStatisticsRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@ApiModelProperty(value = "用户当前连赢")
	private Integer UserWinning;

	@ApiModelProperty(value = "今日赢率")
	private Float todayWinrateJson;

	@ApiModelProperty(value = "今日盈利率")
	private BigDecimal todayProfitrateJson;

	@ApiModelProperty(value = "今日赢输平次数,';'隔开")
	private String todayNumJson;
	
	@ApiModelProperty(value = "昨日赢率   ")
	private Float yesterdayWinrateJson;

	@ApiModelProperty(value = "昨日盈利率")
	private BigDecimal yesterdayProfitrateJson;
	
	@ApiModelProperty(value = "昨日赢输平次数,';'隔开")
	private String yesterdayNumJson;
	
	@ApiModelProperty(value = "10期赢率   ")
	private Float tenWinrateJson;

	@ApiModelProperty(value = "10期盈利率")
	private BigDecimal tenProfitrateJson;

	@ApiModelProperty(value = "20期赢率   ")
	private Float twentyWinrateJson;

	@ApiModelProperty(value = "20期盈利率")
	private BigDecimal twentyProfitrateJson;

	@ApiModelProperty(value = "30期赢率   ")
	private Float thirtyWinrateJson;

	@ApiModelProperty(value = "30期盈利率")
	private BigDecimal thirtyProfitrateJson;

	@ApiModelProperty(value = "50期赢率   ")
	private Float fiftyWinrateJson;

	@ApiModelProperty(value = "50期盈利率")
	private BigDecimal fiftyProfitrateJson;
}
