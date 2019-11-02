package com.bh.live.rpc.service.trade;

import feign.hystrix.FallbackFactory;

public class TradeFeignServiceFallback implements FallbackFactory<ITradeFeignService>{

    @Override
    public ITradeFeignService create(Throwable throwable) {
        return null;
    }
}
