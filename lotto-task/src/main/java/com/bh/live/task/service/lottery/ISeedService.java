package com.bh.live.task.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Seed;

import java.util.List;

/**
 * <p>
 * 彩种表 服务类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
public interface ISeedService extends IService<Seed> {

    /**
     * 查询所有彩种
     * @return
     */
    List<Seed> selectAllSeed();
}
