package com.bh.live.controller.lottery.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.config.OrderUserProfitConfg;
import com.bh.live.model.lottery.config.vo.OrderUserConfigVO;
import com.bh.live.model.lottery.config.vo.OrderUserProfitConfgVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.config.IOrderUserProfitConfgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/order-user-profit-confg")
@Api(tags = "彩种-用户盈利率设置价格等级")
public class OrderUserProfitConfgController extends BaseController {

    @Autowired
    private IOrderUserProfitConfgService orderUserProfitConfgService;

    @PostMapping("/list")
    @ApiOperation(value = "分页查询全部数据")
    public Object list() {
        return orderUserProfitConfgService.list();
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询：用户类别所设置价格等级", response = OrderUserConfigVO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object pageList(@RequestBody OrderUserProfitConfgVO vo) {
        Assert.isNotNull(vo.getLotteryCode(), CodeMsg.E_90003);
        Page<OrderUserProfitConfg> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        QueryWrapper<OrderUserProfitConfg> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OrderUserProfitConfg::getLotteryCode, vo.getLotteryCode()).orderByDesc(OrderUserProfitConfg::getCreateTime);;
        return Result.success(new TableDataInfo(orderUserProfitConfgService.page(page, wrapper)));
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增用户类别所设置价格等级", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(@RequestBody OrderUserProfitConfgVO vo) {
        OrderUserProfitConfg orderUserConfig = new OrderUserProfitConfg();
        BeanUtils.copyProperties(vo,orderUserConfig);
        setInsertProperties(OrderUserProfitConfg.class, orderUserConfig);
        return Result.success(orderUserProfitConfgService.save(orderUserConfig));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户类别所设置价格等级", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(@RequestBody OrderUserProfitConfgVO vo) {
        Assert.isNotNull(vo.getId(), CodeMsg.E_90003);
        OrderUserProfitConfg orderUserConfig = new OrderUserProfitConfg();
        BeanUtils.copyProperties(vo,orderUserConfig);
        setUpdateProperties(OrderUserProfitConfg.class, orderUserConfig);
        return Result.success(orderUserProfitConfgService.updateById(orderUserConfig));
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户类别所设置价格等级")
    @ApiImplicitParam(name = "id", value = "配置id", required = true, dataType = "Integer", paramType = "path")
    public Object deleteById(@PathVariable Integer id) {
        Assert.isNotNull(id, CodeMsg.E_90003);
        return Result.success(orderUserProfitConfgService.removeById(id));
    }
}

