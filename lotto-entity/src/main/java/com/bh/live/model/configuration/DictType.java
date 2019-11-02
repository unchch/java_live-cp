package com.bh.live.model.configuration;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_dict_type")
@ApiModel(value="DictType对象", description="字典类型表")
public class DictType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "dict_type_code", type = IdType.AUTO)
    private Integer dictTypeCode;

    @ApiModelProperty(value = "字典类型")
    private String dictTypeName;

    @ApiModelProperty(value = "(是否启用(0：禁用；1：启用)")
    private int isDisabled;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间)")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人)")
    private String updateBy;
}
