package com.bh.live.pojo.res.lottery.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 字典类型
 *
 * @Author: WJ
 * @date: 2019/8/12
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "字典类型", description = "字典类型")
public class DictTypeRes implements Serializable {

    private static final long serialVersionUID = 8335916312641212085L;

    @ApiModelProperty(value = "字典id")
    private int dictTypeCode;

    @ApiModelProperty(value = "字典类型名称")
    private String dictTypeName;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

    private Map<Integer, List<DictRes>> dictResMap;
}
