package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 主播表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-06
 */
@Data
@ApiModel("主播个人主页返回前端信息对象")
public class HostUserHomePageRes implements Serializable {

    //主播头像、主播昵称、主播房间号、主播粉丝数、荣誉榜、主播状态（休息、直播中-彩种图标直播标识）
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("直播状态")
    private Integer status;

    /**
     * 擅长彩票，彩种以逗号隔开
     */
    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("直播时间")
    private String liveTime;

    @ApiModelProperty("人气值")
    private Integer hotVal;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("房间号")
    private Integer roomId;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("财富值")
    private BigDecimal moneyAmount;

    @ApiModelProperty("图库")
    private String gallery;

    @ApiModelProperty("个人简介")
    private String personalProfile;

    @ApiModelProperty("是否已关注 0未关注 1已关注")
    private Integer isAttent;

    @ApiModelProperty("正在直播彩种编号")
    private Integer seedNo;

    @ApiModelProperty("正在直播彩种logo图")
    private String logo;

    @ApiModelProperty("主播预告")
    private List<AdvancesRes> advanceList;

    @ApiModelProperty("粉丝列表")
    private List<FansListRes> fansList;

}
