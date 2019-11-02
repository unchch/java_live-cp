package com.bh.live.pojo.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 发言内容
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/5 20:57
 */
@Data
@ApiModel(value = "发言内容", description = "发言内容")
public class MsgNormalReq  extends BaseMsgReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主播id
     */
    @ApiModelProperty(value = "主播id")
    private Integer hostId;

    /**
     * at 用户名
     */
    @ApiModelProperty(value = "at用户")
    private String atUser;



}
