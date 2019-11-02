package com.bh.live.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.cms.UserStatisticsCMS;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.user.LiveUserFullReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.LiveUserFullExcelRes;
import com.bh.live.pojo.res.user.UserStatisticsRes;

import java.util.List;

/**
 * <p>
 * 直播用户表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
public interface ILiveUserService extends IService<LiveUser> {

	/**
	 * 获取用户列表
	 * 
	 * @param liveUser
	 * @return
	 */
	TableDataInfo queryLiveUserPage(LiveUserFullReq liveUser);

	/**
	 * 更新用户信息
	 * 
	 * @param liveUser
	 * @return
	 */
	boolean updateLiveUserInfo(LiveUserFullReq liveUser);

	/**
	 * 用户的解除或禁止行为操作
	 * 
	 * @param userId
	 * @param prohibitType 0:禁止登录 1:禁止发布竞猜方案 2:禁止聊天室发言
	 * @return
	 */
	boolean isProhibit(Integer userId, String prohibitType);

	/**
	 * 强制用户退出登录
	 * 
	 * @param userId
	 * @return
	 */
	boolean forceToExit(Integer userId);

	/**
	 * 解除或禁用用户
	 * 
	 * @param userId
	 * @return
	 */
	boolean isProhibitLiveUser(Integer userId);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	boolean resetUserpassword(Integer userId);

	/**
	 * 根据userId查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	UserStatisticsRes queryLiveUserAllInfoByID(Integer userId);
	/**
	 *@description 刷新前台用户token
	 *@author WuLong
	 *@date 2019/8/2 11:48
	 *@param liveUser
	 *@return Result
	 */
	Result refreshHeadToken(LiveUser liveUser);

	/**
	 * 查询用户统计信息
	 * @return
	 */
	UserStatisticsCMS queryUserStatistics();

	/**
	 * 导出Excel用户列表
	 *
	 * @return
	 */
	List<LiveUserFullExcelRes> queryUserTransExcel(LiveUserFullReq userFullReq);
	
}
