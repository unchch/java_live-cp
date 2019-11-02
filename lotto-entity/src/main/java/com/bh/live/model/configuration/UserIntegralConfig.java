package com.bh.live.model.configuration;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户积分规则配置表
 * </p>
 *
 * @author WJ
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_user_integral_config")
@ApiModel(value="积分规则配置", description="积分规则配置")
public class UserIntegralConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String creatBy;

    @ApiModelProperty(value = "修改人")
    private String updateBy;


}
