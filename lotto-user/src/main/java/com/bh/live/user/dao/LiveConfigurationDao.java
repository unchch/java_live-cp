package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.configuration.LiveConfiguration;

import java.util.List;

/**
 * <p>
 * 全局配置表 Mapper 接口
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
public interface LiveConfigurationDao extends BaseMapper<LiveConfiguration> {

	/**
	 * 查询可用配置类型的key和value
	 * 
	 * @return
	 */
	public List<LiveConfiguration> queryConfigTypeForKeyAndValue();

}
