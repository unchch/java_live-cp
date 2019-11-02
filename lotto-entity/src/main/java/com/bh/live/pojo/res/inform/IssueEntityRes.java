package com.bh.live.pojo.res.inform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName IssueEntityRes
 * @description: IssueEntityRes
 * @author: yq.
 * @date 2019-08-10 14:41:00
 */
@Data
@ApiModel(value = "彩期res")
public class IssueEntityRes implements Serializable {

    private static final long serialVersionUID = -3318898222541698616L;

    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    /**
     * 彩期期号
     */
    @ApiModelProperty("彩期期号")
    private String issueNo;

    /**
     * 彩种编号
     */
    @ApiModelProperty("彩种编号")
    private Integer seedNo;

    /**
     * 开售时间
     */
    @ApiModelProperty("开售时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 彩期封盘时间（封盘时间之后不允许投注）
     */
    @ApiModelProperty("彩期封盘时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date closingTime;

    /**
     * 停售时间
     */
    @ApiModelProperty("停售时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 开奖时间（记录开奖成功的时间）
     */
    @ApiModelProperty("开奖时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;

    /**
     * 停盘的秒数
     */
    @ApiModelProperty("停盘的秒数")
    private Integer closingSeconds;

    /**
     * 彩期生命周期（单位：秒）
     */
    @ApiModelProperty("彩期生命周期（单位：秒）")
    private Integer lifetime;

    /**
     * 彩期状态 0:预售中,1:销售中,2:封盘中等待开奖,3:开奖中,4:已开奖等待派奖,5:已派奖,6:过期未开奖
     */
    @ApiModelProperty("彩期状态")
    private Integer status;

    @ApiModelProperty("上一期期号")
    private String preIssue;

    @ApiModelProperty("周期期数总数")
    private Integer totalIssue;

    @ApiModelProperty("剩余期数")
    private Integer surplusIssue;

    @ApiModelProperty("开奖号码")
    private String result;

    @ApiModelProperty(value = "定位", hidden = true)
    private String position;

    @ApiModelProperty(value = "当前系统时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date systemDate;
}
