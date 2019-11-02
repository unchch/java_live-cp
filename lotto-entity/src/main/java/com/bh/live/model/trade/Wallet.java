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
 * 用户金额明细表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("trade_wallet")
@ApiModel(value="Wallet对象", description="用户金额明细表")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "钱包id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "钱包标识")
    private String walletIdent;

    @ApiModelProperty(value = "可提现金额")
    private String thirdUsername;

    @ApiModelProperty(value = "第三方密码")
    private String thirdPassword;

    @ApiModelProperty(value = "可用余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "冻结额度")
    private BigDecimal freeAmt;

    @ApiModelProperty(value = "秘钥（可用余额+冻结额度加密）")
    private String sign;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
