package com.bh.live.pojo.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName SeedUpdateReq
 * @description: SeedUpdateReq
 * @author: yq.
 * @date 2019-07-26 10:15:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩种修改req", description = "彩种修改req")
public class SeedUpdateReq implements Serializable {

    private static final long serialVersionUID = -5577659627447414067L;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "开奖方式（0：自动开奖  1：手动开奖）")
    private Integer prizeMode;

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

    @ApiModelProperty(value = "修改人", hidden = true)
    private String updateBy;
}