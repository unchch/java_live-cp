package com.bh.live.pojo.res.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SeedPageRes
 * @description: SeedPageRes
 * @author: yq.
 * @date 2019-07-25 10:44:00
 */
@Data
@ApiModel(value = "彩种列表", description = "彩种列表")
public class SeedPageRes implements Serializable {

    private static final long serialVersionUID = -5937429377738608692L;

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "封面PC")
    private String coverPcIcon;

    @ApiModelProperty(value = "logo pc")
    private String logoPcIcon;

    @ApiModelProperty(value = "是否显示（0：隐藏 1：显示，2：隐藏官方玩法，3：隐藏信用玩法）")
    private Integer showAble;

    @ApiModelProperty(value = "是否推荐首页（0：不推荐 1：推荐）")
    private Integer showHomePage;

    @ApiModelProperty(value = "销售状态（0：待开售  1：销售中 2：封盘中 3：已截止 4：已停售）")
    private Integer saleState;

    @ApiModelProperty(value = "推荐玩法模式（1：官方 2：信用）")
    private Integer playModeRecommend;

    @ApiModelProperty(value = "推荐官方玩法")
    private String officialPlayRecommend;

    @ApiModelProperty(value = "推荐官方玩法编号")
    private String officialPlayNo;

    @ApiModelProperty(value = "推荐信用玩法")
    private String creditPlayRecommend;

    @ApiModelProperty(value = "推荐信用玩法编号")
    private String creditPlayNo;
}
