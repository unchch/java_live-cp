package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.PlayItemDao;
import com.bh.live.award.service.IPlayItemService;
import com.bh.live.model.lottery.PlayItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 投注项表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class PlayItemServiceImpl extends ServiceImpl<PlayItemDao, PlayItem> implements IPlayItemService {

    @Override
    public List<PlayItem> selectByBitNo(List<String> bitNos) {
        QueryWrapper<PlayItem> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().in(PlayItem::getBitNo,bitNos);
        return list(queryWrapper);
    }
}
