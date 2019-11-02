package com.bh.live.pojo.res.live;

import com.bh.live.pojo.req.live.BaseMsgReq;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 发言内容
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/8 15:11
 */
@Data
public class BaseData extends BaseMsgReq {
    private Integer sysType;
    private String content;
    private Date sendTime;
    private ChatUser user;
    private ChatUser manager;
    private ChatOpenCode openCode;
    private ChatGuess guess;
    private ChatGift gift;
}
