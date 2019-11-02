package com.bh.live.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.announcement.Announcement;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
public interface AnnouncementDao extends BaseMapper<Announcement> {

    /**
     * 查询公告详情上页
     * @param annId
     * @return
     */
    public AnnouncementRes queryUpAnnouncementById(@Param("annId") Integer annId);

    /**
     * 查询公告详情上页
     * @param annId
     * @return
     */
    public AnnouncementRes queryDownAnnouncementById(@Param("annId") Integer annId);


}
