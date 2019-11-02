package com.bh.live.pojo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *@Author: WuLong
 *@Date: 2019/7/16 22:01
 *@Description: 用户接收参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登录对象")
public class LoginReq implements Serializable {

    private static final long serialVersionUID = -5247224285951357898L;
    @ApiModelProperty(value = "密码,6-20个字符（包含数字，字符，特殊字符其中两种）")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$", message = "手机号格式有误")
    @ApiModelProperty(value = "手机号码长度11位", required = true)
    private String mobile;

    @ApiModelProperty(value = "登录ip",hidden = true)
    private String ip;

    @ApiModelProperty(value = "解密密码的iv")
    private String iv;

    @ApiModelProperty(value = "一周内自动登录 0否 1是")
    private Integer auto = 0;

    @ApiModelProperty(value = "登录短信验证码")
    private String verifyCode;

    @NotNull(message = "登录方式不能为空")
    @ApiModelProperty(value="登录方式 0密码登录 1短信验证码登录",required = true)
    private Integer mode;

    @ApiModelProperty(value = "登录终端",hidden = true)
    private String terminal;

    @ApiModelProperty(value = "访问设备UserAgent",hidden = true)
    private String userAgent;

}
