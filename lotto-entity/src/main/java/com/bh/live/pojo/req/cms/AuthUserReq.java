package com.bh.live.pojo.req.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author yq.
 * @date 2019/4/15 4:05 PM
 * @description
 * @since
 **/
@Data
public class AuthUserReq implements Serializable {

    /**
     * 登录名、唯一标识
     */
    private String uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 角色
     */
    private Set<Integer> roles;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上级ID
     */
    private String parentUserId;
}
