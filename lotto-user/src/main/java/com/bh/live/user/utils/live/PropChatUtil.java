package com.bh.live.user.utils.live;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName PropUtil
 * @description: 通过@Value注解读取yml配置文件
 * @author: wuhuanrong
 * @date 2019-08-06
 */
@Component
public class PropChatUtil {
    /**
     * 聊天室信息推送
     */
    public static String remoteAddress;

    /**
     * 视频流地址
     */
    public static String rtmp;

    @Value("${chat.remote-address}")
    public void setRemoteAddress(String remoteAddress) {
        PropChatUtil.remoteAddress = remoteAddress;
    }

    @Value("${chat.rtmp}")
    public void setRtmp(String rtmp) {
        PropChatUtil.rtmp = rtmp;
    }


}
