package com.bh.live.controller.lottery.config;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.model.lottery.config.OrderUserConfig;
import com.bh.live.model.lottery.config.OrderUserProfitConfg;
import com.bh.live.model.lottery.config.vo.OrderConfigVO;
import com.bh.live.pojo.res.lottery.config.OrderConfigRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.ISeedService;
import com.bh.live.service.lottery.config.IOrderConfigService;
import com.bh.live.service.lottery.config.IOrderUserConfigService;
import com.bh.live.service.lottery.config.IOrderUserProfitConfgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 彩种订单配置表 前端控制器
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/lotto/order-config")
@Api(tags = "竞猜管理：彩种配置")
@Slf4j
public class OrderConfigController extends BaseController {

    @Autowired
    private IOrderConfigService orderConfigService;

    @Autowired
    private IOrderUserConfigService orderUserConfigService;

    @Autowired
    private IOrderUserProfitConfgService orderUserProfitConfgService;

    @Autowired
    private ISeedService seedService;

    @PostMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ApiOperation(value = "查询全部数据", response = OrderConfigVO.class)
    public Object list() {
        return Result.success(orderConfigService.list());
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询数据", response = OrderConfigVO.class)
    public Object pageList(@RequestBody OrderConfigVO vo) {
        TableDataInfo result = orderConfigService.selectOrderConfig(vo);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询页面", response = OrderConfigRes.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "int", paramType = "path")
    public Object selectConfigById(@PathVariable("id") Integer id) {
        OrderConfigVO vo = new OrderConfigVO();
        vo.setId(id);
        TableDataInfo tableDataInfo = orderConfigService.selectOrderConfig(vo);
        if (CollectionUtil.isEmpty(tableDataInfo.getList())) {
            return Result.error();
        }
        OrderConfigRes res = (OrderConfigRes) tableDataInfo.getList().get(0);
        return Result.success(res);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增彩种订单配置", response = Result.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(@RequestBody OrderConfigVO vo) {
        Assert.collectIsNotNull(vo.getGradeLimitList(), CodeMsg.E_100002);
        Assert.collectIsNotNull(vo.getProfitLimitList(), CodeMsg.E_100003);
        Assert.isNotNull(vo.getUserGroup(), CodeMsg.E_100004);
        QueryWrapper<OrderConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderConfig::getSeedNo, vo.getSeedNo());
        OrderConfig config = orderConfigService.getOne(queryWrapper);
        Assert.isNull(config, CodeMsg.E_100005);
        OrderConfig orderConfig = new OrderConfig();
        BeanUtils.copyProperties(vo, orderConfig);
        setInsertProperties(OrderConfig.class, orderConfig);
        updateConfig(vo);
        setUserPlan(vo, orderConfig);
        orderConfig.setPrizeOnOff(vo.getPrizeOnOff() ? 1 : 0);
        return Result.success(orderConfigService.save(orderConfig));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改彩种订单配置", response = Result.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(@RequestBody OrderConfigVO vo) {
        Assert.isNotNull(vo.getId(), CodeMsg.E_90003);
        Assert.collectIsNotNull(vo.getGradeLimitList(), CodeMsg.E_100002);
        Assert.collectIsNotNull(vo.getProfitLimitList(), CodeMsg.E_100003);
        Assert.isNotNull(vo.getUserGroup(), CodeMsg.E_100004);
        UpdateWrapper<OrderConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", vo.getId());
        OrderConfig orderConfig = new OrderConfig();
        BeanUtils.copyProperties(vo, orderConfig);
        orderConfig.setCreateTime(new Date());
        orderConfig.setUpdateTime(new Date());
        setUpdateProperties(OrderConfig.class, orderConfig);
        orderConfig.setPrizeOnOff(vo.getPrizeOnOff() ? 1 : 0);
        updateConfig(vo);
        setUserPlan(vo, orderConfig);
        return Result.success(orderConfigService.update(orderConfig, updateWrapper));
    }

    public void setUserPlan(OrderConfigVO vo, OrderConfig orderConfig) {
        final String[] userPlan = {""};
        vo.getGradeLimitList().forEach(v -> {
            if (ObjectUtil.isNotEmpty(v.getUserType())) {
                userPlan[0] = StrUtil.join(SymbolConstants.COMMA, v.getUserType(), userPlan[0]);
            }
        });
        if (userPlan[0].contains(SymbolConstants.COMMA)) {
            userPlan[0] = StrUtil.sub(userPlan[0], 0, userPlan[0].length() - 1);
        }
        orderConfig.setUserPlan(userPlan[0]);
        Assert.isTrue(userPlan.length > 0, CodeMsg.E_90003);
    }

    public void updateConfig(OrderConfigVO vo) {
        QueryWrapper<OrderUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderUserConfig::getLotteryCode, vo.getSeedNo());
        orderUserConfigService.remove(queryWrapper);
        QueryWrapper<OrderUserProfitConfg> orderUserProfitConfgQueryWrapper = new QueryWrapper<>();
        orderUserProfitConfgQueryWrapper.lambda().eq(OrderUserProfitConfg::getLotteryCode, vo.getSeedNo());
        orderUserProfitConfgService.remove(orderUserProfitConfgQueryWrapper);
        vo.getGradeLimitList().forEach(v -> {
            OrderUserConfig orderUserConfig = new OrderUserConfig();
            BeanUtils.copyProperties(v, orderUserConfig);
            setInsertProperties(OrderUserConfig.class, orderUserConfig);
            orderUserConfigService.saveOrUpdate(orderUserConfig);
        });

        vo.getProfitLimitList().forEach(v -> {
            OrderUserProfitConfg orderUserProfitConfg = new OrderUserProfitConfg();
            BeanUtils.copyProperties(v, orderUserProfitConfg);
            setInsertProperties(OrderUserProfitConfg.class, orderUserProfitConfg);
            orderUserProfitConfgService.saveOrUpdate(orderUserProfitConfg);
        });
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除彩种订单配置")
    @ApiImplicitParam(name = "id", value = "配置id", required = true, dataType = "int", paramType = "path")
    public Object deleteById(@PathVariable Integer id) {
        Assert.isNotNull(id, CodeMsg.E_90003);
        OrderConfig orderConfig = orderConfigService.getById(id);
        Assert.isNotNull(orderConfig, CodeMsg.E_90003);
        QueryWrapper<OrderUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderUserConfig::getLotteryCode, orderConfig.getSeedNo());
        orderUserConfigService.remove(queryWrapper);
        QueryWrapper<OrderUserProfitConfg> orderUserProfitConfgQueryWrapper = new QueryWrapper<>();
        orderUserProfitConfgQueryWrapper.lambda().eq(OrderUserProfitConfg::getLotteryCode, orderConfig.getSeedNo());
        orderUserProfitConfgService.remove(orderUserProfitConfgQueryWrapper);
        return Result.success(orderConfigService.removeById(id));
    }

}

