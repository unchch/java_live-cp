package com.bh.live.common.config.mq.serializer;

import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;

/**
 * @ClassName FstSerializer
 * @description: FstSerializer
 * @author: Y.
 * @date 2019-06-05 10:01:49
 */
@Slf4j
public class FstSerializer implements ISerializer {

    private static FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(Object obj) {
        if (obj == null) return null;
        return fst.asByteArray(obj);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return fst.asObject(bytes);
        } catch (Throwable ex) {
            log.error(ex.toString(), ex);
        }
        return null;
    }

}
