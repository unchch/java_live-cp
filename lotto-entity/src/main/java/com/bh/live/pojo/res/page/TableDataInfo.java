package com.bh.live.pojo.res.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Data
@NoArgsConstructor
public class TableDataInfo<T> implements Serializable {

    private static final long serialVersionUID = -6358925325236308778L;

    /**
     * 总记录数
     */
    private long totalNumber;

    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 封装分页对象
     *
     * @param page
     */
    public TableDataInfo(IPage page) {
        this.setList(page.getRecords());
        this.setTotalNumber(page.getTotal());
    }

    public TableDataInfo(List<T> list, long totalNumber) {
        this.list = list;
        this.totalNumber = totalNumber;
    }
}
