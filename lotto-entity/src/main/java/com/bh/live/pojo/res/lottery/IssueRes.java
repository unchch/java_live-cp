package com.bh.live.pojo.res.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @ClassName issueRes
 * @description: 彩种开奖信息
 * @author: Wj
 * @date 2019-08-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩种开奖信息", description = "彩种开奖信息")
public class IssueRes implements Serializable {

    private static final long serialVersionUID = 9006395552906977357L;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "期号")
    private String issueNo;

    @ApiModelProperty(value = "开售时间")
    private String startTime;

    @ApiModelProperty(value = "停售时间")
    private String endTime;

    @ApiModelProperty(value = "开奖时间")
    private String openTime;

    @ApiModelProperty(value = "开奖号码")
    private String result;

    @ApiModelProperty(value = "开奖状态")
    private Integer prizeState;

    @ApiModelProperty(value = "彩期状态")
    private Integer status;

}
