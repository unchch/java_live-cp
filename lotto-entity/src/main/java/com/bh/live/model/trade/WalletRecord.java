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
 * 用户流水表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("trade_wallet_record")
@ApiModel(value = "WalletRecord对象", description = "用户流水表")
public class WalletRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    private Integer walletId;

    @ApiModelProperty(value = "交易流水编号")
    private String tranNo;

    @ApiModelProperty(value = "订单编号")
    private String businessNo;

    @ApiModelProperty(value = "交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成")
    private Integer type;

    @ApiModelProperty(value = "0：交易失败；1：交易成功；2：待审核； 3：审核通过； 4：审核不通过")
    private Integer status;

    private Integer property;

    @ApiModelProperty(value = "交易总金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "账户总余额,（现金总余额+中奖余额）")
    private BigDecimal balance;

    @ApiModelProperty(value = "备注")
    private String description;

    private String ipAddr;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String ip;

    private String createBy;


}
