package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 直播间主播信息返回
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-08-05
 */
@Data
@ApiModel(value = "主播直播信息", description = "主播直播信息")
public class LiveInfoRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户Id")
    private Integer hostId;

    @ApiModelProperty("直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("直播结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "赢次")
    private Integer win;

    @ApiModelProperty(value = "输次")
    private Integer lose;

    @ApiModelProperty(value = "胜率")
    private BigDecimal winRate;

    @ApiModelProperty(value = "盈亏")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "盈利率")
    private BigDecimal profitRate;


}
