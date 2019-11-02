package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.BarrageConf;

/**
 * <p>
 * 房间弹幕功能配置表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface IBarrageConfService extends IService<BarrageConf> {

    BarrageConf getBarrageConfByRoomId(Integer roomId);
}
