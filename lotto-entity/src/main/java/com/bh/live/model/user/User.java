package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("uc_user")
@ApiModel(value = "User对象", description = "直播用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "帐号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户图像")
    private String portrait;

    @ApiModelProperty(value = "性别  0女 1男")
    private Integer sex;

    @ApiModelProperty(value = "当前积分值（用户等级用途）")
    private Integer integral;

    @ApiModelProperty(value = "用户等级")
    private Integer userGradeId;

    @ApiModelProperty(value = "登录终端")
    private String terminal;

    @ApiModelProperty(value = "是否可用 0不可 1可用")
    private Integer status;

    @ApiModelProperty(value = "是否在线  0不再  1在线")
    private Integer isOnline;

    @ApiModelProperty(value = "是否是主播  0否 1是")
    private Integer userType;

    @ApiModelProperty(value = "最后登录ip")
    private String lastIp;

    @ApiModelProperty(value = "登录ip地址")
    private String loginIp;

    @ApiModelProperty(value = "注册ip")
    private String registerIp;

    @ApiModelProperty(value = "注册地址")
    private String registerAddr;

    @ApiModelProperty(value = "当前所在房间id")
    private Integer roomId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastlogin;

    @ApiModelProperty(value = "注册时间")
    private Date creatTime;


}
