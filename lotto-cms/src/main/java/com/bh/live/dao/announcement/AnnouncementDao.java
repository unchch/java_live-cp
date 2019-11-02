package com.bh.live.dao.announcement;

import com.bh.live.model.announcement.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
public interface AnnouncementDao extends BaseMapper<Announcement> {

    /**
     * 列表筛选
     * @param announcementReq
     * @return
     */
    List<AnnouncementRes> queryAnnouncementList(@Param("announcementReq") AnnouncementReq announcementReq);
}
