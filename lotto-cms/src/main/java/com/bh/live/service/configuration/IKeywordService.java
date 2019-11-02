package com.bh.live.service.configuration;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.cms.Keyword;
import com.bh.live.pojo.res.page.TableDataInfo;

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
     *
     * @return
     */
    List<Keyword> getUsableKeywords();

    /**
     * 敏感词查询分页数据
     *
     * @return
     */
    TableDataInfo getPageKeywords(String keyword,int pageSize,int pageNum);
}
