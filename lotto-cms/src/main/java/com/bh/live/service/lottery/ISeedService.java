package com.bh.live.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.req.lottery.SeedReq;
import com.bh.live.pojo.req.lottery.SeedSettingsReq;
import com.bh.live.pojo.req.lottery.SeedUpdateReq;
import com.bh.live.pojo.res.lottery.SeedSettingsRes;
import com.bh.live.pojo.res.lottery.SeedUpdateRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 彩种表 服务类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
public interface ISeedService extends IService<Seed> {

    /**
     * 查询彩种信息
     *
     * @param req
     * @return
     * @author yq.
     */
    TableDataInfo selectSeedPage(SeedReq req);

    /**
     * 查询彩种信息
     *
     * @param seedNo
     * @return
     * @author yq.
     */
    Seed selectBySeedNo(Integer seedNo);

    /**
     * 查询彩种及玩法赔率
     * @param seedNo
     * @return
     * @author yq.
     */
    SeedUpdateRes query(Integer seedNo);

    /**
     * 修改彩种及玩法赔率
     * @param req
     * @return
     * @author yq.
     */
    boolean update(SeedUpdateReq req);

    /**
     * 设置彩种默认玩法
     *
     * @param seedNo
     * @param playMode
     * @return
     * @author yq.
     */
    boolean defaultPlay(Integer seedNo, Integer playMode);

    /**
     * 设置彩种隐藏
     *
     * @param seedNo
     * @param showAble
     * @return
     * @author yq.
     */
    boolean display(Integer seedNo, Integer showAble);

    /**
     * 查询彩种玩法配置
     *
     * @param seedNo
     * @return
     * @author yq.
     */
    List<SeedSettingsRes> querySettings(Integer seedNo);

    /**
     * 彩种玩法配置
     *
     * @param req
     * @return
     * @author yq.
     */
    boolean settings(List<SeedSettingsReq> req);
}
