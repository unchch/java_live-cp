package com.bh.live.common.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutionException;

/**
 * @date 2019/4/23 2:04 PM
 * @description
 * @author yq.
 * @since
 **/
public interface ILoadingCacheService {

    /**
     * 获取访问频率
     * @param key
     * @return
     * @throws ExecutionException
     */
    RateLimiter getRateLimiter(String key) throws ExecutionException ;

}
