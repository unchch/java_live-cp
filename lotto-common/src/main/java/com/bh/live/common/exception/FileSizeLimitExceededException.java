package com.bh.live.common.exception;

/**
 * 文件名大小限制异常类
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class FileSizeLimitExceededException extends FileException {

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }

}
