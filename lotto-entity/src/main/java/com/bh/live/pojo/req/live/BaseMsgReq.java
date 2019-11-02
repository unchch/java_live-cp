package com.bh.live.pojo.req.live;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/7 14:16
 */
@Data
public class BaseMsgReq {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "消息类型：msg_normal(普通消息),msg_system(系统消息),gift_reward(礼物指令)")
    private String event;

    @ApiModelProperty(value = "房间")
    private String room;

    @ApiModelProperty(value = "发言内容：文字和图片")
    private String content;

    @ApiModelProperty(value = "消息类型")
    private String type;

    @ApiModelProperty(value = "直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "直播结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
