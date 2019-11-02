package com.bh.live.common.redislock;

import com.bh.live.common.redislock.lock.RedisDistributedLock;
import com.bh.live.common.redislock.lock.RedisLockException;
import com.bh.live.common.utils.CommonUtil;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * redis 分布式锁实现
 *
 * @author Y.
 * @date 2019-8-1 18:32:41
 */
@Aspect
@Component
@Order(0)
public class RedisLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Pointcut("@annotation(com.bh.live.common.redislock.RedisLock)")
    private void lockPoint() {

    }

    @Around(value = "lockPoint()")
    public Object aroundAdvisor(ProceedingJoinPoint point) throws Throwable {
        RedisLock redisLock = getAnnotationLog(point);
        String cacheKey = getCacheKey(point, redisLock);
        long start = System.currentTimeMillis();
        Object ret = null;
        try {
            //获取redis分布式锁
            if (redisDistributedLock.lock(cacheKey, redisLock.expires(), redisLock.retryTimes())) {
                ret = point.proceed();
            }
        } catch (RedisLockException rex) {
            logger.error("redis分布式锁获取失败 cause:{} message:{}", rex.getCause(), rex.getMessage());
            throw new RuntimeException("钱包交易异常，请联系管理员！");
        } catch (Exception ex) {
            logger.error("异常信息: cause{} message:{}", ex.getMessage(), ex.getMessage());
            throw ex;
        } finally {
            // 休眠0.1s，用来执行对应程序，然后才解锁
            long end = System.currentTimeMillis();
            logger.debug("# [ {} ] 已经执行结束，耗费时间：{} ms. RedisKey将在0.1s后解锁.", cacheKey, (end - start));
            if (redisLock.sleeps() > 0) {
                // 是否休眠，延迟解锁
                Thread.sleep(redisLock.sleeps());
            }
            boolean releaseRes = redisDistributedLock.releaseLock(cacheKey);
            logger.debug("# [ {} ] release redisLock. res:{}", cacheKey, releaseRes);
        }
        return ret;
    }

    /**
     * 生成redis缓存key
     *
     * @param point
     * @param lock
     * @return
     * @throws IllegalAccessException
     */
    private String getCacheKey(ProceedingJoinPoint point, RedisLock lock) throws IllegalAccessException {
        String cacheKey = lock.redisKey();

        String prop = lock.prop();
        if (CommonUtil.isEmpty(prop)) {
            return cacheKey;
        }
        Object[] args = point.getArgs();
        List<Object> fieldValues = Lists.newArrayList();
        for (Object obj : args) {
            if (CommonUtil.isEmpty(obj) || obj.getClass().isPrimitive()) {
                return null;
            }
            Field field = ReflectionUtils.findField(obj.getClass(), prop);
            field.setAccessible(true);
            Object fieldVal = field.get(obj);
            if (CommonUtil.isEmpty(fieldVal)) {
                continue;
            }
            fieldValues.add(fieldVal);
            break;
        }
        cacheKey = String.format(cacheKey, fieldValues);
        return cacheKey;
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private RedisLock getAnnotationLog(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(RedisLock.class);
        }
        return null;
    }
}
