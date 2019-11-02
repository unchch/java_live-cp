package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 直播间主播信息返回
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-08-02
 */
@Data
@ApiModel(value = "主播信息", description = "主播信息")
public class AnchorRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("主播头像")
    private String imageUrl;

    @ApiModelProperty("主播昵称")
    private String nickname;

    @ApiModelProperty("主播房间")
    private Integer roomId;

    @ApiModelProperty("彩种Id")
    private Integer seedNo;

    @ApiModelProperty("彩种名称")
    private String seedName;

    @ApiModelProperty("是否关注 0：未关注 1：关注")
    private Integer isAttent;

    @ApiModelProperty("主播粉丝数")
    private Integer attentCount;

    @ApiModelProperty("人气榜")
    private BigDecimal popularityValue;

    @ApiModelProperty("财富榜")
    private BigDecimal treasureValue;

    @ApiModelProperty("直播时间")
    private String liveTime;

    @ApiModelProperty("直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("直播结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "总次数")
    private Integer total;

    @ApiModelProperty(value = "赢次")
    private Integer win;

    @ApiModelProperty(value = "输次")
    private Integer lose;

    @ApiModelProperty(value = "胜率")
    private BigDecimal winRate;

    @ApiModelProperty(value = "盈亏")
    private BigDecimal profitAmount;

    @ApiModelProperty(value = "盈利率")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "走势")
    private String awardState;

    @ApiModelProperty(value = "流媒体地址")
    private String mediaUrl;

    @ApiModelProperty(value = "是否可用 0关闭 1启用 2踢下线")
    private Integer isUsable;

    @ApiModelProperty(value = "主播状态 0离线 1在线 2直播中")
    private Integer status;

    @ApiModelProperty(value = "主播公告")
    private String notice;

    @ApiModelProperty(value = "视频流密钥")
    private String secretKey;
}
