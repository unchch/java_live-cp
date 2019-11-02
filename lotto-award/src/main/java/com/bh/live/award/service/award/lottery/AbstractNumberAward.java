package com.bh.live.award.service.award.lottery;

import com.bh.live.award.service.award.AbstractAward;
import com.bh.live.award.service.award.entity.WinMoneyBO;
import com.bh.live.award.utils.MatchUtil;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @desc 数字彩开奖抽象
 * @author WuLong
 * @date 2017年5月25日

 * @version 1.0s
 */
@Slf4j
public abstract class AbstractNumberAward extends AbstractAward {
	// 扣税金额
	private static int TAX_DEDUCTION = 10000;
	// 各种玩法的奖金
	protected Map<String, Integer> draw;
	// 数字彩追加
	protected Map<String, Integer> addDraw;

	@Override
	protected void getDrawCode(List<Order> orders) {
		/*if (haveDrawCode()) {
			return;
		}
		synchronized (this) {
			if (!haveDrawCode()) {
				OrderInfoBO bo = null;
				if (orderInfoBOs != null && (bo = orderInfoBOs.get(0)) != null) {
					LotteryIssueBO issue = orderService.getLotteryIssue(bo.getLotteryCode(), bo.getLotteryIssue());
					if (StringUtils.isEmpty(issue.getDrawCode())) {
						throw new ServiceRuntimeException("不存在开奖号码");
					}
					if (StringUtils.isEmpty(issue.getDrawDetail())) {
						throw new ServiceRuntimeException("不存在开奖奖项详情");
					}
					drawCode = issue.getDrawCode();
					handleDrawCode(drawCode);
					handleDrawDetail(issue.getDrawDetail());
				}
			}

		}*/
	}

	/**
	 * 处理开奖号码
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年5月25日 下午2:35:40
	 * @param code
	 */
	protected abstract void handleDrawCode(String code);

	/**
	 * 处理开奖详情
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年5月26日 上午10:21:08
	 * @param drawDetail
	 */
	protected abstract void handleDrawDetail(String drawDetail);

	/**
	 * 判断是否已经获取解析了开奖号码
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年5月25日 下午2:39:48
	 * @return
	 */
	protected abstract boolean haveDrawCode();

	/**
	 * 拆分中奖详情
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年5月26日 上午10:29:14
	 * @param drawDetail
	 *            格式 一等奖|2|10000000,二等奖|5|200000
	 * @param keyNum
	 *            表示奖项位数
	 * @param valueNum
	 *            金额位数
	 * @return
	 */
	protected Map<String, Integer> splitDrawDetail(String drawDetail, int keyNum, int valueNum) {
		Map<String, Integer> map = new HashMap<>();
		String[] details = drawDetail.split(SymbolConstants.COMMA);
		for (String detail : details) {
			String[] str = detail.split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
			if (str.length < keyNum + 1) {
				continue;
			}
			if (str.length < valueNum + 1) {
				continue;
			}
			try {
				String money = str[valueNum].trim();
				if(StringUtils.isBlank(money)){
					money = "0";
					log.info("开奖奖项【"+str[keyNum].trim()+"】不存在奖项金额，默认奖金为0进行开奖");
				}
				map.put(str[keyNum].trim(), Integer.parseInt(money));
			} catch (NumberFormatException e) {
				log.info("开奖奖项【"+str[keyNum].trim()+"】奖金转换错误："+str[valueNum],e);
			}
		}
		return map;
	}

