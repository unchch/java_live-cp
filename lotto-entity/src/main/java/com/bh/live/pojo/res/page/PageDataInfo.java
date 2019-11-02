package com.bh.live.pojo.res.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页数据对象
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Data
@NoArgsConstructor
public class PageDataInfo implements Serializable {

    private static final long serialVersionUID = -1387205221084561844L;

    /**
     * 统计
     */
    private Object total;

    /**
     * 分页数据
     */
    private TableDataInfo page;

    /**
     * 封装分页对象
     *
     * @param page
     */
    public PageDataInfo(Page page, Object total) {
        setTotal(total);
        setPage(new TableDataInfo(page));
    }
}
