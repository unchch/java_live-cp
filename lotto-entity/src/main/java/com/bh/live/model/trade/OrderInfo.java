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
 * 投注订单表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_order_info")
@ApiModel(value = "OrderInfo对象", description = "投注订单表")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "注单号")
    private String betNo;

    @ApiModelProperty(value = "订单号。关联lot_order")
    private String orderNo;

    @ApiModelProperty(value = "注单流程状态= - 1:待支付，2:待开奖，3:未中奖，4:已中奖，5:已派奖，6重置开奖 7:已撤单")
    private Integer status;

    @ApiModelProperty(value = "撤单理由")
    private String revokeReason;

    @ApiModelProperty(value = "终端appID")
    private Integer appId;

    @ApiModelProperty(value = "终端名称")
    private String appName;

    @ApiModelProperty(value = "账户ID")
    private Integer accountId;

    @ApiModelProperty(value = "彩期")
    private String lotIssueNo;

    @ApiModelProperty(value = "下注时间")
    private Date buyTime;

    @ApiModelProperty(value = "投注项内容")
    private String betContent;

    @ApiModelProperty(value = "投注位编号")
    private String betCode;

    @ApiModelProperty(value = "投注项编号")
    private String lotBitNo;

    @ApiModelProperty(value = "投注项名称")
    private String lotBitContent;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotSeedNo;

    @ApiModelProperty(value = "彩种名称")
    private String lotSeedName;

    @ApiModelProperty(value = "玩法编号")
    private String lotPlayNo;

    @ApiModelProperty(value = "玩法名称")
    private String lotPlayName;

    @ApiModelProperty(value = "中奖输赢状态(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

    @ApiModelProperty(value = "开奖号码")
    private String awardNumber;

    @ApiModelProperty(value = "购买倍数")
    private Integer buyModelMultiple;

    @ApiModelProperty(value = "单注金额")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "购买注数")
    private Integer buyQuantity;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "赔率")
    private BigDecimal odds;

    @ApiModelProperty(value = "中奖金额")
    private BigDecimal awardAmount;

    @ApiModelProperty(value = "盈亏（负数为亏损）")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "中奖注数")
    private Integer winQuantity;

    @ApiModelProperty(value = "注单派奖状态 - 0:自动，1:手动，2:重置")
    private Integer prizeStatus;

    @ApiModelProperty(value = "开奖时间")
    private Date lotteryTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
