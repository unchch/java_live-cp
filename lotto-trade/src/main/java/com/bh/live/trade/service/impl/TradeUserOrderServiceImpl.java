package com.bh.live.trade.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.TradeUserOrder;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeUserOrderReq;
import com.bh.live.pojo.res.trade.AwardOrderRes;
import com.bh.live.pojo.res.trade.TradeUserOrderRes;
import com.bh.live.trade.dao.TradeUserOrderDao;
import com.bh.live.trade.service.IOrderService;
import com.bh.live.trade.service.ITradeUserOrderService;
import com.bh.live.trade.service.IUserTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户购买记录 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Service
@Slf4j
public class TradeUserOrderServiceImpl extends ServiceImpl<TradeUserOrderDao, TradeUserOrder> implements ITradeUserOrderService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserTradeService userTradeService;

    @Override
    public List<TradeUserOrderRes> selectUserByOrder(Wrapper<TradeUserOrderReq> userWrapper) {
        return baseMapper.selectUserByOrder(userWrapper);
    }

    @Override
    public int selectOrderCount(Wrapper<TradeUserOrderReq> userWrapper) {
        return baseMapper.selectOrderCount(userWrapper);
    }

    @Override
    public int insertUserOrder(Order order, LiveUser user) {
        Assert.isFalse(order.getSaleEndTime().getTime() < System.currentTimeMillis(), CodeMsg.E_50107);
        LambdaQueryWrapper<TradeUserOrder> tradeUserOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tradeUserOrderLambdaQueryWrapper.eq(TradeUserOrder::getOrderCode, order.getOrderNo()).eq(TradeUserOrder::getUserId, user.getId());
        List<TradeUserOrder> count = baseMapper.selectList(tradeUserOrderLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(count)) {
            return 1;
        }
        BigDecimal payAmount = order.getPayAmount();
        String serialCode = userTradeService.updateUserWallet(user, payAmount, TransEnum.TransTypeEnum.BUY, order.getOrderNo(), 1);
        TradeUserOrder tradeUserOrder = new TradeUserOrder();
        tradeUserOrder.setOrderCode(order.getOrderNo());
        tradeUserOrder.setSerialCode(serialCode);
        tradeUserOrder.setUserId(user.getId());
        tradeUserOrder.setAmount(payAmount);
        tradeUserOrder.setCreateBy("admin");
        tradeUserOrder.setModifyBy("admin");
        tradeUserOrder.setCreateTime(new Date());
        tradeUserOrder.setUpdateTime(new Date());
        int num = baseMapper.insert(tradeUserOrder);
        log.info("购买竞猜：{}", tradeUserOrder.toString());
        return num;
    }

    @Override
    public List<AwardOrderRes> updateAwardOrder(Map<String, Object> map) {
        log.info("派奖参数：{}", JsonUtil.obj2json(map));
        String issue = MapUtil.getStr(map, "issueNo");
        Integer seedNo = MapUtil.getInt(map, "seedNo");
        Integer type = MapUtil.getInt(map, "type");
        List<AwardOrderRes> list = baseMapper.selectAwardOrderRes(issue, seedNo);
        switch (HandleEnum.AwardTypeEnum.getEnumByCode(type)) {
            case OPEN_AWARD:
                log.info("一共查询出来{}条数据", list.size());
                List<AwardOrderRes> amountList = list.stream()
                        .filter(item -> item.getAmount().compareTo(new BigDecimal(0)) > 0)
                        .collect(Collectors.toList());
                Map<Integer, List<AwardOrderRes>> accountMap = amountList.stream().collect(Collectors.groupingBy(AwardOrderRes::getAccountId));
                log.info("需要给{}位用户加钱", accountMap.size());
                accountMap.forEach((k, v) -> {
                    try {
                        BigDecimal team = v.stream().map(AwardOrderRes::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                        LiveUser user = new LiveUser();
                        user.setId(k);
                        BigDecimal amount = team.multiply(new BigDecimal(0.5));
                        String serialNum = userTradeService.updateUserWallet(user, amount, TransEnum.TransTypeEnum.RETURN_PRIZE, null, null);
                    } catch (Exception e) {
                        log.info("派奖出错：派奖用户ID{},派奖参数{}", k, v.toString());
                    }
                });
                break;
            case REST_OPEN_AWARD:
                //todo 重置开奖处理
                break;
            default:
        }
        return list;
    }
}
