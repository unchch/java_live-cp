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
import java.util.Date;

/**
 * <p>
 * 彩期生成的参数
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_issue_argument")
@ApiModel(value = "IssueArgument对象", description = "彩期生成的参数")
public class IssueArgument implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer seedId;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩期时间间隔（单位：秒）")
    private Integer issueTimeInterval;

    @ApiModelProperty(value = "每期停盘的秒数")
    private Integer closingSeconds;

    @ApiModelProperty(value = "时间段内彩期数量")
    private Integer issueCount;

    @ApiModelProperty(value = "ssc、快乐十分2部分彩期：上一部分的彩期数量，为0则为第一部分")
    private Integer upIssueCount;

    @ApiModelProperty(value = "彩期期号表达式")
    private String issueExp;

    @ApiModelProperty(value = "彩票开售时间")
    private Date beginTime;

    @ApiModelProperty(value = "彩票停售时间")
    private Date endTime;

    @ApiModelProperty(value = "彩期号是否每天都重新从1开始计算,0否，1是")
    private Integer issueNoIsCountEveryday;

    @ApiModelProperty(value = "前一个彩期参数id")
    private Integer preIssueArgumentId;


}
