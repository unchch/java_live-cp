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
 * 主播表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_host_user")
public class HostUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 房间号
     */
    private Integer roomId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 玩彩年限
     */
    private Integer ageLimit;

    /**
     * 擅长彩票，彩种以逗号隔开
     */
    private String adeptLottery;

    /**
     * 直播经验
     */
    private Integer experience;

    /**
     * 电脑系统
     */
    private String pcVersion;

    /**
     * 主播等级
     */
    private Integer hostLv;

    /**
     * 注册时间（申请成为主播时间）
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否可用 0不可以 1可用
     */
    private Integer isUsable;

    /**
     * 封面
     */
    private String cover;

    /**
     * 图库，图片链接以","逗号隔开
     */
    private String gallery;

    /**
     * 个人简介
     */
    private String personalProfile;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 账户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String icon;

    /**
     * 身份证号码
     */
    private String cardNum;

    /**
     * 居住地
     */
    private String address;

    /**
     * 注册来源终端 0:ios  1：安卓  2：pc
     */
    private String device;

    /**
     * 所属代理
     */
    private Integer inviteCode;

    /**
     * 开启手机登录 0否  1是
     */
    private Integer phoneLogin;

    /**
     * 审核成功时间
     */
    private Date verifyTime;

    /**
     * 审核状态（0拒绝 1通过 2待审核 3审核中）
     */
    private Integer verifyStatus;

    /**
     * 结算类型 0等级结算 1礼物数结算 2自定义
     */
    private Integer settlementType;

    /**
     * 自定义结算值
     */
    private Integer stValue;

    /**
     * 结算周期 0日结 1周结 2月结
     */
    private Integer settlementCycle;

    /**
     * 主播状态 0离线 1在线 2直播中
     */
    private Integer status;

    /**
     * 是否推荐首页 0否，1是
     */
    private Integer recommHomePage;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 人气值
     */
    private Integer hotVal;

    /**
     * 直播时间
     */
    private String liveTime;
}
