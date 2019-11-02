package com.bh.live.model.cms;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bh.live.model.trade.TradeTransUserData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户统计
 * </p>
 *
 * @author ww
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户统计信息")
public class UserStatisticsCMS implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("新增人数  pc")
    private Long newlyPC;
    
    @ApiModelProperty("新增人数  安卓")
    private Long newlyAndroid;
    
    @ApiModelProperty("新增人数  ios")
    private Long newlyIOS;

    @ApiModelProperty("持有彩币")
    private Double allMoney;
    
    @ApiModelProperty("在线人数  pc")
    private Long onlinePC;
    
    @ApiModelProperty("在线人数  安卓")
    private Long onlineAndroid;
    
    @ApiModelProperty("在线人数  ios")
    private Long onlineIOS;
    
}
