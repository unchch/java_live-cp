package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.anchor.HostAdvanceDao;
import com.bh.live.dao.anchor.LiveSeedDao;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.req.anchor.LiveSeedReq;
import com.bh.live.pojo.res.anchor.LiveSeedRes;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.pojo.res.lottery.SeedRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.ILiveSeedService;
import com.bh.live.service.lottery.ISeedCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 直播彩种管理 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@Service
public class LiveSeedServiceImpl extends ServiceImpl<LiveSeedDao, LiveSeed> implements ILiveSeedService {

    @Resource
    private LiveSeedDao liveSeedDao;

    @Autowired
    private ISeedCategoryService seedCategoryService;

    @Resource
    private HostAdvanceDao hostAdvanceDao;

    @Override
    public void addLiveSeed(LiveSeedReq req) {
        LiveSeed liveSeed = new LiveSeed();
        CommonUtil.beanCopy(req, liveSeed);
        this.save(liveSeed);
    }

    @Override
    public TableDataInfo getLiveSeeds(LiveSeedReq req) {
        List<LiveSeedRes> resList = new ArrayList<>();
        Page<LiveSeed> page = new Page<>(req.getPageNum(), req.getPageSize());
        QueryWrapper<LiveSeed> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(LiveSeed::getIsDel, 0);
        if (req.getSeedNo() != null) {
            wrapper.lambda().eq(LiveSeed::getSeedNo, req.getSeedNo());
        }
        IPage<LiveSeed> liveSeedIPage = liveSeedDao.selectPage(page, wrapper);
        liveSeedIPage.getRecords().forEach(item -> {
            LiveSeedRes res = new LiveSeedRes();
            BeanUtils.copyProperties(item, res);
            resList.add(res);
        });
        return new TableDataInfo(resList, liveSeedIPage.getTotal());
    }

    @Override
    public void updateLiveSeed(LiveSeedReq req) {
        LiveSeed grade = new LiveSeed();
        CommonUtil.beanCopy(req, grade);
        this.updateById(grade);
    }

    @Override
    public List<SeedCategoryRes> getCategoryLiveSeed() {
        QueryWrapper<LiveSeed> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(LiveSeed::getIsDel, 0);
        List<LiveSeed> liveSeeds = liveSeedDao.selectList(wrapper);
        List<SeedRes> seedRes = new ArrayList<>();
        liveSeeds.forEach(item -> {
            SeedRes res = new SeedRes();
            BeanUtils.copyProperties(item, res);
            seedRes.add(res);
        });
        List<Integer> ids = liveSeeds.stream().map(LiveSeed::getCategoryNo).collect(Collectors.toList());
        List<SeedCategoryRes> seedCategoryRes = seedCategoryService.querySeedCategoryByNos(ids);
        seedCategoryRes.forEach((c) ->
                c.setSeeds(seedRes.stream()
                        .filter(s -> s.getCategoryNo().compareTo(c.getCategoryNo()) == 0)
                        .collect(Collectors.toList())));

        return seedCategoryRes;
    }
}
