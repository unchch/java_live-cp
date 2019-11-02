package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.anchor.GiftDao;
import com.bh.live.model.anchor.Gift;
import com.bh.live.pojo.req.anchor.GiftReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.IGiftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 礼物表 服务实现类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
@Service
public class GiftServiceImpl extends ServiceImpl<GiftDao, Gift> implements IGiftService {
    @Resource
    GiftDao giftDao;

    @Transactional
    @Override
    public Integer addGift(GiftReq gift) {
        Gift g=new Gift();
        CommonUtil.beanCopy(gift,g);
        g.setCreateTime(new Date());
        // 有登陆用户后修改
        g.setCreateBy(1);
        return giftDao.insert(g);
    }

    @Transactional
    @Override
    public Integer updateGift(GiftReq gift) {
        Gift g=new Gift();
        CommonUtil.beanCopy(gift,g);
        // 有登陆用户后修改
        g.setUpdateBy(1);
        g.setUpdateTime(new Date());
        return giftDao.updateById(g);
    }

    @Transactional
    @Override
    public Integer deleteGift(List<Integer> ids) {
        return giftDao.delete(ids);
    }

    @Override
    public TableDataInfo getGifts(GiftReq gift) {
        Page<GiftReq> page = new Page<>(gift.getPageNum(), gift.getPageSize());
        page.setRecords(giftDao.queryGiftPage(page,gift));
        return new TableDataInfo(page);
    }

    @Override
    public GiftReq getGiftById(Integer giftId) {
        Gift gift = giftDao.selectById(giftId);
        GiftReq giftReq=new GiftReq();
        CommonUtil.beanCopy(gift,giftReq);
        return giftReq;
    }
}
