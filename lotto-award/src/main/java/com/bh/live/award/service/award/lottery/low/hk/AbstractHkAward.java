package com.bh.live.award.service.award.lottery.low.hk;

import com.bh.live.award.service.award.lottery.AbstractNumberAward;
import com.bh.live.model.trade.Order;

import java.util.List;

/**
 *@author WuLong
 *@date 2019/8/6 11:00
 *@description hk抽象类
 */

public abstract class AbstractHkAward extends AbstractNumberAward {
    /**
     *开奖号码  1,2,3,4,5,6#7   正码#特码
     */
    protected String[] drawCodes;

    /**
     *@description 获取玩法类型
     *@author WuLong
     *@date 2019/8/6 11:02
     *@param childCode 子玩法Code
     *@return
     *@exception
     */
    protected abstract int getType(Integer childCode);


}
