package com.bh.live.service.impl.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.lottery.SeedCategoryDao;
import com.bh.live.dao.lottery.SeedDao;
import com.bh.live.model.lottery.SeedCategory;
import com.bh.live.pojo.req.lottery.SeedCategoryReq;
import com.bh.live.pojo.res.inform.LottoTypeRes;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.pojo.res.lottery.SeedRes;
import com.bh.live.service.lottery.ISeedCategoryService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 彩种分类 服务实现类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class SeedCategoryServiceImpl extends ServiceImpl<SeedCategoryDao, SeedCategory> implements ISeedCategoryService {

    @Autowired
    private SeedDao seedDao;

    /**
     * 查询彩种分类
     *
     * @return
     */
    @Override
    public List<SeedCategory> selectSeedCategoryByReq(SeedCategoryReq req) {
        QueryWrapper<SeedCategory> wrapper = new QueryWrapper<>();
        if (CommonUtil.isNotEmpty(req)) {
            if (CommonUtil.isNotEmpty(req.getCategoryNo())) {
                wrapper.lambda().eq(SeedCategory::getCategoryNo, req.getCategoryNo());
            }
            if (CommonUtil.isNotEmpty(req.getCategoryName())) {
                wrapper.lambda().eq(SeedCategory::getCategoryName, req.getCategoryName());
            }
        }
        return baseMapper.selectList(wrapper);
    }

    /**
     * 查询彩种分类编号和名称
     *
     * @return
     */
    @Override
    public List<SeedCategoryRes> selectSeedCategory() {
        return baseMapper.selectSeedCategory();
    }

    /**
     * 构建彩种分类及彩种集合树
     *
     * @return
     */
    @Override
    public List<SeedCategoryRes> buildSeedCategoryTree() {
        log.info("buildSeedCategoryTree.start");

        List<SeedCategoryRes> categories = baseMapper.selectSeedCategory();
        if (CommonUtil.isEmpty(categories)) {
            log.error("buildSeedCategoryTree. categories is null. categories:{}", categories);
            return null;
        }
        List<SeedRes> seeds = seedDao.selectSeed();
        if (CommonUtil.isEmpty(categories)) {
            log.error("buildSeedCategoryTree. seeds is null. seeds:{}", seeds);
            return categories;
        }
        categories.forEach((c) ->
                c.setSeeds(seeds.stream()
                        .filter(s -> s.getCategoryNo().compareTo(c.getCategoryNo()) == 0)
                        .collect(Collectors.toList()))
        );
        log.info("buildSeedCategoryTree.end ret:{}", categories);
        return categories;
    }

    @Override
    public List<SeedCategoryRes> queryLottoHall() {
        QueryWrapper<SeedCategory> wrapper = new QueryWrapper<>();
        List<SeedCategory> list = baseMapper.selectList(wrapper);
        List<SeedCategoryRes> resList = Lists.newArrayList();
        for (SeedCategory vo : list) {
            SeedCategoryRes res = new SeedCategoryRes();
            BeanUtils.copyProperties(vo, res);
            resList.add(res);
        }
        return resList;
    }

    @Override
    public Map<String, List<LottoTypeRes>> queryLottoType() {
        List<LottoTypeRes> list = seedDao.queryLottoType();
        Map<String, List<LottoTypeRes>> map = list.stream().collect(Collectors.groupingBy(LottoTypeRes::getCategoryName));
        return map;
    }

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
