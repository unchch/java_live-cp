package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.enums.ChatMsgTypeEnum;
import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.ChatLog;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.pojo.res.live.ChatUser;
import com.bh.live.rpc.service.trade.ITradeFeignService;
import com.bh.live.user.dao.ChatRoomDao;
import com.bh.live.user.utils.live.LiveChatUtil;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.TradeUserAward;
import com.bh.live.pojo.req.trade.TradeUserAwardReq;
import com.bh.live.pojo.res.live.ChatGift;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.TradeUserAwardRes;
import com.bh.live.pojo.res.trade.UserAwardtRankingRes;
import com.bh.live.user.dao.TradeUserAwardDao;
import com.bh.live.user.service.ITradeUserAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户打赏记录 服务实现类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
@Service
public class TradeUserAwardServiceImpl extends ServiceImpl<TradeUserAwardDao, TradeUserAward> implements ITradeUserAwardService {

    @Resource
    TradeUserAwardDao tradeUserAwardDao;

    @Resource
    private ChatRoomDao chatRoomDao;

    @Autowired
    private ITradeFeignService tradeFeignService;


    @Transactional
    @Override
    public Result add(TradeUserAwardReq req, ChatUser user, LiveUser u) {
        TradeUserAward tradeUserAward = new TradeUserAward();
        CommonUtil.beanCopy(req, tradeUserAward);
        // 设置总价格
        BigDecimal amount = tradeUserAward.getGiftAmount().multiply(new BigDecimal(tradeUserAward.getGiftNum()));
        // 先修改主线包有交易流水再保存打赏信息
        TradeSerialNumReq tradeSerialNumReq = new TradeSerialNumReq();
        tradeSerialNumReq.setUser(u);
        tradeSerialNumReq.setAddUserId(req.getTvUserId());
        tradeSerialNumReq.setAmount(amount);
        tradeSerialNumReq.setTransType(TransEnum.TransTypeEnum.REWARD.getCode());
        Result trade = tradeFeignService.userTrade(tradeSerialNumReq);
        if (trade.getCode() == 200) {
            try {
                tradeUserAward.setTransCode(trade.getData().toString());
                tradeUserAward.setCreateTime(new Date());
                tradeUserAward.setAmount(amount);
                tradeUserAwardDao.insert(tradeUserAward);
                // 发送礼物指令
                ChatGift gift = tradeUserAwardDao.getGift(tradeUserAward.getGiftId());
                gift.setNumber(req.getGiftNum());
                String json = LiveChatUtil.sendMsg(req, user, gift, ChatMsgTypeEnum.GIFT.getCode());
                // 保存至聊天
                ChatLog log = new ChatLog(user.getUserId(), json, req.getToken(), new Date(), req.getRoom());
                chatRoomDao.insert(log);
                // 修改主播和用户的经验值
                // 待接入接口
                // 使用注解的方式
                return Result.success(1);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServiceRuntimeException(CodeMsg.E_90004);
            }
        }
        return trade;
    }

    @Override
    public TableDataInfo getList(Integer roomId, Integer pageNum, Integer pageSize, Date startTime, Date endTime) {
        Page<TradeUserAwardRes> page = new Page<>(pageNum, pageSize);
        page.setRecords(tradeUserAwardDao.queryTradePage(page, roomId, startTime, endTime));
        return new TableDataInfo(page);
    }

    @Override
    public List<UserAwardtRankingRes> getRankingList(Integer roomId, Date startTime, Date endTime) {
        return tradeUserAwardDao.queryRanking(roomId, startTime, endTime);
    }
}
