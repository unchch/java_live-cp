package com.bh.live.trade.mqconsumer;

import com.bh.live.common.constant.QueueConstants;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.trade.service.ITradeUserOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lgs
 * @title: TransListener
 * @projectName java_live-cp
 * @description: 开奖更新用户余额
 * @date 2019/8/15  16:42
 */
@Component
@Slf4j
public class TransListener {

    @Autowired
    private ITradeUserOrderService tradeUserOrderService;
    /**
     * (queuesToDeclare = @Queue("test.wulong"))这种方式是RabbitMq 消息队列不存在，会自动创建 <br>
     * (queues = "test.wulong") 这种必须手动在RabbitMq 管理后台创建消息队列，不然启动会报错
     * 接收通知用户返奖加钱
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(QueueConstants.LOTTERY_TRANS_QUEUE))
    @RabbitHandler
    public void onMessage(String message, Channel channel, Message m) {
        try {
            log.info("接收到mq消息:{}", message);
            /**
             *             map.put("seedNo",String.valueOf(seedNo));
             *             map.put("issueNo",issueNo);
             *             map.put("type",String.valueOf(type)); AwardTypeEnum
             **/
            Map<String, Object> map = JsonUtil.json2map(message);
            try{
                tradeUserOrderService.updateAwardOrder(map);
            }catch (Exception e){
                e.printStackTrace();
                log.info("派奖出错：：：：：{}",e.getMessage());
            }
            channel.basicAck(m.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("开奖消息队列" + QueueConstants.LOTTERY_TRANS_QUEUE + ",处理异常:" + e.getMessage(), e);
        }
    }


}
