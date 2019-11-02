package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lgs
 * @title: ItemTabRes
 * @projectName java_live-cp
 * @description: 竞猜项表
 * @date 2019/8/6  20:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="玩法竞猜项", description="玩法竞猜项")
public class ItemTabRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "投注项编号")
    private String itemNo;

    @ApiModelProperty(value = "内容(数值)")
    private String content;

    @ApiModelProperty(value = "投注项显示排序")
    private Integer sort;
}
