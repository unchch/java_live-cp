package com.bh.live.pojo.res.live;


import com.bh.live.pojo.req.live.BaseMsgReq;
import lombok.Data;

/**
 * @Description: 聊天室礼物信息
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/6 18:03
 */
@Data
public class ChatGift extends BaseMsgReq {

    /**
     * 礼物名称
     */
    private String giftName;
    /**
     * 描述
     */
    private String giftDesc;

    /**
     * PC端礼物动画
     */
    private String pcImage;

    /**
     * PC端礼物gif
     */
    private String pcGif;

    /**
     * PC端全屏动画
     */
    private String pcFullGif;

    /**
     * PC端礼物小图标
     */
    private String pcIcon;

    /**
     * 快捷组，多个逗号分隔（eg：1，520，1314，9999）
     */
    private String groups;

    /**
     * 礼物数量
     */
    private Integer number;


}
