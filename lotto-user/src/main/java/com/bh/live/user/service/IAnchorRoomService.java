package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.pojo.res.anchor.*;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.res.lottery.HistoryLotteryRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 直播间管理 服务类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-29
 */
public interface IAnchorRoomService extends IService<AnchorRoomInfoRes> {

    /**
     * 根据彩种获取历史开奖
     *
     * @param seedNo
     * @return
     * @throws ServiceRuntimeException
     */
    List<HistoryLotteryRes> getLottoSeed(Integer seedNo) throws ServiceRuntimeException;

    /**
     * 同彩种主播推荐
     *
     * @param seedNo
     * @return
     * @throws ServiceRuntimeException
     */
    List<AnchorRoomInfoRes> getAnchorRecommend(Integer userId, Integer seedNo) throws ServiceRuntimeException;

    /**
     * 我关注的主播
     *
     * @param seedNo
     * @return
     * @throws ServiceRuntimeException
     */
    List<AnchorRoomInfoRes> getAttentionAnchor(Integer userId, Integer seedNo) throws ServiceRuntimeException;

    /**
     * 获取礼物列表
     *
     * @return
     * @throws ServiceRuntimeException
     */
    List<GiftInfoRes> getGiftList() throws ServiceRuntimeException;

    /**
     * 修改主播房间公告
     *
     * @param notice
     * @param roomId
     */
    Result updateRoomNotice(String notice, Integer roomId);

    /**
     * 修改昵称
     *
     * @param nickname
     * @param hostId
     */
    void updateUserNickname(String nickname, Integer hostId);

    /**
     * 根据房间号和彩种获取主播信息
     *
     * @param roomId
     * @return
     */
    AnchorRes getHostUserInfo(Integer roomId, Integer userId, Integer seedNo);

    /**
     * 获取4条推荐直播
     *
     * @return
     */
    List<LiveAdvanceRes> getLiveRecommend();

    /**
     * 根据房间号获取彩种id
     *
     * @param roomId
     * @return
     */
    Integer getSeedByRoom(Integer roomId) throws ServiceRuntimeException;
}
