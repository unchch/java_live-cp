package com.bh.live.service.lottery.config;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.lottery.config.vo.OrderConfigVO;
import com.bh.live.pojo.res.page.TableDataInfo;


/**
 * <p>
 * 彩种订单配置表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
public interface IOrderConfigService extends IService<OrderConfig> {

    TableDataInfo selectOrderConfig(OrderConfigVO vo);
}
