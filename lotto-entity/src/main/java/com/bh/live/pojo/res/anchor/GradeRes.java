package com.bh.live.pojo.res.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户、主播等级表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@Data
@ApiModel(value = "等级信息", description = "等级信息")
public class GradeRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer id;

    @ApiModelProperty(value = "等级名称")
    private String lvName;

    @ApiModelProperty(value = "等级经验值")
    private String lvValue;

    @ApiModelProperty(value = "等级图标")
    private String lvImage;


}
