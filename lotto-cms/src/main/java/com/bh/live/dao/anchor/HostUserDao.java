package com.bh.live.dao.anchor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserRes;
import com.bh.live.pojo.res.anchor.HostUserResDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主播表 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
public interface HostUserDao extends BaseMapper<HostUser> {

    /**
     * 获取主播分页列表信息
     *
     * @param page
     * @param param
     * @return
     */
    List<HostUserRes> getListByPage(Page<HostUserRes> page, @Param("param") HostUserReq param);

    /**
     * 用户详情
     * @param userId
     * @return
     */
    HostUserResDetail userDetail(@Param("userId") Integer userId);

}
