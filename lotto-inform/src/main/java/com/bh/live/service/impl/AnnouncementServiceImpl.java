package com.bh.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.AnnouncementDao;
import com.bh.live.model.announcement.Announcement;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.IAnnouncementService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementDao, Announcement>
        implements IAnnouncementService {

    @Resource
    private AnnouncementDao announcementDao;

    @Override
    public TableDataInfo queryNewsInformation(AnnouncementReq req) {
        LambdaQueryWrapper<Announcement> query = new QueryWrapper<Announcement>().lambda();
        if (req.getType() != null) {
            query.eq(Announcement::getType, req.getType());
        }
        if (req.getStartDate() != null && req.getEndDate() != null) {
            query.between(Announcement::getReleaseTime, req.getStartDate(), req.getEndDate());
        }
        query.eq(Announcement::getStatus, 1).orderByDesc(Announcement::getIsHot)
                .orderByDesc(Announcement::getIsStick).orderByDesc(Announcement::getReleaseTime);
        IPage<Announcement> page = baseMapper.selectPage(new Page<>(req.getPageNum(), req.getPageSize()), query);
        return new TableDataInfo(page);
    }

    @Override
    public AnnouncementRes queryNewsInformationDetailById(Integer id) {
        AnnouncementRes res = new AnnouncementRes();
        BeanUtils.copyProperties(super.getById(id), res);
        AnnouncementRes up = announcementDao.queryUpAnnouncementById(id);
        if (up != null) {
            res.setUpTitle(up.getUpTitle());
            res.setUpTitleId(up.getUpTitleId());
        }
        AnnouncementRes down = announcementDao.queryDownAnnouncementById(id);
        if (down != null) {
            res.setDownTitle(down.getDownTitle());
            res.setDownTitleId(down.getDownTitleId());
        }
        return res;
    }
}
