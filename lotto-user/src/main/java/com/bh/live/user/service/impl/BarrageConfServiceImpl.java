package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.BarrageConf;
import com.bh.live.user.dao.BarrageConfDao;
import com.bh.live.user.service.IBarrageConfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 房间弹幕功能配置表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Service
public class BarrageConfServiceImpl extends ServiceImpl<BarrageConfDao, BarrageConf> implements IBarrageConfService {

    @Resource
    private BarrageConfDao barrageConfDao;

    @Override
    public BarrageConf getBarrageConfByRoomId(Integer roomId) {
        QueryWrapper<BarrageConf> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id", roomId);
        return barrageConfDao.selectOne(wrapper);
    }
}
