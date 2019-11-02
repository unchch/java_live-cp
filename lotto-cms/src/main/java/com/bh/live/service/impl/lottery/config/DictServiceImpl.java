package com.bh.live.service.impl.lottery.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.dao.lottery.config.DictDao;
import com.bh.live.dao.lottery.config.DictTypeDao;
import com.bh.live.model.configuration.Dict;

import com.bh.live.pojo.req.lottery.config.DictReq;
import com.bh.live.pojo.res.lottery.config.DictTypeRes;
import com.bh.live.pojo.res.lottery.config.DictRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.config.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 字典信息 服务实现类
 *
 * @Author: WJ
 * @date: 2019/8/12
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictDao, Dict> implements IDictService {

    @Resource
    DictDao dictDao;

    @Resource
    DictTypeDao dictTypeDao;

    /**
     * 字典信息列表
     * @param dictReq
     * @return
     */
    @Override
    public TableDataInfo selectByParam(DictReq dictReq){
        Page<DictRes> page = new Page<>(dictReq.getPageNum(), dictReq.getPageSize());
        page.setRecords(baseMapper.selectByParam(page, dictReq));
        return new TableDataInfo(page);
    }

    /**
     * 新增
     * @param dictReqs
     * @return
     */

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveDict(List<DictReq> dictReqs){
        Assert.isNotNull(dictReqs, CodeMsg.E_90003);
        try {
            List<Dict> dicts = new ArrayList<>();
            Dict dict;
            for (DictReq dictReq : dictReqs) {
                dict = new Dict();
                BeanUtils.copyProperties(dictReq,dict);
                dict.setCreateBy(ShiroSubUtil.getLoginName());
                dict.setCreateTime(new Date());
                dicts.add(dict);
            }
            return  super.saveBatch(dicts) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            log.error("method saveDict error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
    }

    /**
     * 修改
     * @param dictReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateDict(DictReq dictReq){
        Assert.isNotNull(dictReq, CodeMsg.E_90003);
        Dict dict = new Dict();
        try {
            BeanUtils.copyProperties(dictReq,dict);
            dict.setUpdateBy(ShiroSubUtil.getLoginName());
            dict.setUpdateTime(new Date());
            return  (baseMapper.updateById(dict) > 0) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e) {
            log.error("method updateDict error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
    }

    /**
     * 通过typeCode集合List
     * @param typeCodes
     * @return
     */
    @Override
    public  List<DictTypeRes> getByTypeCodes(String typeCodes) {
        Assert.isNotNull(typeCodes, CodeMsg.E_90003);
        try {
            //获得字典类型
            List<DictTypeRes> dictTypeRes = dictTypeDao.selectByIds(typeCodes);
            //根据字典cod集合获得字典信息列表
            List<DictRes> dicts = dictDao.selectByTypeCodes(typeCodes);
            //循环组装数据
            for (DictTypeRes typeRes : dictTypeRes) {
                Integer dictTypeCode = typeRes.getDictTypeCode();
                Map<Integer, List<DictRes>> listMap = new HashedMap();
                List<DictRes> dictList = new ArrayList<>();
                for (DictRes dictRe : dicts) {
                    Integer typeCode = dictRe.getTypeCode();
                    if(dictTypeCode.equals(typeCode)){
                        dictList.add(dictRe);
                    }
                }
                listMap.put(dictTypeCode,dictList);
                typeRes.setDictResMap(listMap);
            }
            return dictTypeRes;
        } catch (Exception e) {
            log.error("method getByTypeCodes error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }

    }
}
