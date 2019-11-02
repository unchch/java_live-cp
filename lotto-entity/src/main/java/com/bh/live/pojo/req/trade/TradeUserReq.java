package com.bh.live.pojo.req.trade;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户流水表
 * </p>
 *
 * @author ww
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_trade_trans_user")
@ApiModel(value = "TradeTransUser对象", description = "用户流水表")
public class TradeUserReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "流水类型 交易流水类别:0：查询全部；1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成")
    private Integer transType;

    @ApiModelProperty(value = "开始时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "页数")
    private Integer pageNum=1;
    
    @ApiModelProperty(value = "行数")
    private Integer pageSize=10;
    
    @ApiModelProperty(value = "结束时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;
}
