package com.bh.live.service.announcement;

import com.bh.live.model.announcement.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.pojo.req.cms.AnnoSaveReq;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import com.bh.live.pojo.res.cms.NewsInformationRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
public interface IAnnouncementService extends IService<Announcement> {

    /**
     * 查询
     * @param announcementReq
     * @return
     */
	public TableDataInfo queryAnnouncementList(AnnouncementReq announcementReq);

    /**
     * save
     * @param announcementReq
     * @return
     */
    int saveAnnouncement(AnnoSaveReq req);

    /**
     * update
     * @param announcementReq
     * @return
     */
    int updateAnnouncement(AnnoSaveReq announcementReq);

    /**
     * delete
     * @param announcementReq
     * @return
     */
    boolean deleteAnnouncement(Integer annId);

    /**
     * 新闻资讯
     * @return
     */
    List<NewsInformationRes> queryNewsInformation();
    
    
    public Announcement queryNewsInformationDetailInfo(Integer id);
    
}
