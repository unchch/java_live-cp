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
 * @author ww
 * @since 2019-07-25
 */
@Data
@ApiModel(value = "直播彩种返回前端对象", description = "直播彩种返回前端对象")
public class LiveSeedBannerRes implements Serializable {

    private static final long serialVersionUID = 1L;

    public LiveSeedBannerRes(){}
    
    public LiveSeedBannerRes(Integer lsId,Integer seedNo,Integer liveCount,String cover){
    	this.lsId=lsId;
    	this.seedNo=seedNo;
    	this.liveCount=liveCount;
    	this.cover=cover;
    }
    
    @ApiModelProperty(value = "序号")
    private Integer lsId;

    @ApiModelProperty("彩种编号")
    private Integer seedNo;

    @ApiModelProperty("直播数量")
    private Integer liveCount;

    @ApiModelProperty("封面")
    private String cover;

}
