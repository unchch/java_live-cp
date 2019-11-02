package com.bh.live.service.lottery.config;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.configuration.UserIntegralConfig;
import com.bh.live.pojo.req.lottery.config.UserIntegralConfigReq;
import com.bh.live.pojo.res.page.TableDataInfo;

/**
 * <p>
 *  用户积分规则配置 服务类
 * </p>
 *
 * @author WJ
 * @since 2019-08-13
 */
public interface IUserIntegralConfigService extends IService<UserIntegralConfig> {

    /**
     * 用户积分规则分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo selectByParam(Integer pageNum, Integer pageSize);

    /**
     * 新增用户积分规则
     * @param configReq
     * @return
     */
    boolean saveUserConifg(UserIntegralConfigReq configReq);

    /**
     * 更新用户积分规则
     * @param configReq
     * @return
     */
    boolean updateUserConfig(UserIntegralConfigReq configReq);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteConfig(Integer id);

}
