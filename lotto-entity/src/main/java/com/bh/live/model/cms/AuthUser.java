package com.bh.live.model.cms;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_auth_user")
@ApiModel(value="AuthUser对象", description="用户信息表")
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uid,用户账号,主键")
    @TableId(value = "uid")
    private String uid;

    @ApiModelProperty(value = "用户名(nick_name)")
    private String username;

    @ApiModelProperty(value = "密码(MD5(密码+盐))")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "用户真名")
    private String realName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 3.删除 4.非法)")
    private Integer status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户上级")
    private String parentUserId;

    @ApiModelProperty(value = "谷歌验证码密匙")
    private String secretKey;

    private String secretQrcode;
}
