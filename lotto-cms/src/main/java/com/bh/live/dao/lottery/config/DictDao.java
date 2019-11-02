package com.bh.live.dao.lottery.config;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.configuration.Dict;
import com.bh.live.pojo.req.lottery.config.DictReq;
import com.bh.live.pojo.res.lottery.config.DictRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典信息表 Mapper 接口
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
public interface DictDao extends BaseMapper<Dict> {
    /**
     * 字典信息列表
     * @param page
     * @param dictReq
     * @return
     */
    List<DictRes> selectByParam (Page<DictRes> page, @Param("param") DictReq dictReq);

    List<DictRes> selectByTypeCodes(@Param("typeCodes") String typeCodes);
}
