package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.NoTalkConf;
import com.bh.live.pojo.req.anchor.NoTalkConfReq;
import com.bh.live.user.dao.NoTalkConfDao;
import com.bh.live.user.service.INoTalkConfService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 房间发言要求配置表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Service
public class NoTalkConfServiceImpl extends ServiceImpl<NoTalkConfDao, NoTalkConf> implements INoTalkConfService {

    @Resource
    private NoTalkConfDao noTalkConfDao;

    @Override
    public NoTalkConfReq getNoTalkConf(Integer roomId) {
        QueryWrapper<NoTalkConf> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id", roomId);
        NoTalkConf noTalkConf = noTalkConfDao.selectOne(wrapper);
        if (noTalkConf == null) return new NoTalkConfReq();
        NoTalkConfReq req = new NoTalkConfReq();
        BeanUtils.copyProperties(noTalkConf, req);
        return req;
    }

}
