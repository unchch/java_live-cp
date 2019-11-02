package com.bh.live.rpc.service.inform;

import com.bh.live.pojo.res.inform.IssueCurrentRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName IIssueFeignService
 * @description: IIssueFeignService
 * @author: yq.
 * @date 2019-08-10 15:37:00
 */
@FeignClient(value = "lotto-inform", fallbackFactory = IssueFeignServiceFallback.class)
public interface IIssueFeignService {

    /**
     * 查询彩期
     *
     * @param seedNo
     * @return IssueCurrentRes：last:上一期，current:当前期，next:下一期
     */
    @GetMapping("/inform/rpc/issue/selectCurrentIssue")
    IssueCurrentRes selectCurrentIssue(@RequestParam(value = "seedNo", required = false) Integer seedNo);
}
