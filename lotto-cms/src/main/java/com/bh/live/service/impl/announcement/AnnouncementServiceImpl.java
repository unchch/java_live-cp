package com.bh.live.service.impl.announcement;

import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.model.announcement.Announcement;
import com.bh.live.model.user.LiveUser;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.dao.announcement.AnnouncementDao;
import com.bh.live.pojo.req.cms.AnnoSaveReq;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import com.bh.live.pojo.res.cms.NewsInformationRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.announcement.IAnnouncementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementDao, Announcement> implements IAnnouncementService {

    @Resource
    private AnnouncementDao announcementDao;

    @Override
    public TableDataInfo queryAnnouncementList(AnnouncementReq req) {
        LambdaQueryWrapper<Announcement> query = new QueryWrapper<Announcement>().lambda();
        query.like(StringUtils.isNotEmpty(req.getTitle()),Announcement::getTitle,req.getTitle())
             .eq(StringUtils.isNotEmpty(req.getTitle()),Announcement::getType, req.getType());
        if (req.getStartDate() != null && req.getEndDate() != null
                && !req.getStartDate().equals("") && !req.getEndDate().equals("")) {
            query.between(Announcement::getReleaseTime, req.getStartDate(), req.getEndDate());
        }
        query.orderByDesc(Announcement::getReleaseTime);
        IPage<Announcement> page = super.page(new Page<Announcement>(req.getPageNum(), req.getPageSize()), query);
        return new TableDataInfo(page.getRecords(), page.getTotal());
//    	return announcementDao.queryAnnouncementList(announcementReq);
    }

    @Override
    public int saveAnnouncement(AnnoSaveReq req) {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(req, announcement);
        announcement.setReleaseTime(DateUtils.getNowDate());
        announcement.setModifyTime(DateUtils.getNowDate());
        return announcementDao.insert(announcement);
    }

    @Override
    public int updateAnnouncement(AnnoSaveReq req) {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(req, announcement);
        announcement.setModifyTime(DateUtils.getNowDate());
        return announcementDao.updateById(announcement);
    }

    @Override
    public boolean deleteAnnouncement(Integer annId) {
        Announcement announcement = new Announcement();
        announcement.setStatus(0);
        announcement.setId(annId);
        announcement.setModifyTime(DateUtils.getNowDate());
        return super.updateById(announcement);
    }

    @Override
    public List<NewsInformationRes> queryNewsInformation() {
//        List<AnnouncementRes> list = announcementDao.queryAnnouncementList(null);
        return null;
    }

    @Override
    public Announcement queryNewsInformationDetailInfo(Integer id) {

        return super.getById(id);
    }
}
