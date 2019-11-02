package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 房间发言要求配置表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_no_talk_conf")
public class NoTalkConf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "conf_id", type = IdType.AUTO)
    private Integer confId;

    /**
     * 最低注册时间
     */
    private Integer lowDays;

    /**
     * 最低用户等级
     */
    private Integer lowUserLev;

    /**
     * 全局禁言
     */
    private Integer globle;

    /**
     * 非粉丝用户禁言
     */
    private Integer notFans;

    /**
     * 发言时间（时长）限制
     */
    private Integer cdTalk;

    /**
     * 房间id
     */
    private Integer roomId;
}
