package com.bh.live.service.impl.lottery.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.dao.lottery.config.UserIntegralConfigDao;
import com.bh.live.model.configuration.UserIntegralConfig;
import com.bh.live.pojo.req.lottery.config.UserIntegralConfigReq;
import com.bh.live.pojo.res.lottery.config.UserIntegralConfigRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.config.IUserIntegralConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WJ
 * @since 2019-08-13
 */
@Service
@Slf4j
public class UserIntegralConfigServiceImpl extends ServiceImpl<UserIntegralConfigDao, UserIntegralConfig> implements IUserIntegralConfigService {

    @Resource
    UserIntegralConfigDao configDao;

    @Override
    public TableDataInfo selectByParam(Integer pageNum, Integer pageSize) {
        Page<UserIntegralConfigRes> page = new Page<>(pageNum, pageSize);
        return new TableDataInfo( page.setRecords(configDao.selectByParam(page)));
    }

    @Override
    public boolean saveUserConifg(UserIntegralConfigReq configReq) {
        Assert.isNotNull(configReq, CodeMsg.E_90003);
        Assert.isNotNull(configReq.getValue(), CodeMsg.E_90003);
        Assert.isNotNull(configReq.getValueType(), CodeMsg.E_90003);
        Assert.isNotNull(configReq.getPeriodType(), CodeMsg.E_90003);
        Assert.isNotNull(configReq.getAddExp(), CodeMsg.E_90003);
        try {
            UserIntegralConfig userIntegralConfig = new UserIntegralConfig();
            BeanUtils.copyProperties(configReq,userIntegralConfig);
            userIntegralConfig.setCreatBy("admin");
            userIntegralConfig.setCreateTime(new Date());
            return  baseMapper.insert(userIntegralConfig) > 0 ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            log.error("save UserIntegralConfigReq error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
    }

    @Override
    public boolean updateUserConfig(UserIntegralConfigReq configReq) {
        Assert.isNotNull(configReq, CodeMsg.E_90003);
        Assert.isNotNull(configReq.getId(), CodeMsg.E_90003);
        Assert.isNotNull(configReq.getDictCode(), CodeMsg.E_90003);
        try {
        UserIntegralConfig userIntegralConfig = new UserIntegralConfig();
        BeanUtils.copyProperties(configReq,userIntegralConfig);
        userIntegralConfig.setUpdateBy(ShiroSubUtil.getLoginName());
        userIntegralConfig.setUpdateTime(new Date());
        return  baseMapper.updateById(userIntegralConfig) > 0 ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            log.error("update UserIntegralConfigReq error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
    }

    @Override
    public boolean deleteConfig(Integer id) {
        Assert.isNotNull(id, CodeMsg.E_90003);
        try {
            return baseMapper.deleteById(id) > 0 ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            log.error("delete UserIntegralConfigReq error:" + e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }

    }
}
