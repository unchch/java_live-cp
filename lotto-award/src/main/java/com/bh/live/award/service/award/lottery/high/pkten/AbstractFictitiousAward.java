package com.bh.live.award.service.award.lottery.high.pkten;

import com.bh.live.award.service.award.AbstractAward;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.enums.trade.OrderEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CalculatorUtil;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.lottery.*;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WuLong
 * @date 2019/8/6 14:16
 * @description 虚拟类（幸运飞艇、北京赛车）
 */
public abstract class AbstractFictitiousAward extends AbstractAward {
    /**
     * 开奖号码  1,2,3,4,5,6,7,8,9,10
     */
    protected String[] drawCodes;
    /**
     * 玩法表
     */
    protected Map<String, Play> playMap = new ConcurrentHashMap<>();
    /**
     * 投注位表
     */
    protected Map<String, List<PlayBit>> playBitMap = new ConcurrentHashMap<>();

    /**
     * 投注项表
     */
    protected Map<String, List<PlayItem>> playItemMap = new ConcurrentHashMap<>();

    /**
     * 赔率基础表
     */
    protected Map<String, List<PlayBet>> playBetMap = new ConcurrentHashMap<>();
    /**
     * 匹配单双结果
     */
    protected Map<String, String> matchSingleDoubleResultMap = new ConcurrentHashMap<>();
    /**
     * 匹配大小结果
     */
    protected Map<String, String> matchBigSmallResultMap = new ConcurrentHashMap<>();
    /**
     * 匹配龙虎结果
     */
    protected Map<String, String> matchDragonTigerResultMap = new ConcurrentHashMap<>();
    /**
     * 匹配定位结果
     */
    protected Map<String, String> matchLocationResultMap = new ConcurrentHashMap<>();
    /** 匹配冠亚和结果 */
    protected Map<String,String>  guanYaResultMap = new ConcurrentHashMap<>();

