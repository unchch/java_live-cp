package com.bh.live.trade.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.ChatMsgTypeEnum;
import com.bh.live.common.enums.lottery.LotteryEnum;
import com.bh.live.common.enums.trade.OrderEnum;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.SerialNumUtil;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.model.lottery.Issue;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderInfo;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.live.MsgRcpReq;
import com.bh.live.pojo.req.order.OrderDetailReq;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.pojo.req.trade.OrderFullReq;
import com.bh.live.pojo.req.trade.OrderHistoryFullReq;
import com.bh.live.pojo.req.trade.ProfitRateRankReq;
import com.bh.live.pojo.res.live.ChatGuess;
import com.bh.live.pojo.res.live.ChatUser;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.*;
import com.bh.live.rpc.service.user.IUserFeignService;
import com.bh.live.trade.dao.OrderDao;
import com.bh.live.trade.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-07-17
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements IOrderService {

    @Resource
    private OrderDao orderDao;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private ValidatorGenerator generator;

    @Autowired
    private IOrderConfigService orderConfigService;

    @Autowired
    private BaseValidateService baseValidateService;

    @Autowired
    private IUserFeignService userFeignService;

    @Override
    public Result<OrderRes> insert(OrderReq orderReq, LiveUser liveUser) {
        Order param = new Order();
        if (!liveUser.getIsPublish().equals(UserEnum.UserPublishEnum.ON.getCode())) {
            throw new ResultJsonException(CodeMsg.E_50020);
        }
        if (liveUser.getIsExpert() == 1) {
            param.setOrderType(0);
        }
        if (liveUser.getIsAnchor() == 1) {
            param.setOrderType(1);
        }
        OrderConfig orderConfig = orderConfigService.selectObj(orderReq.getLotteryCode());
        if (ObjectUtil.isEmpty(orderConfig)) {
            throw new ResultJsonException(CodeMsg.E_50011);
        }

        //验证彩期
        Issue issue = baseValidateService.findIssue(orderReq.getLotteryCode(), orderReq.getIssue());
        baseValidateService.verifyIssueSaleStatus(issue);
        //计算订单销售截止时间
        Calendar gc = new GregorianCalendar();
        gc.setTime(issue.getEndTime());
        gc.add(GregorianCalendar.SECOND, (0 - orderConfig.getEndTime()));
        Date saleEndTime = gc.getTime();

        //验证发布可以最大发布数
        int count = selectCount(orderReq, liveUser);
        if (count >= orderConfig.getMaxNum()) {
            throw new ResultJsonException(CodeMsg.E_50027);
        }
        if (CollUtil.isEmpty(orderReq.getContent())) {
            throw new ServiceRuntimeException(CodeMsg.E_90003);
        }

        //验证格式
        Result<Map<String, Object>> result = generator.validator(orderReq);
        int[] betNum = ((Result<int[]>) result.getData().get("betNum")).getData();
        String content = (String) result.getData().get("content");
        param.setBuyTime(new Date())
                .setBuyQuantity(betNum[0])
                .setClientType(orderReq.getClientType())
                .setIsPay(orderReq.getPrice().compareTo(0d) == 1 ? OrderEnum.IsPayEnum.NO_FREE.getCode() : OrderEnum.IsPayEnum.FREE.getCode())
                .setContent(content)
                .setOrderNo(SerialNumUtil.getOrderNo())
                .setPayAmount(new BigDecimal(orderReq.getPrice()))
                .setLotSeedNo(orderReq.getLotteryCode())
                .setLotSeedName(LotteryEnum.LottoSeedNoEnum.getLottoSeed(orderReq.getLotteryCode()).getName())
                .setLotIssueNo(orderReq.getIssue())
                .setStatus(OrderEnum.OrderStatusEnum.PENDING_AWARD.getCode())
                .setAccountId(liveUser.getId())
                .setSaleEndTime(saleEndTime)
                //本金一注元。因此等于注数
                .setAmount(betNum[0]);

        List<OrderDetailReq> detailReqs = orderReq.getContent();
        List<OrderInfo> orderInfos = new ArrayList<>();
        detailReqs.forEach(v -> {
            OrderInfo orderInfo = new OrderInfo();

            orderInfo.setOrderNo(param.getOrderNo());

            orderInfo.setBetNo(SerialNumUtil.getOrderNo());
            orderInfo.setBuyQuantity(v.getBetNum())
                    .setLotSeedName(LotteryEnum.LottoSeedNoEnum.getLottoSeed(orderReq.getLotteryCode()).getName())
                    .setLotPlayNo(v.getPlayNo())
                    .setLotPlayName(v.getPlayName())
                    .setBetCode(v.getItemNo())
                    .setBetContent(v.getContent())
                    .setOdds(v.getOdds())
                    .setLotBitNo(v.getBitNo())
                    .setUnitPrice(new BigDecimal(CommonConstants.ONE))
                    .setBuyModelMultiple(CommonConstants.ONE)
                    .setStatus(OrderEnum.OrderStatusEnum.PENDING_AWARD.getCode())
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date());
            orderInfos.add(orderInfo);
        });

        orderDao.insert(param);
        orderInfoService.saveBatch(orderInfos);
        if (liveUser.getIsAnchor() == 1) {
            sendMsg(param, liveUser);
        }
        return Result.success();
    }

    private void sendMsg(Order param, LiveUser liveUser) {
        try {
            IPage<Order> iPage = new Page<>(0, 10);
            QueryWrapper<Order> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(Order::getLotSeedNo, param.getLotSeedNo()).eq(Order::getAccountId, param.getAccountId()).gt(Order::getAwardState, 0);
            iPage = orderDao.selectPage(iPage, queryWrapper);
            List<Order> list = iPage.getRecords();
            String trend = "";
            List<Integer> temp = list.stream().filter(item -> ObjectUtil.isNotEmpty(item.getAwardState())).map(Order::getAwardState).collect(Collectors.toList());
            List<String> trends = new ArrayList<>();
            temp.forEach(v -> {
                String desc = OrderEnum.AwardStateEnum.get(v).getDesc();
                trends.add(desc);
            });
            trend = trends.stream().collect(Collectors.joining(SymbolConstants.COMMA));
            ChatUser user = new ChatUser(param.getAccountId(), liveUser.getNickname(), UserEnum.ChatUserTypeEnum.ANCHOR.getDesc());
            ChatGuess guess = new ChatGuess(param.getLotIssueNo(), param.getId().intValue(), trend, param.getContent(), param.getPayAmount());
            MsgRcpReq req = new MsgRcpReq(guess, ChatMsgTypeEnum.GUESS.getCode(), String.valueOf(param.getLotSeedNo()), user);
            Integer integer = userFeignService.sendMsg(req);
        } catch (Exception e) {
            log.error("发布竞猜后发送消息异常{}", e.getMessage());
        }
    }

    @Override
    public TableDataInfo queryOrderListHistoryByUserId(OrderHistoryFullReq req) {
        Page<HistoryOrderRes> iPage = new Page<HistoryOrderRes>(req.getPageNum(), req.getPageSize());
        List<HistoryOrderRes> list = orderDao.queryOrderListHistoryByUserId(iPage, req);
        for (Iterator<HistoryOrderRes> iterator = list.iterator(); iterator.hasNext(); ) {
            HistoryOrderRes res = iterator.next();
            //判断该用户是否可以查看内容 付费且，未购买
            if (res.getIsPay() == 1 && res.getIsBuy() == 0) {
                //如果已经登录，则可以看到自己的单
                if (!res.getAccountId().equals(req.getUserId())) {
                    //内容模糊处理
                    res.setContent("***");
                }
            }
        }
        iPage.setRecords(list);
        return new TableDataInfo(iPage);
    }

    @Override
    public TableDataInfo queryOrderList(OrderFullReq req) {
        // 前端显示:专家昵称、专家图像、今日盈利率、当前连赢、今日赢率、彩种类型、彩期、号码、近十期竞猜结果、发布订单时间、是否收费
        Page<OrderFullRes> iPage = new Page<OrderFullRes>(req.getPageNum(), req.getPageSize());
        List<OrderFullRes> list = baseMapper.queryOrderList(iPage, req);
        list.forEach(v -> {
            OrderFullRes res = v;
            Integer tempUserId = res.getUserId();
            if (tempUserId != null) {
                UserOrderStatisticsRes statistics = queryUserOrderStatistics(tempUserId);// 获取订单统计信息
                res.setProfitrateValue(statistics.getTodayProfitrateJson());// 今日盈利率
                res.setWingingValue(statistics.getUserWinning());// 当前连赢
                res.setWinrateValue(statistics.getTodayWinrateJson());// 今日赢率
                res.setRecentResult(orderDao.queryRecentResultByTen(tempUserId));//近十期竞猜结果
                //判断该用户是否可以查看内容 //付费且，未购买
                if ("1".equals(res.getIsPay()) && res.getIsBuy() == 0) {
                    //如果已经登录，则可以看到自己的单
                    if (!res.getUserId().equals(req.getUserId())) {
                        //内容模糊处理
                        res.setContent("***");
                    }else{
                        v.setIsBuy(1);
                    }
                }
            }
        });
        iPage.setRecords(list);
        return new TableDataInfo(iPage);
    }

    @Override
    public UserOrderStatisticsRes queryUserOrderStatistics(Integer userId) {
        UserOrderStatisticsRes res = new UserOrderStatisticsRes();
        /**
         * 当前连赢 指标 10期 20期 30期 50期 今日 昨日 赢率 盈利
         */
        // 近五十期数据
        List<Order> orderList = super.page(new Page<Order>(0, 50), new QueryWrapper<Order>().lambda()
                .eq(Order::getAccountId, userId).orderByDesc(Order::getCreateTime)).getRecords();
        BigDecimal all = new BigDecimal(0);// 盈利率
        Integer winNum = 0, WiningNum = 0;// 赢的场数,连赢次数
        boolean isWining = true;// 是否连赢
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            if (order != null && order.getAwardState() == 1) {
                winNum++;// 赢的场数
                if (isWining) {
                    WiningNum++;// 连赢
                }
            } else {
                isWining = false;
            }

            all.add(order.getProfitRate() != null ? order.getProfitRate() : new BigDecimal(0));// 订单盈利率
            if (i == 9) {// 10期
                res.setTenProfitrateJson(all);
                res.setTenWinrateJson((float) (winNum / i + 1));
            } else if (i == 19) {// 20期
                res.setTwentyProfitrateJson(all);
                res.setTwentyWinrateJson((float) (winNum / i + 1));
            } else if (i == 29) {// 30期
                res.setThirtyProfitrateJson(all);
                res.setThirtyWinrateJson((float) (winNum / i + 1));
            } else if (i == 49) {// 50期
                res.setFiftyProfitrateJson(all);
                res.setFiftyWinrateJson((float) (winNum / i + 1));
            }
        }
        res.setUserWinning(WiningNum);// 设置用户连赢
        // 单独出参昨日和今日数据，附带赢输平数据
        Map<String, Object> yesterday = queryStatisticsByDay(userId, -1, 0);// 昨日
        res.setYesterdayNumJson((String) (yesterday.get("num") != null ? yesterday.get("num") : 0));
        res.setYesterdayProfitrateJson((BigDecimal) (yesterday.get("profitrate") != null ? yesterday.get("profitrate") : 0));
        res.setYesterdayWinrateJson((Float) (yesterday.get("winrate") != null ? yesterday.get("winrate") : 0));
        Map<String, Object> today = queryStatisticsByDay(userId, 0, 1);// 今日
        res.setTodayNumJson((String) (today.get("num") != null ? today.get("num") : 0));
        res.setTodayProfitrateJson((BigDecimal) (today.get("profitrate") != null ? today.get("profitrate") : 0));
        res.setTodayWinrateJson((Float) (today.get("winrate") != null ? today.get("winrate") : 0));
        res.setUserId(userId);// 用户id
        return res;
    }

    @Override
    public Integer selectCount(OrderReq orderReq, LiveUser liveUser) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Order::getAccountId, liveUser.getId())
                .eq(Order::getLotIssueNo, orderReq.getIssue())
                .eq(Order::getLotSeedNo, orderReq.getLotteryCode());
        return orderDao.selectCount(wrapper);
    }

    @Override
    public List<GuessingRes> selectGuess(Integer seedNo, String lotIssueNo) {
        return orderDao.guessingList(seedNo, lotIssueNo);
    }

    @Override
    public GuessingDetailRes getGuessDetail(Integer id, Integer userId) {
        GuessingDetailRes detailRes = orderDao.guessingDetail(id, userId);
        if (detailRes == null) {
            return null;
        }
        //计算连赢次数
        int count = 1;
        //返回结果（赢,输,赢,输,输,赢,赢,输,输,输）
        String result10 = detailRes.getResult10();
        if (StringUtils.isNotEmpty(result10)) {
            String[] strings = result10.split(SymbolConstants.COMMA);
            String first = result10.split(SymbolConstants.COMMA)[0]; //获取第一个结果，从第一个开始计算连赢次数
            for (int i = 1; i < strings.length; i++) {
                if (strings[i].equals(first)) {
                    count++;
                }
            }
            detailRes.setNewResult(first);
            detailRes.setSeriesWinLoseCount(count);
        }
        if (detailRes.getIsAttent() == null) {
            detailRes.setIsAttent(0);
        }
        //如果用户未购买，不返回竞猜详情
        if (detailRes.getIsBuy() == 0 && detailRes.getIsPay() == 1) {
            detailRes.setGuessingResultList(null);
        }
        return detailRes;
    }

    private Map<String, Object> queryStatisticsByDay(Integer userId, int i, int j) {
        BigDecimal all = new BigDecimal(0);// 盈利率
        Integer winNum = 0, loseNum = 0, flatNum = 0;// 赢输平
        List<Order> list = super.list(new QueryWrapper<Order>().lambda().eq(Order::getAccountId, userId)
                .between(Order::getCreateTime, DateUtils.getDay(i), DateUtils.getDay(j))// day
                .orderByDesc(Order::getCreateTime));
        for (int k = 0; k < list.size(); k++) {
            Order order = list.get(k);
            // 中奖输赢状态(1:赢，2:输，3:和)
            if (order != null && order.getAwardState() == 1) {
                winNum++;
            } else if (order != null && order.getAwardState() == 2) {
                loseNum++;
            } else if (order != null && order.getAwardState() == 3) {
                flatNum++;
            }
            all.add(order.getProfitRate() != null ? order.getProfitRate() : new BigDecimal(0));// 订单盈利率
        }
        float winrate = (list != null && list.size() != 0) ? winNum / list.size() : 0;// 胜率
        // 打包出参
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("profitrate", all);
        map.put("winrate", winrate);
        map.put("num", winNum + ";" + loseNum + ";" + flatNum);// 赢输平
        return map;
    }

    @Override
    public List<OrderInfoContentRes> selectOrderInfo(Order order) {
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getOrderNo, order.getOrderNo());
        List<OrderInfo> list = orderInfoService.list(queryWrapper);
        Map<String, List<OrderInfo>> map = list.stream().collect(Collectors.groupingBy(OrderInfo::getLotPlayNo));
        List<OrderInfoContentRes> result = new ArrayList<>();
        map.forEach((k, v) -> {
            String content = v.stream().map(OrderInfo::getBetContent).collect(Collectors.joining(SymbolConstants.COMMA));
            String playName = v.get(0).getLotPlayName();
            String playNo = v.get(0).getLotPlayNo();
            OrderInfoContentRes res = new OrderInfoContentRes();
            res.setContent(content).setGuessName(playName).setPlayNo(Integer.valueOf(playNo));
            result.add(res);
        });
        result.sort(Comparator.comparing(OrderInfoContentRes::getPlayNo));
        return result;
    }


    @Override
    public List<ProfitRateRankRes> selectRateRank(ProfitRateRankReq req) {
        Assert.isNotNull(req.getSeedNo(), CodeMsg.E_50009);
        if (ObjectUtil.isEmpty(req.getNum())) {
            req.setNum(10);
        }
        if (req.getNum() > 50) {
            req.setNum(50);
        }

        List<ProfitRateRankRes> list = new ArrayList<>();
        if (ObjectUtil.isEmpty(req.getType()) || req.getType() == 1) {
            list = orderDao.selectUserJoinWin(req);
        }
        if (req.getType() == 2) {
            list = orderDao.selectUserWinRate(req);
        }
        if (req.getType() == 3) {
            list = orderDao.selectProfitRate(req);
        }
        List<String> strings = new ArrayList<>();
        list.forEach(v -> {
            strings.add(String.valueOf(v.getUserId()));
        });
        String userIds = strings.stream().collect(Collectors.joining(SymbolConstants.COMMA));
        List<Integer> accounts = orderDao.selectUserPush(userIds, req.getSeedNo());
        list.forEach(v -> {
            if (accounts.contains(v.getUserId())) {
                v.setPush(1);
            } else {
                v.setPush(0);
            }
        });
        return list;
    }

    @Override
    public List<LottoPlayNoNameRes> queryPlayNoAndNameBySeedNo(Integer seedNo) {

        return orderDao.queryPlayNoAndNameBySeedNo(seedNo);
    }
}
