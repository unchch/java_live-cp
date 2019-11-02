package com.bh.live.dao.anchor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.anchor.Gift;
import com.bh.live.pojo.req.anchor.GiftReq;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 礼物表 Mapper 接口
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
public interface GiftDao extends BaseMapper<Gift> {


    Integer delete(@Param("ids") List<Integer> ids);

    List<GiftReq> queryGiftPage(Page<GiftReq> page,@Param("gift")  GiftReq gift);
}
