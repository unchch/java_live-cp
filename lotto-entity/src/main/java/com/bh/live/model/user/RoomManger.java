package com.bh.live.model.user;

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
 * 直播间管理员表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_room_manger")
public class RoomManger implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 权限id.暂时不用
     */
    private String powerId;

    /**
     * 是否可用 0不可以 1可用
     */
    private Integer isUsable;

    /**
     * 主播用户id
     */
    private Integer hostUserId;

    /**
     * 任命时间
     */
    private Date createTime;

    /**
     * 用户等级
     */
    private Integer userLv;


}
