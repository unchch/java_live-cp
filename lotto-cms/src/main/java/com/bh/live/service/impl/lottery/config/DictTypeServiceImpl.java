package com.bh.live.service.impl.lottery.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.dao.lottery.config.DictTypeDao;
import com.bh.live.model.configuration.DictType;
import com.bh.live.pojo.req.lottery.config.DictReq;
import com.bh.live.pojo.req.lottery.config.DictTypeReq;
import com.bh.live.pojo.res.lottery.config.DictTypeRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.config.IDictService;
import com.bh.live.service.lottery.config.IDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
@Service
@Slf4j
public class DictTypeServiceImpl extends ServiceImpl<DictTypeDao, DictType> implements IDictTypeService {

    @Resource
    DictTypeDao dictTypeDao;

    @Resource
    IDictService dictService;


    /**
     * 字典类型列表
     * @param dictTypeReq
     * @return
     */
    @Override
    public TableDataInfo selectByParam(DictTypeReq dictTypeReq,Integer pageNum, Integer pageSize) {
        Page<DictTypeRes> page = new Page<>(pageNum, pageSize);
        page.setRecords(baseMapper.selectByParam(page, dictTypeReq));
        return new TableDataInfo(page);
    }

    /**
     * 字典类型新增
     * @param dictTypeReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveDictTypeAndDicty(DictTypeReq dictTypeReq) {
        Assert.isNotNull(dictTypeReq.getDictTypeName(), CodeMsg.E_90003);
        try {
            dictTypeReq.setCreateBy(ShiroSubUtil.getLoginName());
            dictTypeReq.setCreateTime(new Date());
            dictTypeDao.saveDicyType(dictTypeReq);
            //获得新增的主键
            int typeCode = dictTypeReq.getDictTypeCode();
            if(typeCode > 0 && CommonUtil.isNotEmpty(dictTypeReq.getDictReqs())){
                for (DictReq dictReq : dictTypeReq.getDictReqs()) {
                    dictReq.setTypeCode(typeCode);
                }
            }
            return dictService.saveDict(dictTypeReq.getDictReqs());
        }catch (Exception e){
            log.error("method saveDictTypeAndDicty error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
    }

    /**
     * 字典类型修改
     * @param dictTypeReq
     * @return
     */
    @Override
    public boolean updateDictType(DictTypeReq dictTypeReq) {
        Assert.isNotNull(dictTypeReq, CodeMsg.E_90003);
        try {
            DictType dict = new DictType();
            BeanUtils.copyProperties(dictTypeReq,dict);
            dict.setUpdateBy(ShiroSubUtil.getLoginName());
            dict.setUpdateTime(new Date());
            return  (baseMapper.updateById(dict) > 0) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            log.error("method updateDictType error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
    }
}
