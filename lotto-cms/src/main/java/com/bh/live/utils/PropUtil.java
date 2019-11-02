package com.bh.live.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName PropUtil
 * @description: 通过@Value注解读取yml配置文件
 * @author: yq.
 * @date 2019-06-06 23:02:49
 */
@Component
public class PropUtil {
    /**
     * 文件存储位置
     */
    public static String filePath;
    /**
     * 文件访问地址
     */
    public static String fileAccessUrl;
    /**
     * 文件导出地址
     */
    public static String fileExportUrl;
    /**
     * APP API地址
     */
    public static String gameBoxApi;
    

    @Value("${neo.game-box-api}")
    public void setGameBoxApi(String gameBoxApi) {
        PropUtil.gameBoxApi = gameBoxApi;
    }

    @Value("${neo.file-path}")
    public void setFileStoreUrl(String fileStoreUrl) {
        PropUtil.filePath = fileStoreUrl;
    }

    @Value("${neo.file-access-url}")
    public void setFileAccessUrl(String fileAccessUrl) {
        PropUtil.fileAccessUrl = fileAccessUrl;
    }

    @Value("${neo.file-export-url}")
    public void setFileExportUrl(String fileExportUrl) {
        PropUtil.fileExportUrl = fileExportUrl;
    }
}
