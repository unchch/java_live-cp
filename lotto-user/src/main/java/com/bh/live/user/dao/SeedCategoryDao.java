package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.SeedCategory;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩种分类 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-08-06
 */
public interface SeedCategoryDao extends BaseMapper<SeedCategory> {

}
