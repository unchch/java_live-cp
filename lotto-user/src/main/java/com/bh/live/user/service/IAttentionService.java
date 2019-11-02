package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.Attention;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.anchor.FansListRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 关注表 服务类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-25
 */
public interface IAttentionService extends IService<Attention> {

    /**
     *@description 添加or取消关注
     *@author wuhuanrong
     *@date 2019/7/25 15:02
     *@param  attention
     *@return Result
     *@exception
     */
    Integer insertOrUpdate(Attention attention) throws ServiceRuntimeException;

    /**
     *@description 关注的用户列表
     *@author wuhuanrong
     *@date 2019/7/25 15:02
     *@return Result
     *@exception
     */
    TableDataInfo queryAttentList(Integer type,Integer userId,Integer pageNum,Integer pageSize) throws ServiceRuntimeException;

    /**
     * 根据条件获取关注列表
     * @param userId
     * @return
     */
    List<FansListRes> getAttentionByUserId(Integer userId);

}
