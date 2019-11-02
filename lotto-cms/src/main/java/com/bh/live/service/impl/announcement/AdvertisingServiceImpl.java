package com.bh.live.service.impl.announcement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.dao.announcement.AdvertisingDao;
import com.bh.live.model.announcement.Advertising;
import com.bh.live.pojo.req.cms.AdverSaveReq;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.announcement.IAdvertisingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 广告表 服务实现类
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@Service
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingDao, Advertising>
        implements IAdvertisingService {

    @Resource
    private AdvertisingDao advertisingDao;

    @Override
    public TableDataInfo queryAdvertisList(AdvertisingReq req) {
//        List<Advertising> list = advertisingDao.queryAdvertisList(req);
//        List<AdvertisingRes> resList = Lists.newArrayList();
//        for (int i = 0; i <list.size() ; i++) {
//            AdvertisingRes res= new AdvertisingRes();
//            BeanUtils.copyProperties(list.get(i),res);
//        }
        LambdaQueryWrapper<Advertising> query = new QueryWrapper<Advertising>().lambda();
        if (req.getName() != null && !req.getName().equals("")) {
            query.eq(Advertising::getName, req.getName());
        }
        if (req.getId() != null) {
            query.eq(Advertising::getId, req.getId());
        }
        if (req.getStatus() != null) {
            query.eq(Advertising::getStatus, req.getStatus());
        }
        if (req.getTerrace() != null && !req.getTerrace().equals("")) {
            query.eq(Advertising::getTerrace, req.getTerrace());
        }
        if (req.getStartDate() != null && req.getEndDate() != null && !req.getStartDate().equals("")
                && !req.getEndDate().equals("")) {
            query.between(Advertising::getCreationTime, req.getStartDate(), req.getEndDate());
        }
        query.orderByDesc(Advertising::getPlanUpTime);
        IPage<Advertising> page = super.page(new Page<Advertising>(req.getPageNum(), req.getPageSize()),
                query);
        return new TableDataInfo(super.page(page, query).getRecords(), page.getTotal());
    }

    @Override
    public int updateRelease(AdverSaveReq req) {
        Advertising advertising = new Advertising();
        advertising.setId(req.getId());
        advertising.setIsRelease(req.getIsRelease());
        int i = advertisingDao.updateById(advertising);
        return i;
    }

    @Override
    public boolean saveAdvertis(AdverSaveReq advertisingReq) {
        Advertising advertising = new Advertising();
        BeanUtils.copyProperties(advertisingReq, advertising);
        advertising.setCreationTime(DateUtils.getNowDate());//创建时间
        advertising.setModificationTime(DateUtils.getNowDate());//修改时间
        return super.save(advertising);
        //return advertisingDao.insert(advertising);
    }

    @Override
    public boolean updateAdvertis(AdverSaveReq req) {
//        Advertising advertising = advertisingDao.selectById(req.getId());
//        int flag = advertisingDao.updateById(advertising);
        Advertising advertising = new Advertising();
        BeanUtils.copyProperties(req, advertising);
        advertising.setModificationTime(DateUtils.getNowDate());//修改时间
        return super.updateById(advertising);
    }

    @Override
    public int deleteAdvertis(Integer adverId) {
        Advertising advertising = new Advertising();
        // advertisingDao.selectById(advertisingReq.getId());
        advertising.setStatus(0);
        advertising.setId(adverId);
        //修改时间
        advertising.setModificationTime(DateUtils.getNowDate());
        //  advertisingDao.deleteById(adverId);
        //  return advertisingDao.deleteById(adverId);
        return advertisingDao.updateById(advertising);
    }

    @Override
    public List<String> queryLiveCarousel() {
        List<String> list = advertisingDao.queryLiveCarousel();
        return list;
    }

    @Override
    public Advertising queryLiveCarouselDetailInfo(Integer id) {

        return super.getById(id);
    }
}
