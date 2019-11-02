package com.bh.live.award.service.award;

import com.bh.live.award.service.IIssueService;
import com.bh.live.award.service.IOrderService;
import com.bh.live.award.service.award.entity.AwardOrderBO;
import com.bh.live.award.service.award.entity.AwardResultBO;
import com.bh.live.award.service.award.factory.AwardFactory;
import com.bh.live.common.constant.QueueConstants;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.enums.lottery.IssueEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.rabbitmq.ProduceMqService;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.model.trade.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author WuLong
 * @date 2019/8/5 20:59
 * @description 订单开奖主体服务
 */
@Service
@Slf4j
public class ForkJoinOrder implements IOrder {

    /**
     * 开奖新线程池
     */
    private static ForkJoinPool FORK_JOIN_POOL;
    /**
     * 保存开奖信息集合
     */
    private static Map<String, OrderAwardResult> AWARD_RESULT = new ConcurrentHashMap<>();

    static {
        FORK_JOIN_POOL = new ForkJoinPool(30);
    }

    @Autowired
    private AwardFactory awardFactory;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IIssueService issueService;
    @Resource
    private ProduceMqService produceMqService;

    @Override
    public AwardResultBO getResult(int lotteryCode, String issue, Integer handleType) throws ServiceRuntimeException {

        OrderAwardResult orderAwardResult = AWARD_RESULT.get(getResutlKey(lotteryCode, issue, handleType));
        if (orderAwardResult == null || orderAwardResult.getTotal() == 0) {
            return new AwardResultBO();
        }
        return getAwardInfo(orderAwardResult);
    }

    @Override
    public void distribute(List<Order> orders, Integer lotteryCode, String issue, Integer type, HandleEnum.PrizeStatusEnum prizeStatusEnum) throws ServiceRuntimeException {
        String key = getResutlKey(lotteryCode, issue, prizeStatusEnum.getCode());
        OrderAwardResult orderAwardResult = AWARD_RESULT.get(key);
        if (orderAwardResult != null && orderAwardResult.isAward()) {
            throw new ServiceRuntimeException(CodeMsg.E_80002.code, "该彩种{" + lotteryCode + "},期号{" + issue + "}正在" + prizeStatusEnum.getDesc() + "开奖");
        }
        IAward award = awardFactory.getAward(lotteryCode);
        //每个子任务处理最大订单数
        int unit = 100;
        OrderAwardResult result = new OrderAwardResult(orders.size(), lotteryCode, issue);
        AWARD_RESULT.put(key, result);
        OrderTask orderTask = new OrderTask(0, orders.size(), unit, orders, award, result, true, type, prizeStatusEnum);
        FORK_JOIN_POOL.execute(orderTask);
        clearAwardResult();
    }


    /**
     * 生成唯一key
     *
     * @param lotteryCode
     * @param issue
     * @return
     * @author WuLong
     * @date 2019/8/5 20:08
     */
    private static String getResutlKey(int lotteryCode, String issue, Integer handleType) {
        if (CommonUtil.isEmpty(issue)) {
            issue = "";
        }
        String key = lotteryCode + SymbolConstants.UNDERLINE + issue + SymbolConstants.UNDERLINE + handleType;
        return key;
    }

    /**
     * 清理已经开完奖的信息
     *
     * @author WuLong
     * @date 2019/8/5 20:08
     */
    private void clearAwardResult() {
        for (Map.Entry<String, OrderAwardResult> entry : AWARD_RESULT.entrySet()) {
            boolean isAward = entry.getValue().isAward();
            if (!isAward) {
                log.info(JsonUtil.obj2json(entry.getValue()));
                AWARD_RESULT.remove(entry.getKey());
            }
        }
    }

    /**
     * 计算实时开奖信息
     *
     * @param orderAwardResult
     * @return
     * @author WuLong
     * @date 2019/8/5 20:08
     */
    private static AwardResultBO getAwardInfo(OrderAwardResult orderAwardResult) throws ServiceRuntimeException {
        AwardResultBO bo = new AwardResultBO();
        Date startTime = orderAwardResult.getStartTime();
        int total = orderAwardResult.getTotal();
        int success = orderAwardResult.getSuccess();
        List<String> fail = orderAwardResult.getFailOrder();
        int failNum = fail.size();
        bo.setFail(failNum);
        bo.setSuccess(success);
        bo.setTotal(total);
        bo.setFailOrder(fail);
        bo.setDrawCode(orderAwardResult.getDrawCode());
        bo.setFailMessage(orderAwardResult.getFailMessage());
        bo.setWinCount(orderAwardResult.getWinCount());
        // 开奖订单数
        int awardNum = success + failNum;
        long now = System.currentTimeMillis();
        if (orderAwardResult.getEndTime() != null) {
            now = orderAwardResult.getEndTime().getTime();
        }
        // 已开奖时间
        long awardSecond = now - startTime.getTime();
        bo.setAwardSecond(awardSecond / 1000);
        // 剩余开奖时间
        if (awardNum > 0) {
            double plan = awardSecond / (double) awardNum * (total - awardNum);
            bo.setPlanSecond((long) (plan / 1000));
        } else {
            bo.setPlanSecond(-1L);
        }
        return bo;
    }

