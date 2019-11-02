package com.bh.live.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.lottery.config.OrderUserConfig;
import com.bh.live.trade.dao.OrderUserConfigDao;
import com.bh.live.trade.service.IOrderUserConfigService;
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
