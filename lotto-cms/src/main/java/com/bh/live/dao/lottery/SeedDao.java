package com.bh.live.dao.lottery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.req.lottery.SeedReq;
import com.bh.live.pojo.res.inform.LottoTypeRes;
import com.bh.live.pojo.res.lottery.SeedPageRes;
import com.bh.live.pojo.res.lottery.SeedRes;
import com.bh.live.pojo.res.lottery.SeedUpdateRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩种表 Mapper 接口
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
public interface SeedDao extends BaseMapper<Seed> {

    /**
     * 彩种列表
     *
     * @param req
     * @param page
     * @return
     */
    List<SeedPageRes> selectSeedPage(Page<SeedPageRes> page, @Param("param") SeedReq req);

    /**
     * 查询所有彩种编号及名称
     *
     * @return
     */
    @Select("SELECT category_no, seed_no, seed_name FROM lotto_seed ORDER BY sort")
    List<SeedRes> selectSeed();

    /**
     * 查询彩种
     * @param req
     * @return
     */
    SeedUpdateRes selectSeedDetail(@Param("req") SeedReq req);

    /**
     * 彩种分类
     * @return
     */
    @Select("SELECT c.category_name,s.seed_name from lotto_seed_category c LEFT JOIN lotto_seed s on c.category_no= s.category_no")
    List<LottoTypeRes> queryLottoType();
}
