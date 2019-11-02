package com.bh.live.trade.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bh.live.common.enums.lottery.PlayEnum;
import com.bh.live.common.enums.lottery.SeedShowAbleEnum;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.*;
import com.bh.live.trade.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lgs
 * @title: BaseValidateService
 * @projectName java_live-cp
 * @description: 公共彩种信息
 * @date 2019/7/17  14:40
 */
@Service
@Slf4j
public class BaseValidateService {

    @Autowired
    private SeedDao seedDao;

    @Autowired
    private PlayDao playDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IssueArgumentDao issueArgumentDao;

    @Autowired
    private PlayBitDao playBitDao;

    @Autowired
    private PlayItemDao playItemDao;

    @Autowired
    private PlayBetDao playBetDao;


    /**
     * 根据lotteryCode 获取彩种信息
     *
     * @param lotteryCode
     * @return
     */
    public Seed findLottery(Integer lotteryCode) {
        QueryWrapper<Seed> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Seed::getSeedNo, lotteryCode);
        return seedDao.selectOne(wrapper);
    }

    /**
     * 根据lotteryCode 获取彩期
     *
     * @param lotteryCode
     * @return
     */
    public Issue findIssue(Integer lotteryCode, String issue) {
        QueryWrapper<Issue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Issue::getSeedNo, lotteryCode).eq(Issue::getIssueNo, issue);
        return issueDao.selectOne(wrapper);
    }

    /**
     * 验证彩种code销售状态
     *
     * @param lotteryCode
     * @return
     */
    protected Result<?> verifySaleStatus(Integer lotteryCode) {
        Assert.isNotNull(lotteryCode, CodeMsg.E_50009);
        return verifySaleStatus(findLottery(lotteryCode));
    }


    /**
     * 验证彩种销售状态
     *
     * @param lottery
     * @return
     */
    protected Result<Seed> verifySaleStatus(Seed lottery) {
        Assert.isNotNull(lottery, CodeMsg.E_50012);
        if (!lottery.getShowAble().equals(SeedShowAbleEnum.DISPLAY.getCode())) {
            throw new ResultJsonException(CodeMsg.E_50011);
        }
        return Result.success(lottery);
    }

    /**
     * 验证彩期code销售状态
     *
     * @param issue
     * @return
     */
    protected Result<Issue> verifyIssueSaleStatus(Integer lotteryCode, String issue) {
        Assert.isNotNull(issue, CodeMsg.E_50010);
        Assert.isNotNull(issue, CodeMsg.E_50009);
        return verifyIssueSaleStatus(findIssue(lotteryCode, issue));
    }


    /**
     * 验证彩期销售状态
     *
     * @param issue
     * @return
     */
    public Result<Issue> verifyIssueSaleStatus(Issue issue) {
        Assert.isNotNull(issue, CodeMsg.E_50013);
        //**销售状态
        if (issue.getStartTime().getTime() > System.currentTimeMillis()) {
            throw new ResultJsonException(CodeMsg.E_50021);
        }
        //**封盘时间
        if (issue.getClosingTime().getTime() < System.currentTimeMillis()) {
            throw new ResultJsonException(CodeMsg.E_50015);
        }
        return Result.success(issue);
    }

    /**
     * 获取彩种玩法
     *
     * @param lotteryCode
     * @param playNo
     * @return
     */
    public Play getLotteryPay(Integer lotteryCode, String playNo) {
        QueryWrapper<Play> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Play::getSeedNo, lotteryCode).eq(Play::getPlayNo, playNo);
        return playDao.selectOne(queryWrapper);
    }

    /**
     * 获取彩种玩法
     *
     * @param lotteryCode
     * @return
     */
    public List<Play> getLotteryPay(Integer lotteryCode) {
        QueryWrapper<Play> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Play::getSeedNo, lotteryCode);
        return playDao.selectList(queryWrapper);
    }

    /**
     * 验证彩种玩法销售状态
     *
     * @param lotteryCode
     * @param playNo
     * @return
     */
    protected Result<Play> verifyLotteryPaySaleStatus(Integer lotteryCode, String playNo) {
        Assert.isNotNull(lotteryCode, CodeMsg.E_50013);
        Assert.isNotNull(playNo, CodeMsg.E_50016);
        return verifyLotteryPaySaleStatus(getLotteryPay(lotteryCode, playNo));
    }

    /**
     * 验证彩种玩法销售状态
     *
     * @param play
     * @return
     */
    protected Result<Play> verifyLotteryPaySaleStatus(Play play) {
        Assert.isNotNull(play, CodeMsg.E_50016);
        //**销售状态
        if (!play.getStatus().equals(PlayEnum.PlayStatusEnum.ON.getCode())) {
            throw new ResultJsonException(CodeMsg.E_50017);
        }
        return Result.success(play);
    }

    /**
     * 根据玩法code 查询投注位
     *
     * @param playNo
     * @return
     */
    public List<PlayBit> getPlayBit(String playNo) {
        Assert.isNotNull(playNo, CodeMsg.E_50016);
        QueryWrapper<PlayBit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayBit::getPlayNo, playNo);
        return playBitDao.selectList(queryWrapper);
    }

    /**
     * 验证投注位是否开放
     *
     * @param bit
     * @return
     */
    protected Result<PlayBit> verifyPlayBitStatus(PlayBit bit) {
        Assert.isNotNull(bit, CodeMsg.E_50018);
        //**销售状态
        if (!bit.getStatus().equals(PlayEnum.PlayStatusEnum.ON.getCode())) {
            throw new ResultJsonException(CodeMsg.E_50019);
        }
        return Result.success(bit);
    }

    /**
     * 验证投注位是否开放
     *
     * @param bitNo
     * @return
     */
    protected Result<PlayBit> verifyPlayBitStatus(String bitNo) {
        Assert.isNotNull(bitNo, CodeMsg.E_50018);
        //**销售状态
        QueryWrapper<PlayBit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayBit::getBitNo, bitNo);
        return verifyPlayBitStatus(playBitDao.selectOne(queryWrapper));
    }

    /**
     * 获取投注项表
     *
     * @param bitNo
     * @return
     */
    protected List<PlayItem> getPlayItem(String bitNo) {
        Assert.isNotNull(bitNo, CodeMsg.E_50018);
        QueryWrapper<PlayItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayItem::getBitNo, bitNo);
        return playItemDao.selectList(queryWrapper);
    }

    /**
     * 验证投注项表是否开放
     *
     * @param itemNo
     * @return
     */
    protected Result<PlayItem> verifyPlayItemStatus(String itemNo) {
        Assert.isNotNull(itemNo, CodeMsg.E_50018);
        QueryWrapper<PlayItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayItem::getItemNo, itemNo);
        //**销售状态
        return verifyPlayItemStatus(playItemDao.selectOne(queryWrapper));
    }

    /**
     * 验证投注项表是否开放
     *
     * @param item
     * @return
     */
    protected Result<PlayItem> verifyPlayItemStatus(PlayItem item) {
        Assert.isNotNull(item, CodeMsg.E_50018);
        //**销售状态
        if (!item.getStatus().equals(PlayEnum.PlayStatusEnum.ON.getCode())) {
            throw new ResultJsonException(CodeMsg.E_50019);
        }
        return Result.success(item);
    }


    /**
     * 获取赔率值
     *
     * @param playNo
     * @return
     */
    protected List<PlayBet> getPlayBets(String playNo) {
        Assert.isNotNull(playNo, CodeMsg.E_50018);
        QueryWrapper<PlayBet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayBet::getPlayNo, playNo);
        List<PlayBet> bets = playBetDao.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(bets)){
            throw new ResultJsonException(CodeMsg.E_50018);
        }
        return bets;
    }


    /**
     * 获取赔率值
     *
     * @param playNo
     * @return
     */
    protected PlayBet getPlayBets(String playNo,String itemNo) {
        Assert.isNotNull(playNo, CodeMsg.E_50018);
        Assert.isNotNull(itemNo, CodeMsg.E_50018);
        List<PlayBet> playBets = getPlayBets(playNo);
        return playBets.stream().filter(item->(item.getItemNo().equals(itemNo))).findAny().orElse(null);
    }
}
