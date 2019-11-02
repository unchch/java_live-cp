package com.bh.live.pojo.res.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 订单表
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_order")
@ApiModel(value="Order对象", description="订单表")
public class OrderRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单流程状态 - 1:待支付，2:待开奖，3:待兑奖，4:未中奖，5:已派奖，6:已关闭，7:已撤单，8:被撤销(待重置派奖)，9:已取消(派奖)")
    private Integer status;

    @ApiModelProperty(value = "订单来源(1:app，2:pc,，3:H5)")
    private Integer clientType;

    @ApiModelProperty(value = "终端appID")
    private Integer appId;

    @ApiModelProperty(value = "终端app名称")
    private String appName;

    @ApiModelProperty(value = "账户ID")
    private Integer accountId;

    @ApiModelProperty(value = "账户名（冗余账户表字段）")
    private String accountName;

    @ApiModelProperty(value = "彩期号")
    private String lotIssueNo;

    @ApiModelProperty(value = "下注时间")
    private Date buyTime;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotSeedNo;

    @ApiModelProperty(value = "彩种名称（冗余彩种表字段）")
    private String lotSeedName;

    @ApiModelProperty(value = "玩法编号")
    private String lotPlayNo;

    @ApiModelProperty(value = "订单内容")
    private String content;

    @ApiModelProperty(value = "玩法名称（冗余玩法表字段）")
    private String lotPlayName;

    @ApiModelProperty(value = "中奖输赢状态(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

    @ApiModelProperty(value = "开奖号码")
    private String awardNumber;

    @ApiModelProperty(value = "购买注数")
    private Integer buyQuantity;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;





    @ApiModelProperty(value = "中奖金额")
    private BigDecimal awardAmount;

    @ApiModelProperty(value = "盈亏（负数为亏损）")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "是否需要付费；0不付费。1付费")
    private Integer isPay;

    @ApiModelProperty(value = "订单盈利率")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "中奖注数")
    private Integer winQuantity;

    @ApiModelProperty(value = "注单派奖状态 - 0:自动，1:手动，2:重置")
    private Integer prizeStatus;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "备注")
    @TableField(exist = false)
    private Integer total;
}
