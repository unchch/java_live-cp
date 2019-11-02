package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 直播间
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
@Data
@ApiModel(value = "直播间返回前端信息", description = "直播间返回前端信息")
public class HostRoomRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "房间id")
    private Integer id;

    @ApiModelProperty(value = "主播id")
    private Integer hostId;

    @ApiModelProperty(value = "开播时间")
    private String openTime;

    @ApiModelProperty(value = "直播结束时间")
    private String endTime;

    @ApiModelProperty(value = "预开播时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date yLiveTime;

    @ApiModelProperty(value = "直播时长")
    private long liveTime;

    @ApiModelProperty(value = "彩种名称")
    private String lottoName;

    @ApiModelProperty(value = "房间状态 0关闭中，1直播中 2未开播 3已完成")
    private Integer status;

    @ApiModelProperty(value = "房间在线人数")
    private Integer linePersons;

    @ApiModelProperty(value = "主播昵称")
    private String nickname;

    @ApiModelProperty(value = "是否推荐首页 0否，1是")
    private Integer recommHomePage;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "送礼物人数")
    private Integer giftPerCount;

    @ApiModelProperty(value = "礼物数")
    private Integer giftCount;

    @ApiModelProperty(value = "礼物彩币数")
    private BigDecimal giftCurrencyCount;

}
