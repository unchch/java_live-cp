package com.bh.live.controller.anchorAdmin;

import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.Grade;
import com.bh.live.pojo.req.anchor.GradeReq;
import com.bh.live.pojo.res.anchor.GradeRes;
import com.bh.live.service.anchor.IGradeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Morphon
 * @date 2019/7/25 15:20
 * @desc 主播等级管理控制器
 * @Version 1.0
 */
@RestController
@RequestMapping("/anchorLevel")
@Api(tags = "主播等级管理")
@Slf4j
public class AnchorLevelController {

    @Autowired
    private IGradeService gradeService;

    /**
     * 等级列表
     *
     * @return
     */
    @ApiOperation(value = "列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GradeRes.class, code = 0, message = "等级列表")})
    @GetMapping("/list")
    public Result list() {
        try {
            List<GradeRes> grades = (List<GradeRes>) gradeService.getGrades("controlCall");
            return Result.success(grades);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
    }

    /**
     * 新增等级
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "新增", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "等级新增")})
    @PostMapping("/add")
    public Result add(@RequestBody GradeReq req) {
        try {
            gradeService.addGradeConf(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 修改等级
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "等级修改")})
    @ApiParam(name = "修改", value = "修改对象")
    @PostMapping("/update")
    public Result update(@RequestBody GradeReq req) {
        try {
            gradeService.updateGrade(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 删除等级
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "等级删除")})
    @GetMapping("/delete")
    public Result delete(@ApiParam("主键id") Integer id) {
        try {
            Grade grade = new Grade();
            grade.setId(id);
            grade.setIsDel(1);
            gradeService.updateById(grade);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }
}
