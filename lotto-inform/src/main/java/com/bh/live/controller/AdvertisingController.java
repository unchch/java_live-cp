package com.bh.live.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.result.Result;
import com.bh.live.model.announcement.Advertising;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import com.bh.live.service.IAdvertisingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <p>
 * 广告表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/advertising")
@Api(tags = "广告")
public class AdvertisingController {
	
	@Resource
	private IAdvertisingService advertisingService;
	
    @ApiOperation(value = "直播轮播图片", response = Advertising.class)
    @ApiResponses(value = {@ApiResponse(response = AdvertisingReq.class, code = 0, message = "直播轮播图片")})
    @GetMapping("liveCarousel")
    public Result queryLiveCarousel(AdvertisingReq req){
    	
        return  Result.success(advertisingService.queryLiveCarousel(req));
    }
}

