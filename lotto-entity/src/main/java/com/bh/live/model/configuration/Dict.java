package com.bh.live.model.configuration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典信息表
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_dict")
@ApiModel(value="Dict对象", description="字典信息表")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type = IdType.AUTO)
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

    @ApiModelProperty(value = "父级code(三级联动以上)")
    private Integer parentId;

    @ApiModelProperty(value = "创建人")
    private Date createTime;

    @ApiModelProperty(value = "创建时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "排序)")
    private Integer sort;
}
