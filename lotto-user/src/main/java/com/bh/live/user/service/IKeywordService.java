package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.cms.Keyword;

import java.util.List;

/**
 * <p>
 * 平台全局敏感词过滤表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-31
 */
public interface IKeywordService extends IService<Keyword> {

    /**
     * 查询所有启用的关键词
     * @return
     */
    List<Keyword> getUsableKeywords();
}
