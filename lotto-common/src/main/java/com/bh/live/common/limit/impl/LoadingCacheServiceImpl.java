package com.bh.live.common.limit.impl;


import com.bh.live.common.limit.ILoadingCacheService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @date 2019/4/23 1:53 PM
 * @description 接口访问限流service，此接口主要用于针对ip地址对于接口访问的限流控制
 * 建议所有直接对外开放的接口都进行限流
 * @author yq.
 * @since
 **/
@Service
public class LoadingCacheServiceImpl implements ILoadingCacheService {

    LoadingCache<String, RateLimiter> ipRequestCaches = CacheBuilder.newBuilder()
            // 设置缓存个数
            .maximumSize(200)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String s){
                    // 新的IP初始化 (限流每秒0.1个令牌响应,即10s一个令牌)
                    return RateLimiter.create(0.2);
                }
            });

    @Override
    public RateLimiter getRateLimiter(String key) throws ExecutionException {
        return ipRequestCaches.get(key);
    }
}