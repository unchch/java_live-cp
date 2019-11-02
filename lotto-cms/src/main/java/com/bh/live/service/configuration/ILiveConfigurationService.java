package com.bh.live.service.configuration;

import com.bh.live.model.configuration.LiveConfiguration;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 全局配置表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
public interface ILiveConfigurationService extends IService<LiveConfiguration> {

	/**
	 * 查询配置配置类型的key和value
	 * 
	 * @return
	 */
	public List<LiveConfiguration> queryConfigTypeForKeyAndValue();

	/**
	 * 根据条件查询配置列表，默认查询有效全部配置
	 * 
	 * @param config
	 * @return
	 */
	public List<LiveConfiguration> queryUsableConfigByCondition(LiveConfiguration config);

	/**
	 * 批量增加全局配置
	 * 
	 * @param configList
	 * @return
	 */
	public boolean addGlobalConfig(List<LiveConfiguration> configList);

	/**
	 * 批量修改
	 * 
	 * @param configList
	 * @return
	 */
	public boolean updateGlobalConfig(List<LiveConfiguration> configList);

	
	/**
	 * 根据条件查询配置列表，默认查询有效全部配置
	 * 
	 * @param config
	 * @return
	 */
	public LiveConfiguration queryUsableConfig(LiveConfiguration config);
}
