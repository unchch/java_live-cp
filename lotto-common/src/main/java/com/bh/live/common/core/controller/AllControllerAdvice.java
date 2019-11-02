package com.bh.live.common.core.controller;


import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lgs
 * @Desc 异常处理
 */
@ControllerAdvice
@Slf4j
public class AllControllerAdvice {

    /**
     * 异常处理
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object exp(HttpServletRequest request, IllegalArgumentException ex) {
        handleException(ex);
        return Result.error(500, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public Object exp(HttpServletRequest request, IllegalStateException ex) {
        handleException(ex);
        return Result.error(500, ex.getMessage());
    }


    @ExceptionHandler(ResultJsonException.class)
    @ResponseBody
    public Object exp(HttpServletRequest request, ResultJsonException ex) {
        handleException(ex);
        return Result.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    @ResponseBody
    public Object exp(HttpServletRequest request, ServiceRuntimeException ex) {
        handleException(ex);
        return Result.error(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Object exp(HttpServletRequest request, Exception ex) {
        handleException(ex);
        return Result.error(CodeMsg.E_500);
    }

    private void handleException(Exception ex) {
        log.error(ex.toString());
        ex.printStackTrace();
    }

}
