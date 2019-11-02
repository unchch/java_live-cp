package com.bh.live.service.impl.configuration;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.configuration.LiveConfigurationDao;
import com.bh.live.model.configuration.LiveConfiguration;
import com.bh.live.service.configuration.ILiveConfigurationService;

/**
 * <p>
 * 全局配置表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
@Service
public class LiveConfigurationServiceImpl extends ServiceImpl<LiveConfigurationDao, LiveConfiguration>
		implements ILiveConfigurationService {

	@Resource
	private LiveConfigurationDao liveConfigurationDao;

	@Override
	public List<LiveConfiguration> queryConfigTypeForKeyAndValue() {

		return liveConfigurationDao.queryConfigTypeForKeyAndValue();
	}

	@Override
	public List<LiveConfiguration> queryUsableConfigByCondition(LiveConfiguration config) {
		
		return super.list(new QueryWrapper().setEntity(config));
	}

	@Override
	public boolean addGlobalConfig(List<LiveConfiguration> configList) {

		return super.saveBatch(configList);
	}

	@Override
	public boolean updateGlobalConfig(List<LiveConfiguration> configList) {

		return super.updateBatchById(configList);
	}

	@Override
	public LiveConfiguration queryUsableConfig(LiveConfiguration config) {

		return super.getOne(new QueryWrapper<LiveConfiguration>().setEntity(config));
	}

}
