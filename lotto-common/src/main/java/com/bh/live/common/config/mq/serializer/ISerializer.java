package com.bh.live.common.config.mq.serializer;

/**
 * @ClassName ISerializer
 * @description: ISerializer
 * @author: Y.
 * @date 2019-06-04 20:16:49
 */
public interface ISerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes);
}
