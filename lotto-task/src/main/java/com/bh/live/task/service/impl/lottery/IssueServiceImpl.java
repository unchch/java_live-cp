package com.bh.live.task.service.impl.lottery;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.enums.lottery.IssueEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.lottery.Issue;
import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.pojo.res.inform.IssueEntityRes;
import com.bh.live.task.dao.lottery.IssueDao;
import com.bh.live.task.service.lottery.IIssueArgumentService;
import com.bh.live.task.service.lottery.IIssueService;
import com.bh.live.task.service.lottery.ISeedService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 彩期 服务实现类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class IssueServiceImpl extends ServiceImpl<IssueDao, Issue> implements IIssueService {

    @Autowired
    private ISeedService seedService;

    @Autowired
    private IIssueArgumentService issueArgumentService;

    @Override
    public void createNewIssue() {
        //1.查询所有彩种
        List<Seed> seedList = seedService.selectAllSeed();
        //2.查询彩期生成参数
        Map<Integer, List<IssueArgument>> argsMap = issueArgumentService.selectAllIssueArgumentToMap();
        //3.查询最后期
        List<Issue> lastIssues = baseMapper.selectLastCreateIssue();
        Map<Integer, List<Issue>> issueMap = new HashMap<>(lastIssues.size());
        if (CommonUtil.isNotEmpty(lastIssues)) {
            issueMap = lastIssues.stream().collect(Collectors.groupingBy(Issue::getSeedNo));
        }

        for (Seed seed : seedList) {
            if (CommonUtil.isEmpty(argsMap.get(seed.getSeedNo()))) {
                log.warn("createIssue exception. 彩种分类:(" + seed.getSeedNo() + ") 未找到彩期参数配置信息 -> issue_argument.");
                continue;
            }
            List<IssueArgument> arguments = argsMap.get(seed.getSeedNo()).stream()
                    .sorted(Comparator.comparing(IssueArgument::getId))
                    .collect(Collectors.toList());
            List<Issue> issues = issueMap.get(seed.getSeedNo());
            Issue lastIssue = CommonUtil.isNotEmpty(issues) ? issues.get(0) : null;

            // 检查是否需要生成彩期
            int days = DateUtils.differentDays(DateUtils.getSqlDate(DateUtils.getNowDate()), seed.getCreatedDate());
            // 只提前生成2天以内（包括2天）的彩期
            if (days >= 2) {
                log.warn("createIssue continue. seedNo:{} days:{}, createDate:{}", seed.getSeedNo(), days, seed.getCreatedDate());
                continue;
            }

            try {
                createIssueBySeed(seed, arguments, lastIssue);
            } catch (Exception e) {
                log.error("createIssue exception. seedName:{} cause:{} message:{}", seed.getSeedName(), e.getCause(), e.getMessage());
                continue;
            }
        }
    }

    /**
     * 创建彩期
     *
     * @param seed      彩种
     * @param arguments 彩期生成规则
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void createIssueBySeed(Seed seed, List<IssueArgument> arguments, Issue lastIssue) {
        List<Issue> issues = Lists.newArrayList();
        arguments.forEach((args) -> {
            // 统一生成彩期编号
            issues.addAll(createIssue(seed, args, lastIssue));
        });
        if (CommonUtil.isNotEmpty(issues)) {
            int res = baseMapper.batchInsertIssue(issues);
            if (!seedService.updateById(seed)) {
                log.info("create issue. [ERROR] callback seed. seedNo:{} date:{}", seed.getSeedNo(), DateUtils.getTime());
            }
            log.info("create issue. [SUCCESS] seedNo:{} date:{} res:{}", seed.getSeedNo(), DateUtils.getTime(), res);
            return;
        }
        throw new ServiceRuntimeException(null, null);
    }

    /**
     * 创建彩期期号
     *
     * @param seed
     * @param args
     * @return
     */
    private List<Issue> createIssue(Seed seed, IssueArgument args, Issue lastIssue) {
        List<Issue> issues = Lists.newArrayList();

        String exp = args.getIssueExp();
        String ymd = exp.substring(0, exp.indexOf("{"));
        String len = exp.substring(exp.indexOf("{") + 1, exp.indexOf("}"));

        Date date = createDateProcess(seed.getCreatedDate(), args.getBeginTime(), args.getEndTime());

        // 彩期号是否每天都重新从1开始计算,0否，1是
        boolean withZero = args.getIssueNoIsCountEveryday().compareTo(LotteryConstants.VALUE_0) == 0;

        for (int i = 0; i < args.getIssueCount(); i++) {
            // 开盘时间
            Date startTime;

            // 获取目前的最后一期
            if (CommonUtil.isNotEmpty(issues)) {
                lastIssue = issues.get(i - 1);
            }

            // 正常开盘日期
            Date normalStartTime = DateUtils.getIssueStartTime(args.getBeginTime(), date, args.getIssueTimeInterval(), i);
            if (CommonUtil.isEmpty(lastIssue)) {
                startTime = normalStartTime;
                lastIssue = new Issue().setIssueNo(seed.getReferIssueNo());
            } else {
                startTime = lastIssue.getClosingTime();
            }
            // 开奖时间
            Date openTime = DateUtils.getIssueOpenTime(normalStartTime, args.getIssueTimeInterval());
            // 封盘时间
            Date closingTime = DateUtils.getIssueClosingTime(openTime, args.getClosingSeconds());

            Issue issue = new Issue()
                    .setSeedNo(seed.getSeedNo())
                    .setStartTime(startTime)
                    .setClosingTime(closingTime)
                    .setEndTime(closingTime)
                    .setOpenTime(openTime)
                    .setClosingSeconds(args.getClosingSeconds())
                    .setLifetime(args.getIssueTimeInterval())
                    .setPreIssue(seed.getReferIssueNo())
                    .setPeriodIssue(i + 1);
            // 生成彩期编号
            if (withZero) {
                // 不从0开始
                if (CommonUtil.isEmpty(ymd)) {
                    int issueNo = Integer.valueOf(lastIssue.getIssueNo()) + args.getUpIssueCount();
                    issue.setIssueNo(String.valueOf(issueNo));
                } else {
                    int no = Integer.valueOf(lastIssue.getIssueNo().substring(ymd.length())) + args.getUpIssueCount();
                    String issueNo = DateUtils.parseDateToStr(ymd, date) + String.format("%0" + len + "d", no);
                    issue.setIssueNo(issueNo);
                }
            } else {
                // 从0开始
                String issueNo = DateUtils.parseDateToStr(ymd, date) + String.format("%0" + len + "d", i + args.getUpIssueCount());
                issue.setIssueNo(issueNo);
            }
            // 回写当前期为最后一期
            seed.setReferIssueNo(issue.getIssueNo());
            seed.setCreatedDate(issue.getStartTime());
            lastIssue = issue;

            issues.add(issue);
        }
        return issues;
    }

    /**
     * 获取每个彩种最后的开奖时间
     *
     * @return
     */
    public Date createDateProcess(Date createdDate, Date BeginTime, Date EndTime) {
        // 判断时间是否需要加一天
        if (DateUtils.timeHHMMDD(BeginTime, EndTime)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createdDate);
            calendar.add(Calendar.DATE, 1);
            return calendar.getTime();
        }
        return createdDate;
    }

    /**
     * 修改彩期状态
     * status:last修改为2，current修改为1
     * sale_state:last修改2，current修改为1
     * status:彩期状态 0:预售中,1:销售中,2:封盘中等待开奖,3:开奖中,4:已开奖等待派奖,5:已派奖,6:过期未开奖
     * sale_state:销售状态（0：待开售  1：销售中 2：封盘中 3：已截止 4：已停售）
     *
     * @param res
     * @return
     * @author yq.
     */
    @Override
    public void updateIssueStatus(IssueCurrentRes res) {
        log.info("updateIssueStatus req:{}", res);
        try {
            List<Issue> issues = Lists.newArrayList();
            // 修改上一期状态
            IssueEntityRes last = res.getLast();
            Issue lastIssue = new Issue()
                    .setId(last.getId())
                    .setStatus(IssueEnum.IssueStatusEnum.WAITING_AWARD.getCode())
                    .setSaleState(IssueEnum.IssueSaleStateEnum.SEALING.getCode());
            issues.add(lastIssue);
            // 修改当前期状态
            IssueEntityRes current = res.getCurrent();
            Issue currentIssue = new Issue()
                    .setId(current.getId())
                    .setStatus(IssueEnum.IssueStatusEnum.SALE.getCode())
                    .setSaleState(IssueEnum.IssueSaleStateEnum.SALE.getCode());
            issues.add(currentIssue);
            if (CommonUtil.isEmpty(issues)) {
                log.info("updateIssueStatus exception. issues:{}", issues);
                return;
            }
            super.updateBatchById(issues);
        } catch (Exception e) {
            log.error("updateIssueStatus exception. cause:{}, message:{}", e.getCause(), e.getMessage());
        }
    }
}