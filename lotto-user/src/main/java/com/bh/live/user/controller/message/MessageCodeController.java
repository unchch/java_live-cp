package com.bh.live.user.controller.message;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.user.MessageCode;
import com.bh.live.pojo.req.user.SendMessageReq;
import com.bh.live.user.service.IMessageCodeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 * 发送短信类 控制器
 * </p>
 *
 * @author WuLong
 * @since 2019-07-25
 */
@RestController
@Slf4j
@Api(tags = "用户中心-发送短信类")
public class MessageCodeController extends BaseController {
    @Resource
    private IMessageCodeService messageCodeService;

    @ApiOperation(value = "发送短信", response = Result.class)
    @PostMapping("/send/code")
    public Result sendCode(@RequestBody @Valid SendMessageReq sendMessageReq) {
        MessageCodeEnum messageCodeEnum = MessageCodeEnum.getEnumByCode(sendMessageReq.getType());
        if(null == messageCodeEnum){
            return Result.error(CodeMsg.E_10015);
        }
        return messageCodeService.sendMessage(sendMessageReq.getMobile(), messageCodeEnum);
    }

    @ApiOperation(value="获取数据库短信码，开发测试联调阶段使用",response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile",value = "手机号",required = true,dataType = "string"),
            @ApiImplicitParam(name = "type",value="短信类型:1 注册  2重置密码 3登录",required = true,dataType = "int")
    })
    @GetMapping("/get/code")
    public Result getCode(@RequestParam("mobile") String mobile,@RequestParam("type") Integer type){
        MessageCodeEnum messageCodeEnum = MessageCodeEnum.getEnumByCode(type);
        if(null == messageCodeEnum){
            return Result.error(CodeMsg.E_10015);
        }
        return messageCodeService.getMessage(mobile,messageCodeEnum);
    }


    @ApiOperation(value = "校验手机验证码", notes = "校验手机验证码", response = Result.class)
    @GetMapping("/verifyCode")
    public Result verifyCode(@ApiParam("手机号")  @RequestParam("mobile") String mobile,
                             @ApiParam("验证码")  @RequestParam("verifyCode") String verifyCode) {
        //验证验证码是否正确
        QueryWrapper<MessageCode> messageCodeQueryWrapper = new QueryWrapper<>();
        messageCodeQueryWrapper.lambda().eq(MessageCode::getMobile, mobile)
                .eq(MessageCode::getType, MessageCodeEnum.RESET_PASSWORD.getCode())
                .orderByDesc(MessageCode::getId);
        MessageCode messageCode = messageCodeService.getOne(messageCodeQueryWrapper, false);
        if (null == messageCode) {
            return Result.error(CodeMsg.E_10014);
        }
        Date date = new Date();
        Date createTime = messageCode.getCreateTime();
        int differentSecond = DateUtils.differentSeconds(date, createTime);
        //验证验证码是否已过期
        if (differentSecond > 600) {
            return Result.error(CodeMsg.E_10013);
        }
        String code = messageCode.getCode();
        if (!verifyCode.equals(code)) {
            return Result.error(CodeMsg.E_10012);
        }
        return Result.success(1);
    }

}

