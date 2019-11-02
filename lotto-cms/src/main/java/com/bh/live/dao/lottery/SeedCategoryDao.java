package com.bh.live.dao.lottery;

import com.bh.live.model.lottery.SeedCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩种分类 Mapper 接口
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
public interface SeedCategoryDao extends BaseMapper<SeedCategory> {

    /**
     * 查询所有彩种分类编号及名称
     * @return
     */
    @Select("SELECT category_no, category_name FROM lotto_seed_category ORDER BY sort")
    List<SeedCategoryRes> selectSeedCategory();
}
