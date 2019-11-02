package com.bh.live.pojo.res.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LiveRoomSeedRes
 * @description: LiveRoomSeedRes
 * @author: Morphon
 * @date 2019-08-09
 */
@Data
@ApiModel(value = "直播彩种返回前端对象信息", description = "直播彩种返回前端对象信息")
public class LiveRoomSeedRes implements Serializable {

    private static final long serialVersionUID = -4626047106998154494L;
    @ApiModelProperty(value = "主播id")
    private Integer userId;

    @ApiModelProperty(value = "房间号")
    private Integer roomId;

    @ApiModelProperty(value = "直播状态")
    private Integer status;

    @ApiModelProperty(value = "人气值")
    private Integer hotVal;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty("是否被关注 0未关注 1已关注")
    private Integer isAttent;

    @ApiModelProperty(value = "直播时间")
    private String liveTime;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

}
