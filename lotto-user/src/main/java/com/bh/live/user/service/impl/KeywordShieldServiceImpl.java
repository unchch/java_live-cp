package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.KeywordShield;
import com.bh.live.user.dao.KeywordShieldDao;
import com.bh.live.user.service.IKeywordShieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 房间发言关键词过滤表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Service
public class KeywordShieldServiceImpl extends ServiceImpl<KeywordShieldDao, KeywordShield> implements IKeywordShieldService {

    @Resource
    private KeywordShieldDao keywordShieldDao;

    @Override
    public List<KeywordShield> shield(Integer roomId) {
        QueryWrapper<KeywordShield> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(KeywordShield::getRoomId, roomId);
        return keywordShieldDao.selectList(wrapper);
    }
}
