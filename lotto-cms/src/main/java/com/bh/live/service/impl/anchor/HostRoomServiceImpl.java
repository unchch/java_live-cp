package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.enums.HostRoomStatusEnum;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.JudgeDateUtil;
import com.bh.live.dao.anchor.HostRoomDao;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.anchor.LiveRecord;
import com.bh.live.model.anchor.UserLeave;
import com.bh.live.model.trade.TradeUserAward;
import com.bh.live.pojo.req.anchor.HostRoomReq;
import com.bh.live.pojo.req.anchor.LottoTypeReq;
import com.bh.live.pojo.res.anchor.RoomLiveRecord;
import com.bh.live.pojo.res.inform.LottoTypeRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.IHostRoomService;
import com.bh.live.service.anchor.IHostUserService;
import com.bh.live.service.anchor.ILiveRecordService;
import com.bh.live.service.anchor.IUserLeaveService;
import com.bh.live.service.trade.ITradeUserAwardService;
import com.bh.live.service.user.ILiveUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 直播间 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
@Service
public class HostRoomServiceImpl extends ServiceImpl<HostRoomDao, HostRoom> implements IHostRoomService {

    @Autowired
    private IUserLeaveService userLeaveService;

    @Autowired
    private ITradeUserAwardService userAwardService;

    @Autowired
    private ILiveUserService liveUserService;

    @Resource
    private HostRoomDao hostRoomDao;

    @Autowired
    private ILiveRecordService liveRecordService;

    @Autowired
    private IHostUserService hostUserService;


    @Override
    public TableDataInfo getByCondition(HostRoomReq req) {
        IPage<RoomLiveRecord> page = new Page<>(req.getPageNum(), req.getPageSize());
        page.setRecords(hostRoomDao.queryRoomLiveInfo(page, req));
        //查询礼物相关数据
        List<TradeUserAward> userAwards = userAwardService.list();
        List<RoomLiveRecord> records = page.getRecords();
        List<RoomLiveRecord> resList = new ArrayList<>();
        int i = DateUtils.dayForWeek(new Date()); //今天星期几
        //计算送礼人数，礼物数以及彩币数
        if (records != null) {
            for (RoomLiveRecord record : records) { //遍历有多少直播间
                String str = " ";
                //预告数据
                if (record.getStatus().compareTo(0) == 0) {
                    String openTime = record.getOpenTime().substring(0, 5);
                    String endTime = record.getEndTime().substring(0, 5);
                    str += openTime + "-" + endTime;
                    record.setOpenTimeComplete(null); //预告数据没有开播和结束时间
                    record.setEndTimeComplete(null);
                    if (record.getLivePeriod().compareTo(0) == 0) {
                        //周期性的预告
                        //判断今天星期几并判断是否过了今天，过了返回false，不返回已经过了的直播预告
                        //时当天时，还未判断时间
                        Map<Integer, Boolean> judgeDate = JudgeDateUtil.judge(record.getYLiveTime(), record.getOpenTime());
                        for (Map.Entry<Integer, Boolean> entry : judgeDate.entrySet()) {
                            if (entry.getValue()) {
                                RoomLiveRecord rlr = new RoomLiveRecord();
                                if (entry.getKey().compareTo(i) == 0) {
                                    rlr.setYLiveTime(DateUtils.getDay(0) + str);
                                } else {
                                    rlr.setYLiveTime(DateUtils.getDay(entry.getKey() - i) + str);
                                }
                                rlr.setRoomId(record.getRoomId());
                                rlr.setSeedName(record.getSeedName());
                                rlr.setStatus(0);
                                rlr.setAdvanceId(record.getAdvanceId());
                                rlr.setUserId(record.getUserId());
                                rlr.setSeedNo(record.getSeedNo());
                                rlr.setNickname(record.getNickname());
                                rlr.setRecommHomePage(record.getRecommHomePage());
                                rlr.setSort(record.getSort());
                                resList.add(rlr);
                            }
                        }
                    } else {
                        //天的预告
                        //设置预播时间格式返回前端
                        record.setYLiveTime(record.getYLiveTime() + str);
                    }
                    //预告数据不显示开播结束时间
                    record.setOpenTime(null);
                    record.setEndTime(null);
                } else {
                    //正在直播中的数据，查询礼物数据情况
                    if (record.getStatus().compareTo(1) == 0) {
                        //查询出所有送礼数据后按照主播和直播彩种分组
                        if (userAwards != null) {
                            getAward(userAwards, record, 0);
                        }
                    }
                    //已直播完成
                    record.setOpenTimeComplete(DateUtils.parseDate(record.getOpenTime()));
                    String openTime = record.getOpenTime().split(" ")[1].substring(0, 5);
                    String endTime = "";
                    if (StringUtils.isNotEmpty(record.getEndTime())) {
                        record.setEndTimeComplete(DateUtils.parseDate(record.getEndTime()));
                        endTime = record.getEndTime().split(" ")[1].substring(0, 5);
                    }
                    record.setOpenTime(openTime);
                    record.setEndTime(endTime);
                }
            }
            records.removeIf(r -> judgeTrue(r));
            resList.addAll(records);
            sort(resList);

        }
        return new TableDataInfo(resList, page.getTotal());
    }

