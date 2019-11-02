package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.GiftListIncomeRes;
import com.bh.live.user.dao.StatUserIncomeDao;
import com.bh.live.user.service.IStatUserIncomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主播礼物收入统计表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
@Service
public class StatUserIncomeServiceImpl extends ServiceImpl<StatUserIncomeDao, StatUserIncome> implements IStatUserIncomeService {

    @Resource
    private StatUserIncomeDao userIncomeDao;

    @Override
    public TableDataInfo selectUserIncome(Map<String, Object> params) {
        IPage<StatUserIncome> page = new Page<>(Long.valueOf(params.get("pageNum").toString()), Long.valueOf(params.get("pageSize").toString()));
        QueryWrapper<StatUserIncome> wrapper = new QueryWrapper<>();
        String date = params.get("date").toString();
        Integer userId = Integer.valueOf(params.get("userId").toString());
        if (date != null && !date.equals("")) wrapper.eq("stat_date", date);
        if (userId != null && userId > 0) wrapper.eq("user_id", userId);
        IPage<StatUserIncome> pageData = userIncomeDao.selectPage(page, wrapper);
        return new TableDataInfo(pageData);
    }

    @Override
    public TableDataInfo giftDonateList(Map<String, Object> params) {
        IPage<GiftListIncomeRes> page = new Page<>(Long.valueOf(params.get("pageNum").toString()), Long.valueOf(params.get("pageSize").toString()));
        page.setRecords(userIncomeDao.giftIncomeList(page, params));
        return new TableDataInfo(page);
    }
}
