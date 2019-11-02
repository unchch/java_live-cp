package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.anchor.NoTalk;
import com.bh.live.pojo.res.anchor.NoTalkRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 直播间禁言表 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface NoTalkDao extends BaseMapper<NoTalk> {

    List<NoTalkRes> getNoTalkList(@Param("nickname") String nickname, @Param("queryType") String queryType,
                                  @Param("userId") Integer userId);
}
