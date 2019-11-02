package com.bh.live.common.redislock.lock;

/**
 * @description: RedisLockKey
 * @author Y.
 * @date 2019-8-1 18:31:28
 */
public class RedisLockKey {

    public static final String ISSUE_TASK = "task:issue:%s";

    public static final String USER_WALLET = "user:wallet:%s";
}
