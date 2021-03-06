package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.pojo.res.user.GiftListIncomeRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主播礼物收入统计表 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
public interface StatUserIncomeDao extends BaseMapper<StatUserIncome> {

    /**
     * 获取送礼物清单
     *
     * @param params
     * @return
     */
    List<GiftListIncomeRes> giftIncomeList(IPage<GiftListIncomeRes> page, @Param("params") Map<String, Object> params);
}
