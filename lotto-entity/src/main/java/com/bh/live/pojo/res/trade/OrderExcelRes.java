package com.bh.live.pojo.res.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value = "Order对象", description = "订单表")
public class OrderExcelRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "方案编号")
    private String orderNo;

    @ApiModelProperty(value = "订单流程状态 - 1:待支付，2:待开奖，3:待兑奖，4:未中奖，5:已派奖，6:已关闭，7:已撤单，8:被撤销(待重置派奖)，9:已取消(派奖)")
    private Integer status;

    @ApiModelProperty(value = "订单来源(1:app，2:pc,，3:H5)")
    private Integer clientType;

    @ApiModelProperty(value = "订单用户类型   0 专家  1 主播")
    private Integer orderType;

    @ApiModelProperty(value = "终端appID", hidden = true)
    private Integer appId;

    @ApiModelProperty(value = "终端app名称", hidden = true)
    private String appName;

    @ApiModelProperty(value = "用户ID")
    private Integer accountId;

    @ApiModelProperty(value = "用户名称")
    @TableField(exist = false)
    private String accountName;

    @ApiModelProperty(value = "期号")
    private String lotIssueNo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "下注时间")
    private Date buyTime;

    @ApiModelProperty(value = "彩种编号")
    @TableField("lot_seed_no")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种")
    private String lotSeedName;

    @ApiModelProperty(value = "玩法编号")
    private String lotPlayNo;

    @ApiModelProperty(value = "订单内容")
    private String content;

    @ApiModelProperty(value = "玩法名称（冗余玩法表字段）")
    private String lotPlayName;

    @ApiModelProperty(value = "中奖输赢状态(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

    @ApiModelProperty(value = "中奖输赢状态(0:默认，1:赢，2:输，3:和)")
    private String awardName;

    @ApiModelProperty(value = "开奖结果")
    private String awardNumber;

    @ApiModelProperty(value = "购买注数")
    private Integer buyQuantity;

    @ApiModelProperty(value = "方案价格")
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
    @ApiModelProperty(value = "发布时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "中奖注数")
    private Integer winQuantity;

    @ApiModelProperty(value = "注单派奖状态 - 0:自动，1:手动，2:重置")
    private Integer prizeStatus;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "方案购买数")
    @TableField(exist = false)
    private Integer total;

    @ApiModelProperty(value = "本金")
    private Integer amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "销售截止时间")
    private Date saleEndTime;

    @ApiModelProperty(value = "开奖时间", hidden = true)
    private Date lotteryTime;

    public String getAwardName() {
        if (this.awardState == 0) {
            return "";
        } else if (this.awardState == 1) {
            return "赢";
        } else if (this.awardState == 2) {
            return "输";
        } else if (this.awardState == 3) {
            return "和";
        }
        return "";
    }
}
