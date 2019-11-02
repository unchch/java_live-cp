package com.bh.live.pojo.res.rankingList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 排行表
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询用户信息")
public class SearchLiveUserRes implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户昵称")
	private String nickname;

	@ApiModelProperty("房间号")
	private String roomId;
	
	@ApiModelProperty("用户id")
	private Integer userId;
	
	@ApiModelProperty("用户图像")
	private String imageUrl;
    
    @ApiModelProperty("用户类型 0普通 1专家 2主播")
	private Integer userType;
    
    @ApiModelProperty("胜率值")
    private BigDecimal winrateValue;

    @ApiModelProperty("盈利率值")
    private BigDecimal profitrateValue;

    @ApiModelProperty("人气值")
    private String hostLv;
    
    @ApiModelProperty("关注数")
    private String attNum;

    @ApiModelProperty("是否关注")
    private String isAttent;

}
