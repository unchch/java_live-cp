package com.bh.live.award.controller;

import com.bh.live.award.service.IIssueService;
import com.bh.live.award.service.IOrderService;
import com.bh.live.award.service.award.IOrder;
import com.bh.live.common.annotation.ParamsNotNull;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.enums.lottery.IssueEnum;
import com.bh.live.common.enums.trade.OrderEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.lottery.Issue;
import com.bh.live.model.trade.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *@author WuLong
 *@date 2019/8/5 19:41
 *@description 开奖
 */
@RestController
@Slf4j
@Api(tags = "开奖")
public class AwardController extends BaseController {
    @Resource
    private IOrderService orderService;
    @Resource
    private IIssueService issueService;
    @Resource
    private IOrder orderHandler;

    @ApiOperation(value = "开奖和重置开奖接口", notes = "开奖")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "issueNo",value = "彩期号",required = true,dataType = "string"),
            @ApiImplicitParam(name = "seedNo",value = "彩种编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "result",value = "彩果",required = true,dataType = "string"),
            @ApiImplicitParam(name = "type",value="类型:1开奖 2重置开奖",required = true,dataType = "int")
    })
    @GetMapping("/lottery")
    public Result award(@ParamsNotNull @RequestParam("issueNo") String issueNo, @ParamsNotNull @RequestParam("seedNo")Integer seedNo
            ,@ParamsNotNull @RequestParam("result") String result,@ParamsNotNull @RequestParam("type") Integer type){
        Issue issue = issueService.selectBySeedIssueNo(issueNo, seedNo);
        if(issue == null){
            log.info("未查询到彩种:{},彩期:{},不存在",seedNo,issueNo);
            throw new ServiceRuntimeException(CodeMsg.E_80017);
        }
        issue.setResult(result);
        HandleEnum.PrizeStatusEnum prizeStatusEnum = HandleEnum.PrizeStatusEnum.getEnumByCode(type);
        if(prizeStatusEnum == null){
            throw new ServiceRuntimeException(CodeMsg.E_80019);
        }
        //彩期状态校验
        IssueEnum.IssueStatusEnum issueStatus = IssueEnum.IssueStatusEnum.getIssueStatus(issue.getStatus());
        //判断彩期状态是否符合开奖动作
        boolean toLottery = (issueStatus == IssueEnum.IssueStatusEnum.WAITING_AWARD && prizeStatusEnum == HandleEnum.PrizeStatusEnum.MANUAL)||
                ((issueStatus == IssueEnum.IssueStatusEnum.AWARD || issueStatus == IssueEnum.IssueStatusEnum.OPENING_AWARD ||
                        issueStatus == IssueEnum.IssueStatusEnum.WAITING_AWARD )&& prizeStatusEnum == HandleEnum.PrizeStatusEnum.RESET);
        if(toLottery){
            List<Integer> status = new ArrayList<>();
            switch (prizeStatusEnum){
                case MANUAL :
                    status.add(OrderEnum.OrderStatusEnum.PENDING_AWARD.getCode());
                    break;
                case RESET:
                    status.add(OrderEnum.OrderStatusEnum.NOT_WINNING.getCode());
                    status.add(OrderEnum.OrderStatusEnum.WINNING.getCode());
                    status.add(OrderEnum.OrderStatusEnum.HAVE_BEEN_AWARDED.getCode());
                    break;
            }
            List<Order> orders = orderService.getOrderByIssueNoSeedNoStatus(issueNo, seedNo, status);
            if(CollectionUtils.isEmpty(orders)){
                log.info("未查询到彩种:{},彩期:{},订单状态:{}",seedNo,issueNo,status.toString());
                throw new ServiceRuntimeException(CodeMsg.E_80015);
            }
            //设置彩期正在开奖中
            issueService.updateStatusBySeedIssueNo(issueNo,seedNo,IssueEnum.IssueStatusEnum.OPENING_AWARD.getCode(),result);
            //执行开奖动作
            orderHandler.distribute(orders,seedNo,issueNo,HandleEnum.LotteryTypeEnum.NUMBER.getCode(),prizeStatusEnum);
        }else{
            //开奖失败后，重置状态为封盘中等待开奖
            issueService.updateStatusBySeedIssueNo(issueNo,seedNo,issueStatus.getCode(),result);
            throw new ServiceRuntimeException(CodeMsg.E_80018.code,String.format(CodeMsg.E_80018.message,prizeStatusEnum.getDesc()+"开奖",issueStatus.getDesc()));
        }
        return Result.success();
    }

    @ApiOperation(value = "根据订单编号开奖", notes = "开奖")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNos",value = "多个订单开奖格式如：A#B",required = true,dataType = "string"),
    })
    @GetMapping("/lottery/orders")
    public Result awardByOrderNos(@RequestParam("orderNos") String orderNos){
        //按订单开奖
        if(CommonUtil.isNotEmpty(orderNos)){
            List<String> orders = Arrays.asList(orderNos.split(SymbolConstants.COMMA));
            return Result.success();
        }else{
            throw new ServiceRuntimeException(CodeMsg.E_80013);
        }
    }

}
