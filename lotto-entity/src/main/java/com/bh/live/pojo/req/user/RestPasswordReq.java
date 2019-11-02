package com.bh.live.pojo.req.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: 用户重置密码
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/13 19:27
 */
@Data
public class RestPasswordReq implements Serializable {
    private static final long serialVersionUID = -7347340614820906534L;

    /**
     * 新密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    /**
     * 确认密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "确认密码", required = true)
    private String confirmPassword;

    @NotBlank(message = "iv不能为空")
    @ApiModelProperty(value = "解密密码的iv", required = true)
    private String iv;
}
