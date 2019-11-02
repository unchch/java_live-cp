package com.bh.live.common.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProduceMqServiceImpl implements ProduceMqService{
    @Autowired
    private RabbitTemplate amqpTemplate;

    @Override
    public void sendDataToQueue(String queueKey, String message) {
        log.info(new StringBuilder("发送mq信息：queue:").append(queueKey).append(",messge:").append(message).toString());
        Message message2 = getMessage(message);
        amqpTemplate.send(queueKey,message2);
    }

    @Override
    public void sendDataToRouting(String exchange, String key, String message) {
        log.info(new StringBuilder("发送mq信息：exchange:").append(exchange).append(",routing:").append(key).append(",messge:").append(message).toString());
        Message message2 = getMessage(message);
        amqpTemplate.send(exchange,key, message2);
    }

    private Message getMessage(String message) {
        byte[] body = message.getBytes();
        MessageProperties properties = new MessageProperties();
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        return new Message(body, properties);
       /*return MessageBuilder
               .withBody(message.getBytes())
               .setContentType(MessageProperties.CONTENT_TYPE_JSON)
               .build();*/
    }
}
