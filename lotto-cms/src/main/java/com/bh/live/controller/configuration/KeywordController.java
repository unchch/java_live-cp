package com.bh.live.controller.configuration;


import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.cms.Keyword;
import com.bh.live.pojo.req.anchor.LiveSeedReq;
import com.bh.live.pojo.res.anchor.LiveSeedRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.configuration.IKeywordService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 平台全局敏感词过滤表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-31
 */
@RestController
@RequestMapping("/keyword")
@Api(tags = "敏感词管理")
@Slf4j
public class KeywordController {

    @Autowired
    private IKeywordService keywordService;

    /**
     * 敏感词列表
     *
     * @return
     */
    @ApiOperation(value = "敏感词列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Keyword.class, code = 0, message = "敏感词返回前端对象")})
    @GetMapping("/list")
    public Result list(@ApiParam("查询敏感词") @RequestParam(value = "keyword", required = false) String keyword,
                       @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") int pageSize,
                       @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") int pageNum) {
        try {
            TableDataInfo keywords = keywordService.getPageKeywords(keyword, pageSize, pageNum);
            return Result.success(keywords);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
    }

    /**
     * 敏感词新增
     *
     * @return
     */
    @ApiOperation(value = "敏感词新增", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "敏感词新增")})
    @PostMapping("/add")
    public Result add(@ApiParam("敏感词接收对象") @RequestBody Keyword keyword) {
        try {
            keyword.setUpdateTime(new Date());
            keywordService.save(keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }

    /**
     * 敏感词修改
     *
     * @return
     */
    @ApiOperation(value = "敏感词修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "敏感词修改")})
    @PostMapping("/update")
    public Result update(@ApiParam("敏感词接收对象") @RequestBody Keyword keyword) {
        try {
            keyword.setUpdateTime(new Date());
            keywordService.updateById(keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }
}

