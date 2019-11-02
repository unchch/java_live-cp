package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户、主播等级表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("uc_grade_conf")
@ApiModel(value = "GradeConf对象", description = "用户、主播等级表")
public class GradeConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "等级id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "等级类型  0用户  1主播")
    private Integer gradeType;

    @ApiModelProperty(value = "等级名称")
    private String gradeName;

    @ApiModelProperty(value = "等级经验范围最小值")
    private Integer integralMin;

    @ApiModelProperty(value = "等级经验范围最大值")
    private Integer integralMax;

    @ApiModelProperty(value = "等级图标")
    private String icon;

    @ApiModelProperty(value = "是否可用 0不可 1可用")
    private Integer status;


}
