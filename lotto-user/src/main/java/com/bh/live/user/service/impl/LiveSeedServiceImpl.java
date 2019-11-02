package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.model.lottery.Seed;
import com.bh.live.model.user.Attention;
import com.bh.live.pojo.res.live.LiveRoomSeedRes;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.pojo.res.lottery.SeedRes;
import com.bh.live.user.dao.HostUserDao;
import com.bh.live.user.dao.LiveSeedDao;
import com.bh.live.user.service.IHostAdvanceService;
import com.bh.live.user.service.ILiveSeedService;
import com.bh.live.user.service.ISeedCategoryService;
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
    private HostUserDao hostUserDao;

    @Autowired
    private IHostAdvanceService hostAdvanceService;

    @Override
    public List<SeedCategoryRes> getCategoryLiveSeed() {
        QueryWrapper<LiveSeed> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(LiveSeed::getIsDel, 0);
        wrapper.lambda().eq(LiveSeed::getStatus, 1);
        List<LiveSeed> liveSeeds = liveSeedDao.selectList(wrapper);
        List<Integer> seedNos = liveSeeds.stream().map(LiveSeed::getSeedNo).collect(Collectors.toList());
        List<Seed> seeds = liveSeedDao.getBySeedNos(seedNos);
        List<SeedRes> seedRes = new ArrayList<>();
        liveSeeds.forEach(item -> {
            SeedRes res = new SeedRes();
            BeanUtils.copyProperties(item, res);
            seeds.forEach((seed -> {
                if (res.getSeedNo().compareTo(seed.getSeedNo()) == 0) {
                    res.setSeedLogo(seed.getLogoPcIcon());
                    res.setSeedBanner(seed.getImageIcon());
                }
            }));
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

    @Override
    public List<LiveRoomSeedRes> getLiveUserRoomInfo(Integer seedNo, String queryType) {
        return liveSeedDao.getLiveUserBySeed(seedNo, queryType);
    }

    @Override
    public List<Attention> attentions(Integer targetId, List<Integer> ids) {
        return hostUserDao.getUserAttention(null,targetId,"list",ids);
    }

}
