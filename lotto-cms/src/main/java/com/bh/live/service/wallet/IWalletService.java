package com.bh.live.service.wallet;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.wallet.Wallet;

/**
 * <p>
 * 用户金额明细表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-07-27
 */
public interface IWalletService extends IService<Wallet> {
	/**
	 * 限制的钱包可操作
	 * 
	 * @param userId
	 * @param doType 0:提款 1:冻结
	 * @param freezeReason 冻结原因
	 * @return
	 */
	public boolean isDoWallet(Integer userId, String doType,String freezeReason);

	/**
	 * 赠送/扣款的操作
	 * 
	 * @param platId
	 * @param userId
	 * @param sum
	 * @param doType 0:扣款 1:赠送
	 * @return
	 */
	public boolean isDoMoney(String platId, Integer userId, BigDecimal sum, String doType);

	/**
	 * 查询用户钱包
	 * @param userId
	 * @return
	 */
	public Wallet queryWalletById(Integer userId);

	/**
	 *@description 批量查询用户钱包
	 *@author WuLong
	 *@date 2019/8/8 16:02
	 *@param userIds 用户ID 集合
	 *@return List<Wallet>
	 */
	List<Wallet> queryWalletByUserIds(List<Integer> userIds);
	
	
}
