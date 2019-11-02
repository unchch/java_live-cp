package com.bh.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.announcement.Advertising;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import com.bh.live.pojo.res.page.TableDataInfo;

/**
 * <p>
 * 广告表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
public interface IAdvertisingService extends IService<Advertising> {
	
    /**
     * 直播轮播图片
     * @return
     */
    public TableDataInfo queryLiveCarousel(AdvertisingReq req);
    
}
