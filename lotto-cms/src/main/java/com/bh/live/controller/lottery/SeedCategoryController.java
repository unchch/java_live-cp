package com.bh.live.controller.lottery;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.service.lottery.ISeedCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 彩种分类 前端控制器
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/seed/category")
@Api(tags = "彩种管理-彩种分类")
public class SeedCategoryController extends BaseController {

    @Autowired
    private ISeedCategoryService seedCategoryService;

    /**
     * 彩种分类及彩种树
     *
     * @return
     */
    @ApiOperation(value = "彩种分类及彩种树", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = SeedCategoryRes.class, code = 0, message = "彩种分类及彩种树")})
    @GetMapping("/tree")
    public Result seedCategoryTree() {
        return Result.success(seedCategoryService.buildSeedCategoryTree());
    }

    /**
     * 彩票大厅图标
     *
     * @return
     */
    @ApiOperation(value = "彩票大厅图标", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = SeedCategoryRes.class, code = 0, message = "彩票大厅图标")})
    @GetMapping("/hall")
    public Result queryLottoHall() {
        return Result.success(seedCategoryService.queryLottoHall());
    }
    /**
     * 彩种分类
     *
     * @return
     */
    @ApiOperation(value = "彩种分类", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = SeedCategoryRes.class, code = 0, message = "彩种分类")})
    @GetMapping("/lottoType")
    public Result queryLottoType() {
        return Result.success(seedCategoryService.queryLottoType());
    }
}

