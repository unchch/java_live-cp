package com.bh.live.model.user;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户信息(包含统计、帐号等信息)
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
@Data
public class UserStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户实体类
	private LiveUser liveUser;
	// 金额
	private BigDecimal maneyQuantity;
	// 关注人数
	private int attendQuantity;
	// 粉丝数量
	private int fansQuantity;
	// 发布竞猜数量
	private int publishQuantity;
	// 月充值
	private BigDecimal monthRecharge;
	// 月打赏
	private BigDecimal monthReward;
	// 月消费
	private BigDecimal monthExpense;
	// 盈利率
	private float tenEarningsYield;
	// 胜率
	private float tenWinRate;
	// 连赢连输
	private int winningOrLose;
	//连赢连输标识：0连输，1连赢
	private int winLostFlag;
	// 开户帐号
	private String openAccount;
	// 银行名称
	private String bankName;
	// 银行支行
	private String bankNameBranch;
	//帐号所属人
	private String accountBy;
	// 微信号
	private String vxAccount;
	// 支付宝帐号
	private String zfbAccount;
	// 支付宝帐号
	private String wbAccount; 
	//是否可以提款 0不可以 1可用
	private Integer isExtract;
	//是否冻结交易  0否  1是 （所有交易受限.如购买方案、打赏、提款等）
	private Integer isFreeze;

	private Integer isExpert;

	private Integer isAnchor;

}
