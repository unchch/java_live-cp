package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.lottery.SeedCategory;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.user.dao.SeedCategoryDao;
import com.bh.live.user.service.ISeedCategoryService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 彩种分类 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-23
 */
@Service
@Slf4j
public class SeedCategoryServiceImpl extends ServiceImpl<SeedCategoryDao, SeedCategory> implements ISeedCategoryService {

    @Override
    public List<SeedCategoryRes> querySeedCategoryByNos(List<Integer> ids) {
        QueryWrapper<SeedCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SeedCategory::getCategoryNo, ids);
        List<SeedCategory> list = baseMapper.selectList(wrapper);
        List<SeedCategoryRes> resList = Lists.newArrayList();
        for (SeedCategory vo : list) {
            SeedCategoryRes res = new SeedCategoryRes();
            BeanUtils.copyProperties(vo, res);
            resList.add(res);
        }
        return resList;
    }
}
