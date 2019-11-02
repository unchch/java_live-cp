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

/**
 * <p>
 * 用户类别所设置价格等级
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_order_user_profit_confg")
@ApiModel(value="OrderUserProfitConfg对象", description="用户类别所设置价格等级")
public class OrderUserProfitConfgVO extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonProperty("seedNo")
    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryCode;

    @JsonProperty("section")
    @ApiModelProperty(value = "盈利率范围开始")
    private BigDecimal section;

    @ApiModelProperty(value = "价格类别：方案价格区间")
    private String priceType;

    @ApiModelProperty(value = "价格类别：方案价格区间 前端是哟")
    private String[] priceList;

    @ApiModelProperty(value = "周期范围类别")
    private Integer dateType;

}
