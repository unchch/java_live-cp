package com.bh.live.trade.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.redislock.RedisLock;
import com.bh.live.common.redislock.lock.RedisLockKey;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.SerialNumUtil;
import com.bh.live.model.trade.Wallet;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.pojo.req.trade.TradeTransUserLogReq;
import com.bh.live.pojo.res.trade.TradeTransUserRes;
import com.bh.live.trade.dao.TradeTransUserDao;
import com.bh.live.trade.dao.TradeTransUserLogDao;
import com.bh.live.trade.dao.WalletDao;
import com.bh.live.trade.service.IUserTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lgs
 * @title: UserTradeServiceImpl
 * @projectName java_live-cp
 * @description: 用户交易服务
 * @date 2019/8/8  15:38
 */
@Service
@Slf4j
public class UserTradeServiceImpl implements IUserTradeService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WalletDao walletDao;

    @Autowired
    private TradeTransUserDao tradeTransUserDao;

    @Autowired
    private TradeTransUserLogDao tradeTransUserLogDao;


    /**
     * 用户加钱
     *
     * @param user   操作用户
     * @param amount 操作金额
     * @return 返回用户操作之前余额
     */
    @RedisLock(redisKey = RedisLockKey.USER_WALLET, prop = "id")
    protected BigDecimal userWallet(LiveUser user, BigDecimal amount, TransEnum.TransTypeEnum transTypeEnum) {
        LambdaQueryWrapper<Wallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Wallet::getUserId, user.getId());
