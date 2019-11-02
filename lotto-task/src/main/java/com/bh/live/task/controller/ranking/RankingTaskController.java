package com.bh.live.task.controller.ranking;


import com.bh.live.task.service.ranking.RankingTaskService;
import com.bh.live.task.util.DateUtis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 排行表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/ranking/anchor")
@Api(tags = "排行榜")
public class RankingTaskController {
    @Resource
    private RankingTaskService rankingListService;

    /**
     * @description 主播按天排行榜
     * @author WuLong
     * @date 2019/8/15 20:43
     * @return void
     */
    @ApiOperation(value = "主播按天排行榜", notes = "排行榜")
    @GetMapping("/day")
    public void realtimeCollectRankingList(){
        rankingListService.collectRankingList(0, DateUtis.getToday(), DateUtis.getCurrent());
    }

    /**
     * @description 统计主播按周排行榜
     * @author WuLong
     * @date 2019/8/15 20:43
     * @return void
     */
    @ApiOperation(value = "主播按周排行榜", notes = "排行榜")
    @GetMapping("/week")
    public void weeklyCollectRankingList() {
        rankingListService.collectRankingList(1, DateUtis.getWeekStart(), DateUtis.getWeekEnd());
    }

    /**
     * @description 统计主播按月排行榜
     * @author WuLong
     * @date 2019/8/15 20:43
     * @return void
     */
    @ApiOperation(value = "主播按月排行榜", notes = "排行榜")
    @GetMapping("/month")
    public void monthlyCollectRankingList() {
        rankingListService.collectRankingList(2, DateUtis.getMonthStart(), DateUtis.getMonthEnd());
    }
}

