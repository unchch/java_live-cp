package com.bh.live.common.exception;


import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;

/**
 * @author lgs
 * @Desc 发生此异常，结果返回处理成json格式
 */
@SuppressWarnings("serial")
public class ResultJsonException extends ServiceRuntimeException {

    public ResultJsonException(CodeMsg msg) {
        super(msg);
    }

    public ResultJsonException(CodeMsg msg, Throwable t) {
        super(msg, t);
    }

    /**
     * @param result 异常结果
     */
    public ResultJsonException(Result<?> result) {
        super(result);
    }
}
