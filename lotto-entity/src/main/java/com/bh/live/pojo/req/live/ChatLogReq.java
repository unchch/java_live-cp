package com.bh.live.pojo.req.live;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description: 查询聊天记录
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/8 22:21
 */
@Data
@ApiModel(value = "聊天记录", description = "聊天记录")
public class ChatLogReq {

    @ApiModelProperty(value = "直播开始时间")
    private String startTime;

    @ApiModelProperty(value = "直播结束时间")
    private String endTime;

    @ApiModelProperty(value = "房间Id")
    private  Integer roomId;

    @ApiModelProperty(value = "位置")
    private  Integer pageIndex;

    @ApiModelProperty(value = "条数")
    private  Integer pageSize;
}
