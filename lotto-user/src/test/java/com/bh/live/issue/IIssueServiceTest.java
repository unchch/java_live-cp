package com.bh.live.issue;

import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.rpc.service.inform.IIssueFeignService;
import com.bh.live.user.LottoUserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName IIssueService
 * @description: IIssueService
 * @author: yq.
 * @date 2019-08-10 15:24:00
 */
@SpringBootTest(classes = LottoUserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IIssueServiceTest {

    @Resource
    private IIssueFeignService issueFeignService;

    @Test
    public void selectCurrentIssue() {
        IssueCurrentRes res = issueFeignService.selectCurrentIssue(401);
        System.out.println(res);
    }
}
