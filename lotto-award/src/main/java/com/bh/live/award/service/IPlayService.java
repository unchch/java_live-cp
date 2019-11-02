package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Play;

import java.util.List;

/**
 * <p>
 * 玩法表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IPlayService extends IService<Play> {
    /**
     *@description 查询玩法表
     *@author WuLong
     *@date 2019/8/6 15:40
     *@param seedNo 彩种编号
     *@return List<Play>
     */
    List<Play> selectBySeedNo(Integer seedNo);
}
