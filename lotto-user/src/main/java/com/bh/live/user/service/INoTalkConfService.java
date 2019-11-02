package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.NoTalkConf;
import com.bh.live.pojo.req.anchor.NoTalkConfReq;

/**
 * <p>
 * 房间发言要求配置表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface INoTalkConfService extends IService<NoTalkConf> {

    NoTalkConfReq getNoTalkConf(Integer roomId);
}
