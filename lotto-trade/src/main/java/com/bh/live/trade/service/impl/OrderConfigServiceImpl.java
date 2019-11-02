package com.bh.live.trade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.model.lottery.Issue;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.lottery.config.OrderUserConfig;
import com.bh.live.model.lottery.config.OrderUserProfitConfg;
import com.bh.live.model.rankingList.RankingList;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.pojo.res.trade.PlayOptRes;
import com.bh.live.trade.dao.*;
import com.bh.live.trade.service.IOrderConfigService;
import com.bh.live.trade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private OrderUserConfigDao orderUserConfigDao;

    @Autowired
    private OrderUserProfitConfgDao orderUserProfitConfgDao;

    @Autowired
    private RankingListDao rankingListDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IOrderService orderService;

    @Override
    public OrderConfig selectObj(Integer lotteryCode) {
        QueryWrapper<OrderConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderConfig::getSeedNo, lotteryCode);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PlayOptRes selectOpt(Integer lotteryCode, LiveUser liveUser) {
        PlayOptRes res = new PlayOptRes();
        LambdaQueryWrapper<OrderUserProfitConfg> queryProfitWrapper = new LambdaQueryWrapper();
        queryProfitWrapper.eq(OrderUserProfitConfg::getLotteryCode, lotteryCode);
        List<OrderUserProfitConfg> orderUserProfitConfgs = orderUserProfitConfgDao.selectList(queryProfitWrapper);

        UserEnum.UserTypeEnum userTypeEnum = UserEnum.UserTypeEnum.getUserType(liveUser);
        QueryWrapper<RankingList> rankingListQueryWrapper = new QueryWrapper<>();
        rankingListQueryWrapper.lambda().eq(RankingList::getUserId, liveUser.getId()).eq(RankingList::getRankPeriod, 3);
        RankingList rankingList = rankingListDao.selectOne(rankingListQueryWrapper);
        String priceType = "";
        if (ObjectUtil.isNotEmpty(rankingList)) {
            BigDecimal profitrateValue = rankingList.getProfitrateValue();
            AtomicReference<OrderUserProfitConfg> userProfitConfg = new AtomicReference<>(new OrderUserProfitConfg());
            orderUserProfitConfgs.forEach(v -> {
                if (v.getSection().compareTo(profitrateValue) > 0) {
                    userProfitConfg.set(v);
                }
            });
            priceType = userProfitConfg.get().getPriceType();
        }

        List<Integer> result = getPriceType(priceType);
        if (CollectionUtil.isEmpty(result)) {
            LambdaQueryWrapper<OrderUserConfig> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(OrderUserConfig::getLotteryCode, lotteryCode).eq(OrderUserConfig::getUserType, userTypeEnum.getCode());
            OrderUserConfig userConfigs = orderUserConfigDao.selectOne(queryWrapper);
            //如果都查不到用户数据 默认发免费0元
            if (ObjectUtil.isEmpty(userConfigs)) {
                result = new ArrayList<>();
                result.add(0);
                res.setPrices(result);
            } else {
                result = getPriceType(userConfigs.getPriceType());
                //如果都查不到用户数据 默认发免费0元
                if (CollectionUtil.isEmpty(result)) {
                    result = new ArrayList<>();
                    result.add(0);
                }
            }
        }

        OrderConfig orderConfig = selectObj(lotteryCode);
        if (ObjectUtil.isEmpty(orderConfig)) {
            throw new ResultJsonException(CodeMsg.E_50011);
        }

        QueryWrapper<Issue> issueQueryWrapper = new QueryWrapper<>();
        Date date = new Date();
        issueQueryWrapper.lambda().select(Issue::getIssueNo, Issue::getClosingTime).eq(Issue::getSeedNo, lotteryCode).le(Issue::getStartTime, date).ge(Issue::getClosingTime, date);
        Issue issue = issueDao.selectOne(issueQueryWrapper);
        if (ObjectUtil.isNotEmpty(issue)) {
            OrderReq orderReq = new OrderReq();
            orderReq.setIssue(issue.getIssueNo()).setLotteryCode(lotteryCode);
            //验证发布可以最大发布数
            int count = orderService.selectCount(orderReq, liveUser);
            if (count >= orderConfig.getMaxNum()) {
                res.setPush(0);
            } else {
                res.setPush(1);
            }
            res.setIssue(issue.getIssueNo());
            res.setEndTime(issue.getClosingTime());
        }
        res.setPrices(result).setServerTime(date);
        return res;
    }

    private List<Integer> getPriceType(String priceType) {
        if (StrUtil.isEmpty(priceType)) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        String[] priceTypes;
        if (priceType.contains(SymbolConstants.COMMA)) {
            priceTypes = priceType.split(SymbolConstants.COMMA);
        } else {
            priceTypes = new String[]{priceType};
        }
        for (String type : priceTypes) {
            result.add(Integer.valueOf(type));
        }
        return result;
    }

//	@Override
//	public List<Seed> lottoHall() {
//
//		return null;
//	}
}
