package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播间禁言表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_no_talk")
public class NoTalk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "no_talk_id", type = IdType.AUTO)
    private Integer noTalkId;

    /**
     * 禁言用户
     */
    private Integer userId;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 禁言时间
     */
    private Date beginTime;

    /**
     * 有效期
     */
    private Date endTime;

    /**
     * 操作人
     */
    private String operater;

    /**
     * 是否解禁 0否 1是
     */
    private Integer status;

    /**
     * 禁言ip
     */
    private String noTalkIp;

    /**
     * 禁言类型 1用户  2 IP
     */
    private Integer type;

    /**
     * 主播id
     */
    private Integer hostUserId;

    /**
     * 禁言原因
     */
    private String remark;

}
