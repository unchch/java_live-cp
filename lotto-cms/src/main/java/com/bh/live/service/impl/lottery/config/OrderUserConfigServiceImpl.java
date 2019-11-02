package com.bh.live.service.impl.lottery.config;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.lottery.config.OrderUserConfigDao;
import com.bh.live.model.lottery.config.OrderUserConfig;
import com.bh.live.service.lottery.config.IOrderUserConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户类别所设置价格等级 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@Service
public class OrderUserConfigServiceImpl extends ServiceImpl<OrderUserConfigDao, OrderUserConfig> implements IOrderUserConfigService {

}
