package com.bh.live.service.impl.lottery;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.dao.lottery.IssueDao;
import com.bh.live.model.lottery.Issue;
import com.bh.live.pojo.req.lottery.IssueLotteryReq;
import com.bh.live.pojo.req.lottery.IssueReq;
import com.bh.live.pojo.res.lottery.IssueRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.IIssueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 彩期 服务实现类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class IssueServiceImpl extends ServiceImpl<IssueDao, Issue> implements IIssueService {
    @Resource
    private RestTemplate httpClientTemplate;
    /**
     * 开奖地址
     */
    @Value("${award.lottery.url}")
    private String AWARD_LOTTERY_URL;

    /**
     * 查询开奖列表
     *
     * @param issueReq
     * @return res
     */
    @Override
    public TableDataInfo selectByIssueReq(IssueReq issueReq) {
        Assert.isNotNull(issueReq.getSeedNo(), CodeMsg.E_80014);
        Page<IssueRes> page = new Page<>(issueReq.getPageNum(), issueReq.getPageSize());
        page.setRecords(baseMapper.selectByIssueReq(page, issueReq));
        return new TableDataInfo(page);
    }

    /**
     * 彩期开奖
     *
     * @param issueLotteryReq
     * @return int
     */
    @Override
    public Result issueNoLottery(IssueLotteryReq issueLotteryReq, HandleEnum.PrizeStatusEnum prizeStatusEnum) {
        Assert.isNotNull(issueLotteryReq.getIssueNo(), CodeMsg.E_80013);
        Assert.isNotNull(issueLotteryReq.getResult(), CodeMsg.E_80008);
        Assert.isNotNull(issueLotteryReq.getSeedNo(), CodeMsg.E_80021);
        Map<String, String> map = new HashMap<>();
        map.put("issueNo", issueLotteryReq.getIssueNo());
        map.put("seedNo", issueLotteryReq.getSeedNo());
        map.put("result", issueLotteryReq.getResult());
        map.put("type", String.valueOf(prizeStatusEnum.getCode()));
        String url = AWARD_LOTTERY_URL + "?issueNo={issueNo}&seedNo={seedNo}&result={result}&type={type}";
        ResponseEntity<Result> resultResponseEntity = null;
        try {
            resultResponseEntity = httpClientTemplate.getForEntity(url, Result.class, map);
        } catch (RestClientException e) {
            log.error("请求开奖操作失败:{},{}", e.getMessage(), e);
        }
        if (resultResponseEntity != null) {
            return resultResponseEntity.getBody();
        }else{
            return Result.error(CodeMsg.E_80022.code, String.format(CodeMsg.E_80022.message, prizeStatusEnum.getDesc()));
        }
    }

    /**
     * 彩期重置开奖
     *
     * @param issueLotteryReq
     * @return int
     */
    @Override
    public int issueNoResetLottery(IssueLotteryReq issueLotteryReq) {
        Assert.isNotNull(issueLotteryReq.getIssueNo(), CodeMsg.E_80013);
        Assert.isNotNull(issueLotteryReq.getIssueNo(), CodeMsg.E_80008);
        return 0;
    }

    /**
     * 彩期撤单
     *
     * @param issueLotteryReq
     * @return int
     */
    @Override
    public int issueNoRevokeOrder(IssueLotteryReq issueLotteryReq) {
        Assert.isNotNull(issueLotteryReq.getIssueNo(), CodeMsg.E_80013);
        return 0;
    }
}
