package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.model.trade.Order;
import com.bh.live.pojo.res.anchor.*;
import com.bh.live.pojo.res.lottery.HistoryLotteryRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 直播间主播信息 Mapper 接口
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-29
 */
public interface AnchorRoomDao extends BaseMapper<AnchorRoomInfoRes> {
    /**
     * 同彩种主播推荐
     * @param seedNo
     * @return
     */
    List<AnchorRoomInfoRes> getAnchorRecommend(@Param("userId") Integer userId,@Param("seedNo") Integer seedNo);

    /**
     * 我关注的主播
     * @param seedNo
     * @return
     */
    List<AnchorRoomInfoRes> getAttentionAnchor( @Param("userId") Integer userId, @Param("seedNo") Integer seedNo);

    /**
     * 根据彩种获取历史开奖
     * @param seedNo
     * @return
     */
    List<HistoryLotteryRes> getLottoSeed(@Param("seedNo") Integer seedNo);

    /**
     * 获取礼物列表
     * @return
     * @throws ServiceRuntimeException
     */
    List<GiftInfoRes> getGiftList();

    /**
     * 根据房间号获取主播信息
     * @param roomId
     * @return
     */
    AnchorRes getHostUserInfo(@Param("roomId") Integer roomId,@Param("userId")Integer userId,@Param("seedNo") Integer seedNo);

    /**
     * @author Morphon
     * @param notice
     * @param roomId
     * @return
     * 修改房间公告
     */
    @Update("update lotto_host_room set notice = #{notice} where id = #{roomId}")
    int updateNoticeById(@Param("notice") String notice, @Param("roomId") Integer roomId);

    /**
     * @author Morphon
     * @param nickname
     * @param hostId
     * @return
     * 修改昵称
     */
    @Update("update lotto_host_room set nickname = #{nickname} where host_id = #{hostId}")
    int updateNicknameByUser(@Param("nickname") String nickname, @Param("hostId") Integer hostId);

    /**
     * 获取直播的数据
     * @param roomId
     * @return
     */
    String getLiving(@Param("roomId")  Integer roomId);

    /**
     * 获取正在直播的战绩
     * @param hostId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Order> getResult(@Param("hostId")Integer hostId,@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 获取下一场预告
     * @param hostId
     * @return
     */
    List<HostAdvanceRes> getAdvance(@Param("hostId")  Integer hostId,@Param("seedNo") Integer seedNo);

    /**
     * 获取4条直播推荐
     * @return
     */
    List<LiveAdvanceRes> getLiveRecommend();

    /**
     * 根据房间获取id
     * @param roomId
     * @return
     */
    Integer getSeedByRoom(@Param("roomId")Integer roomId);

}
