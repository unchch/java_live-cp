package com.bh.live.controller.configuration;


import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.Grade;
import com.bh.live.model.configuration.GiftDivide;
import com.bh.live.model.configuration.GradeDivideInto;
import com.bh.live.pojo.res.anchor.NoTalkRes;
import com.bh.live.service.configuration.IGiftDivideService;
import com.bh.live.service.configuration.IGradeDivideIntoService;
import com.bh.live.utils.JudgeRangeUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 礼物数分成比例表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/divideAdmin")
@Slf4j
@Api(tags = "主播分成管理")
public class DivideAdminController {

    @Autowired
    private IGiftDivideService giftDivideService;

    @Autowired
    private IGradeDivideIntoService gradeDivideIntoService;

    @ApiOperation(value = "等级分成列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GradeDivideInto.class, code = 0, message = "等级分成列表")})
    @GetMapping("/gradeList")
    public Result gradeList() {
        List<GradeDivideInto> list = null;
        try {
            list = gradeDivideIntoService.list();
            list.forEach(item -> {
                item.setDivideInto(item.getDivideInto().multiply(new BigDecimal(100)).stripTrailingZeros());
            });
            //排序后返回
            list = list.stream().sorted(Comparator.comparingInt(GradeDivideInto::getMinLv)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success(list);
    }

    @ApiOperation(value = "礼物数分成列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GiftDivide.class, code = 0, message = "礼物数分成列表")})
    @GetMapping("/giftList")
    public Result giftList() {
        List<GiftDivide> list = null;
        try {
            list = giftDivideService.list();
            list.forEach(item -> {
                item.setDivide(item.getDivide().multiply(new BigDecimal(100)).stripTrailingZeros());
            });
            list = list.stream().sorted(Comparator.comparingInt(GiftDivide::getMaxVal).reversed()).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success(list);
    }

    @ApiOperation(value = "等级分成添加", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "等级分成添加")})
    @PostMapping("/addGradeDivide")
    public Result addGradeDivide(@ApiParam("等级分成对象") @RequestBody GradeDivideInto gradeDivideInto) {
        try {
            gradeDivideInto.setDivideInto(gradeDivideInto.getDivideInto().divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));
            List<GradeDivideInto> list = gradeDivideIntoService.list();
            if (list == null || list.size() == 0) {
                gradeDivideIntoService.save(gradeDivideInto);
            } else {
                list = list.stream().sorted(Comparator.comparingInt(GradeDivideInto::getMinLv)).collect(Collectors.toList());
                //判断输入的等级范围是否正确
                JudgeRangeUtil.judgeRange(gradeDivideInto, list, "divide");
                gradeDivideIntoService.save(gradeDivideInto);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @ApiOperation(value = "礼物数分成添加", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "礼物数分成添加")})
    @PostMapping("/addGiftDivide")
    public Result addGiftDivide(@ApiParam("礼物数分成对象") @RequestBody GiftDivide giftDivide) {
        try {
            if (giftDivide.getMinVal() > giftDivide.getMaxVal()){
                return Result.error("最小值不能比最大值大，请检查您输入的数据是否有误");
            }
            giftDivide.setDivide(giftDivide.getDivide().divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));
            giftDivideService.save(giftDivide);
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof DuplicateKeyException){
                return Result.error("该等级已存在，无需重复添加，修改即可");
            }
            return Result.error(CodeMsg.E_500);
        }
        return Result.success();
    }

    @ApiOperation(value = "礼物数分成修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "礼物数分成修改")})
    @PostMapping("/updateGiftDivide")
    public Result updateGiftDivide(@ApiParam("礼物数分成对象") @RequestBody GiftDivide giftDivide) {
        try {
            if (giftDivide.getMinVal() > giftDivide.getMaxVal()){
                return Result.error("最小值不能比最大值大，请检查您输入的数据是否有误");
            }
            giftDivide.setDivide(giftDivide.getDivide().divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));
            giftDivide.setUpdateTime(new Date());
            giftDivideService.updateById(giftDivide);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success();
    }

    @ApiOperation(value = "等级分成修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "等级分成修改")})
    @PostMapping("/updateGradeDivide")
    public Result updateGradeDivide(@ApiParam("等级分成对象") @RequestBody GradeDivideInto gradeDivideInto) {
        try {
            gradeDivideInto.setDivideInto(gradeDivideInto.getDivideInto().divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));
            gradeDivideInto.setUpdateTime(new Date());
            List<GradeDivideInto> list = gradeDivideIntoService.list();
            list = list.stream().sorted(Comparator.comparingInt(GradeDivideInto::getMinLv)).collect(Collectors.toList());
            //判断输入的等级范围是否正确
            JudgeRangeUtil.judgeRange(gradeDivideInto, list, "divide");
            gradeDivideIntoService.updateById(gradeDivideInto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}

