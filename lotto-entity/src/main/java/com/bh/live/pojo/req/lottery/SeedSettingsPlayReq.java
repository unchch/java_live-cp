package com.bh.live.pojo.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SeedSettingsPlayReq
 * @description: 玩法
 * @author: yq.
 * @date 2019-07-25 20:18:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "玩法", description = "玩法")
public class SeedSettingsPlayReq implements Serializable {

    private static final long serialVersionUID = 7552585866468861531L;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "玩法名称")
    private String playName;

    @ApiModelProperty(value = "状态（0：停用 1：启用）")
    private Boolean status;

    @ApiModelProperty(value = "前端参数")
    private Integer radios = 0;

    @ApiModelProperty(value = "二级玩法（开启）")
    private List<SeedSettingsPlayReq> subPlayNos;
}
