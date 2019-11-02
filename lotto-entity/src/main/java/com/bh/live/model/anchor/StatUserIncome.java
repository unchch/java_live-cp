package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_stat_user_income")
public class StatUserIncome {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 统计日期
     */
    @ApiModelProperty("日期")
    private Date statDate;

    @ApiModelProperty("主播id")
    private Integer userId;

    /**
     * 礼物数
     */
    @ApiModelProperty("礼物数")
    private Long giftCount;
    /**
     * 礼物彩币数
     */
    @ApiModelProperty("礼物价值")
    private BigDecimal giftAmount;
    /**
     * 分成比例
     */
    @ApiModelProperty("分成比例，需要x100")
    private BigDecimal divide;
    /**
     * 总收入
     */
    @ApiModelProperty("主播最终收入")
    private BigDecimal income;


}
