package com.bh.live.pojo.req.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 房间发言要求配置表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@ApiModel("房间发言要求配置前端入参对象")
public class NoTalkConfReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id,修改时传参")
    private Integer confId;

    @ApiModelProperty("最低注册时间")
    private Integer lowDays;

    @ApiModelProperty("最低用户等级")
    private Integer lowUserLev;

    @ApiModelProperty("全局禁言 0关闭 1开启")
    private Integer globle;

    @ApiModelProperty("非粉丝用户禁言 0关闭 1开启")
    private Integer notFans;

    @ApiModelProperty("发言时间（时长）限制")
    private Integer cdTalk;

    @ApiModelProperty("房间id")
    private Integer roomId;

}