    @Override
    protected void compute(Order order, HandleEnum.PrizeStatusEnum prizeStatusEnum) throws ServiceRuntimeException {
        //订单表先默认未中奖
        OrderEnum.OrderStatusEnum orderStatusEnum = OrderEnum.OrderStatusEnum.NOT_WINNING;
        List<OrderInfo> orderInfos = order.getOrderInfos();
        //中奖金额
        BigDecimal winAmount = BigDecimal.ZERO;
        //中奖注数
        Integer win_quantity = 0;
        //盈亏（负数为亏损）
        BigDecimal profit_amount = BigDecimal.ZERO;
        //赋值开奖号码
        order.setAwardNumber(drawCode);
        for (OrderInfo orderInfo : orderInfos) {
            //中奖金额
            BigDecimal orderInfo_winAmount = BigDecimal.ZERO;
            //计算盈亏
            BigDecimal orderInfo_profit_amount = BigDecimal.ZERO;
            //单注金额
            BigDecimal unit_price = orderInfo.getUnitPrice();
            //购买倍数
            BigDecimal buy_model_multiple = BigDecimal.valueOf(orderInfo.getBuyModelMultiple());
            //赔率
            BigDecimal odds = orderInfo.getOdds();
            //投注订单表先默认未中奖
            OrderEnum.OrderStatusEnum orderInfoStatusEnum = OrderEnum.OrderStatusEnum.NOT_WINNING;
            //玩法编号
            String lotPlayNo = orderInfo.getLotPlayNo();
            //投注选项编号
            String betCode = orderInfo.getBetCode();
            //投注位
            String lotBitNo = orderInfo.getLotBitNo();
            //投注内容
            String betContent = orderInfo.getBetContent();
            List<PlayBit> playBitList = playBitMap.get(lotPlayNo);
            Map<String, PlayBit> playBitMap = playBitList.stream().collect(Collectors.toMap(PlayBit::getBitNo, Function.identity(), (key1, key2) -> key2));
            PlayBit playBit = playBitMap.get(lotBitNo);
            List<PlayItem> playItems = playItemMap.get(playBit.getBitNo());
            Map<String, PlayItem> playItemMap = playItems.stream().collect(Collectors.toMap(PlayItem::getItemNo, Function.identity(), (key1, key2) -> key2));
            PlayItem playItem = playItemMap.get(betCode);
            String playItemContent = playItem.getContent();

            HandleEnum.FictitiousBitEnum fictitiousBitEnum = HandleEnum.FictitiousBitEnum.getEnumByCode(playBit.getContent());
            //获取定位号码的彩果
            String key = lotPlayNo + SymbolConstants.COMMA + playBit.getBitNo();
            String result = null;
            if (fictitiousBitEnum == HandleEnum.FictitiousBitEnum.NUMBER) {
                result = matchLocationResultMap.get(key);
            }
            //获取两面的彩果
            else if (fictitiousBitEnum == HandleEnum.FictitiousBitEnum.TWO_SIDE) {
                if (playItemContent.equals(HandleEnum.FictitiousItemEnum.BIG.getCode())
                        || playItemContent.equals(HandleEnum.FictitiousItemEnum.SMALL.getCode())) {
                    result = matchBigSmallResultMap.get(key);
                } else if (playItemContent.equals(HandleEnum.FictitiousItemEnum.SINGLE.getCode())
                        || playItemContent.equals(HandleEnum.FictitiousItemEnum.DUAL.getCode())) {
                    result = matchSingleDoubleResultMap.get(key);
                } else if (playItemContent.equals(HandleEnum.FictitiousItemEnum.DRAGON.getCode())
                        || playItemContent.equals(HandleEnum.FictitiousItemEnum.TIGER.getCode())) {
                    result = matchDragonTigerResultMap.get(key);
                }
            }else if(fictitiousBitEnum == HandleEnum.FictitiousBitEnum.GUAN_YA_HE){
                result = guanYaResultMap.get(key);
            }
            orderInfo.setAwardNumber(result);
            //判断彩果是否匹配
            if (result.equals(betContent)) {
                orderInfo.setWinQuantity(1);
                //只要投注订单表记录有一条已中奖就设置，这整个订单已中奖
                if (orderStatusEnum == OrderEnum.OrderStatusEnum.NOT_WINNING) {
                    orderStatusEnum = OrderEnum.OrderStatusEnum.WINNING;
                }
                orderInfoStatusEnum = OrderEnum.OrderStatusEnum.WINNING;
                //计算奖金
                double v = CalculatorUtil.cauScale(2, buy_model_multiple.multiply(odds).multiply(unit_price));
                orderInfo_winAmount = BigDecimal.valueOf(v);
                //order表中奖金额累加
                win_quantity++;
            }
            orderInfo_profit_amount = orderInfo_winAmount.subtract(orderInfo.getUnitPrice());
            profit_amount = profit_amount.add(orderInfo_profit_amount);
            winAmount = winAmount.add(orderInfo_winAmount);
            //设置中奖输赢状态
            if (orderInfo_profit_amount.compareTo(BigDecimal.ZERO) > 0) {
                orderInfo.setAwardState(OrderEnum.AwardStateEnum.WIN.getCode());
            } else if (orderInfo_profit_amount.compareTo(BigDecimal.ZERO) < 0) {
                orderInfo.setAwardState(OrderEnum.AwardStateEnum.LOST.getCode());
            } else {
                orderInfo.setAwardState(OrderEnum.AwardStateEnum.DRAW.getCode());
            }
            //中奖金额
            orderInfo.setAwardAmount(orderInfo_winAmount);
            orderInfo.setProfitAmount(orderInfo_profit_amount);
            orderInfo.setStatus(orderInfoStatusEnum.getCode());
        }
        //设置订单表状态属性
        order.setStatus(orderStatusEnum.getCode());
        order.setWinQuantity(win_quantity);
        order.setAwardAmount(winAmount);
        if (profit_amount.compareTo(BigDecimal.ZERO) > 0) {
            order.setAwardState(OrderEnum.AwardStateEnum.WIN.getCode());
        } else if (profit_amount.compareTo(BigDecimal.ZERO) < 0) {
            order.setAwardState(OrderEnum.AwardStateEnum.LOST.getCode());
        } else {
            order.setAwardState(OrderEnum.AwardStateEnum.DRAW.getCode());
        }
        order.setPrizeStatus(prizeStatusEnum.getCode());
        order.setLotteryTime(new Date());
        order.setProfitRate(CalculatorUtil.getProfitRate(profit_amount,BigDecimal.valueOf(order.getAmount())));
        order.setProfitAmount(profit_amount);
    }

