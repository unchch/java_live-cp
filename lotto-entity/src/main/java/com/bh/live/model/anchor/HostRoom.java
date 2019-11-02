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
 * 直播间
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_host_room")
public class HostRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 直播间id
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 主播id
     */
    private Integer hostId;

    /**
     * 直播间名称
     */
    private String name;

    /**
     * 描述
     */
    private String roomDescribe;

    /**
     * 预告
     */
    private String prevue;

    /**
     * 标签
     */
    private String label;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 预开播时间
     */
    private String yLiveTime;

    /**
     * 开播时间
     */
    private Date openTime;

    /**
     * 直播结束时间
     */
    private Date endTime;

    /**
     * 是否可用 0关闭 1启用 2踢下线 3禁言
     */
    private Integer isUsable;

    /**
     * 彩种id
     */
    private Integer lottoId;

    /**
     * 彩种名称
     */
    private String lottoName;

    /**
     * 房间状态 0关闭中，1直播中 2未开播 3已完成
     */
    private Integer status;

    /**
     * 房间在线人数
     */
    private Integer linePersons;

    /**
     * 主播昵称
     */
    private String nickname;

    /**
     * 是否推荐首页 0否，1是
     */
    private Integer recommHomePage;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 踢下线原因
     */
    private Integer kickOffLine;

    /**
     * 房间公告
     */
    private String notice;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 直播中截图
     */
    private String liveCover;

    /**
     * 房间密钥
     */
    private String secretKey;
}
