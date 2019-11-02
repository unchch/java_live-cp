package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 房间弹幕功能配置表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_barrage_conf")
public class BarrageConf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @TableId(value = "barr_id", type = IdType.AUTO)
    private Integer barrId;

    @ApiModelProperty("房间id")
    private Integer roomId;
    /**
     * 弹幕加一 1开启 0关闭
     */
    @ApiModelProperty("弹幕加一 1开启 0关闭")
    private Integer add1Open;

    /**
     * 弹幕加一最低使用用户等级
     */
    @ApiModelProperty("弹幕加一最低使用用户等级")
    private Integer add1Level;

    /**
     * 弹幕加一仅粉丝可使用
     */
    @ApiModelProperty("弹幕加一仅粉丝可使用 1开启 0关闭")
    private Integer add1OnlyFans;

    /**
     * 弹幕回复 1开启 0关闭
     */
    @ApiModelProperty("弹幕回复 1开启 0关闭")
    private Integer replyOpen;

    /**
     * 弹幕回复最低使用用户等级
     */
    @ApiModelProperty("弹幕回复最低使用用户等级")
    private Integer replyLevel;

    /**
     * 弹幕回复仅粉丝可使用1
     */
    @ApiModelProperty("弹幕回复仅粉丝可使用 1开启 0关闭")
    private Integer replyOnlyFans;

    /**
     * 重复发言 1开启 不开启为0
     */
    @ApiModelProperty(" 重复发言 1开启 0关闭")
    private Integer repeatTalk;

    /**
     * 高级弹幕 1开启 0关闭
     */
    @ApiModelProperty("高级弹幕 1开启 0关闭")
    private Integer higherBarrage;


}
