package com.bh.live.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.AdvertisingDao;
import com.bh.live.model.announcement.Advertising;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.IAdvertisingService;

/**
 * <p>
 * 广告表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
@Service
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingDao, Advertising>
		implements IAdvertisingService {

	@Override
	public TableDataInfo queryLiveCarousel(AdvertisingReq req) {
		LambdaQueryWrapper<Advertising> query = new QueryWrapper<Advertising>().lambda();
		if (req.getType() != null) {
			query.eq(Advertising::getType, req.getType());
		}
		
		query.eq(Advertising::getStatus, 1).orderByDesc(Advertising::getPlanUpTime);
		IPage<Advertising> page = super.page(new Page<Advertising>(req.getPageNum(), req.getPageSize()),
				query);
		return new TableDataInfo(super.page(page, query).getRecords(), page.getTotal());
	}
}
