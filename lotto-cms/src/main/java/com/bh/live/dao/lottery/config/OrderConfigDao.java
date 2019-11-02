package com.bh.live.dao.lottery.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.lottery.config.vo.OrderConfigVO;
import com.bh.live.pojo.res.lottery.config.OrderConfigRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 彩种订单配置表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
public interface OrderConfigDao extends BaseMapper<OrderConfig> {

    /**
     * 分页查询订单配置
     * @auth lgs
     * @param page
     * @param vo
     * @return
     */
    List<OrderConfigRes> selectOrderConfig(Page<OrderConfigRes> page, @Param("vo") OrderConfigVO vo);

}
