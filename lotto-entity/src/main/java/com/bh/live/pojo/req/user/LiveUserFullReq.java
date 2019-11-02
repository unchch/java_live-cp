package com.bh.live.pojo.req.user;

import java.io.Serializable;
import java.util.Date;

import com.bh.live.pojo.req.PageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("直播用户完整信息请求")
public class LiveUserFullReq extends PageParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户id")
	private Integer id;

	@ApiModelProperty("用户类型 0普通 1专家 2主播")
	private Integer userType;

	@ApiModelProperty("用户昵称")
	private String nickname;

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

	@ApiModelProperty("注册终端 0:ios 1：安卓  2：pc")
	private Integer channel;

	@ApiModelProperty("注册时间")
	private Date creatTime;
	
	@ApiModelProperty("注册开始日期")
	private String startTime;
	
	@ApiModelProperty("注册结束日期")
	private String endTime;

	@ApiModelProperty("是否是专家  0否  1是")
	private Integer isExpert;

	@ApiModelProperty("是否是主播  0否 1是")
	private Integer isAnchor;
	
}
