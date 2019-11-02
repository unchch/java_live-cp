package com.bh.live.pojo.res.live.video;


import lombok.Data;


/**
 * @Description: 开奖号码
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/12 14:03
 */
@Data
public class OpenCode {

    /**
     * 期号
     */
    private String expect;
    /**
     * 开奖码
     */
    private String openCode;


}
