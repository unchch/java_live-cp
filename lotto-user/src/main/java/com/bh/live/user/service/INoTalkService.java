package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.NoTalk;
import com.bh.live.model.user.LiveUser;

/**
 * <p>
 * 直播间禁言表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface INoTalkService extends IService<NoTalk> {

    /**
     * 禁言列表
     *
     * @param nickname
     * @param queryType
     * @return
     */
    Result noTalkList(String nickname, String queryType,Integer hostUserId);

    /**
     * 添加禁言记录
     *
     * @param value
     * @param time
     * @param type
     * @return
     */
    Result addNoTalk(String value, Integer time, String timeType, Integer type,String remark,LiveUser liveUser);
}
