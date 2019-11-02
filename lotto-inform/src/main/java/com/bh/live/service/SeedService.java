package com.bh.live.service;

import com.bh.live.pojo.res.lottery.SeedNavRes;
import java.util.List;

/**
 * <p>
 * 彩种分类 服务类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-08-02
 */
public interface SeedService {
    /**
     * 查询所有彩种分类编号及名称
     * @return
     */
    List<SeedNavRes> selectSeed();

}
