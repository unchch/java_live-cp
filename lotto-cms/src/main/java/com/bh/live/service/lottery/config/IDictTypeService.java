package com.bh.live.service.lottery.config;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.configuration.DictType;
import com.bh.live.pojo.req.lottery.config.DictTypeReq;
import com.bh.live.pojo.res.page.TableDataInfo;



/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
public interface IDictTypeService extends IService<DictType> {
    /**
     * 字典类型列表
     * @param dictReq
     * @return
     */
    TableDataInfo selectByParam(DictTypeReq dictReq, Integer pageNum,Integer pageSize);

    /**
     * 新增
     * @param dictTypeReq
     * @return
     */
    boolean saveDictTypeAndDicty(DictTypeReq dictTypeReq);

    /**
     * 修改
     * @param dictTypeReq
     * @return
     */
    boolean updateDictType(DictTypeReq dictTypeReq);
}
