package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class TradeUserAwardRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "主播用户id")
    private Integer tvUserId;

    @ApiModelProperty(value = "主播用户名称")
    private String tvUserName;

    @ApiModelProperty(value = "礼物id")
    private Integer giftId;

    @ApiModelProperty(value = "礼物名称")
    private String giftName;

    @ApiModelProperty(value = "礼物数目")
    private Integer giftNum;

    @ApiModelProperty(value = "礼物图片")
    private String giftImage;

    @ApiModelProperty(value = "时间")
    private String createTime;
    
    @ApiModelProperty(value = "订单id")
    private String transCode;

}
