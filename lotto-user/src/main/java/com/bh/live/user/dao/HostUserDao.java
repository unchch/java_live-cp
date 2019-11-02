package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.user.Attention;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.pojo.res.anchor.HostUserHomePageRes;
import com.bh.live.pojo.res.anchor.HostUserRes;
import com.bh.live.pojo.res.anchor.HostUserResDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * 个人主页基础信息
     *
     * @param userId
     * @return
     */
    HostUserHomePageRes getUserHomePageDetail(@Param("userId") Integer userId);

    /**
     * 查询用户是否关注1对1
     *
     * @param userId
     * @param loginUser
     * @return
     */
    List<Attention> getUserAttention(@Param("userId") Integer userId, @Param("loginUser") Integer loginUser,
                                     @Param("type") String type,@Param("ids") List<Integer> ids);


    @Select("select notice from lotto_host_room where host_id = #{userId}")
    String hostRoomNotice(@Param("userId") Integer userId);

}