    /**
     * @author WuLong
     * @version 1.0
     * @desc 订单开奖task
     * @date 2019/8/5 20:08
     */
    @SuppressWarnings("serial")
    private class OrderTask extends RecursiveTask<Integer> {
        // list 开始位置
        private int start;
        // list 结束位置
        private int end;
        // 处理单元（一次处理订单数）
        private int unit;
        // 开奖服务
        private IAward award;
        // 开奖订单
        private List<Order> orders;
        // 开奖结果
        private OrderAwardResult result;
        //是否要请求嘉奖接口
        private boolean isBonus;
        //是否重置开奖
        private int type;
        //0:自动，1:手动，2:重置
        private HandleEnum.PrizeStatusEnum prizeStatusEnum;

        public OrderTask(int start, int end, int unit, List<Order> orders, IAward award, OrderAwardResult result, boolean isBonus, int type, HandleEnum.PrizeStatusEnum prizeStatusEnum) {
            this.start = start;
            this.end = end;
            this.orders = orders;
            this.award = award;
            this.unit = unit;
            this.result = result;
            this.isBonus = isBonus;
            this.type = type;
            this.prizeStatusEnum = prizeStatusEnum;
        }

        @Override
        protected Integer compute() {
            int invertal = end - start;
            if (invertal <= unit) {
                List<Order> comList = orders.subList(start, end);
                try {
                    AwardOrderBO awardOrder = award.handle(comList, isBonus, type, prizeStatusEnum);
                    result.add(awardOrder.getWinCount(), comList.size() - awardOrder.getFail().size(), awardOrder.getFail());
                    result.setDrawCode(award.getDrawCode());
                    if (result.isAllSuccess()) {
                        log.info(result.getLotteryCode() + "|" + result.getLotteryIssue() + "|开奖完成|所有订单开奖成功，修改数字彩状态");
                        //全部订单开奖成功
                        result.setEndTime(new Date());
                        issueService.updateStatusBySeedIssueNo(result.getLotteryIssue(), result.getLotteryCode(), IssueEnum.IssueStatusEnum.AWARD.getCode(), null);
                        Map<String, String> mq = new HashMap<>();
                        mq.put("seedNo", String.valueOf(result.getLotteryCode()));
                        mq.put("issueNo", result.getLotteryIssue());
                        mq.put("type", String.valueOf(type));
                        //开奖后通知交易中心，增加购买推荐账户的流水到方案发布者账户
                        produceMqService.sendDataToQueue(QueueConstants.LOTTERY_TRANS_QUEUE, JsonUtil.obj2json(mq));
                    }
                    if (result.isAccomplish()) {
                        if (!result.getFailOrder().isEmpty()) {
                            orderService.updateOrderDrawFail(result.getFailOrder());
                        }
                        log.info(result.getLotteryCode() + "|" + result.getLotteryIssue() + "|开奖完成|" + getAwardInfo(result));
                        log.info("所有彩种开奖执行时间（毫秒）" + award.getExecuteTime());
                    }
                } catch (Exception e) {
                    log.error("订单开奖失败" + comList.toString(), e);
                    result.addFailOrder(comList);
                }
            } else {
                int middle = (start + end) / 2;
                OrderTask left = new OrderTask(start, middle, unit, orders, award, result, isBonus, type, prizeStatusEnum);
                OrderTask right = new OrderTask(middle, end, unit, orders, award, result, isBonus, type, prizeStatusEnum);
                left.fork();
                right.fork();
            }
            return 0;
        }

    }

    @Override
    public void RecommendDistribute(Integer lotteryCode, Integer lotteryChild, Integer orderStatus, Integer type, String systemCode) {
//		List<IssueContentInfoBO> contentInfoBOs = null;
////		if(type==0){
////			contentInfoBOs = orderService.getIssueContentInfos(lotteryCode, orderStatus, lotteryChild,null);
////		}else{
////			contentInfoBOs = orderService.getIssueContentInfos(lotteryCode, orderStatus, lotteryChild,systemCode);
////		}
////		if(contentInfoBOs.isEmpty()){
////			return;
////		}
////		IAward award = awardFacotry.getAward(lotteryCode);
////		SportsAward sportsAward = (SportsAward)award;
////		RecommendTask orderTask = new RecommendTask(0, contentInfoBOs.size(), 1, contentInfoBOs, sportsAward);
////		FORK_JOIN_POOL.execute(orderTask);
    }
}
