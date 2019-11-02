package com.bh.live.model.lottery;

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

/**
 * <p>
 * 赔率基础表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_play_bet")
@ApiModel(value = "PlayBet对象", description = "赔率基础表")
public class PlayBet implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "投注项编号(只设置投注玩法时对应值为0)")
    private String itemNo;

    @ApiModelProperty(value = "投注项名称")
    private String itemName;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "玩法名称")
    private String playName;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "当期赔率")
    private BigDecimal odds;

    @ApiModelProperty(value = "单注最小")
    private BigDecimal eachBetMin;

    @ApiModelProperty(value = "单注最高")
    private BigDecimal eachBetMax;

    @ApiModelProperty(value = "单项最高")
    private BigDecimal eachItemMax;

    @ApiModelProperty(value = "最高派奖金额")
    private BigDecimal supremeAward;

    @ApiModelProperty(value = "限号状态（0：非限号 1：限号）")
    private Integer status;

    @ApiModelProperty(value = "赔率类型（1：玩法赔率 2：投注项赔率 3：组合赔率）")
    private Integer oddsType;

    @ApiModelProperty(value = "赔率正则")
    private String oddsRegex;

    @ApiModelProperty(value = "注单描述")
    private String betOrderDesc;

    @ApiModelProperty(value = "玩家累计限额")
    private BigDecimal playerBetMax;


}
