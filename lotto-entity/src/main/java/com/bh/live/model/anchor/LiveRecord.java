package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_live_record")
@ApiModel(value="LiveRecord对象", description="主播直播记录")
public class LiveRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "主播id")
    private Integer userId;

    @ApiModelProperty(value = "房间id")
    private Integer roomId;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "直播开始时间")
    private Date openTime;

    @ApiModelProperty(value = "直播结束时间")
    private Date endTime;

    @ApiModelProperty(value = "直播预告时间")
    private String yLiveTime;

    @ApiModelProperty(value = "房间人数")
    private Integer roomNum;

    @ApiModelProperty(value = "赠送礼物数")
    private Integer giftNum;

    @ApiModelProperty(value = "送礼人数")
    private Integer giveCount;

    @ApiModelProperty(value = "直播状态 1直播中 2已完成")
    private Integer status;

    @ApiModelProperty(value = "直播预告id")
    private Integer advanceId;

    @ApiModelProperty(value = "礼物价值")
    private BigDecimal amount;

    @ApiModelProperty(value = "直播时长")
    private String time;

}
