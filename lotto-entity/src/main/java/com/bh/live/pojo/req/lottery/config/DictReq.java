package com.bh.live.pojo.req.lottery.config;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典信息表
 *
 * @Author: WJ
 * @date: 2019/8/12
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "字典信息", description = "字典信息")
public class DictReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 1434410185023804604L;

    @ApiModelProperty(value = "类型code")
    public Integer id;

    @ApiModelProperty(value = "类型code")
    private Integer typeCode;

    @ApiModelProperty(value = "字典名")
    private String dictName;

    @ApiModelProperty(value = "字典值")
    public String dictValue;

    @ApiModelProperty(value = "是否启用(0：禁用；1：启用)")
    public Integer isDisabled;

    @ApiModelProperty(value = "是否固定值（0:不固定;1:固定;）")
    public Integer fixed;

    @ApiModelProperty(value = "排序)")
    private Integer sort;
}
