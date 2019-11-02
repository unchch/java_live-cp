package com.bh.live.user.controller.login;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.pojo.req.user.LoginReq;
import com.bh.live.user.service.ILiveUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "用户中心-登录类")
public class LoginController extends BaseController {
    @Autowired
    ILiveUserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口", notes = "登录")
    public Result login(@RequestBody @ApiParam(name = "登录", value = "登录对象") @Valid LoginReq loginReq) {
        loginReq.setIp(getAddr());
        loginReq.setTerminal(getDevice().getCode());
        loginReq.setUserAgent(getUserAgent());
        if(loginReq.getMode().equals(UserEnum.LoginModeEnum.PASSWORD.getCode())){
            Result result = decrypt(loginReq.getPassword(), loginReq.getIv());
            if (CodeMsg.SUCCESS.code != result.getCode()) {
                return result;
            }
            String password = result.getData().toString();
            String regex = "(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*()_+`\\-={}:\";'<>?,.\\/]+)$)^[\\w~!@#$%^&*()_+`\\-={}:\";'<>?,.\\/]{6,20}$";
            if (!password.matches(regex)) {
                return Result.error(CodeMsg.E_10021);
            }
            loginReq.setPassword(result.getData().toString());
        }else{
            if(CommonUtil.isEmpty(loginReq.getVerifyCode())){
                return Result.error(CodeMsg.E_10024);
            }
            String verifyCodeRegex = "^\\d{6}$";
            if(!loginReq.getVerifyCode().matches(verifyCodeRegex)){
                return Result.error(CodeMsg.E_10025);
            }
        }
        return userService.login(loginReq);
    }
}
