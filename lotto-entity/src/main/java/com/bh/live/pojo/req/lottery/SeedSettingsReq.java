package com.bh.live.pojo.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SeedSettingsReq
 * @description: SeedSettingsReq
 * @author: yq.
 * @date 2019-07-25 16:05:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩种玩法配置参数", description = "彩种玩法配置参数")
public class SeedSettingsReq implements Serializable {

    private static final long serialVersionUID = -2895750252763193136L;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "默认玩法")
    private String playNo;

    @ApiModelProperty(value = "玩法模式（1：官方  2：信用）")
    private Integer playMode;

    @ApiModelProperty(value = "二级玩法集合")
    private List<SeedSettingsPlayReq> plays;
}
