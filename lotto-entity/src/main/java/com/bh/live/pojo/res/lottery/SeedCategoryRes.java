package com.bh.live.pojo.res.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SeedCategoryRes
 * @description: SeedCategoryRes
 * @author: yq.
 * @date 2019-07-23 17:57:00
 */
@Data
@ApiModel(value = "彩种分类及彩种信息树", description = "彩种分类及彩种信息树")
public class SeedCategoryRes implements Serializable {

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种分类名称")
    private String categoryName;

    @ApiModelProperty(value = "彩种集合")
    private List<SeedRes> seeds;

    @ApiModelProperty(value = "彩种分类小图标")
    private String iconSmallUrl;

    @ApiModelProperty(value = "彩种分类大图标")
    private String iconLargeUrl;

    @ApiModelProperty(value = "分类类型")
    private Integer categoryType;

    @ApiModelProperty(value = "分类模式")
    private Integer categoryMode;
}


