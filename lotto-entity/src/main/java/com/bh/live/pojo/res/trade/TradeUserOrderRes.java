package com.bh.live.pojo.res.trade;

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
 * 用户购买记录
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_trade_user_order")
@ApiModel(value = "TradeUserOrder对象", description = "用户购买记录")
public class TradeUserOrderRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主机")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "所购买订单号")
    private Integer orderCode;

    @ApiModelProperty(value = "流水号")
    private String serialCode;

    @ApiModelProperty(value = "购买金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;


}
