package com.bh.live.pojo.res.lottery.config;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户积分规则配置
 *
 * @Author: WJ
 * @date: 2019/8/13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "积分规则", description = "用户积分规则")
public class UserIntegralConfigRes implements Serializable {

    private static final long serialVersionUID = -912060374331703415L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "字典信息code")
    private Integer dictCode;

    @ApiModelProperty(value = "数值")
    private Integer value;

    @ApiModelProperty(value = "数值类型")
    private Integer valueType;

    @ApiModelProperty(value = "周期类型(1：每天；2：每周；3：每月；4：累计次数)")
    private Integer periodType;

    @ApiModelProperty(value = "增加经验值")
    private Integer addExp;

    @ApiModelProperty(value = "经验值峰值")
    private Integer addExpMax;

    @ApiModelProperty(value = "规则描述")
    private String configDescribe;

}
