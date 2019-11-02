package com.bh.live.award.mqconsumer;

import com.bh.live.award.service.IIssueService;
import com.bh.live.award.service.IOrderService;
import com.bh.live.award.service.award.IOrder;
import com.bh.live.common.annotation.ParamsNotNull;
import com.bh.live.common.constant.QueueConstants;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.enums.lottery.IssueEnum;
import com.bh.live.common.enums.trade.OrderEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.rabbitmq.ProduceMqService;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.model.lottery.Issue;
import com.bh.live.model.trade.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WuLong
 * @Date: 2019/7/15 20:56
 * @Description: rabbitmq监听事例
 */
@Component
@Slf4j
public class AwardListener {
    @Resource
    private IOrderService orderService;
    @Resource
    private ProduceMqService produceMqService;
    @Resource
    private IIssueService issueService;
    @Resource
    private IOrder orderHandler;

    /**
     * (queuesToDeclare = @Queue("test.wulong"))这种方式是RabbitMq 消息队列不存在，会自动创建 <br>
     * (queues = "test.wulong") 这种必须手动在RabbitMq 管理后台创建消息队列，不然启动会报错
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(QueueConstants.LOTTERY_RESULT_QUEUE))
    @RabbitHandler
    public void onMessage(String message, Channel channel, Message m) {
       /* try {
            channel.basicAck(m.getMessageProperties().getDeliveryTag(), false);
            log.info("接收到mq消息:{}", message);
            Map<String, Object> map = JsonUtil.json2map(message);
            autoLottery(map);
        } catch (Exception e) {
            log.error("开奖消息队列"+QueueConstants.LOTTERY_RESULT_QUEUE+",处理异常:" + e.getMessage(), e);
        }*/
    }

    /**
     * 自动开奖
     *
     * @param map 彩期,彩种彩果
     * @author WuLong
     * @date 2019-08-14
     */
    private void autoLottery(Map<String, Object> map) {
        String issueNo = map.get("issueNo").toString();
        Integer seedNo = Integer.valueOf(map.get("seeedNo").toString());
        String result = map.get("result").toString();
        Issue issue = issueService.selectBySeedIssueNo(issueNo, seedNo);
        if (issue == null) {
            log.info("未查询到彩种:{},彩期:{},不存在", seedNo, issueNo);
            throw new ServiceRuntimeException(CodeMsg.E_80017);
        }
        //更新彩果入库
        issueService.updateStatusBySeedIssueNo(issueNo, seedNo, null, result);
        HandleEnum.PrizeStatusEnum prizeStatusEnum = HandleEnum.PrizeStatusEnum.getEnumByCode(issue.getPrizeMode());
        if (prizeStatusEnum != HandleEnum.PrizeStatusEnum.AUTOMATIC) {
            log.info("未查询到彩种:{},彩期:{},不能自动开奖", seedNo, issueNo);
            throw new ServiceRuntimeException(CodeMsg.E_80020.code, String.format(CodeMsg.E_80020.message, seedNo, issueNo));
        }
        //彩期状态校验
        IssueEnum.IssueStatusEnum issueStatus = IssueEnum.IssueStatusEnum.getIssueStatus(issue.getStatus());
        if (issueStatus == IssueEnum.IssueStatusEnum.WAITING_AWARD) {
            List<Integer> status = new ArrayList<>();
            status.add(OrderEnum.OrderStatusEnum.PENDING_AWARD.getCode());
            List<Order> orders = orderService.getOrderByIssueNoSeedNoStatus(issueNo, seedNo, status);
            if (CollectionUtils.isEmpty(orders)) {
                log.info("未查询到彩种:{},彩期:{},订单状态:{}", seedNo, issueNo, status.toString());
                throw new ServiceRuntimeException(CodeMsg.E_80015);
            }
            //设置彩期正在开奖中
            issueService.updateStatusBySeedIssueNo(issueNo, seedNo, IssueEnum.IssueStatusEnum.OPENING_AWARD.getCode(), null);
            //执行开奖动作
            orderHandler.distribute(orders, seedNo, issueNo, HandleEnum.AwardTypeEnum.OPEN_AWARD.getCode(),HandleEnum.PrizeStatusEnum.AUTOMATIC);
            Map<String, String> mq = new HashMap<>();
            mq.put("seedNo", String.valueOf(seedNo));
            mq.put("issueNo", issueNo);
            mq.put("type", HandleEnum.AwardTypeEnum.OPEN_AWARD.getCode().toString());
            //开奖后通知交易中心，增加购买推荐账户的流水到方案发布者账户
            produceMqService.sendDataToQueue(QueueConstants.LOTTERY_TRANS_QUEUE, JsonUtil.obj2json(mq));
        } else {
            throw new ServiceRuntimeException(CodeMsg.E_80018.code, String.format(CodeMsg.E_80018.message, "自动开奖", issueStatus.getDesc()));
        }
    }

}
