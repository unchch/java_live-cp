package com.bh.live.pojo.req.trade;

import com.bh.live.model.user.LiveUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用户流水表
 * </p>
 *
 * @author ww
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户交易流水号", description = "用户交易流水号")
public class TradeSerialNumReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户对象", required = true)
    private LiveUser user;
    @ApiModelProperty(value = "交易金额", required = true)
    private BigDecimal amount;
    @ApiModelProperty(value = "流水类型 交易流水类别:0：查询全部；1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成", required = true)
    private Integer transType;
    @ApiModelProperty(value = "订单号", required = false)
    private String orderNo;
    @ApiModelProperty(value = "渠道编号", required = false)
    private Integer channelId;
    @ApiModelProperty(value = "打赏用户，需要加钱用户id", required = false)
    private Integer addUserId;
}
