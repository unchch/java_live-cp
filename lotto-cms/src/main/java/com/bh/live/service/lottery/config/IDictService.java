package com.bh.live.service.lottery.config;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.configuration.Dict;
import com.bh.live.pojo.req.lottery.config.DictReq;
import com.bh.live.pojo.res.lottery.config.DictTypeRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 字典信息表 服务类
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
public interface IDictService extends IService<Dict> {

    /**
     * 字典信息列表
     * @param dictReq
     * @return
     */
    TableDataInfo selectByParam(DictReq dictReq);

    /**
     * 新增
     * @param dictReqs
     * @return
     */
    boolean saveDict(List<DictReq> dictReqs);

    /**
     * 修改
     * @param dictReq
     * @return
     */
    boolean updateDict(DictReq dictReq);

    /**
     * 通过codes获得List集合
     * @param typeCodes
     * @return
     */
    List<DictTypeRes> getByTypeCodes(String typeCodes);


}
