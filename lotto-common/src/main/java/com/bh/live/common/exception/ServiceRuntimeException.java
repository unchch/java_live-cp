package com.bh.live.common.exception;

import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;

/**
 * @author lgs
 * @title: ServiceExcaption
 * @projectName java_live-cp
 * @description: 服务自定以异常.
 * @date 2019/7/17  17:45
 */
public class ServiceRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    /**
     * 异常结果
     */
    private Result<?> result;

    public ServiceRuntimeException(CodeMsg codeMsg) {
        super(codeMsg.message);
        this.code = codeMsg.code;
        this.msg = codeMsg.message;
    }

    public ServiceRuntimeException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ServiceRuntimeException(int code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.msg = message;
    }

    public ServiceRuntimeException(CodeMsg codeMsg, Throwable t) {
        super(codeMsg.message, t);
        this.code = codeMsg.code;
        this.msg = codeMsg.message;
    }

    public ServiceRuntimeException(Result result) {
        super(result.getMessage());
        this.result = result;
        this.code = result.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
