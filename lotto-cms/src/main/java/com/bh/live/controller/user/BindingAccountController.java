package com.bh.live.controller.user;


import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.BindingAccount;
import com.bh.live.pojo.req.user.BindingAccountReq;
import com.bh.live.service.user.IBindingAccountService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户绑定账户表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-30
 */
@RestController
@RequestMapping("/bindingAccount")
@Slf4j
@Api(tags = "用户绑定账户")
public class BindingAccountController {

    @Autowired
    private IBindingAccountService accountService;

    /**
     * 账户修改
     *
     * @return
     */
    @ApiOperation(value = "账户修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "账户修改")})
    @PostMapping("/update")
    public Result update(@ApiParam("账户修改入参信息") @RequestBody BindingAccountReq req) {
        try {
            BindingAccount account = new BindingAccount();
            BeanUtils.copyProperties(req, account);
            accountService.updateById(account);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }

    /**
     * 解绑账户
     *
     * @return
     */
    @ApiOperation(value = "解绑账户", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "解绑账户")})
    @PostMapping("/unBind/{userId}")
    public Result unBind(@ApiParam("解绑用户id") @PathVariable("userId") Integer userId) {
        try {
            BindingAccount account = new BindingAccount();
            account.setUserId(userId);
            account.setIsDelete(0);
            accountService.updateById(account);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }

    /**
     * 重置密码
     *
     * @return
     */
    @ApiOperation(value = "重置密码", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "重置密码")})
    @PostMapping("/resetPassword/{userId}")
    public Result resetPassword(@ApiParam("解绑用户id") @PathVariable("userId") Integer userId) {
        try {
            //调用发送密码重置短信接口
            //todo
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }
}

