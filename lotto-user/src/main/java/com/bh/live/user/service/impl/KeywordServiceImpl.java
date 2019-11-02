package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.cms.Keyword;
import com.bh.live.user.dao.KeywordDao;
import com.bh.live.user.service.IKeywordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 平台全局敏感词过滤表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-31
 */
@Service
public class KeywordServiceImpl extends ServiceImpl<KeywordDao, Keyword> implements IKeywordService {

    @Resource
    private KeywordDao keywordDao;

    @Override
    public List<Keyword> getUsableKeywords() {
        QueryWrapper<Keyword> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        return keywordDao.selectList(wrapper);
    }

}
