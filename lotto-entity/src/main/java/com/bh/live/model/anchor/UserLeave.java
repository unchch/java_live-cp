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
 * 直播间
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_user_leave")
public class UserLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主播id
     */
    private Integer userId;

    /**
     * 强制离线时长/秒
     */
    private Integer liveTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否可用 0不可用 1可用
     */
    private Integer isUsable;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createBy;

}
