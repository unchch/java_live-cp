package com.bh.live.pojo.res.lottery;

import com.bh.live.model.lottery.PlayBet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PlayOddsRes
 * @description: 玩法赔率res
 * @author: yq.
 * @date 2019-07-26 15:40:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "玩法及赔率", description = "玩法及赔率")
public class PlayOddsRes implements Serializable {
    private static final long serialVersionUID = -5242647467604936718L;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "玩法名称")
    private String playName;

    @ApiModelProperty(value = "状态（0：停用 1：启用）")
    private Integer status;

    @ApiModelProperty(value = "二级玩法")
    private List<PlayBet> subOddsRes;
}
