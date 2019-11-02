package com.bh.live.common.redislock;

import java.lang.annotation.*;

/**
 * @author Y.
 * @description: redis 分布式锁
 * @date 2019-8-1 18:31:32
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    String redisKey();

    long expires() default 3000L;

    long sleeps() default 0L;

    int retryTimes() default 3;

    /**
     * 反射取对象中的参数名称
     *
     * @return
     */
    String prop() default "";
}
