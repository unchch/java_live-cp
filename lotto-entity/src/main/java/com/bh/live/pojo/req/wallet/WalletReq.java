package com.bh.live.pojo.req.wallet;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户钱包")
public class WalletReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 钱包id
     */
    @ApiModelProperty("钱包id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;

    /**
     * 可提现金额
     */
    @ApiModelProperty("可提现金额")
    private BigDecimal depositlMoney;

    /**
     * 不可提现金额
     */
    @ApiModelProperty("不可提现金额")
    private BigDecimal notdepositlMoney;

    /**
     * 总金额
     */
    @ApiModelProperty("总金额")
    private BigDecimal allMoney;

    /**
     * 是否可以提款 0不可以 1可用
     */
    @ApiModelProperty("是否可以提款 0不可以 1可用")
    private Integer isExtract;

    /**
     * 是否冻结交易  0否  1是 （所有交易受限.如购买方案、打赏、提款等）
     */
    @ApiModelProperty("是否冻结交易  0否  1是 （所有交易受限.如购买方案、打赏、提款等）")
    private Integer isFreeze;


}
