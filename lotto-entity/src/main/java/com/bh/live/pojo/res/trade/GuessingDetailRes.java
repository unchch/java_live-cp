package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 方案列表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("实时竞猜方案详情返回前端对象")
public class GuessingDetailRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "是否已订阅  0未关注 1已关注")
    private Integer isAttent;

    @ApiModelProperty(value = "是否需要付费；0不付费。1付费")
    private Integer isPay;

    @ApiModelProperty(value = "是否已购买；0未购买。1已购买")
    private Integer isBuy;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "近10期盈利率")
    private BigDecimal profitRate10;

    @ApiModelProperty(value = "近10期胜率")
    private BigDecimal winProfit10;

    @ApiModelProperty(value = "需要付费金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "彩种名称")
    private String lotSeedName;

    @ApiModelProperty(value = "彩期号")
    private String lotIssueNo;

    @ApiModelProperty(value = "连赢(输)次数")
    private Integer seriesWinLoseCount;

    @ApiModelProperty(value = "近10场战绩")
    private String result10;

    @ApiModelProperty(value = "最新一期结果（输/赢）")
    private String newResult;

    @ApiModelProperty("竞猜列表")
    private List<GuessingResultListRes> guessingResultList;

}
