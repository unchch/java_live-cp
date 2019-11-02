package com.bh.live.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户金额明细表
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 钱包id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 可提现金额
     */
    private BigDecimal depositlMoney;

    /**
     * 不可提现金额
     */
    private BigDecimal notdepositlMoney;

    /**
     * 总金额
     */
    private BigDecimal allMoney;

    /**
     * 是否可以提款 0不可以 1可用
     */
    private Integer isExtract;

    /**
     * 是否冻结交易  0否  1是 （所有交易受限.如购买方案、打赏、提款等）
     */
    private Integer isFreeze;


}
