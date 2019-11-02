package com.bh.live.controller.lottery;



import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.Issue;
import com.bh.live.pojo.req.lottery.IssueLotteryReq;
import com.bh.live.pojo.req.lottery.IssueReq;
import com.bh.live.service.lottery.IIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



/**
 * <p>
 * 彩期 前端控制器
 * </p>
 *
 * @author WJ
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/issue")
@Api(tags = "彩种管理-开奖管理")
public class IssueController {

    @Autowired
    IIssueService iIssueService;

    @ApiOperation(value = "开奖信息列表", response = Issue.class)
    @GetMapping(value = "/lottery/page", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result lotteryPageInifo(IssueReq issueReq){
        return  Result.success(iIssueService.selectByIssueReq(issueReq));
    }

    @ApiOperation(value = "彩期开奖", response = Result.class)
    @PostMapping(value = "/lottery")
    public Result issueNoLottery(@RequestBody IssueLotteryReq issueLotteryReq){
        return  iIssueService.issueNoLottery(issueLotteryReq, HandleEnum.PrizeStatusEnum.MANUAL);
    }

    @ApiOperation(value = "彩期重置开奖", response = Result.class)
    @PostMapping(value = "/reset/lottery")
    public Result issueNoResetLottery(@RequestBody IssueLotteryReq issueLotteryReq){
        return  iIssueService.issueNoLottery(issueLotteryReq,HandleEnum.PrizeStatusEnum.RESET);
    }

    @ApiOperation(value = "彩期撤单", response = Result.class)
    @PostMapping(value = "/revoke/order")
    public Result issueNoRevokeOrder(@RequestBody IssueLotteryReq issueLotteryReq){
        iIssueService.issueNoRevokeOrder(issueLotteryReq);
         return Result.success();
    }
}

