package com.bh.live.pojo.res.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgs
 * @title: PlayTabRes
 * @projectName java_live-cp
 * @description: 玩法选项
 * @date 2019/8/6  20:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="玩法选项", description="玩法选项")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayTabRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "玩法名称")
    private String playName;

    @ApiModelProperty(value = "父玩法编号")
    private String parentNo;

    @ApiModelProperty(value = "玩法模式（1：官方  2：信用）")
    private Integer playMode;

    @ApiModelProperty(value = "玩法类型（0：导航玩法 1：投注玩法）")
    private Integer playType;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "玩法投注位集合")
    List<BitTabRes> bits;

    @ApiModelProperty(value = "玩法投注位集合")
    List<PlayTabRes> child;
}
