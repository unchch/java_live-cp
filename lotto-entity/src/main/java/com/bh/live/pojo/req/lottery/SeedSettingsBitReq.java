package com.bh.live.pojo.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName SeedSettingsBitReq
 * @description: 二级玩法
 * @author: yq.
 * @date 2019-07-25 20:17:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "二级玩法", description = "二级玩法")
public class SeedSettingsBitReq implements Serializable {

    private static final long serialVersionUID = 7552585866468861531L;

    @ApiModelProperty(value = "位编号")
    private String bitNo;

    @ApiModelProperty(value = "位名称")
    private String content;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：停用 1：启用）")
    private Integer status;
}
