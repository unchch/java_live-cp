package com.bh.live.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Play;
import com.bh.live.pojo.req.lottery.PlayOddsReq;
import com.bh.live.pojo.res.lottery.PlayOddsRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 玩法表 服务类
 * </p>
 *
 * @author yq.
 * @since 2019-07-23
 */
public interface IPlayService extends IService<Play> {

    /**
     * 查询玩法list
     *
     * @param play
     * @return
     * @author yq.
     */
    List<Play> selectPlayList(Play play);

    /**
     * 获得玩法及二级玩法赔率
     *
     * @param seedNo
     * @return K: playMode: 玩法模式（official：官方  credit：信用）
     * @author yq.
     */
    Map<String, List<PlayOddsRes>> selectPlayOdds(Integer seedNo);

    /**
     * 修改玩法及二级玩法赔率
     *
     * @param req
     * @return
     * @author yq.
     */
    boolean updatePlayOdds(Map<String, List<PlayOddsReq>> req);
}
