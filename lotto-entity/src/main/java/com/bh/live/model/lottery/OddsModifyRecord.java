package com.bh.live.model.lottery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 赔率调整记录
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_odds_modify_record")
public class OddsModifyRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 投注项编号(只设置投注玩法时对应值为0)
     */
    private String itemNo;

    /**
     * 投注项名称
     */
    private String itemName;

    /**
     * 玩法编号
     */
    private String playNo;

    /**
     * 玩法名称
     */
    private String playName;

    /**
     * 当期赔率
     */
    private BigDecimal currentOdds;

    /**
     * 单注最小
     */
    private BigDecimal eachBetMin;

    /**
     * 单注最高
     */
    private BigDecimal eachBetMax;

    /**
     * 单项最高
     */
    private BigDecimal eachItemMax;

    /**
     * 最高派奖金额
     */
    private BigDecimal supremeAward;

    /**
     * 状态（0：停用 1：启用）
     */
    private Integer status;

    /**
     * 赔率类型（1：玩法赔率 2：投注项赔率 3：组合赔率）
     */
    private Integer oddsType;

    /**
     * 赔率正则
     */
    private String oddsRegex;

    /**
     * 最低赔率
     */
    private BigDecimal minOdds;

    /**
     * 最高赔率
     */
    private BigDecimal maxOdds;

    /**
     * 13/14特殊赔率
     */
    private BigDecimal specialOdds;

    /**
     * 赔率生效期号
     */
    private String validIssueNo;

    /**
     * 玩家累计限额
     */
    private BigDecimal playerBetMax;

    /**
     * 操作ip
     */
    private String createIp;

    private Integer createId;

    /**
     * 创建时间
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;


}
