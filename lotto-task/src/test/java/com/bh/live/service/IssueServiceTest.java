package com.bh.live.service;

import com.bh.live.task.LottoTaskApplication;
import com.bh.live.task.service.lottery.IIssueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName IssueServiceTest
 * @description: IssueServiceTest
 * @author: yq.
 * @date 2019-08-06 13:59:00
 */
@SpringBootTest(classes = LottoTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IssueServiceTest {

    @Resource
    private IIssueService issueService;

    @Test
    public void createNewIssue(){
        issueService.createNewIssue();
    }
}
