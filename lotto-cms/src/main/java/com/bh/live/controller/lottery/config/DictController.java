package com.bh.live.controller.lottery.config;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.configuration.Dict;
import com.bh.live.pojo.req.lottery.config.DictReq;
import com.bh.live.service.lottery.config.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典信息表 前端控制器
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "字典管理-字典信息")
public class DictController extends BaseController {

    @Autowired
    IDictService iDictService;

    @ApiOperation(value = "字典信息列表", response = Dict.class)
    @GetMapping(value = "/page",  produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result page(DictReq  dictReq){
        return Result.success(iDictService.selectByParam(dictReq));
    }

    @ApiOperation(value = "字典信息新增", response = Result.class)
    @PostMapping("/save")
    public Result saveDict(@RequestBody List<DictReq> dictReqs){
       boolean isOk = iDictService.saveDict(dictReqs);
       return  isOk ? Result.success() : Result.error();
    }

    @ApiOperation(value = "字典信息修改", response = Result.class)
    @PostMapping("/update")
    public Result updateDict(@RequestBody DictReq dictReq){
        boolean isOk =  iDictService.updateDict(dictReq);
        return  isOk ? Result.success() : Result.error();
    }

    @ApiOperation(value = "字典信息", response = Result.class)
    @GetMapping(value = "/get/typeCode", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result getByTypeCode(@ApiParam("字典类型") @RequestParam Integer typeCode){
        if(CommonUtil.isNotEmpty(typeCode)) {
            return Result.success(iDictService.getByTypeCodes(typeCode.toString()));
        }
        return Result.error();
    }
}

