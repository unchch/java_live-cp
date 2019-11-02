package com.bh.live.pojo.req.live;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/7 14:16
 */
@Data
public class BaseMsg{

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "消息类型：msg_normal(普通消息),msg_system(系统消息),gift_reward(礼物指令)")
    private String event;

    @ApiModelProperty(value = "房间")
    private String room;

    @ApiModelProperty(value = "发言内容：文字和图片")
    private String content;

}
