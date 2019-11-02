package com.bh.live.pojo.res.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SeedRes
 * @description: SeedRes
 * @author: yq.
 * @date 2019-07-23 18:38:00
 */
@Data
@ApiModel(value = "彩种信息", description = "彩种信息")
public class SeedRes implements Serializable {

    private static final long serialVersionUID = -4626047106998154494L;

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "彩种logo")
    private String seedLogo;

    @ApiModelProperty(value = "banner图")
    private String seedBanner;
}
