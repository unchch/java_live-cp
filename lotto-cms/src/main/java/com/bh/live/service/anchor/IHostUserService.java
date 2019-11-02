package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserRes;
import com.bh.live.pojo.res.anchor.HostUserResDetail;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 主播表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
public interface IHostUserService extends IService<HostUser> {

    /**
     * 主播列表分页查询
     *
     * @param param
     * @return
     */
    Object getHostUserList(HostUserReq param,String callType);

    /**
     * 根据用户获取主播详情
     *
     * @param userId
     * @return
     */
    HostUserResDetail getHostUserDetailByUserId(Integer userId);

    /**
     * 根据用户获取主播
     *
     * @param userId
     * @return
     */
    HostUser getHostUserByUserId(Integer userId);

    /**
     * 修改主播信息
     * @param req
     */
    void updateHostUser(HostUserReq req);

    /**
     * 主播审核
     * @param id
     * @param verifyStatus
     * @return
     */
    Result verifyHostUser(Integer id,Integer verifyStatus);
}
