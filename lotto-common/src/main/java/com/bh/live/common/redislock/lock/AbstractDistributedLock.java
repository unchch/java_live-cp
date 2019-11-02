package com.bh.live.common.redislock.lock;
/**
 * @description: AbstractDistributedLock
 * @author Y.
 * @date 2019-8-1 18:31:12
 */
public abstract class AbstractDistributedLock implements DistributedLock {

    @Override
    public boolean lock(String key) throws RedisLockException{
        return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes) throws RedisLockException{
        return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes, long sleepMillis) throws RedisLockException{
        return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
    }

    @Override
    public boolean lock(String key, long expire)throws RedisLockException {
        return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes)throws RedisLockException {
        return lock(key, expire, retryTimes, SLEEP_MILLIS);
    }

}
