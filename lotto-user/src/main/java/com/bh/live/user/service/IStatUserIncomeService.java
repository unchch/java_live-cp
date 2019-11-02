package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主播礼物收入统计表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
public interface IStatUserIncomeService extends IService<StatUserIncome> {

    /**
     * 查询主播收入
     *
     * @param params
     * @return
     */
    TableDataInfo selectUserIncome(Map<String, Object> params);

    /**
     * 礼物赠送清单
     *
     * @param params
     * @return
     */
    TableDataInfo giftDonateList(Map<String, Object> params);

}
