package com.bh.live.model.trade.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class OrderVO extends PageParam implements Serializable {

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

    @ApiModelProperty(value = "账户ID")
    private Integer accountId;

    @ApiModelProperty(value = "用户名")
    private String accountName;

    @ApiModelProperty(value = "期号")
    private String lotIssueNo;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "下注时间")
    private Date buyTime;

    @ApiModelProperty(value = "彩种")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种类型")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种名称")
    private String lotSeedName;

    @ApiModelProperty(value = "玩法编号")
    private String lotPlayNo;

    @ApiModelProperty(value = "玩法名称（冗余玩法表字段）")
    private String lotPlayName;

    @ApiModelProperty(value = "结果(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "盈亏（负数为亏损）")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "是否需要付费；0不付费。1付费")
    private Integer isPay;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期开始时间")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期结束时间")
    private Date endTime;

}
