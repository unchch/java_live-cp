package com.bh.live.pojo.req.trade;

import com.bh.live.pojo.req.live.BaseMsgReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户打赏记录
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Data
@ApiModel(value = "用户打赏记录", description = "用户打赏记录")
public class TradeUserAwardReq  extends BaseMsgReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播用户id")
    private Integer tvUserId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "主播名称")
    private String  tvUserName;

    @ApiModelProperty(value = "礼物id")
    private Integer giftId;

    @ApiModelProperty(value = "彩种id")
    private Integer lottoId;

    @ApiModelProperty(value = "礼物数目")
    private Integer giftNum;

    @ApiModelProperty(value = "房间id")
    private Integer roomId;

    @ApiModelProperty(value = "礼物价格")
    private BigDecimal giftAmount;

    @ApiModelProperty(value = "直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "直播结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "渠道id")
    private Integer channelId;

}
