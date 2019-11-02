package com.bh.live.pojo.res.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 主播直播记录
 * </p>
 *
 * @author Morphon
 * @since 2019-08-14
 */
@Data
@ApiModel("直播间预告直播记录")
public class RoomLiveRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id（未开播时为空）")
    private Integer id;

    @ApiModelProperty(value = "预告id（点开播时传参）")
    private Integer advanceId;

    @ApiModelProperty(value = "房间id")
    private Integer roomId;

    @ApiModelProperty(value = "主播id")
    private Integer userId;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "主播昵称")
    private String nickname;

    @ApiModelProperty(value = "直播开始时间")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private String openTime;

    @ApiModelProperty(value = "直播结束时间")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private String endTime;

    @ApiModelProperty(value = "直播开始时间(完整格式)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTimeComplete;

    @ApiModelProperty(value = "直播结束时间(完整格式)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTimeComplete;

    @ApiModelProperty(value = "直播预告时间")
    private String yLiveTime;

    @ApiModelProperty(value = "房间在线人数")
    private Integer roomNum;

    @ApiModelProperty(value = "赠送礼物数")
    private Integer giftNum;

    @ApiModelProperty(value = "送礼人数")
    private Integer giveCount;

    @ApiModelProperty(value = "直播状态 0未开播 1直播中 2已完成")
    private Integer status;

    @ApiModelProperty(value = "礼物价值")
    private BigDecimal amount;

    @ApiModelProperty(value = "直播时长")
    private String time;

    @ApiModelProperty(value = "是否推荐首页 0否，1是")
    private Integer recommHomePage;

    @ApiModelProperty(value = "首页排序")
    private Integer sort;

    @ApiModelProperty(value = "直播周期 ")
    private Integer livePeriod;

}
