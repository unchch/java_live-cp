package com.bh.live.pojo.req.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/27 13:50
 */
@Data
@ApiModel(value = "首页-彩种直播", description = "首页-彩种直播请求入参")
public class LottoTypeReq {
    /**
     * 彩种名称
     */
    @ApiModelProperty(value = "彩种名称")
    private String seedName;
    /**
     * sort 类型
     *  default 默认 number 人数 level 等级 time 时间
     */
    @ApiModelProperty(value = "sort : default number  level  time ")
    private String sort;

}
