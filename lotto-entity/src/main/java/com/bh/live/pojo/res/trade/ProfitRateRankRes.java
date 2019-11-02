package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lgs
 * @title: ProfitRateRankRes
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/8/10  14:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="直播间排行榜结果", description="直播间排行榜结果")
public class ProfitRateRankRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer i;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "盈利率")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "用户头像")
    private String imageUrl;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "是否有竞猜：0没有，1已发")
    private Integer push;
}
