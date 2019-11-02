package com.bh.live.model.trade;

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
 * 用户打赏礼物记录表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("trade_award_record")
@ApiModel(value = "AwardRecord对象", description = "用户打赏礼物记录表")
public class AwardRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "记录流水号")
    private String recordNo;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "赠送用户id")
    private Integer targetUserId;

    @ApiModelProperty(value = "当前场次id(可获取房间、主播id)")
    private Integer roundId;

    @ApiModelProperty(value = "礼物id")
    private Integer giftId;

    @ApiModelProperty(value = "赠送数量")
    private Integer totalCount;

    @ApiModelProperty(value = "礼物单价")
    private BigDecimal price;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "ip地址")
    private String ipAddr;


}
