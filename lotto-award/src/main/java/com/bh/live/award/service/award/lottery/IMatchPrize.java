package com.bh.live.award.service.award.lottery;

public interface IMatchPrize {
    /**
     *@description 获取彩果和赔率
     *@author WuLong
     *@date 2019/8/9 17:14
     *@return Map<String,String>   k = 玩法编号，v=彩果#赔率
     *@exception
     */
    void getResultOdds();
}
