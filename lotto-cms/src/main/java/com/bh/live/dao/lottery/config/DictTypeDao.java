package com.bh.live.dao.lottery.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.configuration.DictType;
import com.bh.live.pojo.req.lottery.config.DictTypeReq;
import com.bh.live.pojo.res.lottery.config.DictTypeRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 字典类型表 接口
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
public interface DictTypeDao extends BaseMapper<DictType> {
    /**
     * 字典类型列表
     * @param page
     * @param dictTypeReq
     * @return
     */
    List<DictTypeRes> selectByParam (Page<DictTypeRes> page, @Param("param") DictTypeReq dictTypeReq);

    /**
     * 字典类型新增
     * @param dictTypeReq
     * @return
     */
    int saveDicyType(@Param("param") DictTypeReq  dictTypeReq);


    /**
     * 字典类型查询
     * @param codes
     * @return
     */
    @Select("select dict_type_code,dict_type_name,create_time from lotto_dict_type where dict_type_code in (${param}) ")
    List<DictTypeRes> selectByIds (@Param("param") String codes);
}
