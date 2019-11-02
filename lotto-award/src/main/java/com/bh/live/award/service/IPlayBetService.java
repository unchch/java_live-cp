package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.PlayBet;

import java.util.List;

/**
 * <p>
 * 赔率基础表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IPlayBetService extends IService<PlayBet> {
    /**
     *@description 查询赔率基础表
     *@author WuLong
     *@date 2019/8/6 16:21
     *@param seedNo 彩种编号
     *@return List<PlayBet>
     */
    List<PlayBet> selectBySeedNo(Integer seedNo);
}
