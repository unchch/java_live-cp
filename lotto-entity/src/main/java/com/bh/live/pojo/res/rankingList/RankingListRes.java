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
@ApiModel("排行表")
public class RankingListRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("排行名次")
    private Integer index;

    @ApiModelProperty("用户id")
    private Integer userId;

	@ApiModelProperty("用户昵称")
	private String nickname;

	@ApiModelProperty("用户图像")
	private String imageUrl;
    
	@ApiModelProperty("用户等级")
	private Integer userGrade;
	
    @ApiModelProperty("用户类型 0普通 1专家 2主播")
	private Integer userType;
    
    @ApiModelProperty("人气值")
    private BigDecimal popularityValue;

    @ApiModelProperty("财富值")
    private BigDecimal treasureValue;

    @ApiModelProperty("连胜值")
    private Integer wingingValue;

    @ApiModelProperty("胜率值")
    private BigDecimal winrateValue;

    @ApiModelProperty("盈利率值")
    private BigDecimal profitrateValue;

    @ApiModelProperty("周期 0天 1周  2月  3总")
    private Integer rankPeriod;

    @ApiModelProperty("是否可用 0不可 1可用")
    private Integer isUsable;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date creatTime;

    @ApiModelProperty("场数")
    private Integer numberOf=0;

    @ApiModelProperty("主播状态 0离线 1在线 2直播中")
    private Integer hostsStatus = 0;

    @ApiModelProperty("主播房间号")
    private  Integer roomId;


}
