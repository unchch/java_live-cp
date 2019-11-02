package com.bh.live.model.trade;

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
import java.util.Objects;

/**
 * <p>
 * 用户打赏记录
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_trade_user_award")
@ApiModel(value = "TradeUserAward对象", description = "用户打赏记录")
public class TradeUserAward implements Serializable {

    private static final long serialVersionUID = 1L;

    public TradeUserAward() {
    }

    public TradeUserAward(Integer tvUserId, Integer lottoId) {
        this.tvUserId = tvUserId;
        this.lottoId = lottoId;
    }

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "主播用户id")
    private Integer tvUserId;

    @ApiModelProperty(value = "礼物id")
    private Integer giftId;

    @ApiModelProperty(value = "礼物数目")
    private Integer giftNum;

    @ApiModelProperty(value = "彩币总价格")
    private BigDecimal amount;

    @ApiModelProperty(value = "礼物价格")
    private BigDecimal giftAmount;

    @ApiModelProperty(value = "房间id")
    private Integer roomId;

    @ApiModelProperty(value = "交易code")
    private String transCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "彩种id")
    private Integer lottoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeUserAward award = (TradeUserAward) o;
        return Objects.equals(userId, award.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }


}
