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
 * 投注订单明细表(派奖后数据插入)
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_order_info_detail")
@ApiModel(value="OrderInfoDetail对象", description="投注订单明细表(派奖后数据插入)")
public class OrderInfoDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属订单号")
    private String orderNo;

    @ApiModelProperty(value = "所属注单号")
    private String betNo;

    @ApiModelProperty(value = "中奖金额")
    private BigDecimal awardAmount;

    @ApiModelProperty(value = "中奖输赢状态(0:默认，1:赢，2:输，3:和)")
    private Integer awardState;

    @ApiModelProperty(value = "注明细状态 -1:待开奖，2:未中奖，3:已中奖，4:已派奖，5:已撤单 6:重置开奖")
    private Integer status;

    private Date createTime;

    @ApiModelProperty(value = "中奖注数")
    private Integer winQuantity;

    @ApiModelProperty(value = "注单派奖状态 - 0:自动，1:手动，2:重置")
    private Integer prizeStatus;

    @ApiModelProperty(value = "开奖时间")
    private Date lotteryTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "派奖时间")
    private Date awardTime;


}
