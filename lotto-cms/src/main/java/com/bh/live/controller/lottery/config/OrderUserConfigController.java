package com.bh.live.controller.lottery.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.config.OrderUserConfig;
import com.bh.live.model.lottery.config.vo.OrderUserConfigVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.config.IOrderUserConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户类别所设置价格等级 前端控制器
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/order-user-config")
@Api(tags = "竞猜管理：彩种配置")
@Slf4j
public class OrderUserConfigController extends BaseController {

    @Autowired
    private IOrderUserConfigService orderUserConfigService;

    @PostMapping("/list")
    @ApiOperation(value = "查询全部数据", response = OrderUserConfigVO.class)
    public Object list() {
        return orderUserConfigService.list();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询用户类别设置价格等级", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderUserConfigVO.class)
    public Object pageList(@RequestBody OrderUserConfigVO vo) {
        Assert.isNotNull(vo.getLotteryCode(), CodeMsg.E_90003);
        Page<OrderUserConfig> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        QueryWrapper<OrderUserConfig> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OrderUserConfig::getLotteryCode, vo.getLotteryCode()).orderByDesc(OrderUserConfig::getCreateTime);
        return Result.success(new TableDataInfo(orderUserConfigService.page(page, wrapper)));
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增用户类别所设置价格等级", response = Result.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(@RequestBody OrderUserConfigVO vo) {
        OrderUserConfig orderUserConfig = new OrderUserConfig();
        BeanUtils.copyProperties(vo,orderUserConfig);
        setInsertProperties(OrderUserConfig.class, orderUserConfig);
        return Result.success(orderUserConfigService.save(orderUserConfig));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户类别所设置价格等级", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(@RequestBody OrderUserConfigVO vo) {
        Assert.isNotNull(vo.getId(), CodeMsg.E_90003);
        OrderUserConfig orderUserConfig = new OrderUserConfig();
        BeanUtils.copyProperties(vo,orderUserConfig);
        setUpdateProperties(OrderUserConfig.class, orderUserConfig);
        return Result.success(orderUserConfigService.updateById(orderUserConfig));
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户类别所设置价格等级")
    @ApiImplicitParam(name = "id", value = "配置id", required = true, dataType = "Integer", paramType = "path")
    public Object deleteById(@PathVariable Integer id) {
        Assert.isNotNull(id, CodeMsg.E_90003);
        return Result.success(orderUserConfigService.removeById(id));
    }


}

