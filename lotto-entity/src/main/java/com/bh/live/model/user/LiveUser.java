package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播用户表
 * </p>
 *
 * @author WuLong
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_live_user")
public class LiveUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 用户图像
	 */
	private String imageUrl;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 密码
	 */
	@TableField("PASSWORD")
	private String password;

	/**
	 * 性别 0女 1男
	 */
	private Integer sex;

	/**
	 * 用户等级
	 */
	private Integer userGrade;

	/**
	 * 登录终端  0:ios  1：安卓  2：pc
	 */
	private String terminal;

	/**
	 * 是否可用 0不可 1可用
	 */
	private Integer isUsable;

	/**
	 * 是否在线 0不再 1在线
	 */
	private Integer isOnline;

	/**
	 * 是否可以登录 0不可 1可以
	 */
	private Integer isLogin;

	/**
	 * 是否可以发布竞猜 0不可 1可以
	 */
	private Integer isPublish;

	/**
	 * 是否可以直播间发言 0不可 1可以
	 */
	private Integer isSpeak;

	/**
	 * 最后登录时间
	 */
	private Date lastlogin;

	/**
	 * 最后登录ip
	 */
	private String lastip;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 注册终端
	 */
	private Integer channel;

	/**
	 * 注册时间
	 */
	private Date creatTime;

	/**
	 * 是否是专家  0否  1是
	 */
	private Integer isExpert;
	
	
	/**
	 * 是否是主播  0否 1是
	 */
	private Integer isAnchor;

	/**
	 *  用户修改昵称次数，默认0
	 */
	private Integer editNameCount;
	
}
