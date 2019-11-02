package com.bh.live.dao.announcement;

import com.bh.live.model.announcement.Advertising;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 广告表 Mapper 接口
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
public interface AdvertisingDao extends BaseMapper<Advertising> {

    /**
     *  广告列表
     * @param advertisingReq 广告请求参数
     * @return 广告
     */
    List<Advertising> queryAdvertisList(@Param("advertisingReq") AdvertisingReq advertisingReq);

    /**
     * 查询推荐图片
     * @return
     */
    @Select(" select img from lotto_advertising where is_recommend = 1 and type=1")
    List<String> queryLiveCarousel();
}
