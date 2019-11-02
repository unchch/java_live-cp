package com.bh.live.model.lottery;

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
 * 彩期
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_issue")
@ApiModel(value = "Issue对象", description = "彩期")
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "彩期期号")
    private String issueNo;

    @ApiModelProperty(value = "彩种ID")
    private Integer seedId;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "开售时间")
    private Date startTime;

    @ApiModelProperty(value = "彩期封盘时间（封盘时间之后不允许投注）")
    private Date closingTime;

    @ApiModelProperty(value = "停售时间")
    private Date endTime;

    @ApiModelProperty(value = "开奖时间（记录开奖成功的时间）")
    private Date openTime;

    @ApiModelProperty(value = "停盘的秒数")
    private Integer closingSeconds;

    @ApiModelProperty(value = "彩期生命周期（单位：秒）")
    private Integer lifetime;

    @ApiModelProperty(value = "彩期状态 0:预售中,1:销售中,2:封盘中等待开奖,3:开奖中,4:已开奖等待派奖,5:已派奖,6:过期未开奖")
    private Integer status;

    @ApiModelProperty(value = "上一期期号")
    private String preIssue;

    @ApiModelProperty(value = "周期内的第几期")
    private Integer periodIssue;

    private String result;

    @ApiModelProperty(value = "本期彩种销售额,单位元")
    private Long sales;

    @ApiModelProperty(value = "中奖人数")
    private Integer winners;

    @ApiModelProperty(value = "投注人数")
    private Integer peoples;

    @ApiModelProperty(value = "投注总数量")
    private Integer betCount;

    @ApiModelProperty(value = "派奖金额,单位元")
    private BigDecimal loseMoney;

    @ApiModelProperty(value = "奖池")
    private Long prizePool;

    @ApiModelProperty(value = "抽水率(收益)")
    private Float profit;

    @ApiModelProperty(value = "是否删除 0否1是")
    private Integer deleted;

    private Date createTime;

    private Date updateTime;

    @ApiModelProperty(value = "机器人开盘通知状态：0未通知、1已通知")
    private Integer robotOpenStatus;

    @ApiModelProperty(value = "机器人封盘提前N秒通知状态：0未通知、1已通知")
    private Integer robotClosingStatus;

    @ApiModelProperty(value = "机器人停盘通知状态：0未通知、1已通知")
    private Integer robotEndStatus;

    @ApiModelProperty(value = "开奖方式（0：自动开奖  1：手动开奖）")
    private Integer prizeMode;

    @ApiModelProperty(value = "开奖状态（0：未开奖  1：开奖中 2：已开奖 3：已重置）")
    private Integer prizeState;

    @ApiModelProperty(value = "销售状态（0：待开售  1：销售中 2：封盘中 3：已截止 4：已停售）")
    private Integer saleState;

    @ApiModelProperty(value = "派奖状态（0：未派奖  1：派奖中 2：已派奖）")
    private Integer awardState;

    @ApiModelProperty(value = "取消彩期状态（0：未取消  1：取消中 2：已取消）")
    private Integer cancelIssueState;


}
