package com.bh.live.pojo.res.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 礼物表
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
@Data
@ApiModel(value = "礼物", description = "礼物")
public class GiftInfoRes  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "礼物id")
    private Integer id;

    /**
     * 礼物名称
     */
    @ApiModelProperty(value = "礼物名称")
    private String giftName;

    /**
     * 消耗彩币（单价）
     */
    @ApiModelProperty(value = "消耗彩币（单价）")
    private BigDecimal amount;

    /**
     * 主播经验值
     */
    @ApiModelProperty(value = "主播成长积分")
    private Integer anchorXp;

    /**
     * 赠送者经验值
     */
    @ApiModelProperty(value = "用户成长积分")
    private Integer userXp;

    /**
     * 描述
     */
    @ApiModelProperty(value = "礼物描述")
    private String giftDesc;

    /**
     * 礼物动画
     */
    @ApiModelProperty(value = "礼物动画")
    private String image;

    /**
     * 礼物gif
     */
    @ApiModelProperty(value = "礼物gif")
    private String gif;

    /**
     * 全屏动画
     */
    @ApiModelProperty(value = "全屏动画")
    private String fullGif;

    /**
     * 礼物小图标
     */
    @ApiModelProperty(value = "礼物小图标")
    private String icon;

    /**
     * 礼物动画
     */
    @ApiModelProperty(value = "礼物动画")
    private String pcImage;

    /**
     * 礼物gif
     */
    @ApiModelProperty(value = "礼物gif")
    private String pcGif;

    /**
     * 全屏动画
     */
    @ApiModelProperty(value = "全屏动画")
    private String pcFullGif;

    /**
     * 礼物小图标
     */
    @ApiModelProperty(value = "礼物小图标")
    private String pcIcon;


    /**
     * 快捷组，多个逗号分隔（eg：1，520，1314，9999）
     */
    @ApiModelProperty(value = "快捷组，多个逗号分隔（eg：1，520，1314，9999）")
    private String groups;

}
