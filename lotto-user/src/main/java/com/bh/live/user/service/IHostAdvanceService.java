package com.bh.live.user.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.pojo.req.anchor.HostAdvanceReq;
import com.bh.live.pojo.req.anchor.SaveHostAdvance;
import com.bh.live.pojo.res.anchor.*;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.rankingList.SearchLiveUserRes;
import com.bh.live.pojo.res.user.LiveHostStateRes;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 主播预告表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-07-29
 */
public interface IHostAdvanceService extends IService<HostAdvance> {

	/**
	 * 添加预告
	 * @param hostId
	 * @param saveHostAdvance
	 * @return
	 */
	public boolean addHostAdvance(Integer hostId,SaveHostAdvance saveHostAdvance);

	/**
	 * 更新预告
	 * 
	 * @param hostAdvance
	 * @return
	 */
	public boolean updateHostAdvance(HostAdvanceReq hostAdvance);

	/**
	 * 查询预告列表
	 * 
	 * @param hostId
	 * @return
	 */
	public List<HostAdvanceRes> queryHostAdvance(Integer hostId, Integer seedNo, Integer size);

	/**
	 * 根据主播id查询预告
	 * @param hostId
	 * @return
	 */
	List<HostUserLiveSeedAdvanceRes> queryHostAdvance(Integer hostId);

	/**
	 * 根据前端要求，更改出参格式为 map key：today 今天 key：tomorrow 明天
	 * 
	 * @param hostId
	 * @return
	 */
	public List<List<HostAdvanceRes>> queryClassifyHostAdvance(Integer hostId, Integer seedNo);

	List<AdvanceRes> queryAllHostAdvance();

	/**
	 * 查询推荐主播
	 * 
	 * @param userId
	 * @param size
	 * @return
	 */
	public List<HostAdvanceRes> queryHostUserListBySize(Integer userId,Integer size);

	/**
	 * 查询热门直播间
	 *
	 * @param size
	 * @return
	 */
	public List<RecomHostRoomRes> queryHostRoomListBySize(Integer userId,Integer size);
	
	/**
	 * 首页搜索列表
	 * @param userType
	 * @return
	 */
	public TableDataInfo homeSearch(Integer userId,String content, Integer userType, Integer pageNum, Integer pageSize);

	/**
	 * 根据用户id查询预告
	 * @param hostId
	 * @return
	 */
	public List<HostAdvanceRes> queryAdvanceByHostId(Integer hostId);

	/**
	 * 查询主播状态
	 * @param hostId
	 * @return
	 */
	public LiveHostStateRes queryLiveHostStateById(Integer hostId);

}
