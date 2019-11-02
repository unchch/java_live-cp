package com.bh.live.pojo.res.anchor;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 热门直播间
 * </p>
 *
 * @author ww
 * @since 2019-07-26
 */
@Data
@ApiModel(value = "热门直播间返回前端信息", description = "热门直播间返回前端信息")
public class RecomHostRoomRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "房间id")
	private Integer roomId;

	@ApiModelProperty(value = "主播id")
	private Integer hostId;

	@ApiModelProperty(value = "彩种名称")
	private String lottoName;

	@ApiModelProperty(value = "彩种id")
	private String lottoId;

	
	@ApiModelProperty(value = "房间状态 0关闭中，1直播中 2未开播 3已完成")
	private Integer status;

	@ApiModelProperty(value = "主播昵称")
	private String nickname;

	@ApiModelProperty(value = "人气值")
	private String hotVal;

	@ApiModelProperty(value = "封面")
	private String cover;
	
	@ApiModelProperty(value = "头像")
	private String imageUrl;
	
	@ApiModelProperty(value = "是否关注  0未关注  1已关注")
	private String isAttend;
}
