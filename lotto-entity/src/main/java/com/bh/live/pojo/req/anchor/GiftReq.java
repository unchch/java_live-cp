package com.bh.live.pojo.req.anchor;

import com.bh.live.pojo.req.PageParam;
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
public class GiftReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "礼物id")
    private Integer id;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类Id")
    private Integer categoryId;

    /**
     * 礼物主题
     */
    @ApiModelProperty(value = "礼物主题")
    private String giftTheme;

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
     * PC端礼物动画
     */
    @ApiModelProperty(value = "PC端礼物动画")
    private String pcImage;

    /**
     * PC端礼物gif
     */
    @ApiModelProperty(value = "PC端礼物gif")
    private String pcGif;

    /**
     * PC端全屏动画
     */
    @ApiModelProperty(value = "PC端全屏动画")
    private String pcFullGif;

    /**
     * PC端礼物小图标
     */
    @ApiModelProperty(value = "PC端礼物小图标")
    private String pcIcon;

    /**
     * 动画循环次数
     */
    @ApiModelProperty(value = "动画循环次数")
    private Integer playTimes;

    /**
     * 快捷组，多个逗号分隔（eg：1，520，1314，9999）
     */
    @ApiModelProperty(value = "快捷组，多个逗号分隔（eg：1，520，1314，9999）")
    private String groups;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 状态（0停用，1启用）
     */
    @ApiModelProperty(value = " 状态（0停用，1启用）")
    private Integer status;


}
