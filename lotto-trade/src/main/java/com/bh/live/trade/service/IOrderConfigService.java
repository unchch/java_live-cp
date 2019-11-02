package com.bh.live.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.trade.PlayOptRes;


/**
 * <p>
 * 彩种订单配置表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
public interface IOrderConfigService extends IService<OrderConfig> {

    /**
     * 查询彩种配置
     * @param lotteryCode
     * @return
     */
    OrderConfig selectObj(Integer lotteryCode);

    /**
     * 查询 发布价格
     * @param lotteryCode
     * @param liveUser
     * @return
     */
    PlayOptRes selectOpt(Integer lotteryCode, LiveUser liveUser);


}
