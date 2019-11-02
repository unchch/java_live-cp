package com.bh.live.service.announcement;

import com.bh.live.model.announcement.Advertising;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.pojo.req.cms.AdverSaveReq;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import com.bh.live.pojo.res.cms.AdvertisingRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 广告表 服务类
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
public interface IAdvertisingService extends IService<Advertising> {

    /**
     * 查询
     * @param advertisingReq
     * @return
     * */
	TableDataInfo queryAdvertisList(AdvertisingReq req);

    /**
     * 修改状态
     * @param advertisingReq
     * @return
     * */
    int updateRelease(AdverSaveReq req);

    /**
     * 保存广告
     * @param advertisingReq
     * @return
     */
    boolean saveAdvertis(AdverSaveReq advertisingReq);

    /**
     * 修改广告
     * @param advertisingReq
     * @return
     */
    boolean updateAdvertis(AdverSaveReq req);
    /**
     * 删除广告
     * @param advertisingReq
     * @return
     */
    int deleteAdvertis(Integer adverId);

    /**
     * 直播轮播图片
     * @return
     */
    List<String> queryLiveCarousel();
    
    /**
     * 广告详情
     * @param id
     * @return
     */
    public Advertising queryLiveCarouselDetailInfo(Integer id);
}
