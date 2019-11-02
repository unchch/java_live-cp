package com.bh.live.pojo.res.lottery.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * 字典信息
 * @Author: WJ
 * @date: 2019/8/12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "字典信息", description = "字典信息")
public class DictRes implements Serializable {
    private static final long serialVersionUID = -2130226011569217647L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "类型code")
    private Integer typeCode;

    @ApiModelProperty(value = "字典名")
    private String dictName;

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "是否启用(0：禁用；1：启用)")
    private Integer isDisabled;

    @ApiModelProperty(value = "是否固定值（0:不固定;1:固定;）")
    private Integer fixed;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty(value = "排序)")
    private Integer sort;
}
