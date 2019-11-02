package com.bh.live.model.trade.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bh.live.pojo.req.PageParam;
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
public class UserBuyOrderVO extends PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水号")
    private String serialCode;

    @ApiModelProperty(value = "购买用户ID")
    private Integer userId;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单流程状态 - 1:待支付，2:待开奖，3:待兑奖，4:未中奖，5:已派奖，6:已关闭，7:已撤单，8:被撤销(待重置派奖)，9:已取消(派奖)")
    private Integer status;

    @ApiModelProperty(value = "订单来源(1:app，2:pc,，3:H5)")
    private Integer clientType;

    @ApiModelProperty(value = "账户ID")
    private Integer accountId;

    @ApiModelProperty(value = "账户名（冗余账户表字段）")
    private String accountName;

    @ApiModelProperty(value = "彩期号")
    private String lotIssueNo;

    @ApiModelProperty(value = "彩种")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种类型")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种名称（冗余彩种表字段）")
    private String lotSeedName;

    @ApiModelProperty(value = "订单内容")
    private String content;

    @ApiModelProperty(value = "玩法名称（冗余玩法表字段）")
    private String lotPlayName;

    @ApiModelProperty(value = "中奖输赢状态(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期开始时间")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期结束时间")
    private Date endTime;

}
