package com.bh.live.pojo.res.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lgs
 * @title: PlayOptRes
 * @projectName java_live-cp
 * @description: 发布竞猜下拉框
 * @date 2019/8/9  18:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "发布竞猜下拉框", description = "发布竞猜下拉框")
public class PlayOptRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "价格集合")
    private List<Integer> prices;

    @ApiModelProperty(value = "彩期")
    private String issue;

    @ApiModelProperty(value = "彩期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "服务器时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date serverTime;

    @ApiModelProperty(value = "能否发布：0,不能发布，1,能；")
    private Integer push;
}
