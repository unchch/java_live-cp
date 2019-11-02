package com.bh.live.controller.user;


import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.user.LoginSuccessfulStatus;
import com.bh.live.service.user.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.bh.live.common.result.Result.success;

/**
 * <p>
 * 用户金额明细表 前端控制器
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private IWalletService iWalletService;
    @GetMapping("/loginSuccessfulStatus")
    public Result<LoginSuccessfulStatus> queryLoginSuccessfulStatus(LiveUser user){
        LoginSuccessfulStatus loginSuccessfulStatus = iWalletService.queryLoginSuccessfulStatus(user);
        return Result.success(loginSuccessfulStatus);
    }
}

