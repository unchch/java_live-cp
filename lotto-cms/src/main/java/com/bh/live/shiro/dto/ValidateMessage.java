package com.bh.live.shiro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author yq.
 * @date 2019/4/19 10:34 AM
 * @description 验证mesage
 * @since
 **/
@Getter
@AllArgsConstructor
public enum ValidateMessage {
    /**
     * 非法请求 400
     */
    ERROR_REQUEST(HttpStatus.BAD_REQUEST.value(), "非法请求"),
    /**
     * 没有权限 403
     */
    NO_PERMISSION(HttpStatus.FORBIDDEN.value(), "没有权限"),

    WRONG_PASS(1002, "用户名或者密码或者验证码错误"),
    LOGIN_SUCCESS(1003, "登录成功"),
    WRONG_TOKEN_KEY(1004, "tokenKey错误"),
    NEW_JWT(1005, "新jwt"),
    EXPIRED_JWT(1006, "jwt已过期,请重新登录申请jwt"),
    ERROR_JWT(1007, "jwt错误,请重新登录申请jwt"),
    OLD_JWT(1008, "jwt已过期,账户已在其他地方登陆，请重新登录申请jwt"),
    DISABLED_ACCOUNT(1009, "账户已被禁用，请联系管理员");

    private int code;

    private String message;
}
