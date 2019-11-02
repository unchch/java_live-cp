package com.bh.live.common.exception;


import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基础异常
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    @Override
    public String getMessage() {
        String message = null;
        if (!CommonUtil.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }


}
