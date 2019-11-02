package com.bh.live.service.impl.configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.dao.configuration.KeywordDao;
import com.bh.live.model.cms.Keyword;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.configuration.IKeywordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Override
    public TableDataInfo getPageKeywords(String keyword, int pageSize, int pageNum) {
        IPage<Keyword> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Keyword> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyword)) {
            wrapper.lambda().like(Keyword::getKwName, keyword);
        }
        IPage<Keyword> iPage = keywordDao.selectPage(page, wrapper);
        return new TableDataInfo(iPage);
    }

}
