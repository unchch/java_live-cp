package com.bh.live.service.impl.trade;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CopyUtil;
import com.bh.live.common.utils.SerialNumUtil;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.dao.trade.TradeTransUserDao;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.TradeTransUser;
import com.bh.live.model.trade.TradeTransUserData;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.pojo.res.trade.TradeTransExcelRes;
import com.bh.live.service.trade.ITradeTransUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Slf4j
public class TradeTransUserServiceImpl extends ServiceImpl<TradeTransUserDao, TradeTransUser>
        implements ITradeTransUserService {

    @Resource
    private TradeTransUserDao tradeTransUserDao;

    @Override
    public TableDataInfo<TradeTransUser> queryUserTransPage(TradeTransUser tradeTransUser) {
        // 设置查询方式
        LambdaQueryWrapper<TradeTransUser> query = new QueryWrapper<TradeTransUser>().lambda();
        if (ObjectUtil.isNotEmpty(tradeTransUser.getUserId())) {
            query.eq(TradeTransUser::getUserId, tradeTransUser.getUserId());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getTransType())) {
            query.eq(TradeTransUser::getTransType, tradeTransUser.getTransType());
        }
        if (StringUtils.isNotEmpty(tradeTransUser.getBeginTime())
                && StringUtils.isNotEmpty(tradeTransUser.getEndTime())) {
            query.between(TradeTransUser::getCreateTime, tradeTransUser.getBeginTime(),
                    tradeTransUser.getEndTime());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getUserId())) {
            query.eq(TradeTransUser::getUserId, tradeTransUser.getUserId());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getOrderCode())) {
            query.eq(TradeTransUser::getOrderCode, tradeTransUser.getOrderCode());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getTransCode())) {
            query.eq(TradeTransUser::getTransCode, tradeTransUser.getTransCode());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getInOut())) {
            query.eq(TradeTransUser::getInOut, tradeTransUser.getInOut());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getTransStatus())) {
            query.eq(TradeTransUser::getTransStatus, tradeTransUser.getTransStatus());
        }
        query.orderByDesc(TradeTransUser::getCreateTime);
        return new TableDataInfo<>(super.page(
                new Page<>(tradeTransUser.getPageNum(), tradeTransUser.getPageSize()), query));
    }

    @Override
    public boolean addUserTransFlow(TradeTransUser tradeTransUser) {
        // 参数校验
        Assert.notNull(tradeTransUser.getUserId(), CodeMsg.E_50111.getMessage());
        Assert.notNull(tradeTransUser.getInOut(), CodeMsg.E_50112.getMessage());
        Assert.notNull(tradeTransUser.getTransType(), CodeMsg.E_50113.getMessage());
        Assert.notNull(tradeTransUser.getCreateBy(), CodeMsg.E_50114.getMessage());
        Assert.notNull(tradeTransUser.getTransAmount(), CodeMsg.E_50115.getMessage());
        Assert.notNull(tradeTransUser.getTotalCashBalance(), CodeMsg.E_50116.getMessage());
        // 订单编号调用生成方法
        tradeTransUser.setOrderCode(SerialNumUtil.getOrderNo());
        return super.save(tradeTransUser);
    }

    @Override
    public TradeTransUserData flowAllData(TradeTransUser tradeTransUser) {
        //交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成
        List<TradeTransUser> list = queryUserTransPage(tradeTransUser).getList();
        com.bh.live.common.exception.Assert.collectIsNotNull(list, CodeMsg.E_100006);
        TradeTransUserData date = new TradeTransUserData();
        // 订单总数
        date.setTransAll(Long.valueOf(list.size()));
        // 充值总额
        date.setRechargeAll(list.stream().filter(e -> e.getTransType() == 1).mapToDouble(e -> e.getTransAmount().longValue()).summaryStatistics().getSum());
        // 打赏总额
        date.setGiveAll(list.stream().filter(e -> e.getTransType() == 2).mapToDouble(e -> e.getTransAmount().longValue()).summaryStatistics().getSum());
        // 购买竞猜总额
        date.setBuyAll(list.stream().filter(e -> e.getTransType() == 6).mapToDouble(e -> e.getTransAmount().longValue()).summaryStatistics().getSum());
        return date;
    }

    @Override
    public TradeTransDetailRes queryTransDetailById(String transId) {
        TradeTransUser trans = super.getOne(new QueryWrapper<TradeTransUser>().lambda().eq(TradeTransUser::getTransCode, transId));
        com.bh.live.common.exception.Assert.isNotNull(trans, CodeMsg.E_50110);
        TradeTransDetailRes detail = new TradeTransDetailRes();
        if (trans != null) {
            Integer transType = trans.getTransType();// 交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除
            String orderCode = trans.getOrderCode();// 订单编号
            detail.setAmount(trans.getTransAmount());// 交易金额
            detail.setInOut(trans.getInOut());// 支入出
            detail.setDateTime(trans.getCreateTime());// 创建时间
            detail.setType(transType);//交易类型
            detail.setCreateBy(trans.getCreateBy());// 创建人
			detail.setOrderNo(orderCode);// 订单标号
			detail.setResion(trans.getRemark());// 原因
			if (TransEnum.TransTypeEnum.BUY.getCode().equals(transType)) {// 购买推荐
				Order order = tradeTransUserDao.queryOrderByOrderno(orderCode);
				if(order!=null){
//					detail.setTargetBy(order.getAccountName());// 发布人
					detail.setLottoSeed(order.getLotSeedName());// 彩种名称
					detail.setReferIssueNo(order.getLotIssueNo());// 彩期
					detail.setContent(order.getContent());// 内容
					detail.setResult(order.getAwardNumber());// 结果
				}
			} else if (TransEnum.TransTypeEnum.REWARD.getCode().equals(transType)) {// 打赏
                Map<String, String> map = tradeTransUserDao.queryGif(transId);
                if (map != null) {
                    detail.setOrderNo(orderCode);// 订单标号
                    detail.setTargetBy(map.get("TargetBy"));// 发布人
//                    detail.setAmount(map.get("amount"));// 价格
                    detail.setCount(String.valueOf(map.get("gift_num")));// 数量
                    detail.setName(map.get("gift_name"));//礼物名称
                }
            }
        }
        return detail;
    }

    @Override
    public List<TradeTransExcelRes> queryUserTransExcel(TradeTransUser tradeTransUser) {

        // 设置查询方式
        LambdaQueryWrapper<TradeTransUser> query = new QueryWrapper<TradeTransUser>().lambda();
        if (ObjectUtil.isNotEmpty(tradeTransUser.getUserId())) {
            query.eq(TradeTransUser::getUserId, tradeTransUser.getUserId());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getTransType())) {
            query.eq(TradeTransUser::getTransType, tradeTransUser.getTransType());
        }
        if (StringUtils.isNotEmpty(tradeTransUser.getBeginTime())
                && StringUtils.isNotEmpty(tradeTransUser.getEndTime())) {
            query.between(TradeTransUser::getCreateTime, tradeTransUser.getBeginTime(),
                    tradeTransUser.getEndTime());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getUserId())) {
            query.eq(TradeTransUser::getUserId, tradeTransUser.getUserId());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getOrderCode())) {
            query.eq(TradeTransUser::getOrderCode, tradeTransUser.getOrderCode());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getTransCode())) {
            query.eq(TradeTransUser::getTransCode, tradeTransUser.getTransCode());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getInOut())) {
            query.eq(TradeTransUser::getInOut, tradeTransUser.getInOut());
        }
        if (ObjectUtil.isNotEmpty(tradeTransUser.getTransStatus())) {
            query.eq(TradeTransUser::getTransStatus, tradeTransUser.getTransStatus());
        }
        query.orderByDesc(TradeTransUser::getCreateTime);
        List<TradeTransUser> tradeTransUsers = getBaseMapper().selectList(query);
        List<TradeTransExcelRes> excelRes = CopyUtil.copyPropertiesList(TradeTransExcelRes.class,tradeTransUsers);
        excelRes.forEach(item -> {
            item.setTransTypeName(TransEnum.TransTypeEnum.valueOf(item.getTransType()).getDesc());

//            item.setInOutName(TransEnum. IncomeExpenditureEnum.valueOf(item.getInOut()).getDesc());

            if(item.getInOut().equals(TransEnum.IncomeExpenditureEnum.INCOME.getCode())){
                String monoey = "-" + item.getTransAmount();
                item.setTransAmountStr(monoey);
            }else{
                String monoey = "+" + item.getTransAmount();
                item.setTransAmountStr(monoey);
            }
        });
        return excelRes;
    }

}
