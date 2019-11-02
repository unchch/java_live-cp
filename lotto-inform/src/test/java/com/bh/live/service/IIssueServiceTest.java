package com.bh.live.service;

import com.bh.live.pojo.res.inform.IssueCurrentRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName IIssueService
 * @description: IIssueService
 * @author: yq.
 * @date 2019-08-10 15:24:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class IIssueServiceTest {

    @Autowired
    private IIssueService issueService;

    @Test
    public void selectCurrentIssue() {
        IssueCurrentRes res = issueService.selectCurrentIssue(401);
        System.out.println(res);
    }
}
