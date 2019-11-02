package com.bh.live.award.service.award;

import com.bh.live.award.service.*;
import com.bh.live.award.service.award.entity.AwardOrderBO;
import com.bh.live.award.service.award.entity.FailOrderBO;
import com.bh.live.award.service.award.lottery.IMatchPrize;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.enums.trade.OrderEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderInfo;
import com.bh.live.model.trade.OrderInfoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WuLong
 * @desc 公共处理类 实现cloneable 使用原型设计模式，对对象进行深度复制
 * @date 2019/8/5 20:08
 */
public abstract class AbstractAward implements IAward, IMatchPrize, Cloneable {

    protected static Logger log = LoggerFactory.getLogger(AbstractAward.class);
    @Autowired
    protected IOrderService orderService;
    @Autowired
    protected IIssueService issueService;
    @Autowired
    protected IPlayService playService;
    @Autowired
    protected IPlayBetService playBetService;
    @Autowired
    protected IPlayBitService playBitService;
    @Autowired
    protected IPlayItemService playItemService;
    @Autowired
    protected IOrderInfoService orderInfoService;
    @Autowired
    protected IOrderInfoDetailService orderInfoDetailService;
    /**
     * 执行总时间cpu
     */
    private static AtomicLong TIME_CPU = new AtomicLong(0);
    /**
     * 执行总时间IO
     */
    private static AtomicLong TIME_IO = new AtomicLong(0);

    protected String drawCode;
    public static final Map<Integer, List<String>> FLUCTUATE;

    static {
        // 浮动奖
        FLUCTUATE = new HashMap<>();
    }

    @Override
    public String getDrawCode() {
        return this.drawCode;
    }

    @Override
    public AwardOrderBO handle(List<Order> orders, boolean isBonus, int type, HandleEnum.PrizeStatusEnum prizeStatusEnum) throws ServiceRuntimeException {
        AwardOrderBO result = new AwardOrderBO();
        List<FailOrderBO> fail = new ArrayList<>();
        result.setFail(fail);
        if (CollectionUtils.isEmpty(orders)) {
            return result;
        }
        log.info("进行开奖订单：" + orders);
        getDrawCode(orders);
        getResultOdds();
        List<String> win = new ArrayList<>();
        List<String> notWin = new ArrayList<>();
        //订单状态
        Integer orderStatus = OrderEnum.OrderStatusEnum.WINNING.getCode();
        //转换成订单map集合
        Map<String, Order> orderMap = orders.stream().collect(Collectors.toMap(Order::getOrderNo, Function.identity(), (key1, key2) -> key2));
        //订单编号list集合
        List<String> orderNos = orders.stream().map(Order::getOrderNo).collect(Collectors.toList());
        //订单投注表list集合
        List<OrderInfo> orderInfos = orderInfoService.selectByOrderNos(orderNos, null);
        //订单投注map集合
        Map<String, List<OrderInfo>> orderInfoMap = orderInfos.stream().collect(Collectors.toMap(OrderInfo::getOrderNo,
                p -> {
                    List<OrderInfo> orderInfoList = new ArrayList<>();
                    orderInfoList.add(p);
                    return orderInfoList;
                },
                (List<OrderInfo> value1, List<OrderInfo> value2) -> {
                    value1.addAll(value2);
                    return value1;
                }
        ));
        for (String key : orderMap.keySet()) {
            try {
                long start = System.currentTimeMillis();
                Order order = orderMap.get(key);
                List<OrderInfo> orderInfoList = orderInfoMap.get(key);
                if (CollectionUtils.isEmpty(orderInfoList)) {
                    continue;
                }
                order.setOrderInfos(orderInfoList);
                //执行开奖
                compute(order, prizeStatusEnum);
                long end = System.currentTimeMillis();
                //更新订单
                orderService.updateById(order);
                orderInfoService.updateBatchById(order.getOrderInfos());
                if (prizeStatusEnum == HandleEnum.PrizeStatusEnum.RESET) {
                    List<OrderInfoDetail> orderInfoDetails = convertToOrderInfoDetail(orderInfoList,order);
                    if(CommonUtil.isNotEmpty(orderInfoDetails)){
                        orderInfoDetailService.saveBatch(orderInfoDetails);
                    }
                }
                //计算cpu
                TIME_CPU.addAndGet(end - start);
                //计算IO
                TIME_IO.addAndGet(System.currentTimeMillis() - end);
                //中奖
                if (Objects.equals(orderStatus, order.getStatus())) {
                    win.add(key);
                } else {
                    notWin.add(key);
                }
            } catch (ServiceRuntimeException e) {
                fail.add(new FailOrderBO(key, e.getMessage()));
                log.error("订单开奖失败" + key, e);
            } catch (Exception e) {
                fail.add(new FailOrderBO(key, "服务器异常"));
                log.error("订单开奖失败" + key, e);
            }
        }
        // 发送中奖和不中奖通知
        if (!win.isEmpty()) {
            result.setWinCount(win.size());
        }
        if (!notWin.isEmpty()) {
            //message.sendNotWin(notWin);
        }
        return result;
    }

