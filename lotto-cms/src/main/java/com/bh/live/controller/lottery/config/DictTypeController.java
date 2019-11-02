package com.bh.live.controller.lottery.config;


import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.configuration.DictType;
import com.bh.live.pojo.req.lottery.config.DictTypeReq;
import com.bh.live.service.lottery.config.IDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author WJ
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/dict/type")
@Api(tags = "字典管理-字典类型")
public class DictTypeController {

    @Autowired
    IDictTypeService dictTypeService;

    @ApiOperation(value = "字典类型列表", response = DictType.class)
    @GetMapping(value = "/page",  produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result page (@ApiParam("字典类型code") Integer dictTypeCode,
                        @ApiParam("字典类型名称") String dictTypeName,
                        @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        DictTypeReq dictTypeReq = new DictTypeReq();
        //判断参数
        if(CommonUtil.isNotEmpty(dictTypeCode)) {
            dictTypeReq.setDictTypeCode(dictTypeCode);
        }
        if(CommonUtil.isNotEmpty(dictTypeName)){
            dictTypeReq.setDictTypeName(dictTypeName);
        }
        return Result.success(dictTypeService.selectByParam(dictTypeReq, pageNum, pageSize));
    }

    @ApiOperation(value = "字典类型新增", response = DictType.class)
    @PostMapping(value = "/save")
    public Result save (@RequestBody DictTypeReq dictTypeReq){
       Boolean isTrue = dictTypeService.saveDictTypeAndDicty(dictTypeReq);
        return isTrue ?  Result.success() : Result.error();
    }

    @ApiOperation(value = "字典类型新增", response = DictType.class)
    @PostMapping(value = "/update")
    public Result update (@RequestBody DictTypeReq dictTypeReq){
        Boolean isTrue = dictTypeService.updateDictType(dictTypeReq);
        return isTrue ?  Result.success() : Result.error();
    }
}

