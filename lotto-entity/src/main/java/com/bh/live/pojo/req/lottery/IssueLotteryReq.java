package com.bh.live.pojo.req.lottery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName IssueLotteryReq
 * @description: 彩期开奖管理-开奖参数
 * @author: WJ
 * @date 2019-08-09
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩期开奖参数", description = "彩期开奖参数")
public class IssueLotteryReq implements Serializable {

    private static final long serialVersionUID = -8140865673409856191L;

    @ApiModelProperty(value = "彩种编号")
    private String seedNo;

    @ApiModelProperty(value = "彩种期号")
    private String issueNo;

    @ApiModelProperty(value = "开奖号码")
    private String result;

}
