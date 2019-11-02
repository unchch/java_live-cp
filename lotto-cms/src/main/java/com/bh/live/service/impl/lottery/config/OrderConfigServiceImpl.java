package com.bh.live.service.impl.lottery.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.lottery.config.OrderConfigDao;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.lottery.config.OrderUserConfig;
import com.bh.live.model.lottery.config.OrderUserProfitConfg;
import com.bh.live.model.lottery.config.vo.OrderConfigVO;
import com.bh.live.pojo.res.lottery.config.OrderConfigRes;
import com.bh.live.pojo.res.lottery.config.OrderUserConfigRes;
import com.bh.live.pojo.res.lottery.config.OrderUserProfitConfgRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.config.IOrderConfigService;
import com.bh.live.service.lottery.config.IOrderUserConfigService;
import com.bh.live.service.lottery.config.IOrderUserProfitConfgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 彩种订单配置表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@Service
public class OrderConfigServiceImpl extends ServiceImpl<OrderConfigDao, OrderConfig> implements IOrderConfigService {

    @Autowired
    private IOrderUserConfigService orderUserConfigService;

    @Autowired
    private IOrderUserProfitConfgService orderUserProfitConfgService;


    @Override
    public TableDataInfo selectOrderConfig(OrderConfigVO vo) {
        Page<OrderConfigRes> iPage = new Page<>(vo.getPageNum(), vo.getPageSize());
        List<OrderConfigRes> resList = baseMapper.selectOrderConfig(iPage, vo);
        resList.forEach(res -> {
            QueryWrapper<OrderUserConfig> orderUserConfigQueryWrapper = new QueryWrapper<>();
            orderUserConfigQueryWrapper.lambda().eq(OrderUserConfig::getLotteryCode, res.getSeedNo()).orderByAsc(OrderUserConfig::getUserType);
            List<OrderUserConfigRes> gradeLimitList = new ArrayList<>();
            List<OrderUserProfitConfgRes> profitLimitList = new ArrayList<>();
            orderUserConfigService.list(orderUserConfigQueryWrapper).stream().filter(item -> CommonUtil.isNotEmpty(item.getPriceType())).forEach(v -> {
                OrderUserConfigRes orderUserConfigRes = new OrderUserConfigRes();
                BeanUtils.copyProperties(v, orderUserConfigRes);
                orderUserConfigRes.setPriceList(Arrays.stream(v.getPriceType().split(SymbolConstants.COMMA)).mapToInt(Integer::valueOf).toArray());
                gradeLimitList.add(orderUserConfigRes);
            });
            QueryWrapper<OrderUserProfitConfg> orderUserProfitConfgQueryWrapper = new QueryWrapper<>();
            orderUserProfitConfgQueryWrapper.lambda().eq(OrderUserProfitConfg::getLotteryCode, res.getSeedNo()).orderByAsc(OrderUserProfitConfg::getSection);
            orderUserProfitConfgService.list(orderUserProfitConfgQueryWrapper).stream().filter(item -> CommonUtil.isNotEmpty(item.getPriceType())).forEach(v -> {
                OrderUserProfitConfgRes orderUserConfigRes = new OrderUserProfitConfgRes();
                BeanUtils.copyProperties(v, orderUserConfigRes);
                orderUserConfigRes.setPriceList(Arrays.stream(v.getPriceType().split(SymbolConstants.COMMA)).mapToInt(Integer::valueOf).toArray());
                profitLimitList.add(orderUserConfigRes);
            });
            res.setGradeLimitList(gradeLimitList);
            res.setProfitLimitList(profitLimitList);
            int[] userGroups = Arrays.stream(res.getUserGroup().split(SymbolConstants.COMMA)).mapToInt(Integer::valueOf).toArray();
            res.setUserGroups(userGroups);
            res.setUserPlans(Arrays.stream(res.getUserPlan().split(SymbolConstants.COMMA)).mapToInt(Integer::valueOf).toArray());
        });
        iPage.setRecords(resList);
        return new TableDataInfo(iPage);
    }
}
