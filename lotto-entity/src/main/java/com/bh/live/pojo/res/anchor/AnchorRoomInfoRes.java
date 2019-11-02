package com.bh.live.pojo.res.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 直播间主播信息返回
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-29
 */
@Data
@ApiModel(value = "直播间关注主播", description = "直播间关注主播")
public class AnchorRoomInfoRes  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("主播名称")
    private String username;

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("头像")
    private String imageUrl;

    @ApiModelProperty("关注人数")
    private String attentCount;

    @ApiModelProperty("主播状态 0离线 1在线 2直播中")
    private Integer status;

    @ApiModelProperty("是否关注 0：未关注 1：关注")
    private Integer isAttent;

}
