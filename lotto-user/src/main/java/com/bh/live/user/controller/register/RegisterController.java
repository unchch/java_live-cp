package com.bh.live.user.controller.register;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.pojo.req.user.RegisterReq;
import com.bh.live.user.service.ILiveUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *@author WuLong
 *@date 2019/7/25 14:00
 *@description 注册
 */
@RestController
@Api(tags = "用户中心-注册类")
public class RegisterController extends BaseController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ILiveUserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册接口", notes = "注册")
    @ApiParam(name = "注册", value = "注册对象")
    public Result register(@RequestBody @Valid RegisterReq registerReq){
        String host = getAddr();
        registerReq.setLastip(host);
        Result result = decrypt(registerReq.getPassword(), registerReq.getIv());
        if(CodeMsg.SUCCESS.code != result.getCode()){
            return result;
        }
        String password = result.getData().toString();
        String regex = "(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*()_+`\\-={}:\";'<>?,.\\/]+)$)^[\\w~!@#$%^&*()_+`\\-={}:\";'<>?,.\\/]{6,20}$";
        if (!password.matches(regex)) {
            return Result.error(CodeMsg.E_10029);
        }
        registerReq.setPassword(password);
        return userService.register(registerReq);
    }
}
