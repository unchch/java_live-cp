package com.bh.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.announcement.Announcement;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import com.bh.live.pojo.res.page.TableDataInfo;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
public interface IAnnouncementService extends IService<Announcement> {
   
	/**
     * 新闻资讯
     * @return
     */
    public TableDataInfo queryNewsInformation(AnnouncementReq req);
    
    
	/**
     * 新闻资讯详情
     * @return
     */
    public AnnouncementRes queryNewsInformationDetailById(Integer id);
}
