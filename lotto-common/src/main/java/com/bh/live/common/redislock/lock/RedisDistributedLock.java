package com.bh.live.common.redislock.lock;

import com.bh.live.common.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @description: RedisDistributedLock
 * @author Y.
 * @date 2019-8-1 18:31:19
 */
@Component
public class RedisDistributedLock extends AbstractDistributedLock {
    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    @Autowired
    private RedisUtil redisManager;

    private ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static final String UNLOCK_LUA;
    static {
        StringBuilder unLock = new StringBuilder();
        unLock.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        unLock.append("then ");
        unLock.append("    return redis.call(\"del\",KEYS[1]) ");
        unLock.append("else ");
        unLock.append("    return 0 ");
        unLock.append("end ");
        UNLOCK_LUA = unLock.toString();
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) throws RedisLockException {
        String value = UUID.randomUUID().toString();
        boolean result = redisManager.setNX(key, value, expire);

        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                logger.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = redisManager.setNX(key, value, expire);
        }
        if(!result){
            throw new RedisLockException("===== 获取 Redis Lock 异常. ======");
        }
        logger.debug("[ lock success] key:{}", key);
        threadLocal.set(value);
        return Boolean.TRUE;
    }

    @Override
    public boolean releaseLock(String key) throws RedisLockException {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> args = new ArrayList<String>();
            args.add(threadLocal.get());

            return redisManager.release(UNLOCK_LUA, keys, args);
        } catch (Exception e) {
            logger.error("release lock occured an exception", e);
            throw new RedisLockException("release lock occured an exception: " + e.getMessage());
        }
    }
}