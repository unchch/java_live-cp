package com.bh.live.pojo.res.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息(包含统计、帐号等信息)返回")
public class UserStatisticsRes implements Serializable {

	private static final long serialVersionUID = 1L;

//	@ApiModelProperty("用户实体类")
//	private LiveUser liveUser;

    @ApiModelProperty("用户id")
    private Integer userId;
	
	@ApiModelProperty("金额")
	private BigDecimal maneyQuantity;

	@ApiModelProperty("关注人数")
	private int attendQuantity;

	@ApiModelProperty("粉丝数量")
	private int fansQuantity;

	@ApiModelProperty("发布竞猜数量")
	private int publishQuantity;

	@ApiModelProperty("月充值")
	private BigDecimal monthRecharge;

	@ApiModelProperty("月打赏")
	private BigDecimal monthReward;

	@ApiModelProperty("月消费")
	private BigDecimal monthExpense;

	@ApiModelProperty("盈利率")
	private float tenEarningsYield;

	@ApiModelProperty("胜率")
	private float tenWinRate;

	@ApiModelProperty("连赢连输")
	private int winningOrLose;

	@ApiModelProperty("连赢连输标识：0连输，1连赢")
	private int winLostFlag;

	@ApiModelProperty("开户帐号")
	private String openAccount;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty("银行支行")
	private String bankNameBranch;

	@ApiModelProperty("帐号所属人")
	private String accountBy;

	@ApiModelProperty("微信号")
	private String vxAccount;

	@ApiModelProperty("支付宝帐号")
	private String zfbAccount;

	@ApiModelProperty("微博宝帐号")
	private String wbAccount;
	
	@ApiModelProperty("用户类型 0普通 1专家 2主播")
    private Integer userType;

	@ApiModelProperty("用户昵称")
    private String nickname;

	@ApiModelProperty("用户图像")
    private String imageUrl;

	@ApiModelProperty("手机号")
    private String mobile;

	@ApiModelProperty("性别  0女 1男")
    private Integer sex;

	@ApiModelProperty("用户等级")
    private Integer userGrade;

	@ApiModelProperty("登录终端")
    private String terminal;

	@ApiModelProperty("是否可用 0不可 1可用")
    private Integer isUsable;

	@ApiModelProperty("是否在线  0不再  1在线")
    private Integer isOnline;

	@ApiModelProperty("是否可以登录  0不可  1可以")
    private Integer isLogin;
	
	@ApiModelProperty("是否可以发布竞猜  0不可  1可以")
    private Integer isPublish;

	@ApiModelProperty("是否可以直播间发言  0不可  1可以")
    private Integer isSpeak;

	@ApiModelProperty("最后登录时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastlogin;

	@ApiModelProperty("最后登录ip")
    private String lastip;

	@ApiModelProperty("更新时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

	@ApiModelProperty("注册终端")
    private Integer channel;

	@ApiModelProperty("注册时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date creatTime;
	
	@ApiModelProperty("是否上榜 0未  1上")
    private Integer isOnlist;
	
	@ApiModelProperty("是否可以提款 0不可以 1可用")
	private Integer isExtract;
	
	@ApiModelProperty("是否冻结交易  0否  1是 （所有交易受限.如购买方案、打赏、提款等）")
	private Integer isFreeze;

	@ApiModelProperty("是否是专家  0否  1是")
	private Integer isExpert;

	@ApiModelProperty("是否是主播  0否 1是")
	private Integer isAnchor;
	
}
