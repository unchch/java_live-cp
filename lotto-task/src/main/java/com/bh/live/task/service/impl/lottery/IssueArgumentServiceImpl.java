package com.bh.live.task.service.impl.lottery;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.task.dao.lottery.IssueArgumentDao;
import com.bh.live.task.service.lottery.IIssueArgumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 彩期生成的参数 服务实现类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class IssueArgumentServiceImpl extends ServiceImpl<IssueArgumentDao, IssueArgument> implements IIssueArgumentService {

    @Autowired
    private RedisUtil redisTemplate;

    /**
     * 根据彩种编号查询彩期生成参数
     *
     * @return
     */
    @Override
    public List<IssueArgument> selectByIssueArgument() {
//        Object cacheValue = redisTemplate.getByFastJson(LotIssueCacheConstants.KEY_ISSUE_ARGUMENT_ALL, IssueArgument.class);
//        List<IssueArgument> args = Lists.newArrayList();
//        if (CommonUtil.isNotEmpty(cacheValue)) {
//            try {
//                args = (List<IssueArgument>) cacheValue;
//            } catch (Exception e) {
//                log.error("selectByIssueArgument. Exception cause:{} message:{}", e.getCause(), e.getMessage());
//            }
//        }
//        if (CommonUtil.isEmpty(args)) {
//            args = baseMapper.selectList(null);
//            // 放入cache
//            try {
//                redisTemplate.setByFastJson(LotIssueCacheConstants.KEY_ISSUE_ARGUMENT_ALL, args, 1, TimeUnit.DAYS);
//            } catch (Exception e) {
//                log.error("所有彩期arg放入缓存中失败", e);
//            }
//        }
//        if (CommonUtil.isEmpty(args)) {
//            log.error("所有彩期argument被清空，无法实例化template");
//            throw new ServiceRuntimeException(CodeMsg.E_400);
//        }
        return baseMapper.selectList(null);
    }

    /**
     * 所有彩期根据seedNo分组
     *
     * @return Map K:seedNo, V:list
     */
    @Override
    public Map<Integer, List<IssueArgument>> selectAllIssueArgumentToMap() {
        List<IssueArgument> args = selectByIssueArgument();
        return args.stream().collect(Collectors.groupingBy(IssueArgument::getSeedNo));
    }
}
