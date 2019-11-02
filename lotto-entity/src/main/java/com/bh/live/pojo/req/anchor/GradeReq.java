package com.bh.live.pojo.req.anchor;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Morphon
 * @date 2019/7/25 17:13
 * @desc
 * @Version 1.0
 */
@Data
@ApiModel(value = "主播等级", description = "主播等级")
public class GradeReq implements Serializable {

    @ApiModelProperty(value = "序号")
    private Integer id;

    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称",required = true)
    private String lvName;

    /**
     * 等级经验值
     */
    @ApiModelProperty(value = "等级经验值",required = true)
    private String lvValue;

    /**
     * 等级图标
     */
    @ApiModelProperty(value = "等级图标",required = true)
    private String lvImage;

}
