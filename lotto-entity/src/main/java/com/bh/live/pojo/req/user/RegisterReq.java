package com.bh.live.pojo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @Author: WuLong
 * @Date: 2019/7/16 22:01
 * @Description: 用户接收参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("注册对象")
public class RegisterReq implements Serializable {
    private static final long serialVersionUID = -8305606177672269780L;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码,6-20个字符（包含数字，字符，特殊字符其中两种）", required = true)
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$", message = "手机号格式有误")
    @ApiModelProperty(value = "手机号码长度11位", required = true)
    private String mobile;

    @NotBlank(message = "短信验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "短信验证码格式有误")
    @ApiModelProperty(value = "短信验证码",required = true)
    private String verifyCode;

    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "[a-zA-Z0-9\\u4E00-\\u9FA5]{2,6}$",message = "昵称格式有误")
    @ApiModelProperty(value = "昵称",required = true)
    private String nickName;
    /**
     * 用户注册ip
     */
    @ApiModelProperty(value = "用户注册ip", hidden = true)
    private String lastip;

    /**
     * 注册渠道：1pc  2h5  3ios  4android
     */
    @NotNull(message = "注册渠道不能为空")
    @ApiModelProperty(value = "注册渠道：1pc  2h5  3ios  4android", required = true)
    private Integer channel;

    @NotBlank(message = "iv不能为空")
    @ApiModelProperty(value = "解密密码的iv", required = true)
    private String iv;

}
