package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.anchor.FansListRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 关注表 Mapper 接口
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-25
 */
public interface AttentionDao extends BaseMapper<Attention> {
    /**
     * 查询关注列表
     * @return
     */
    List<LiveUser> queryAttentListPage(Page page,@Param("type") Integer type, @Param("userId") Integer userId);

    /**
     * 查询被关注用户的粉丝
     * @param userId
     * @return
     */
    List<FansListRes> attentionListAsUser(@Param("userId") Integer userId);
}
