package com.bh.live.pojo.req.rankingList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 排行表
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("排行表")
public class RankingListReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("排行名次")
    private Integer index;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户类型")
	private Integer userType;
    
    @ApiModelProperty("人气值")
    private BigDecimal popularityValue;

    @ApiModelProperty("财富值")
    private BigDecimal treasureValue;

    @ApiModelProperty("连胜值")
    private Integer wingingValue;

    @ApiModelProperty("胜率值")
    private BigDecimal winrateValue;

    @ApiModelProperty("盈利率值")
    private BigDecimal profitrateValue;

    @ApiModelProperty("周期 0天 1周  2月  3总")
    private Integer rankPeriod;

    @ApiModelProperty("是否可用 0不可 1可用")
    private Integer isUsable;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建时间")
    private LocalDateTime creatTime;


}
