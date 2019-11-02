package com.bh.live.model.lottery.config.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bh.live.pojo.req.PageParam;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 彩种订单配置表
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_order_config")
@ApiModel(value="OrderConfig对象", description="彩种订单配置表")
public class OrderConfigVO extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "彩种code")
    private Integer seedNo;

    @ApiModelProperty(value = "可以发单用户群组；类型用，分割开")
    private String userGroup;

    @ApiModelProperty(value = "可以发单用户群组；类型用 前端用")
    private String[] userGroups;

    @JsonProperty("endOverTime")
    @ApiModelProperty(value = "提前多少秒。禁止发布竞猜")
    private Integer endTime;

    @JsonProperty("sizeArticle")
    @ApiModelProperty(value = "一期最大发布数目")
    private Integer maxNum;

    @JsonProperty("jackpot")
    @ApiModelProperty(value = "奖池开关")
    private Boolean prizeOnOff;

    @JsonProperty("average")
    @ApiModelProperty(value = "分配人数")
    private Integer person;

    @JsonProperty("beginAmt")
    @ApiModelProperty(value = "平局分配最小值")
    private BigDecimal avgCoinMin;

    @JsonProperty("endAmt")
    @ApiModelProperty(value = "平局分配最大值")
    private BigDecimal avCoinMax;

    @ApiModelProperty(value = "用户可以发布价格")
    private List<OrderUserConfigVO> gradeLimitList;

    @ApiModelProperty(value = "用户盈利价格")
    private List<OrderUserProfitConfgVO> profitLimitList;
}
