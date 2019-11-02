package com.bh.live.common.exception;

import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 *@author WuLong
 *@date 2019/7/25 19:16
 *@description controller 方法参数异常拦截，返回自定义异常json
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result bindExceptionHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(),ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            CodeMsg codeMsg = CodeMsg.E_400;
            codeMsg.message = fieldError.getDefaultMessage();
            return Result.error(codeMsg);
        }
        return Result.error(CodeMsg.E_500);
    }
}
