package com.bh.live.common.exception;

import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
/**
 *@author WuLong
 *@date 2019/8/5 17:23
 *@description Token检查异常
 */
public class TokenCheckException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    /**
     * 异常结果
     */
    private Result<?> result;

    public TokenCheckException(CodeMsg codeMsg) {
        super(codeMsg.message);
        this.code = codeMsg.code;
        this.msg = codeMsg.message;
    }

    public TokenCheckException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public TokenCheckException(int code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.msg = message;
    }

    public TokenCheckException(CodeMsg codeMsg, Throwable t) {
        super(codeMsg.message, t);
        this.code = codeMsg.code;
        this.msg = codeMsg.message;
    }

    public TokenCheckException(Result result) {
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
