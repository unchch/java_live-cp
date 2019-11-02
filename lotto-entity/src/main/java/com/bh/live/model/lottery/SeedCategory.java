package com.bh.live.model.lottery;

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
 * 彩种分类
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_seed_category")
@ApiModel(value = "SeedCategory对象", description = "彩种分类")
public class SeedCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "彩种分类小图标")
    private String iconSmallUrl;

    @ApiModelProperty(value = "彩种分类大图标")
    private String iconLargeUrl;

    @ApiModelProperty(value = "彩种分类排序")
    private Integer sort;

    @ApiModelProperty(value = "彩种分类描述")
    private String categoryDesc;


}
