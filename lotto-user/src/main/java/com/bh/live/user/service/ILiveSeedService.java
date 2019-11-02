package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.model.user.Attention;
import com.bh.live.pojo.req.anchor.LiveSeedReq;
import com.bh.live.pojo.res.live.LiveRoomSeedRes;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import org.apache.ibatis.annotations.Param;

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
     * 获取直播彩种分类彩种数据
     *
     * @return
     * @auth Morphon
     */
    List<SeedCategoryRes> getCategoryLiveSeed();

    /**
     * 查询直播彩种用户信息
     *
     * @param seedNo
     * @param queryType
     * @return
     */
    List<LiveRoomSeedRes> getLiveUserRoomInfo(Integer seedNo, String queryType);


    List<Attention> attentions(Integer targetId,List<Integer> ids);
}
