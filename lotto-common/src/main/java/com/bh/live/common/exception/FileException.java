package com.bh.live.common.exception;

/**
 * 文件信息异常类
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class FileException extends BaseException {

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
