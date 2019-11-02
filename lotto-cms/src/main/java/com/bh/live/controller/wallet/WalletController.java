package com.bh.live.controller.wallet;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.service.wallet.IWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 用户金额明细表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-07-27
 */
@RestController
@Api(tags = "用户金额明细")
@RequestMapping("/wallet")
public class WalletController extends BaseController {

	@Resource
	private IWalletService walletService;

	@ApiOperation(value = "冻结或解冻用户钱包（提款操作）", response = Boolean.class)
	@GetMapping("/isUsableLiveUser")
	public Result isProhibitLiveUser(@ApiParam("用户id")@RequestParam(value = "userId", required = true) Integer userId,
			@ApiParam("操作类型 0:提款 1:冻结")@RequestParam(value = "doType", required = true) String doType,
			@ApiParam("冻结原因")@RequestParam(value = "freezeReason") String freezeReason) {

		return Result.success(walletService.isDoWallet(userId, doType, freezeReason));
	}

	@ApiOperation(value = "赠送/扣款的操作", response = Boolean.class)
	@GetMapping("/isDoMoney")
	public Result isDoMoney(@ApiParam("用户id") @RequestParam(value = "userId", required = true) Integer userId,
			@ApiParam("操作类型 0:扣款 1:赠送") @RequestParam(value = "doType", required = true) String doType,
			@ApiParam("操作金额") @RequestParam(value = "sum", required = true) BigDecimal sum) {

		return Result.success(walletService.isDoMoney(ShiroSubUtil.getLoginName(), userId, sum, doType));
	}

}
