package com.bh.live.common.rabbitmq;

/**
 * RabbitMp发送消息接口
 * @author wulong
 * @date 2019-07-15
 */
public interface ProduceMqService {
    /**
     * 发送消息到指定队列
     * @param queueKey
     * @param message
     */
     void sendDataToQueue(String queueKey, String message);

    /**
     * 发送路由mq消息
     * @CreatDate 2018年5月3日 下午12:04:45
     * @param exchange 交换机
     * @param key 路由
     * @param message 消息
     */
    void sendDataToRouting(String exchange, String key, String message);
}
