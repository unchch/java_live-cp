package com.bh.live.pojo.req.trade;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lgs
 * @title: ProfitRateRankRes
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/8/10  14:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="直播间排行榜请求头", description="直播间排行榜请求头")
public class ProfitRateRankReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "查看多少期")
    private Integer num;

    @ApiModelProperty(value = "类别；1连胜，2赢率，3盈利")
    private Integer type;
}
