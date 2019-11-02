package com.bh.live.pojo.req.trade;

import java.io.Serializable;

import com.bh.live.pojo.req.PageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ww
 * @projectName java_live-cp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("方案入参对象")
public class OrderFullReq extends PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryCode;

    @ApiModelProperty(value = "订单来源(1:app，2:pc,，3:H5)")
    private String clientType;

    @ApiModelProperty(value = "是否需要付费；0不付费。1付费")
    private Integer isPay;
    
    @ApiModelProperty(value = "彩期期号")
    private String issue;

    @ApiModelProperty(value = "订单类别:1,竞猜.2投注")
    private Integer orderType;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

//    @ApiModelProperty(value = "号码  ','隔开")
//    private String numCondition;
//
//    @ApiModelProperty(value = "文字条件 ','隔开")
//    private String characterCondition;

    @ApiModelProperty(value = "玩法','隔开")
    private String playNos;

}
