package com.bh.live.pojo.res.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SeedUpdateRes
 * @description: 彩种修改res
 * @author: yq.
 * @date 2019-07-26 10:12:00
 */
@Data
@ApiModel(value = "彩种修改res", description = "彩种修改res")
public class SeedUpdateRes implements Serializable {

    private static final long serialVersionUID = 7318080947105767752L;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种分类名称")
    private String categoryName;

    @ApiModelProperty(value = "开奖方式（0：自动开奖  1：手动开奖）")
    private Integer prizeMode;

    @ApiModelProperty(value = "销售状态（0：待开售  1：销售中 2：封盘中 3：已截止 4：已停售）")
    private Integer saleState;

    @ApiModelProperty(value = "封面PC")
    private String coverPcIcon;

    @ApiModelProperty(value = "封面APP")
    private String coverAppIcon;

    @ApiModelProperty(value = "logo pc")
    private String logoPcIcon;

    @ApiModelProperty(value = "logo app")
    private String logoAppIcon;

    @ApiModelProperty(value = "彩种图")
    private String imageIcon;

    @ApiModelProperty(value = "备注")
    private String remark;
}
