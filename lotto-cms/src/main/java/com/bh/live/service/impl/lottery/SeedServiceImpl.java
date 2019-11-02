package com.bh.live.service.impl.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.enums.lottery.SeedShowAbleEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.lottery.SeedDao;
import com.bh.live.model.lottery.Play;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.req.lottery.SeedReq;
import com.bh.live.pojo.req.lottery.SeedSettingsPlayReq;
import com.bh.live.pojo.req.lottery.SeedSettingsReq;
import com.bh.live.pojo.req.lottery.SeedUpdateReq;
import com.bh.live.pojo.res.lottery.SeedPageRes;
import com.bh.live.pojo.res.lottery.SeedSettingsRes;
import com.bh.live.pojo.res.lottery.SeedUpdateRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.IPlayBitService;
import com.bh.live.service.lottery.IPlayService;
import com.bh.live.service.lottery.ISeedCategoryService;
import com.bh.live.service.lottery.ISeedService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 彩种表 服务实现类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class SeedServiceImpl extends ServiceImpl<SeedDao, Seed> implements ISeedService {

    @Autowired
    private ISeedCategoryService seedCategoryService;

    @Autowired
    private IPlayService playService;

    @Autowired
    private IPlayBitService playBitService;

    /**
     * 查询彩种
     *
     * @param req
     * @return
     */
    @Override
    public TableDataInfo selectSeedPage(SeedReq req) {
        Page<SeedPageRes> page = new Page<>(req.getPageNum(), req.getPageSize());
        page.setRecords(baseMapper.selectSeedPage(page, req));
        return new TableDataInfo(page);
    }

    /**
     * 根据彩种编号查询彩种信息
     *
     * @param seedNo
     * @return
     */
    @Override
    public Seed selectBySeedNo(Integer seedNo) {
        QueryWrapper<Seed> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Seed::getSeedNo, seedNo);
        Seed seed = baseMapper.selectOne(wrapper);
        if (CommonUtil.isEmpty(seed)) {
            throw new ServiceRuntimeException(CodeMsg.E_60000);
        }
        return seed;
    }

    /**
     * 查询彩种及玩法赔率
     *
     * @param seedNo
     * @return
     */
    @Override
    public SeedUpdateRes query(Integer seedNo) {
        return baseMapper.selectSeedDetail(new SeedReq().setSeedNo(seedNo));
    }

    /**
     * 修改彩种及玩法赔率
     *
     * @param req
     * @return
     */
    @Override
    public boolean update(SeedUpdateReq req) {
        Seed seed = selectBySeedNo(req.getSeedNo());
        CommonUtil.beanCopy(req, seed);
        return baseMapper.updateById(seed) > 0;
    }

    /**
     * 设置彩种的默认玩法
     *
     * @param seedNo
     * @param playMode
     * @return
     */
    @Override
    public boolean defaultPlay(Integer seedNo, Integer playMode) {
        Seed seed = selectBySeedNo(seedNo);
        if (playMode.compareTo(seed.getPlayModeRecommend()) == 0) {
            return Boolean.TRUE;
        }
        seed.setPlayModeRecommend(playMode);
        return baseMapper.updateById(seed) > 0;
    }

    /**
     * 设置彩种隐藏
     *
     * @param seedNo
     * @param showAble
     * @return
     */
    @Override
    public boolean display(Integer seedNo, Integer showAble) {
        Seed seed = this.selectBySeedNo(seedNo);

        if (null == SeedShowAbleEnum.of(showAble)) {
            throw new ServiceRuntimeException(CodeMsg.E_60101);
        }
        if (seed.getShowAble().compareTo(showAble) == 0) {
            return Boolean.TRUE;
        }
        seed.setShowAble(showAble);
        return baseMapper.updateById(seed) > 0;
    }

    /**
     * 查询彩种玩法配置
     *
     * @param seedNo
     * @return
     */
    @Override
    public List<SeedSettingsRes> querySettings(Integer seedNo) {
        List<SeedSettingsRes> ret = Lists.newArrayList();

        // 0.检查数据
        // 彩种
        Seed seed = selectBySeedNo(seedNo);
        if (CommonUtil.isEmpty(seed)) {
            log.error("settings. seed not found. seedNo:{}", seedNo);
            throw new ServiceRuntimeException(CodeMsg.E_60000);
        }
        // 玩法
        List<Play> plays = playService.selectPlayList(new Play().setSeedNo(seed.getSeedNo()));
        if (CommonUtil.isEmpty(plays)) {
            log.error("settings. plays not found. seedNo:{}", seedNo);
            throw new ServiceRuntimeException(CodeMsg.E_60001);
        }

        // build
        Lists.newArrayList(1, 2).forEach(playMode -> {
            SeedSettingsRes req = new SeedSettingsRes();
            req.setSeedNo(seedNo);
            req.setPlayMode(playMode);
            if (playMode.compareTo(LotteryConstants.PLAY_MODE_OFFICIAL) == 0) {
                // 官方
                req.setPlayNo(seed.getOfficialPlayNo());
            } else if (playMode.compareTo(LotteryConstants.PLAY_MODE_CREDIT) == 0) {
                // 信用
                req.setPlayNo(seed.getCreditPlayNo());
            }

            plays.stream()
                    .filter((p) -> p.getParentNo().equals(String.valueOf(LotteryConstants.VALUE_0)))
                    .filter((p) -> p.getPlayMode().compareTo(playMode) == 0)
                    .forEach((p) -> {
                        List<SeedSettingsPlayReq> subPlayNos = Lists.newArrayList();
                        plays.stream()
                                .filter((b) -> b.getParentNo().compareTo(p.getPlayNo()) == 0)
                                .forEach((b) -> {
                                    SeedSettingsPlayReq subPlay = new SeedSettingsPlayReq();
                                    CommonUtil.beanCopy(b, subPlay);
                                    subPlay.setStatus(b.getStatus().compareTo(LotteryConstants.VALUE_0) != 0);
                                    subPlayNos.add(subPlay);
                                });
                        SeedSettingsPlayReq play = new SeedSettingsPlayReq();
                        CommonUtil.beanCopy(p, play);
                        play.setStatus(p.getStatus().compareTo(LotteryConstants.VALUE_0) != 0);
                        play.setSubPlayNos(subPlayNos);
                        if (CommonUtil.isEmpty(req.getPlays())) {
                            req.setPlays(Lists.newArrayList());
                        }
                        req.getPlays().add(play);
                    });
            ret.add(req);
        });
        return ret;
    }

    /**
     * 彩种玩法配置
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean settings(List<SeedSettingsReq> req) {
        Integer seedNo = req.get(0).getSeedNo();
        // 0.检查数据
        // 彩种
        Seed seed = selectBySeedNo(seedNo);
        if (CommonUtil.isEmpty(seed)) {
            log.error("settings. seed not found. seedNo:{}", seedNo);
            throw new ServiceRuntimeException(CodeMsg.E_60000);
        }
        // 玩法
        List<Play> plays = playService.selectPlayList(new Play().setSeedNo(seedNo));
        if (CommonUtil.isEmpty(plays)) {
            log.error("settings. plays not found. seedNo:{}", seedNo);
            throw new ServiceRuntimeException(CodeMsg.E_60001);
        }
        req.forEach((r) -> {
            if (CommonUtil.isNotEmpty(r.getPlayNo())) {
                Play defaultPlay = plays.stream()
                        .filter((p) -> p.getPlayNo().equals(r.getPlayNo()))
                        .findFirst()
                        .orElse(null);
                if (CommonUtil.isEmpty(defaultPlay)) {
                    throw new ServiceRuntimeException(CodeMsg.E_60002);
                }

                // 1.修改彩种默认玩法
                if (r.getPlayMode().compareTo(LotteryConstants.PLAY_MODE_OFFICIAL) == 0) {
                    // 官方
                    seed.setOfficialPlayNo(defaultPlay.getPlayNo());
                    seed.setOfficialPlayRecommend(defaultPlay.getPlayName());
                } else if (r.getPlayMode().compareTo(LotteryConstants.PLAY_MODE_CREDIT) == 0) {
                    // 信用
                    seed.setCreditPlayNo(defaultPlay.getPlayNo());
                    seed.setCreditPlayRecommend(defaultPlay.getPlayName());
                } else {
                    log.error("[exception] settings. playMode not in (1,2). playMode:{}", r.getPlayMode());
                    throw new ServiceRuntimeException(CodeMsg.E_60102);
                }
                baseMapper.updateById(seed);
            }
            // 2.修改玩法状态
            List<SeedSettingsPlayReq> all = Lists.newArrayList();
            r.getPlays().forEach(item -> {
                all.add(item);
                all.addAll(item.getSubPlayNos());
            });
            Map<String, Boolean> playNos = all.stream()
                    .collect(Collectors.toMap(SeedSettingsPlayReq::getPlayNo, SeedSettingsPlayReq::getStatus));
            plays.stream()
                    .filter((play) -> play.getPlayMode().compareTo(r.getPlayMode()) == 0)
                    .forEach((play) -> {
                        Boolean status = playNos.get(play.getPlayNo());
                        if (CommonUtil.isNotEmpty(status)) {
                            play.setStatus(status ? 1 : 0);
                        }
                    });
        });
        playService.updateBatchById(plays);
        return Boolean.TRUE;
    }
}
