package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 礼物表
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_gift")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类Id
     */
    private Integer categoryId;

    /**
     * 礼物主题
     */
    private String giftTheme;

    /**
     * 礼物名称
     */
    private String giftName;

    /**
     * 消耗彩币（单价）
     */
    private BigDecimal amount;

    /**
     * 主播经验值
     */
    private Integer anchorXp;

    /**
     * 赠送者经验值
     */
    private Integer userXp;

    /**
     * 描述
     */
    private String giftDesc;

    /**
     * 礼物动画
     */
    private String image;

    /**
     * 礼物gif
     */
    private String gif;

    /**
     * 全屏动画
     */
    private String fullGif;

    /**
     * 礼物小图标
     */
    private String icon;

    /**
     * 动画循环次数
     */
    private Integer playTimes;

    /**
     * 快捷组，多个逗号分隔（eg：1，520，1314，9999）
     */
    private String groups;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否删除（0：未删除，1：已删除）
     */
    private Integer isDelete;

    /**
     * 状态（0停用，1启用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改者
     */
    private Integer updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * PC端礼物动画
     */
    private String pcImage;

    /**
     * PC端礼物gif
     */
    private String pcGif;

    /**
     * PC端全屏动画
     */
    private String pcFullGif;

    /**
     * PC端礼物小图标
     */
    private String pcIcon;


}
