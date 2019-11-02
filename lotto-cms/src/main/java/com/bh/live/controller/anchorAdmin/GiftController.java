package com.bh.live.controller.anchorAdmin;


import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.anchor.GiftReq;
import com.bh.live.service.anchor.IGiftService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 礼物表 前端控制器
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/gift")
@Api(tags ="礼物管理")
public class GiftController {
    @Autowired
    IGiftService giftService;

    /**
     * 新增礼物
     *
     * @param giftReq
     * @return
     */
    @ApiOperation(value = "新增礼物", notes = "新增礼物", response = Result.class)
    @PostMapping("/add")
    public Result add(@RequestBody @ApiParam(name = "礼物", value = "礼物对象") GiftReq giftReq) {
        return Result.success(giftService.addGift(giftReq));
    }

    /**
     * 详情礼物
     *
     * @param giftId
     * @return
     */
    @ApiOperation(value = "礼物详情", notes = "礼物详情", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GiftReq.class, code = 0, message = "礼物详情")})
    @ApiParam(name = "礼物", value = "礼物详情ID")
    @GetMapping("/detail")
    public Result detail( @RequestParam("giftId")  Integer giftId) {
        return Result.success(giftService.getGiftById(giftId));
    }

    /**
     * 修改礼物
     *
     * @param giftReq
     * @return
     */
    @ApiOperation(value = "修改礼物", notes = "修改礼物", response = Result.class)
    @PostMapping("/update")
    public Result update(@RequestBody @ApiParam(name = "礼物", value = "礼物对象") GiftReq giftReq) {
        return Result.success(giftService.updateGift(giftReq));
    }

    /**
     * 删除礼物
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除礼物", notes = "删除礼物", response = Result.class)
    @PostMapping("/delete")
    public Result delete(@RequestBody @ApiParam(name = "礼物", value = "礼物ID") List<Integer> ids) {
        return Result.success(giftService.deleteGift(ids));
    }

    /**
     * 礼物列表
     *
     * @param giftReq
     * @return
     */
    @ApiOperation(value = "礼物分页列表", notes = "礼物分页列表查询", response = Result.class)
    @PostMapping("/getPage")
    public Result getPage(@RequestBody @ApiParam(name = "礼物", value = "礼物对象") GiftReq giftReq) {
        return Result.success(giftService.getGifts(giftReq));
    }

}

