package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.AdvanceUtil;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.pojo.res.anchor.*;
import com.bh.live.pojo.res.lottery.HistoryLotteryRes;
import com.bh.live.user.dao.AnchorRoomDao;
import com.bh.live.user.service.IAnchorRoomService;
import com.bh.live.user.utils.live.PropChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/29 14:53
 */
@Service
public class AnchorRoomServiceImpl extends ServiceImpl<AnchorRoomDao, AnchorRoomInfoRes> implements IAnchorRoomService {

    @Resource
    private AnchorRoomDao anchorRoomDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<HistoryLotteryRes> getLottoSeed(Integer seedNo) throws ServiceRuntimeException {
        return anchorRoomDao.getLottoSeed(seedNo);
    }

    @Override
    public List<AnchorRoomInfoRes> getAnchorRecommend(Integer userId,Integer seedNo) throws ServiceRuntimeException {
        return anchorRoomDao.getAnchorRecommend(userId,seedNo);
    }

    @Override
    public List<AnchorRoomInfoRes> getAttentionAnchor(Integer userId, Integer seedNo) throws ServiceRuntimeException {
        return anchorRoomDao.getAttentionAnchor(userId, seedNo);
    }

    @Override
    public List<GiftInfoRes> getGiftList() throws ServiceRuntimeException {
        return anchorRoomDao.getGiftList();
    }

    @Override
    public AnchorRes getHostUserInfo(Integer roomId, Integer userId, Integer seedNo) {
        AnchorRes info = anchorRoomDao.getHostUserInfo(roomId, userId, seedNo);
        info.setSecretKey(PropChatUtil.rtmp+info.getSecretKey());
        // 获取开播时间
        String livingDate = anchorRoomDao.getLiving(roomId);
        if (livingDate!=null){
            info.setLiveTime(livingDate);
            String[] s = livingDate.split(" ");
            String[] sd = s[1].split("-");
            try {
                Date st= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(s[0]+" "+sd[0]);
                Date et= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(s[0]+" "+sd[1]);
                info.setStartTime(st);
                info.setEndTime(et);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            // 获取下一场预告
            List<HostAdvanceRes> list = anchorRoomDao.getAdvance(info.getUserId(),seedNo);
            List<AdvancesRes> advancesRes = AdvanceUtil.advanceHandler(list);
            if (advancesRes.size()!=0){
                AdvancesRes res = advancesRes.get(0);
                info.setStartTime(res.getStartTime());
                info.setEndTime(res.getEndTime());
            }
        }
        return info;
    }

/*
    @Override
    public AnchorRes getAdvance(Integer hostId, Integer seedNo) {
        // 先获取正在直播的数据
        AnchorRes ha =anchorRoomDao.getLiving(hostId, seedNo);
        AnchorRes anchor = new AnchorRes();
        // 没有在直播
        if (ha == null) {
            //获取下一场直播预告
            List<AdvancesRes> advancesRes = AdvanceUtil.advanceHandler(anchorRoomDao.getAdvance(hostId, seedNo));
            AdvancesRes res = advancesRes.get(0);
          *//*  anchor.setStartTime(res.getStartTime());
            anchor.setEndTime(res.getEndTime());*//*
        } else {
            // 根据时间段获取本场战绩
            // ======================暂时用不上 开始===============================
            List<Order> orderList = anchorRoomDao.getResult(hostId, ha.getStartTime(), ha.getEndTime());
            BigDecimal profitAmount = new BigDecimal(0);
            BigDecimal profitRate = new BigDecimal(0);
            StringBuffer awardState = new StringBuffer();
            Integer win = 0;
            Integer lose = 0;
            Integer total = 0;
            for (Order item : orderList) {
                total++;
                profitAmount = profitAmount.add(item.getProfitAmount());
                profitRate = profitRate.add(item.getProfitRate());
                OrderEnum.AwardStateEnum stateEnum = OrderEnum.AwardStateEnum.get(item.getAwardState());
                if (item.getAwardState() == OrderEnum.AwardStateEnum.WIN.getCode()) {
                    win++;
                } else if (item.getAwardState() == OrderEnum.AwardStateEnum.LOST.getCode() || item.getAwardState() == OrderEnum.AwardStateEnum.DRAW.getCode()) {
                    lose++;
                }
                awardState.append(stateEnum.getDesc() + " ");
            }
            anchor.setStartTime(ha.getStartTime());
            anchor.setEndTime(ha.getEndTime());
            anchor.setTotal(total);
            anchor.setWin(win);
            anchor.setLose(lose);
            anchor.setAwardState(awardState.toString());
            if (win == 0) {
                anchor.setWinRate(new BigDecimal(0));
            } else {
                // 胜率等于 (赢/总次数)*100
                anchor.setWinRate(new BigDecimal(win / total).setScale(2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)));
            }
            anchor.setProfitAmount(profitAmount);
            anchor.setProfitRate(profitRate);
            // ======================暂时用不上 结束===============================
        }
        return anchor;
    }*/

    @Override
    public List<LiveAdvanceRes> getLiveRecommend() {
        return anchorRoomDao.getLiveRecommend();
    }

    @Override
    public Integer getSeedByRoom(Integer roomId) {
        return anchorRoomDao.getSeedByRoom(roomId);
    }

    @Override
    public Result updateRoomNotice(String notice, Integer roomId) {
        //房间公告10分钟内不能再次修改
        //查询redis十分钟内有没有该用户修改过的简介，如果有，不予修改
        String noticeStr = redisUtil.getByFastJson(String.format(UserRedisKey.HOST_ROOM_NOTICE_KEY, roomId), String.class);
        if (StringUtils.isNotEmpty(noticeStr)) {
            throw new RuntimeException("10分钟内不能再次修改房间公告喔，亲！");
        }
        //否则，存在redis，时效10分钟(当前测试5秒)
        redisUtil.setByFastJson(String.format(UserRedisKey.HOST_ROOM_NOTICE_KEY, roomId), notice, 10, TimeUnit.MINUTES);

        int success = anchorRoomDao.updateNoticeById(notice, roomId);
        if (success > 0) {
            return Result.success();
        }
        return Result.error(CodeMsg.E_500);
    }

    @Override
    public void updateUserNickname(String nickname, Integer hostId) {
        anchorRoomDao.updateNicknameByUser(nickname, hostId);
    }

}
