package com.bh.live.common.redislock.lock;
/**
 * @description: DistributedLock
 * @author Y.
 * @date 2019-8-1 18:31:15
 */
public interface DistributedLock {

    long TIMEOUT_MILLIS = 10000;

    int RETRY_TIMES = Integer.MAX_VALUE;

    long SLEEP_MILLIS = 100;

    boolean lock(String key) throws RedisLockException;

    boolean lock(String key, int retryTimes) throws RedisLockException;

    boolean lock(String key, int retryTimes, long sleepMillis) throws RedisLockException;

    boolean lock(String key, long expire) throws RedisLockException;

    boolean lock(String key, long expire, int retryTimes) throws RedisLockException;

    boolean lock(String key, long expire, int retryTimes, long sleepMillis) throws RedisLockException;

    boolean releaseLock(String key) throws RedisLockException;
}
