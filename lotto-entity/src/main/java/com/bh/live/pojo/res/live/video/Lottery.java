package com.bh.live.pojo.res.live.video;

import lombok.Data;

/**
 * @Description: 开奖视频
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/12 14:47
 */
@Data
public class Lottery {
    private String abbreviation;
    private String nextExpect;
    private String seconds;
    private String endFlash;
    private String  roomId;
}
