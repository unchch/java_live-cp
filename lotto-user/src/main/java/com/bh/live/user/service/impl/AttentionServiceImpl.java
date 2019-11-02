package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.anchor.FansListRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.user.dao.AttentionDao;
import com.bh.live.user.service.IAttentionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 关注表 服务实现类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-25
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionDao, Attention> implements IAttentionService {

    @Resource
    private AttentionDao attentionDao;

    @Transactional
    @Override
    public Integer insertOrUpdate(Attention attention) throws ServiceRuntimeException {
        // 先查询是否已经存在
        QueryWrapper<Attention> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Attention::getUserId, attention.getUserId()).eq(Attention::getTargetId, attention.getTargetId());
        Attention selectOne = attentionDao.selectOne(queryWrapper);
        Date date = new Date();
        int result = -1;
        if (selectOne != null) {
            // 判断状态是否已经更改,防刷
            if (!attention.getIsAttent().equals(selectOne.getIsAttent())) {
                selectOne.setUpdateTime(date);
                selectOne.setIsAttent(attention.getIsAttent());
                result = attentionDao.updateById(selectOne);
            }else {
                result =1;
            }
        } else {
            // 添加
            attention.setCreatTime(date);
            result = attentionDao.insert(attention);
        }
        return result;
    }

    /**
     * @param  userId
     * @return
     * @throws ServiceRuntimeException
     */
    @Override
    public TableDataInfo queryAttentList(Integer type,Integer userId,Integer pageNum,Integer pageSize) throws ServiceRuntimeException {
        Page<LiveUser> page = new Page<>(pageNum, pageSize);
        page.setRecords(attentionDao.queryAttentListPage(page,type, userId));
        return new TableDataInfo(page);
    }

    @Override
    public List<FansListRes> getAttentionByUserId(Integer userId) {
        return attentionDao.attentionListAsUser(userId);
    }
}
