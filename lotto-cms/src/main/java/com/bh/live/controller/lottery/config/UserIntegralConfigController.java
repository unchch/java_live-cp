package com.bh.live.controller.lottery.config;


import com.bh.live.common.result.Result;
import com.bh.live.model.configuration.UserIntegralConfig;
import com.bh.live.pojo.req.lottery.config.UserIntegralConfigReq;
import com.bh.live.service.lottery.config.IDictService;
import com.bh.live.service.lottery.config.IUserIntegralConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  用户积分规则配置 前端控制器
 * </p>
 *
 * @author WJ
 * @since 2019-08-13
 */
@RestController
@RequestMapping("/user/integral/config")
@Api(tags = "用户积分规则配置")
public class UserIntegralConfigController {

    @Autowired
    IUserIntegralConfigService  configService;

    @Autowired
    IDictService iDictService;

    @ApiOperation(value = "积分规则列表", response = UserIntegralConfig.class)
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result page(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return Result.success(configService.selectByParam(pageNum, pageSize));
    }

    @ApiOperation(value = "新增积分规则", response = UserIntegralConfig.class)
    @PostMapping("/save")
    public Result save(@RequestBody UserIntegralConfigReq configReq){
        Boolean  isTrue = configService.saveUserConifg(configReq);
        return isTrue ? Result.success() : Result.error();
    }

    @ApiOperation(value = "修改积分规则", response = UserIntegralConfig.class)
    @PostMapping("/update")
    public Result update(@RequestBody UserIntegralConfigReq configReq){
        Boolean  isTrue = configService.updateUserConfig(configReq);
        return isTrue ? Result.success() : Result.error();
    }

    @ApiOperation(value = "删除积分规则", response = Boolean.class)
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        Boolean  isTrue = configService.deleteConfig(id);
        return isTrue ? Result.success() : Result.error();
    }


    @ApiOperation(value = "获得用户积分字典", response = UserIntegralConfig.class)
    @GetMapping("/dictInfo/list")
    public Result getUserIntegralDictInfoList(){
        String typeCodes = "10011,10012";
        return  Result.success(iDictService.getByTypeCodes(typeCodes));
    }

}

