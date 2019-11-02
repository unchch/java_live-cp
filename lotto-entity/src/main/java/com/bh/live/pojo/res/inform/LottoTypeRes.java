package com.bh.live.pojo.res.inform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SeedRes
 * @description: SeedRes
 * @author: yq.
 * @date 2019-07-23 18:38:00
 */
@Data
@ApiModel(value = "彩种类型", description = "彩种类型")
public class LottoTypeRes implements Serializable {

    private static final long serialVersionUID = -4626047106998154694L;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "是否直播中")
    private Integer status;

    @ApiModelProperty(value = "等级")
    private int host_lv;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "名称")
    private String nickname;

    @ApiModelProperty(value = "在线人数")
    private int linePersons;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "开播时间")
    private Date openTime;
}
