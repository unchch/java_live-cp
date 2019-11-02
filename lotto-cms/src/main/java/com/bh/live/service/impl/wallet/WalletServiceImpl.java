package com.bh.live.service.impl.wallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.common.utils.SerialNumUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.wallet.WalletDao;
import com.bh.live.model.trade.TradeTransUser;
import com.bh.live.model.wallet.Wallet;
import com.bh.live.service.trade.ITradeTransUserService;
import com.bh.live.service.wallet.IWalletService;

import cn.hutool.core.lang.Assert;

/**
 * <p>
 * 用户金额明细表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-07-27
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletDao, Wallet> implements IWalletService {

	@Resource
	private ITradeTransUserService tradeTransUserService;

	@Override
	public boolean isDoWallet(Integer userId, String doType,String freezeReason) {
		// 根据id查询用户钱包
		Wallet wallet = queryWalletById(userId);
		// 如果用户的提款或者冻结已经被禁止那么就解除，否者反之
		// 操作类型 0:提款 1:冻结
		if ("0".equals(doType)) {
			// 是否可以提款 0不可以 1可用
			Integer isExtract = wallet.getIsExtract() == 0 ? 1 : 0;
			wallet.setIsExtract(isExtract);
		} else if ("1".equals(doType)) {
			// 是否冻结交易 0否 1是 （所有交易受限.如购买方案、打赏、提款等）
			Integer isFreeze = wallet.getIsFreeze() == 0 ? 1 : 0;
			wallet.setIsFreeze(isFreeze);
		}
		return super.updateById(wallet);
	}

	@Transactional
	@Override
	public boolean isDoMoney(String platId, Integer userId, BigDecimal sum, String doType) {
		/**
		 * 实现步骤： 一、查询用户的金额 二、判断用户余额 1、赠送：增加不可提现余额  2、扣款 判断用户总金额是否足够 不足：提示操作人员
		 * 判断用户的不可提现余额是否足够 足够：直接扣款 不足够：扣完不可提现余额，再去口可提现余额 三、更新总余额 四、记录操作流水
		 */
		//流水记录
		TradeTransUser tradeTransUser = new TradeTransUser();
		// 根据id查询用户钱包
		Wallet wallet = queryWalletById(userId);
		BigDecimal allMoney = wallet.getAllMoney();// 总金额
		BigDecimal depositlMoney = wallet.getDepositlMoney();// 可提现
		BigDecimal notdepositlMoney = wallet.getNotdepositlMoney();// 不可提现
		if ("0".equals(doType)) {// 0:扣款
			if (allMoney.compareTo(sum) > 0) {// 总余额充足
				BigDecimal surplus = notdepositlMoney.subtract(sum);
				// 剩余 = 不可提现 - 扣款 ==>> 大于等于0则足够，否者不可提现余额不足
				if (surplus.compareTo(new BigDecimal(0)) >= 0) {
					// 更新不可提现余额
					wallet.setNotdepositlMoney(surplus);
				} else {
					// 不可提现置为0
					wallet.setNotdepositlMoney(new BigDecimal(0));
					// 更新可提现余额
					wallet.setDepositlMoney(depositlMoney.subtract(sum.subtract(notdepositlMoney)));
				}
				wallet.setAllMoney(allMoney.subtract(sum));// 减少总余额
			} else {
				Assert.isFalse(true, "余额不足，无法进行操作");
			}
			tradeTransUser.setTransCode(SerialNumUtil.getSerialNum(TransEnum.TransTypeEnum.SYSTEM_DEDUCTION));
		} else if ("1".equals(doType)) {// 1:赠送
			wallet.setNotdepositlMoney(notdepositlMoney.add(sum));// 增加不可提现余额
			wallet.setAllMoney(allMoney.add(sum));// 增加总余额
			// 交易流水编号用生成方法
			tradeTransUser.setTransCode(SerialNumUtil.getSerialNum(TransEnum.TransTypeEnum.SYSTEM_PRESENTATION));
		}
		// 更新总余额
		super.updateById(wallet);
		// 记录流水

		tradeTransUser.setUserId(wallet.getUserId());// 用户id
		tradeTransUser.setInOut(doType.equals("0") ? 1 : 2);// 支出还是收入：1支出；2收入
		tradeTransUser.setTransType(doType.equals("0") ? 8 : 7);// 交易类型 7：系统赠送 8：系统扣除
		tradeTransUser.setCreateBy(platId);// 操作人员id
		tradeTransUser.setTransAmount(sum);// 交易金额
		tradeTransUser.setTotalCashBalance(wallet.getAllMoney());// 账户总余额
		tradeTransUser.setTransStatus(1);//交易状态
		tradeTransUser.setCashAmount(sum);
		tradeTransUser.setModifyBy(platId);
		tradeTransUser.setCreateTime(new Date());
		tradeTransUser.setUpdateTime(new Date());
		tradeTransUserService.addUserTransFlow(tradeTransUser);
		return true;
	}

	@Override
	public Wallet queryWalletById(Integer userId) {

		return super.getOne(new QueryWrapper<Wallet>().lambda().eq(Wallet::getUserId, userId));
	}

	@Override
	public List<Wallet> queryWalletByUserIds(List<Integer> userIds) {
		QueryWrapper<Wallet> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().in(Wallet::getUserId,userIds);
		return list(queryWrapper);
	}
}
