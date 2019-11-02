package com.bh.live.pojo.res.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 历史开奖
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/29 17:34
 */
    @Data
    @ApiModel(value = "彩种开奖信息", description = "彩种开奖信息")
    public class HistoryLotteryRes {


        @ApiModelProperty("期号")
        private String issueNo;

        @ApiModelProperty("开奖号码")
        private String result;

        @ApiModelProperty("开奖时间")
        private String openTime;



}
