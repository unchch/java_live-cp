package com.bh.live.dao.user;

import com.bh.live.model.user.LiveUser;
import com.bh.live.model.user.UserStatistics;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 直播用户表 Mapper 接口
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
public interface LiveUserDao extends BaseMapper<LiveUser> {

	//强制下线
	public int addUserLeave(@Param(value = "userId") Integer userId,
			@Param(value = "liveTime") String liveTime);
	
	
	//查询用户统计信息
	public UserStatistics queryUserStatisticsById(@Param(value = "userId") Integer userId);
	
	public Double queryAllMoney();
	
}