    @Override
    protected void getDrawCode(List<Order> orders) throws ServiceRuntimeException {
        if (haveDrawCode()) {
            return;
        }
        synchronized (this) {
            if (!haveDrawCode()) {
                Order order = null;
                if (orders != null && (order = orders.get(0)) != null) {
                    Issue issue = issueService.selectBySeedIssueNo(order.getLotIssueNo(), order.getLotSeedNo());
                    if (null == issue) {
                        throw new ServiceRuntimeException(CodeMsg.E_80007);
                    }
                    if (CommonUtil.isEmpty(issue.getResult())) {
                        throw new ServiceRuntimeException(CodeMsg.E_80008);
                    }
                    drawCode = issue.getResult();
                    handleDrawCode(drawCode);
                    if (!havePlay()) {
                        List<Play> plays = playService.selectBySeedNo(order.getLotSeedNo());
                        if (CommonUtil.isNotEmpty(plays)) {
                            playMap = plays.stream().collect(Collectors.toMap(Play::getPlayNo, Function.identity(), (key1, key2) -> key2));
                            if (!havePlayBit()) {
                                List<String> playNos = new ArrayList<>();
                                plays.forEach(play -> {
                                    playNos.add(play.getPlayNo());
                                });
                                List<PlayBit> playBits = playBitService.selectByPlayNo(playNos);
                                if (CommonUtil.isNotEmpty(playBits)) {
                                    playBitMap = playBits.stream().collect(Collectors.toMap(PlayBit::getPlayNo,
                                            p -> {
                                                List<PlayBit> playBitList = new ArrayList<>();
                                                playBitList.add(p);
                                                return playBitList;
                                            },
                                            (List<PlayBit> value1, List<PlayBit> value2) -> {
                                                value1.addAll(value2);
                                                return value1;
                                            }
                                    ));
                                    if (!havePlayItem()) {
                                        List<String> bitNos = new ArrayList<>();
                                        for (PlayBit playBit : playBits) {
                                            bitNos.add(playBit.getBitNo());
                                        }
                                        List<PlayItem> playItems = playItemService.selectByBitNo(bitNos);
                                        if (CommonUtil.isNotEmpty(playItems)) {
                                            playItemMap = playItems.stream().collect(Collectors.toMap(PlayItem::getBitNo,
                                                    p -> {
                                                        List<PlayItem> playItemList = new ArrayList<>();
                                                        playItemList.add(p);
                                                        return playItemList;
                                                    },
                                                    (List<PlayItem> value1, List<PlayItem> value2) -> {
                                                        value1.addAll(value2);
                                                        return value1;
                                                    }
                                            ));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!havePlayBet()) {
                        List<PlayBet> playBets = playBetService.selectBySeedNo(order.getLotSeedNo());
                        if (CommonUtil.isNotEmpty(playBets)) {
                            playBetMap = playBets.stream().collect(Collectors.toMap(PlayBet::getPlayNo,
                                    p -> {
                                        List<PlayBet> playBetList = new ArrayList<>();
                                        playBetList.add(p);
                                        return playBetList;
                                    },
                                    (List<PlayBet> value1, List<PlayBet> value2) -> {
                                        value1.addAll(value2);
                                        return value1;
                                    }
                            ));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void depthClone(AbstractAward award) throws ServiceRuntimeException {
        AbstractFictitiousAward fictitiousAward = (AbstractFictitiousAward) award;
        fictitiousAward.playBetMap = new ConcurrentHashMap<>();
        fictitiousAward.playBitMap = new ConcurrentHashMap<>();
        fictitiousAward.playMap = new ConcurrentHashMap<>();
        fictitiousAward.playItemMap = new ConcurrentHashMap<>();
    }


    /**
     * @param drawCode 已逗号分隔的格式
     * @description 处理彩果拆分
     * @author WuLong
     * @date 2019/8/6 14:25
     */
    protected void handleDrawCode(String drawCode) {
        drawCodes = drawCode.split(SymbolConstants.COMMA);
    }

    /**
     * @return boolean
     * @description 判断是否已经获取解析了开奖号码
     * @author WuLong
     * @date 2019/8/6 15:19
     */
    protected abstract boolean haveDrawCode();

    /**
     * @description 判断是否已经获取到玩法表
     * @author WuLong
     * @date 2019/8/6 15:36
     */
    protected abstract boolean havePlay();

    /**
     * @description 判断是否已经获取到投注位表
     * @author WuLong
     * @date 2019/8/6 15:36
     */
    protected abstract boolean havePlayBit();

    /**
     * @description 判断是否已经获取到赔率基础表
     * @author WuLong
     * @date 2019/8/6 15:36
     */
    protected abstract boolean havePlayBet();

    /**
     * @description 判断是否已经获取到投注项表
     * @author WuLong
     * @date 2019/8/12 11:36
     */
    protected abstract boolean havePlayItem();

    /**
     * @description 判断是否已经根据彩果计算出所有的结果
     * @author WuLong
     * @date 2019/8/12 11:36
     */
    protected abstract boolean haveResultOdds();

    /**
     * @description 根据彩果计算出所有的结果
     * @author WuLong
     * @date 2019/8/12 11:36
     */
    public void resultOdds(String beforePlayNo) throws ServiceRuntimeException {
        if (!haveResultOdds()) {
            String[] splitPlayNo = beforePlayNo.split(SymbolConstants.COMMA);
            guanYaHe(splitPlayNo[1]);
            for (int i = 0, len = drawCodes.length; i < len; i++) {
                String drawCode = drawCodes[i];
                Integer draw = Integer.valueOf(drawCode);
                boolean is_single_double = (draw % 2 == 0);
                String singleDoubleRResult = is_single_double ? HandleEnum.FictitiousItemEnum.DUAL.getCode() : HandleEnum.FictitiousItemEnum.SINGLE.getCode();
                String bigSmallResult = draw > 5 ? HandleEnum.FictitiousItemEnum.BIG.getCode() : HandleEnum.FictitiousItemEnum.SMALL.getCode();
                boolean is_first_zero = true;
                if(draw < 10){
                    is_first_zero = drawCode.startsWith("0");
                }
                drawCode = is_first_zero ? drawCode : ("0" + drawCode);
                String transToKey = ("0"+(i+1));
                String playNo = splitPlayNo[0].concat(transToKey);
                List<PlayBit> playBitList = playBitMap.get(playNo);
                if (CommonUtil.isEmpty(playBitList)) {
                    return;
                }
                for (PlayBit playBit : playBitList) {
                    HandleEnum.FictitiousBitEnum luckyAirshipBitEnum = HandleEnum.FictitiousBitEnum.getEnumByCode(playBit.getContent());
                    //定位号码
                    String key = playNo + SymbolConstants.COMMA + playBit.getBitNo();
                    if (luckyAirshipBitEnum == HandleEnum.FictitiousBitEnum.NUMBER) {
                        matchLocationResultMap.put(key, drawCode);
                    }
                    //两面
                    else if (luckyAirshipBitEnum == HandleEnum.FictitiousBitEnum.TWO_SIDE) {
                        matchSingleDoubleResultMap.put(key, singleDoubleRResult);
                        matchBigSmallResultMap.put(key, bigSmallResult);
                        if (i < 5) {
                            Integer matchDrawCode = Integer.valueOf(drawCodes[len - (i + 1)]);
                            String dragonTigerResult = draw > matchDrawCode ? HandleEnum.FictitiousItemEnum.DRAGON.getCode() : HandleEnum.FictitiousItemEnum.TIGER.getCode();
                            matchDragonTigerResultMap.put(key, dragonTigerResult);
                        }
                    }
                }
            }
        }
    }

    public void guanYaHe(String playNo){
        Integer guan = Integer.valueOf(drawCodes[0]);
        Integer ya = Integer.valueOf(drawCodes[1]);
        Integer sum = guan+ya;
        boolean is_single_double = (sum % 2 == 0);
        String singleDoubleRResult = is_single_double ? HandleEnum.FictitiousItemEnum.DUAL.getCode() : HandleEnum.FictitiousItemEnum.SINGLE.getCode();
        String bigSmallResult = null;
        if(sum == 11){
            bigSmallResult = HandleEnum.FictitiousItemEnum.HE.getCode();
        }else if(sum > 11){
            bigSmallResult = HandleEnum.FictitiousItemEnum.BIG.getCode();
        }else{
            bigSmallResult = HandleEnum.FictitiousItemEnum.SMALL.getCode();
        }
        List<PlayBit> playBitList = playBitMap.get(playNo);
        if (CommonUtil.isEmpty(playBitList)) {
            return;
        }
        for (PlayBit playBit : playBitList) {
            HandleEnum.FictitiousBitEnum luckyAirshipBitEnum = HandleEnum.FictitiousBitEnum.getEnumByCode(playBit.getContent());
            //冠亚和
            String key = playNo + SymbolConstants.COMMA + playBit.getBitNo();
            if (luckyAirshipBitEnum == HandleEnum.FictitiousBitEnum.GUAN_YA_HE) {
                guanYaResultMap.put(key, String.valueOf(sum));
            }
            //两面
            else if (luckyAirshipBitEnum == HandleEnum.FictitiousBitEnum.TWO_SIDE) {
                matchSingleDoubleResultMap.put(key, singleDoubleRResult);
                matchBigSmallResultMap.put(key, bigSmallResult);
            }
        }
    }

}
