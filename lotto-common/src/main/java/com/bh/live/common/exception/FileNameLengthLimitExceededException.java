package com.bh.live.common.exception;

/**
 * 文件名称超长限制异常类
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class FileNameLengthLimitExceededException extends FileException {

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }

}
