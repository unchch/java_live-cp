package com.bh.live.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.res.lottery.SeedNavRes;
import org.apache.ibatis.annotations.Select;
import java.util.List;


/**
 * <p>
 * 彩种表 Mapper 接口
 * </p>
 *
 * @author wuhuanrong.
 * @since 2019-08-02
 */
public interface SeedDao extends BaseMapper<Seed> {

    /**
     * 查询所有彩种编号及名称
     *
     * @return
     */
    @Select("SELECT category_no,cover_pc_icon,cover_app_icon, seed_no, seed_name FROM lotto_seed where show_able !=0 ")
    List<SeedNavRes> selectSeed();

}
