package com.bh.live.trade.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.model.trade.Order;
import com.bh.live.pojo.req.trade.AwardTradeReq;
import com.bh.live.pojo.req.trade.TradeTransUserLogReq;
import com.bh.live.pojo.req.trade.TradeTransUserReq;
import com.bh.live.pojo.req.trade.TradeUserReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.TradeAllTransUser;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.pojo.res.trade.TradeTransUserRes;
import com.bh.live.trade.dao.TradeTransUserDao;
import com.bh.live.trade.dao.TradeTransUserLogDao;
import com.bh.live.trade.service.ITradeTransUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户流水表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Service
public class TradeTransUserServiceImpl extends ServiceImpl<TradeTransUserDao, TradeTransUserRes>
		implements ITradeTransUserService {

	@Resource
	private TradeTransUserLogDao tradeTransUserLogDao;

	@Resource
	private TradeTransUserDao tradeTransUserDao;

	@Override
	public IPage<TradeTransUserRes> queryUserTransPage(int pageNum, int pageSize,
			TradeTransUserReq tradeTransUser) {
		// 设置查询方式
		LambdaQueryWrapper<TradeTransUserRes> query = new QueryWrapper<TradeTransUserRes>().lambda();
		query.like(TradeTransUserRes::getUserId, tradeTransUser.getTransType());

		return super.page(new Page<TradeTransUserRes>(pageNum, pageSize), query);
	}

	@Override
	public int addUserTransFlow(TradeTransUserReq tradeTransUser) {
		// 参数校验
		Assert.notNull(tradeTransUser.getUserId(), "用户id不能为空");
		Assert.notNull(tradeTransUser.getInOut(), "收入支出状态不能为空");
		Assert.notNull(tradeTransUser.getTransType(), "交易类型不能为空");
		Assert.notNull(tradeTransUser.getCreateBy(), "操作人员id不能为空");
		Assert.notNull(tradeTransUser.getTransAmount(), "交易金额不能为空");
		Assert.notNull(tradeTransUser.getTotalCashBalance(), "账户总余额不能为空");
		// 订单编号调用生成方法
//		tradeTransUser.setOrderCode("");
		// 交易流水编号用生成方法
		tradeTransUser.setTransCode("");
		TransEnum.setInOut(tradeTransUser);
		TradeTransUserLogReq tradeTransUserLogReq = new TradeTransUserLogReq();
		BeanUtils.copyProperties(tradeTransUserLogReq, tradeTransUser);
		tradeTransUserLogDao.insert(tradeTransUserLogReq);
		return baseMapper.insert(null);
	}



	@Override
	public IPage<TradeTransUserRes> queryTransPage(TradeUserReq req) {

		// 设置查询方式
		LambdaQueryWrapper<TradeTransUserRes> query = new QueryWrapper<TradeTransUserRes>().lambda();
		if (req.getUserId() != null) {// 按照用户id查询
			query.eq(TradeTransUserRes::getUserId, req.getUserId());
		}
		if (req.getTransType() != null && req.getTransType() != 0) {// 按照流水类型查询
			query.eq(TradeTransUserRes::getTransType, req.getTransType());
		}
		if (req.getStartTime() != null) {// 大于等于
			query.ge(TradeTransUserRes::getCreateTime, req.getStartTime());
		}
		if (req.getEndTime() != null) {// 小于等于
			query.le(TradeTransUserRes::getCreateTime, req.getEndTime());
		}
		query.orderByDesc(TradeTransUserRes::getCreateTime);

		return super.page(new Page<TradeTransUserRes>(req.getPageNum(), req.getPageSize()), query);
	}

	@Override
	public TradeTransDetailRes queryTransDetailById(String transId) {
		TradeTransUserRes trans = super.getOne(
				new QueryWrapper<TradeTransUserRes>().lambda().eq(TradeTransUserRes::getTransCode, transId));
		TradeTransDetailRes detail = new TradeTransDetailRes();
		if(trans!=null){
			Integer transType = trans.getTransType();// 交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除
			String orderCode = trans.getOrderCode();// 订单编号
			//初始化出参
			detail.setAmount(trans.getTransAmount());// 交易金额
			detail.setInOut(trans.getInOut());// 支入出
			detail.setDateTime(trans.getCreateTime());// 创建时间
			detail.setType(transType);//交易类型
			if (TransEnum.TransTypeEnum.BUY.getCode().equals(transType)) {// 购买推荐
				Order order = tradeTransUserDao.queryOrderByOrderno(orderCode);
				if(order!=null){
					detail.setOrderNo(orderCode);// 订单标号
					detail.setCreateBy(trans.getCreateBy());// 创建人
//					detail.setTargetBy(order.getAccountName());// 发布人
					detail.setLottoSeed(order.getLotSeedName());// 彩种名称
					detail.setReferIssueNo(order.getLotIssueNo());// 彩期
					detail.setContent(order.getContent());// 内容
					detail.setResult(order.getAwardNumber());// 结果
				}
			} else if (transType.equals(TransEnum.TransTypeEnum.SYSTEM_PRESENTATION.getCode())
					|| transType.equals(TransEnum.TransTypeEnum.SYSTEM_DEDUCTION.getCode())) {// 系统扣除/系统赠送
				detail.setResion(trans.getRemark());// 原因
			} else if (transType.equals(TransEnum.TransTypeEnum.REWARD.getCode())) {// 打赏
				if(orderCode!=null){
					Map map = tradeTransUserDao.queryGif(orderCode);
					detail.setOrderNo(orderCode);// 订单标号
					if(map!=null){
						detail.setCreateBy(map.get("createBy").toString());// 创建人
						detail.setTargetBy(map.get("TargetBy").toString());// 发布人
						detail.setAmount(new BigDecimal(map.get("amount").toString()));// 价格
						detail.setCount(map.get("gift_num").toString());// 数量
					}
				}
			}
		}
		return detail;
	}

	@Override
	public TableDataInfo queryTradeAllTransUser(AwardTradeReq req) {
		Page<TradeAllTransUser> iPage = new Page<TradeAllTransUser>(req.getPageNum(), req.getPageSize());
		List<TradeAllTransUser> list = baseMapper.queryTradeAllTransUser(iPage, req);
		iPage.setRecords(list);
		return new TableDataInfo(iPage);
//		return new TableDataInfo(tradeTransUserDao.queryTradeAllTransUser(req),tradeTransUserDao.queryTradeAllTransUserCount(req));
	}
}
