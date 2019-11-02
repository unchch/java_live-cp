package com.bh.live.pojo.res.anchor;

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
@ApiModel("主播直播记录返回前端对象信息")
public class LiveRecordRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播id")
    private Integer userId;

    @ApiModelProperty(value = "直播时间")
    private String liveTime;

    @ApiModelProperty(value = "房间人数")
    private Integer roomNum;

    @ApiModelProperty(value = "赠送礼物数")
    private Integer giftNum;

    @ApiModelProperty(value = "礼物价值")
    private BigDecimal amount;

    @ApiModelProperty(value = "直播时长")
    private String time;


}
