package com.bh.live.common.config.mq;

import com.bh.live.common.config.mq.serializer.FstSerializer;
import com.bh.live.common.config.mq.serializer.ISerializer;
import com.bh.live.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.nio.charset.Charset;

/**
 * @ClassName JsonMessageConverter
 * @description: JsonMessageConverter
 * @author: Y.
 * @date 2019-06-04 17:59:49
 */
@Slf4j
public class JsonMessageConverter extends AbstractMessageConverter {

    private static final String defaultCharset = Charset.defaultCharset().name();

    private ISerializer serializer;

    public JsonMessageConverter(){
        this.serializer = new FstSerializer();
    }

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(defaultCharset);
        byte[] bytes = serializer.serialize(object);
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        return new Message(serializer.serialize(object), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        if(CommonUtil.isEmpty(message)){
            return null;
        }
        Object object = serializer.deserialize(message.getBody());
        return object;
    }
}
