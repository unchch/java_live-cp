package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.KeywordShield;

import java.util.List;

/**
 * <p>
 * 房间发言关键词过滤表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface IKeywordShieldService extends IService<KeywordShield> {

    List<KeywordShield> shield(Integer roomId);
}
