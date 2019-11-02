package com.bh.live.model.configuration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 等级分成配置
 * </p>
 *
 * @author Morphon
 * @since 2019-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_grade_divide_into")
@ApiModel(value="GradeDivideInto对象", description="等级分成配置")
public class GradeDivideInto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id,添加不用传")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分成比例")
    private BigDecimal divideInto;

    @ApiModelProperty(value = "最小等级")
    private Integer minLv;

    @ApiModelProperty(value = "最大等级")
    private Integer maxLv;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