    /**
     * @description 把所有的注单重要信息备份到orderInfoDetail
     * @author WuLong
     * @date 2019/8/15 18:14
     * @param orderInfoList 注单
     * @return java.util.List<com.bh.live.model.trade.OrderInfoDetail>
     */
    private List<OrderInfoDetail> convertToOrderInfoDetail(List<OrderInfo> orderInfoList,Order order){
        List<OrderInfoDetail> orderInfoDetails = new ArrayList<>();
        for(OrderInfo orderInfo : orderInfoList){
            OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
            orderInfoDetail.setAwardAmount(orderInfo.getAwardAmount());
            orderInfoDetail.setAwardState(orderInfo.getAwardState());
            orderInfoDetail.setAwardTime(order.getAwardTime());
            orderInfoDetail.setBetNo(orderInfo.getBetNo());
            orderInfoDetail.setLotteryTime(orderInfoDetail.getLotteryTime());
            orderInfoDetail.setCreateTime(new Date());
            orderInfoDetail.setOrderNo(orderInfo.getOrderNo());
            orderInfoDetail.setPrizeStatus(orderInfo.getPrizeStatus());
            orderInfoDetail.setWinQuantity(orderInfo.getWinQuantity());
            orderInfoDetail.setStatus(orderInfo.getStatus());
            orderInfoDetails.add(orderInfoDetail);
        }
        return orderInfoDetails;
    }

    /**
     * @param order 订单信息
     * @return
     * @throws
     * @description 获取到所有的嘉奖
     * @author WuLong
     * @date 2019/8/5 20:29
     */
    private void getAllBonus(Order order) {

    }

    /**
     * 派奖订单扣款
     *
     * @param order
     * @author WuLong
     * @date 2019/8/5 20:08
     */
    private void deductMoney(Order order) {
    }


    @Override
    public Map<String, Long> getExecuteTime() throws ServiceRuntimeException {
        Map<String, Long> map = new HashMap<>();
        map.put("cpu", TIME_CPU.get());
        map.put("io", TIME_IO.get());
        return map;
    }

    @Override
    public AbstractAward clone() throws ServiceRuntimeException {
        try {
            AbstractAward award = (AbstractAward) super.clone();
            depthClone(award);
            return award;
        } catch (Exception e) {
            throw new ServiceRuntimeException(CodeMsg.E_70001);
        }
    }

    /**
     * 进行深度复制,对除基础数据类型进行处理 有需要请进行实现
     *
     * @author WuLong
     * @Version 1.0
     * @date 2019/8/5 20:08
     */
    protected void depthClone(AbstractAward award) {

    }

    /**
     * @param order 订单
     * @description 开奖计算, 对单个订单进行开奖计算
     * @author WuLong
     * @date 2019/8/5 20:47
     */
    protected abstract void compute(Order order, HandleEnum.PrizeStatusEnum prizeStatusEnum) throws ServiceRuntimeException;

    /**
     * @param order 订单
     * @description 判断是否存在浮动奖，如果存在发送报警信息
     * @author WuLong
     * @date 2019/8/5 20:51
     */
    private void fluctuateDrawMessage(Order order) {
		/*String detail = orderInfoBO.getWinningDetail();
		List<String> fluctuate = FLUCTUATE.get(orderInfoBO.getLotteryCode());
		if (StringUtils.isBlank(detail) || CollectionUtils.isEmpty(fluctuate)) {
			return;
		}
		// 判断是否中浮动奖
		for (String str : fluctuate) {
			if (detail.indexOf(str) != -1) {
				message.sendFluctuate(orderInfoBO.getLotteryCode(), orderInfoBO.getLotteryIssue(),
						orderInfoBO.getOrderCode(), str);
			}
		}*/
    }

    /**
     * @param orders 需要开奖的订单集合
     * @description 获取开奖号码
     * @author WuLong
     * @date 2019/8/5 20:51
     */
    protected abstract void getDrawCode(List<Order> orders) throws ServiceRuntimeException;

    private List<Order> getOrderInfoBos(List<String> list) throws ServiceRuntimeException {
        return orderService.getOrderInfoBos(list, null);
    }
}