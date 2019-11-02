package com.bh.live.controller.anchorAdmin;


import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.req.anchor.LiveSeedReq;
import com.bh.live.pojo.res.anchor.LiveSeedRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.ILiveSeedService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * <p>
 * 直播彩种管理 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/liveSeed")
@Api(tags = "直播彩种管理")
@Slf4j
public class LiveSeedController {

    @Autowired
    private ILiveSeedService liveSeedService;

    /**
     * 彩种列表
     *
     * @return
     */
    @ApiOperation(value = "详情", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = LiveSeed.class, code = 0, message = "彩种返回对象")})
    @GetMapping("/detail/{id}")
    public Result detail(@ApiParam("路径参数id") @PathVariable("id") Integer id) {
        try {
            LiveSeed byId = liveSeedService.getById(id);
            return Result.success(byId);
        } catch (Exception e) {
            return Result.error(CodeMsg.E_90004);
        }
    }

    /**
     * 彩种列表
     *
     * @return
     */
    @ApiOperation(value = "列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = LiveSeedRes.class, code = 0, message = "彩种返回对象")})
    @GetMapping("/list")
    public Result list(LiveSeedReq req) {
        try {
            TableDataInfo liveSeeds = liveSeedService.getLiveSeeds(req);
            return Result.success(liveSeeds);
        } catch (Exception e) {
            return Result.error(CodeMsg.E_90004);
        }
    }


    /**
     * 新增彩种
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "新增", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "彩种新增")})
    @PostMapping("/add")
    public Result add(@ApiParam("新增入参") @RequestBody LiveSeedReq req) {
        try {
            liveSeedService.addLiveSeed(req);
        } catch (Exception e) {
            log.error(e.getMessage() + e.getCause());
            if (e instanceof DuplicateKeyException) {
                return new Result(500, "该直播彩种已存在");
            }
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }

    /**
     * 修改彩种
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "彩种修改")})
    @PostMapping("/update")
    public Result update(@ApiParam("修改入参对象") @RequestBody LiveSeedReq req) {
        try {
            liveSeedService.updateLiveSeed(req);
        } catch (Exception e) {
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }

    /**
     * 删除直播彩种
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "直播彩种删除")})
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        try {
            LiveSeed liveSeed = new LiveSeed();
            liveSeed.setLsId(id);
            liveSeed.setIsDel(1);
            liveSeedService.updateById(liveSeed);
        } catch (Exception e) {
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }
}

