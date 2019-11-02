package com.bh.live.service.impl.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.lottery.PlayBitDao;
import com.bh.live.model.lottery.PlayBit;
import com.bh.live.service.lottery.IPlayBitService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 投注位表 服务实现类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@Service
public class PlayBitServiceImpl extends ServiceImpl<PlayBitDao, PlayBit> implements IPlayBitService {

    /**
     * 根据玩法查询投注位
     *
     * @param playNos
     * @param status
     * @return
     */
    @Override
    public List<PlayBit> selectByPlayNos(Collection<String> playNos, Integer status) {
        if (CommonUtil.isEmpty(playNos)) {
            return null;
        }
        QueryWrapper<PlayBit> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(PlayBit::getPlayNo, playNos);
        if (CommonUtil.isNotEmpty(status)) {
            wrapper.lambda().eq(PlayBit::getStatus, status);
        }
        wrapper.lambda().orderByAsc(PlayBit::getSort);
        return baseMapper.selectList(wrapper);
    }

}
