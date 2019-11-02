package com.bh.live.pojo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("发送短信验证码对象")
public class SendMessageReq implements Serializable {

    private static final long serialVersionUID = -1552807130992775166L;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$" ,message = "手机号格式有误")
    @ApiModelProperty("手机号码长度11位")
    private String mobile;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty("短信类型:1 注册  2重置密码 3登录")
    private Integer type;


}
