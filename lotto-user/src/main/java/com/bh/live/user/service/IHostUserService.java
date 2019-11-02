package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.user.Attention;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.pojo.res.anchor.HostUserHomePageRes;
import com.bh.live.pojo.res.anchor.HostUserRecruitReq;
import com.bh.live.pojo.res.anchor.HostUserResDetail;
import com.bh.live.pojo.res.page.TableDataInfo;

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
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    HostUserFormRes getHostUserById(Integer userId);

    /**
     * 修改主播信息
     *
     * @param req
     */
    void updateHostUser(HostUserReq req);

    /**
     * 申请成为主播
     *
     * @param form
     * @return
     */
    Result registerHostUser(HostUserRecruitReq form);

    /**
     * 主播个人主页信息
     * @param userId
     * @return
     */
    HostUserHomePageRes selectUserHomePageDetail(Integer userId);

    /**
     * 获取是否关注
     * @param userId
     * @param loginUser
     * @return
     */
    Attention getIsAttention(Integer userId, Integer loginUser);

    /**
     * 获取主播房间公告
     * @param userId
     * @return
     */
    String getHostNotice(Integer userId);

}