//        Wallet wallet = walletDao.selectOne(queryWrapper);
//        Assert.isFalse(ObjectUtil.isEmpty(wallet), CodeMsg.E_50101);
//        Wallet updateWallet = new Wallet();
//        log.info("用户余额：{}", wallet.toString());
//        Assert.isFalse(UserEnum.FreezeEnum.YES.getCode().equals(wallet.getIsFreeze()), CodeMsg.E_50100);
//        BigDecimal money = null;
//        BigDecimal depositlMoney = wallet.getDepositlMoney();
//        BigDecimal notdepositlMoney = wallet.getNotdepositlMoney();
//        BigDecimal allMoney = null;
//        //查看总余额是否比金额大
//        switch (transTypeEnum) {
//            case RECHARGE:
//            case SYSTEM_PRESENTATION:
//            case REFUND_MONEY:
//                if (ObjectUtil.isNotEmpty(notdepositlMoney)) {
//                    money = notdepositlMoney.add(amount);
//                    updateWallet.setNotdepositlMoney(money);
//                } else {
//                    money = amount;
//                    updateWallet.setNotdepositlMoney(money);
//                }
//                updateWallet.setDepositlMoney(depositlMoney);
//                allMoney = wallet.getAllMoney().add(amount);
//                updateWallet.setAllMoney(allMoney);
//                break;
//            case ANCHOR_COMMISSION:
//            case RETURN_PRIZE:
//                if (ObjectUtil.isNotEmpty(depositlMoney)) {
//                    money = depositlMoney.add(amount);
//                    updateWallet.setDepositlMoney(money);
//                } else {
//                    money = amount;
//                    updateWallet.setDepositlMoney(money);
//                }
//                updateWallet.setNotdepositlMoney(notdepositlMoney);
//                allMoney = wallet.getAllMoney().add(amount);
//                updateWallet.setAllMoney(allMoney);
//                break;
//            case REWARD:
//            case BUY:
//            case SYSTEM_DEDUCTION:
//                //查看总余额是否比金额大
//                BigDecimal surplus = wallet.getAllMoney().subtract(amount);
//                Assert.isFalse(surplus.compareTo(new BigDecimal(0)) < 0, CodeMsg.E_50101);
//                notdepositlMoney = wallet.getNotdepositlMoney().subtract(amount);
//                //如果不可以提现余额 小于0 说明可提现余额小于 用户购买商品金额
//                if (notdepositlMoney.compareTo(new BigDecimal(0)) < 0) {
//                    //正数+负数为减法 不可以提现余额更新为0
//                    depositlMoney = wallet.getDepositlMoney().add(notdepositlMoney);
//                    notdepositlMoney = new BigDecimal(0);
//                } else {
//                    depositlMoney = wallet.getDepositlMoney();
//                }
//                updateWallet.setDepositlMoney(depositlMoney);
//                updateWallet.setNotdepositlMoney(notdepositlMoney);
//                allMoney = wallet.getAllMoney().subtract(amount);
//                updateWallet.setAllMoney(allMoney);
//                break;
//            case DRAWING:
//                money = depositlMoney.subtract(amount);
//                if (money.compareTo(new BigDecimal(0)) < 0) {
//                    throw new ResultJsonException(CodeMsg.E_50117);
//                }
//                updateWallet.setNotdepositlMoney(notdepositlMoney);
//                updateWallet.setDepositlMoney(money);
//                allMoney = wallet.getAllMoney().subtract(amount);
//                updateWallet.setAllMoney(allMoney);
//                break;
//            default:
//                throw new ResultJsonException(CodeMsg.E_50103);
//        }
//        updateWallet.setUserId(user.getId());
//        int num = walletDao.updateUserAmount(updateWallet);
//        Assert.isTrue(num > 0, CodeMsg.E_50104);
//        log.info("更新后余额：{}", updateWallet.toString());
//        return updateWallet.getAllMoney();
        return null;
    }


    /**
     * 更新用户钱包余额 扣款操作
     *
     * @param user          用户对象
     * @param amount        只能是正数
     * @param transTypeEnum 交易类别枚举
     * @return 交易流水号
     */
    @Override
    public String updateUserWallet(LiveUser user, BigDecimal amount, TransEnum.TransTypeEnum transTypeEnum, String orderNo, Integer channelId) {
        Assert.isFalse(amount.compareTo(new BigDecimal(0)) <= 0, CodeMsg.E_50102);
        TransEnum.IncomeExpenditureEnum incomeExpenditureEnum = TransEnum.getInOut(transTypeEnum);
        Assert.isFalse(ObjectUtil.isEmpty(incomeExpenditureEnum), CodeMsg.E_50103);
        BigDecimal allMoney = userWallet(user, amount, transTypeEnum);
        String serialNum = SerialNumUtil.getSerialNum(transTypeEnum);
        TradeTransUserRes tradeTransUser = new TradeTransUserRes();
        tradeTransUser.setTransCode(serialNum)
                .setOrderCode(orderNo)
                .setInOut(incomeExpenditureEnum.getCode())
                .setUserId(user.getId())
                .setTransType(transTypeEnum.getCode())
                .setTransStatus(TransEnum.TransStatusEnum.TRANS_SUCCESS.getCode())
                .setChannelId(channelId)
                .setTransAmount(amount)
                .setCashAmount(amount)
                .setTotalCashBalance(allMoney)
                .setCreateTime(new Date())
                .setUpdateTime(new Date())
                .setCreateBy("admin")
                .setModifyBy("admin");
        TradeTransUserLogReq tradeTransUserLog = new TradeTransUserLogReq();
        BeanUtils.copyProperties(tradeTransUser, tradeTransUserLog);
        tradeTransUserDao.insert(tradeTransUser);
        tradeTransUserLogDao.insert(tradeTransUserLog);
        log.info("增加流水记录{}", tradeTransUser.toString());
        return serialNum;
    }

    @Override
    public String updateUserWallet(TradeSerialNumReq req) {
        TransEnum.TransTypeEnum transTypeEnum = TransEnum.TransTypeEnum.valueOf(req.getTransType());
        String serialNum = "";
        try {
            serialNum = updateUserWallet(req.getUser(), req.getAmount(), transTypeEnum, req.getOrderNo(), req.getChannelId());
            switch (transTypeEnum) {
                case REWARD:
                    //分成比例
                    BigDecimal royalty = new BigDecimal(0.5);
                    BigDecimal amount = req.getAmount().multiply(royalty);
                    LiveUser user = new LiveUser();
                    user.setId(req.getAddUserId());
                    updateUserWallet(user, amount, TransEnum.TransTypeEnum.ANCHOR_COMMISSION, serialNum, req.getChannelId());
                    break;
                default:
                    break;
            }
        } catch (ResultJsonException e) {
            log.info("更新用户钱包出错{},请求参数{}", e.getMessage(), req.toString());
            throw e;
        } catch (Exception e) {
            log.info("更新用户钱包出错{}", e.getMessage());
            e.printStackTrace();
            throw new ResultJsonException(CodeMsg.E_50118);
        }
        return serialNum;
    }

}
