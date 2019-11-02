package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.req.anchor.LiveSeedReq;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 直播彩种管理 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
public interface ILiveSeedService extends IService<LiveSeed> {

    /**
     * 新增直播彩种
     * @auth Morphon
     * @param req
     */
    void addLiveSeed(LiveSeedReq req);

    /**
     * 获取直播彩种列表
     * @auth Morphon
     * @return
     */
    TableDataInfo getLiveSeeds(LiveSeedReq req);

    /**
     * 修改直播彩种
     * @auth Morphon
     * @param req
     */
    void updateLiveSeed(LiveSeedReq req);

    /**
     * 获取直播彩种分类彩种数据
     * @auth Morphon
     * @return
     */
    List<SeedCategoryRes> getCategoryLiveSeed();

}
