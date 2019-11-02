package com.bh.live.pojo.req.lottery;

import com.bh.live.pojo.req.PageParam;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName issueReq
 * @description: issueReq
 * @author: WJ
 * @date 2019-08-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "开奖查询参数", description = "开奖查询参数")
public class IssueReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 1174037654830554975L;

    @ApiModelProperty(value = "彩种期号")
    private String issueNo;

    @ApiModelProperty(value = "彩种编号", required = true)
    private Integer seedNo;

    @ApiModelProperty(value = "开始日期")
    private String startTime;

    @ApiModelProperty(value = "截止日期")
    private String endTime;

    @ApiModelProperty(value = "彩期状态")
    private Integer status;
}
