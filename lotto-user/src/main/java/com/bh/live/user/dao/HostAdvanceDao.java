package com.bh.live.user.dao;

import java.util.List;

import com.bh.live.pojo.res.anchor.HostUserLiveSeedAdvanceRes;
import com.bh.live.pojo.res.user.LiveHostStateRes;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;
import com.bh.live.pojo.res.anchor.RecomHostRoomRes;
import com.bh.live.pojo.res.rankingList.SearchLiveUserRes;

/**
 * <p>
 * 主播预告表 Mapper 接口
 * </p>
 *
 * @author WW
 * @since 2019-07-29
 */
public interface HostAdvanceDao extends BaseMapper<HostAdvance> {

	/**
	 * 根据主播id查询主播信息
	 * @param hostId
	 * @return
	 */
	public HostUser queryHostUserById(@Param(value = "hostId") Integer hostId);
	
	/**
	 * 根据主播id查询房间信息
	 * @param hostId
	 * @return
	 */
	public HostRoom queryHostRoomById(@Param(value = "hostId") Integer hostId);

	/**
	 * 查询主播推荐
	 * @param size
	 * @return
	 */
	public List<HostAdvanceRes> queryHostUserRecommend(@Param(value = "curUserId") Integer curUserId,@Param(value = "size") Integer size);

	/**
	 * 根据用户id查询信息
	 *
	 * @param userId
	 * @return
	 */
	public LiveUser queryLiveUserById(@Param(value = "userId") Integer userId);


	/**
	 * 获取彩种的所有主播预告
	 *
	 * @auth Morphon
	 * @param seedNo
	 * @return
	 */
	List<HostAdvanceRes> queryHostAdvances(@Param("seedNo") Integer seedNo);

	/**
	 * 查询热门直播间
	 *
	 * @param size
	 * @return
	 */
	public List<RecomHostRoomRes> queryHostRoomListBySize(@Param(value = "curUserId") Integer curUserId,@Param(value = "size") Integer size);

	/**
	 * 获取主播预告
	 * @auth Morphon
	 * @param userId
	 * @return
	 */
	List<HostUserLiveSeedAdvanceRes> queryHostAdvancesByUserId(@Param("userId") Integer userId);

	/**
	 * 首页搜索用户信息
	 * 
	 * @return
	 */
	public List<SearchLiveUserRes> querySearchLiveUserInfo(@Param("userId") Integer userId , @Param("content") String content,
														   @Param("userType") Integer userType);


	/**
	 * 查询预告
	 * @param liveDate
	 * @param startTime
	 * @return
	 */
	public List<HostAdvanceRes> queryHostAdvanceResList(@Param("liveDate") Integer liveDate, @Param("startTime") String startTime,
														@Param("endTime") String endTime, @Param("seedNo") Integer seedNo,
														@Param("hostId") Integer hostId);

	/**
	 * 根据用户id查询预告
	 * @param hostId
	 * @return
	 */
	public List<HostAdvanceRes> queryAdvanceByHostId(@Param("hostId") Integer hostId);


	/**
	 * 查询主播状态
	 * @param hostId
	 * @return
	 */
	public LiveHostStateRes queryLiveHostStateById(@Param("hostId") Integer hostId);
}
