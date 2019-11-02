package com.bh.live.pojo.res.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 直播彩种
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@Data
@ApiModel(value = "直播彩种返回前端对象", description = "直播彩种返回前端对象")
public class LiveSeedRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer lsId;

    @ApiModelProperty("彩种编号")
    private Integer seedNo;

    @ApiModelProperty("直播数量")
    private Integer liveCount;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("状态 0关闭 1启用")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否推荐首页 0否 1是")
    private Integer recommendIndex;


}
