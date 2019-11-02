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
@ApiModel("直播用户完整信息返回")
public class LiveUserFullRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户id")
	private Integer id;

//	@ApiModelProperty("用户类型 0普通 1专家 2主播")
//	private Integer userType;

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

	@ApiModelProperty("登录终端  0:ios  1：安卓  2：pc")
	private Integer terminal;

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

	@ApiModelProperty("总金额")
	private BigDecimal allMoney;
	
	@ApiModelProperty("是否是专家  0否  1是")
	private Integer isExpert;

	@ApiModelProperty("是否是主播  0否 1是")
	private Integer isAnchor;
	
}