    private void sort(List<RoomLiveRecord> resList) {
        //自定义排序
        Comparator<RoomLiveRecord> netTypeComparator = new Comparator<RoomLiveRecord>() {
            @Override
            public int compare(RoomLiveRecord o1, RoomLiveRecord o2) {
                if (o1.getStatus() == 1 && o2.getStatus() == 0) {
                    return -1;
                } else if (o1.getStatus() == 0 && o2.getStatus() == 2) {
                    return -1;
                } else if (o1.getStatus() == 1 && o2.getStatus() == 2) {
                    return -1;
                } else if (o1.getStatus() == 0 && o2.getStatus() == 1) {
                    return 1;
                } else if (o1.getStatus() == 2 && o2.getStatus() == 1) {
                    return 1;
                } else if (o1.getStatus() == 2 && o2.getStatus() == 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(resList, netTypeComparator);
    }

    private boolean judgeTrue(RoomLiveRecord record) {
        if (record.getStatus() == 0 && record.getLivePeriod() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算礼物数据
     *
     * @param userAwards
     * @param record
     * @param type
     * @return
     */
    private RoomLiveRecord getAward(List<TradeUserAward> userAwards, RoomLiveRecord record, int type) {
        Map<TradeUserAward, List<TradeUserAward>> collect = userAwards.stream()
                .collect(Collectors.groupingBy(award -> new TradeUserAward(record.getUserId(), record.getSeedNo())));
        //分组后计算
        for (Map.Entry<TradeUserAward, List<TradeUserAward>> entry : collect.entrySet()) {
            List<TradeUserAward> value = entry.getValue();
            //按照直播时间段计算送礼数据
            List<TradeUserAward> awards = value.stream()
                    .filter(userAward -> DateUtils.isBetweenTime(DateUtils.parseDate(record.getOpenTime()),
                            type == 0 ? DateUtils.addDays(userAward.getCreateTime(), 1) : DateUtils.parseDate(record.getEndTime()),
                            userAward.getCreateTime()))
                    .collect(Collectors.toList());

            if (record.getUserId().compareTo(entry.getKey().getTvUserId()) == 0
                    && record.getSeedNo().compareTo(entry.getKey().getLottoId()) == 0) {
                Integer gifts = awards.stream().mapToInt(TradeUserAward::getGiftNum).sum(); //礼物数
                BigDecimal amount = awards.stream().map(TradeUserAward::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add); //总彩币

                record.setGiveCount(awards.stream().distinct().collect(Collectors.toList()).size()); //送礼人数
                record.setGiftNum(gifts);
                record.setAmount(amount);
            }
        }
        return record;
    }


    @Override
    public boolean closeOrOpenRoom(Integer id, Integer status) {
        //强行关闭直播间，在直播间的用户全部都会踢出去
        HostRoom room = new HostRoom();
        room.setId(id);
        room.setStatus(status);
        return this.updateById(room);
    }

    @Override
    public void kickOffLine(Integer id, Integer hostId, Integer kickOffLine) {
        HostRoom room = new HostRoom();
        room.setId(id);
        room.setHostId(hostId);
        room.setKickOffLine(kickOffLine);
        room.setIsUsable(2);
        this.updateById(room);

        //强制离线记录
        UserLeave userLeave = new UserLeave();
        userLeave.setCreateBy("当前登录用户");
        userLeave.setCreateTime(new Date());
        userLeave.setUserId(hostId);
        userLeave.setLiveTime(10 * 6 * 1000); //限制10分钟后才能再次登录
        userLeaveService.save(userLeave);
        //强制退出登录
        liveUserService.forceToExit(hostId);

    }

    @Override
    public void update(HostRoomReq req) {
        HostRoom hostRoom = new HostRoom();
        CommonUtil.beanCopy(req, hostRoom);
        hostRoom.setId(req.getRoomId());
        this.updateById(hostRoom);
    }

    @Override
    public Result queryHostRoomList(LottoTypeReq lottoTypeReq) {
        List<LottoTypeRes> list = hostRoomDao.queryHostRoomList(lottoTypeReq);
        return Result.success(list);
    }

    @Override
    public boolean checkIdExist(Integer roomId) {
        int exist = hostRoomDao.checkRoomIdExist(roomId);
        if (exist > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void openRoomLive(HostRoomReq req) {
        //修改直播间信息
        HostRoom room = new HostRoom();
        room.setId(req.getRoomId());
        room.setStatus(HostRoomStatusEnum.LIVING.getCode());
        room.setOpenTime(new Date());
        room.setEndTime(null);
        room.setYLiveTime(req.getYLiveTime());
        room.setIsUsable(1);
        room.setLottoId(req.getSeedNo());
        room.setLottoName(req.getSeedName());
        room.setUpdateTime(new Date());
        updateById(room);

        //修改主播状态
        HostUser hostUser = new HostUser();
        hostUser.setUserId(req.getUserId());
        hostUser.setStatus(HostRoomStatusEnum.LIVING.getCode());
        UpdateWrapper<HostUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", hostUser.getUserId());
        hostUserService.update(hostUser, wrapper);

        LiveRecord liveRecord = new LiveRecord();
        liveRecord.setUserId(req.getUserId());
        liveRecord.setAdvanceId(req.getAdvanceId());
        liveRecord.setRoomId(req.getRoomId());
        liveRecord.setSeedNo(req.getSeedNo());
        liveRecord.setStatus(HostRoomStatusEnum.LIVING.getCode());
        liveRecord.setOpenTime(new Date());
        liveRecord.setYLiveTime(req.getYLiveTime());
        liveRecordService.save(liveRecord); //保存直播记录
    }

    @Override
    public void endRoomLive(HostRoomReq req) {
        //修改直播间信息
        HostRoom room = new HostRoom();
        room.setId(req.getRoomId());
        room.setStatus(HostRoomStatusEnum.FINISHED.getCode());
        room.setIsUsable(0);
        room.setEndTime(new Date());
        room.setUpdateTime(new Date());
        room.setLottoId(req.getSeedNo());
        room.setLottoName(req.getSeedName());
        updateById(room);

        //修改主播状态
        HostUser hostUser = new HostUser();
        hostUser.setUserId(req.getUserId());
        hostUser.setStatus(2);
        UpdateWrapper<HostUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", hostUser.getUserId());
        hostUserService.update(hostUser, wrapper);

        long min = DateUtils.getDatePoor(new Date(), DateUtils.parseDate(req.getStartTime()), "min");
        RoomLiveRecord record = new RoomLiveRecord();
        record.setUserId(req.getUserId());
        record.setSeedNo(req.getSeedNo());
        record.setOpenTime(req.getStartTime());
        record.setEndTime(DateUtils.formatDateTime(new Date()));
        record = getAward(userAwardService.list(), record, 1);
        LiveRecord liveRecord = new LiveRecord();
        liveRecord.setId(req.getId());
        //liveRecord.setAdvanceId(req.getAdvanceId());
        liveRecord.setStatus(HostRoomStatusEnum.FINISHED.getCode());
        liveRecord.setEndTime(new Date());
        liveRecord.setTime(min + "");
        liveRecord.setAmount(record.getAmount());
        liveRecord.setGiftNum(record.getGiftNum());
        liveRecord.setRoomNum(record.getRoomNum());
        liveRecord.setGiveCount(record.getGiveCount());
        liveRecordService.updateById(liveRecord); //保存直播记录
    }
}
