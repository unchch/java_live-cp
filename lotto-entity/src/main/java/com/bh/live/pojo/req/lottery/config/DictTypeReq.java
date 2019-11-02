package com.bh.live.pojo.req.lottery.config;

/**
 *
 * 字典类型
 * @Author: WJ
 * @date: 2019/8/12
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "字典类型", description = "字典类型")
    public class DictTypeReq implements Serializable {

    private static final long serialVersionUID = 2105216440884133655L;

    @ApiModelProperty(value = "字典id")
    private int dictTypeCode;

    @ApiModelProperty(value = "字典类型名称")
    private String dictTypeName;

    @ApiModelProperty(value = "是否启用(0：禁用；1：启用)")
    private  int isDisabled;

    @ApiModelProperty(value = "字典信息列表")
    List<DictReq> dictReqs;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateBy;
}
