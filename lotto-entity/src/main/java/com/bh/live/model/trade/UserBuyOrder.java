package com.bh.live.model.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lgs
 * @title: UserBuyOrder
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/7/26  16:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户购买记录", description = "用户购买记录")
public class UserBuyOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "方案单号")
    private String serialCode;

    @ApiModelProperty(value = "购买金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "购买者ID")
    private Integer userId;

    @ApiModelProperty(value = "购买者")
    private String userName;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单流程状态 - 1:待支付，2:待开奖，3:待兑奖，4:未中奖，5:已派奖，6:已关闭，7:已撤单，8:被撤销(待重置派奖)，9:已取消(派奖)")
    private Integer status;

    @ApiModelProperty(value = "订单来源(1:app，2:pc,，3:H5)")
    private Integer clientType;

    @ApiModelProperty(value = "终端appID", hidden = true)
    private Integer appId;

    @ApiModelProperty(value = "终端app名称", hidden = true)
    private String appName;

    @ApiModelProperty(value = "发布者ID")
    private Integer accountId;

    @ApiModelProperty(value = "发布者")
    private String accountName;

    @ApiModelProperty(value = "期号")
    private String lotIssueNo;

    @ApiModelProperty(value = "下注时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date buyTime;

    @ApiModelProperty(value = "彩种编号")
    @TableField("lot_seed_no")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种", hidden = true)
    private String lotSeedName;

    @ApiModelProperty(value = "玩法编号", hidden = true)
    private String lotPlayNo;

    @ApiModelProperty(value = "竞猜内容")
    private String content;

    @ApiModelProperty(value = "玩法名称（冗余玩法表字段）", hidden = true)
    private String lotPlayName;

    @ApiModelProperty(value = "结果(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

    @ApiModelProperty(value = "开奖结果")
    private String awardNumber;

    @ApiModelProperty(value = "购买注数", hidden = true)
    private Integer buyQuantity;

    @ApiModelProperty(value = "支出")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "中奖金额")
    private BigDecimal awardAmount;

    @ApiModelProperty(value = "盈亏（负数为亏损）")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "是否需要付费；0不付费。1付费")
    private Integer isPay;

    @ApiModelProperty(value = "订单盈利率")
    private BigDecimal profitRate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "中奖注数", hidden = true)
    private Integer winQuantity;

    @ApiModelProperty(value = "注单派奖状态 - 0:自动，1:手动，2:重置", hidden = true)
    private Integer prizeStatus;

    @ApiModelProperty(value = "备注")
    private String remark;
}
