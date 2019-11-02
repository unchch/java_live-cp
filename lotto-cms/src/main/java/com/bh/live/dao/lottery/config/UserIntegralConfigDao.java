package com.bh.live.dao.lottery.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.configuration.UserIntegralConfig;
import com.bh.live.pojo.req.lottery.config.UserIntegralConfigReq;
import com.bh.live.pojo.res.lottery.config.UserIntegralConfigRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  用户积分配置 接口
 * </p>
 *
 * @author WJ
 * @since 2019-08-13
 */
public interface UserIntegralConfigDao extends BaseMapper<UserIntegralConfig> {
    /**
     * 积分配置规则列表
     * @param page
     * @return
     */
    List<UserIntegralConfigRes> selectByParam (Page<UserIntegralConfigRes> page);

}
