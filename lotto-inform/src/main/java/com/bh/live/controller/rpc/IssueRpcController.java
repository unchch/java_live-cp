package com.bh.live.controller.rpc;

import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.service.IIssueService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IssueController
 * @description: IssueController
 * @author: yq.
 * @date 2019-08-10 15:41:00
 */
@RestController
@RequestMapping("/rpc/issue")
@Api(tags = "彩期rpc")
@Slf4j
public class IssueRpcController {

    @Autowired
    private IIssueService issueService;

    /**
     * 查询彩期
     *
     * @param seedNo
     * @return IssueCurrentRes：last:上一期，current:当前期，next:下一期
     */
    @GetMapping(value = "/selectCurrentIssue")
    public IssueCurrentRes selectCurrentIssue(@RequestParam("seedNo") Integer seedNo) {
        IssueCurrentRes issueCurrentRes = null;
        try {
            issueCurrentRes = issueService.selectCurrentIssue(seedNo);
        } catch (Exception e) {
            log.error("查询当前期出错，彩种编号{}",seedNo);
            e.printStackTrace();
        }
        return issueCurrentRes;
    }
}