	/**
	 * 组装中奖详情
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年5月26日 下午4:11:44
	 * @param detail
	 * @return
	 */
	protected String connectWinDetail(Map<String, Integer> detail) {
		if (CommonUtil.isEmpty(detail)) {
			return "";
		}
		// 三等奖_5注，四等奖_12注
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Integer> entry : detail.entrySet()) {
			if (sb.length() > 0) {
				sb.append(SymbolConstants.COMMA);
			}
			sb.append(entry.getKey());
			sb.append(SymbolConstants.UNDERLINE);
			sb.append(entry.getValue());
			sb.append("注");
		}
		return sb.toString();
	}

	/**
	 * 扣税金额计算
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年5月27日 下午3:14:22
	 * @param money
	 * @return
	 */
	protected double taxMoney(double money) {
		if (money < TAX_DEDUCTION) {
			return 0;
		}
		return MatchUtil.mul(money, 0.2);
	}

	/**
	 *@description 计算金额
	 *@author WuLong
	 *@date 2019/8/5 21:21
	 *@param
	 *@return
	 *@exception
	 */
	protected WinMoneyBO computeMoney(Map<String, Integer> prize, int multipleNum, boolean isAddMoney) {
		Map<String, Integer> winningDetail = new HashMap<>();
		// 税前
		double preBonus = 0;
		// 税
		double taxBonus = 0.0;
		// 追加
		double addBonus = 0.0;
		for (Map.Entry<String, Integer> entry : prize.entrySet()) {
			Integer count = entry.getValue();
			if(count == null || entry.getValue() == 0){
				continue;
			}
			String level = getLevel(entry.getKey());
			if (level == null) {
				continue;
			}
			Integer money = getLevelMoney(level);
			if (money == null) {
				throw new ServiceRuntimeException(CodeMsg.E_80004);
			}
			int nm = multipleNum * count;
			preBonus = MatchUtil.sum(preBonus, money * nm);
			if (isAddMoney) {
				// 追加金额
				Integer addMoney = getAddBonus(money, level);
				if (addMoney != null && addMoney.intValue() > 0) {
					// 追加总金额
					double addMoneyNm = MatchUtil.mul(addMoney, nm);
					addBonus = MatchUtil.sum(addBonus, addMoneyNm);
					preBonus = MatchUtil.sum(preBonus, addMoneyNm);
					double taxAddMoney = taxMoney(addMoney);
					if (taxAddMoney > 0) {
						taxAddMoney = MatchUtil.mul(taxAddMoney, nm);
						taxBonus = MatchUtil.sum(taxBonus, taxAddMoney);
					}
				}
			}
			double taxMoney = taxMoney(money);
			if (taxMoney > 0) {
				taxMoney = MatchUtil.mul(taxMoney, nm);
				taxBonus = MatchUtil.sum(taxBonus, taxMoney);
			}
			Integer num = winningDetail.get(level);
			if (num == null) {
				num = 0;
			}
			winningDetail.put(level, count + num);
		}
		double aftBonus = preBonus;
		if (taxBonus > 0) {
			aftBonus = MatchUtil.sub(aftBonus, taxBonus);
		}
		return new WinMoneyBO(preBonus, aftBonus, addBonus,null,null, winningDetail);
	}

	@Override
	protected void compute(Order order, HandleEnum.PrizeStatusEnum prizeStatusEnum) {
		/*orderInfoBO.setDrawCode(drawCode);
		List<TicketInfoBO> details = orderInfoBO.getTicketInfoBOs();
		// 中奖等级
		Set<String> totalLevel = new HashSet<>();
		// 中奖总金额
		double totalMoney = 0, totalAfterMoney = 0, totalAddedBonus = 0;
		for (TicketInfoBO detail : details) {
			WinMoneyBO win = computeWinMoney(detail);
			if (!CollectionUtils.isEmpty(win.getWinningDetail())) {
				detail.setWinningStatus(OrderWinningStatus.WINNING.getValue());
				totalMoney = MatchUtil.sum(totalMoney, win.getPreBonus());
				totalAfterMoney = MatchUtil.sum(totalAfterMoney, win.getAftBonus());
				totalAddedBonus = MatchUtil.sum(totalAddedBonus, win.getAddedBonus());
				totalLevel.addAll(win.getWinningDetail().keySet());
			} else {
				detail.setWinningStatus(OrderWinningStatus.NOT_WINNING.getValue());
			}
			detail.setPreBonus(win.getPreBonus());
			detail.setAftBonus(win.getAftBonus());
			detail.setAddedBonus(win.getAddedBonus());
			detail.setWinningDetail(connectWinDetail(win.getWinningDetail()));
		}
		short orderWinStatus = totalLevel.size() > 0 ? OrderWinningStatus.WINNING.getValue()
				: OrderWinningStatus.NOT_WINNING.getValue();
		String winDetail = org.springframework.util.StringUtils.collectionToDelimitedString(totalLevel,
				SymbolConstants.COMMA);
		orderInfoBO.setWinningDetail(winDetail);
		orderInfoBO.setPreBonus(totalMoney);
		orderInfoBO.setAftBonus(totalAfterMoney);
		orderInfoBO.setAddedBonus(totalAddedBonus);
		orderInfoBO.setWinningStatus(orderWinStatus);*/
	}

	/**
	 * 中奖
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年6月8日 下午5:51:21
	 * @param prize
	 * @param num
	 * @param multipleNum
	 * @return
	 */
	protected WinMoneyBO getWinMoney(String prize, int num, int multipleNum) {
		Map<String, Integer> result = new HashMap<>();
		result.put(prize, num);
		return computeMoney(result, multipleNum, true);
	}

	/**
	 * 获取未未中奖
	 *
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年6月8日 下午5:50:50
	 * @return
	 */
	protected WinMoneyBO getNotWinMoney() {
		Map<String, Integer> result = new HashMap<>();
		return computeMoney(result, 0, false);
	}

	/**
	 *@description 计算单个票的中奖信息
	 *@author WuLong
	 *@date 2019/8/5 21:29
	 *@param orderInfo 投注订单详情
	 *@return WinMoneyBO
	 *@exception
	 */
	protected abstract WinMoneyBO computeWinMoney(OrderInfo orderInfo);

	/**
	 *@description 中奖等级对应的中奖的金额 一等奖 ： 5000000
	 *@author WuLong
	 *@date 2019/8/5 21:30
	 *@param level 级别
	 *@return Integer
	 */
	protected Integer getLevelMoney(String level) {
		return draw.get(level);
	}

	/**
	 *@description 通过奖项获取中奖等级如：双色球：6-1 一等奖 获取等级
	 *@author WuLong
	 *@date 2019/8/5 21:30
	 *@param prize
	 *@return String
	 */
	protected abstract String getLevel(String prize);

	/**
	 *@description 计算获取最追加金额
	 *@author WuLong
	 *@date 2019/8/5 21:30
	 *@param money 中奖金额
	 *@param  level  奖项级别
	 *@return Integer
	 */
	protected Integer getAddBonus(int money, String level) {
		if (addDraw != null) {
			return addDraw.get(level);
		}
		return null;
	};
}
