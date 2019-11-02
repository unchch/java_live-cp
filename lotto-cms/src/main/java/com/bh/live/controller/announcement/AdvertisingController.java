package com.bh.live.controller.announcement;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.cms.AdverSaveReq;
import com.bh.live.pojo.req.cms.AdvertisingReq;
import com.bh.live.pojo.res.cms.AdvertisingRes;
import com.bh.live.service.announcement.IAdvertisingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * <p>
 * 广告表 前端控制器
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/advertising")
@Api(tags = "广告管理")
public class AdvertisingController extends BaseController {
    @Autowired
    private IAdvertisingService iAdvertisingService;

    @ApiOperation(value = "列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AdvertisingReq.class, code = 0, message = "广告列表")})
    @PostMapping("/list")
    public Result queryAdvertList(@RequestBody AdvertisingReq req){
//       List<AdvertisingRes> list =iAdvertisingService.queryAdvertisList(advertisingReq);

       return  Result.success(iAdvertisingService.queryAdvertisList(req));
    }
    
//    @ApiOperation(value = "修改状态", response = Result.class)
//    @ApiResponses(value = {@ApiResponse(response = AdverSaveReq.class, code = 0, message = "修改状态")})
//    @PostMapping("/updateRelease")
//    public Result updateRelease(AdverSaveReq req){
//
//        return  Result.success(iAdvertisingService.updateRelease(req));
//    }

    @ApiOperation(value = "保存广告", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AdverSaveReq.class, code = 0, message = "保存")})
    @PostMapping("/saveAdvertis")
    public Result saveAdvertis( @RequestBody AdverSaveReq req){

    	return Result.success(iAdvertisingService.saveAdvertis(req));
    }
    
    @ApiOperation(value = "修改广告", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AdverSaveReq.class, code = 0, message = "修改广告")})
    @PostMapping("/updateAdvertis")
    public Result updateAdvertis(@RequestBody AdverSaveReq req){
    	
        return Result.success(iAdvertisingService.updateAdvertis(req));
    }
    
	@ApiOperation(value = "删除状态", response = Result.class)
	@ApiResponses(value = { @ApiResponse(response = AdvertisingRes.class, code = 0, message = "删除") })
	@GetMapping("/deleteAdvertis")
	public Result deleteAdvertis(
			@ApiParam("广告id") @RequestParam(value = "adverId", required = true) Integer adverId) {
		
		return Result.success(iAdvertisingService.deleteAdvertis(adverId));
	}
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }
    
//    @ApiOperation(value = "直播轮播图片", response = Result.class)
//    @ApiResponses(value = {@ApiResponse(response = AdvertisingRes.class, code = 0, message = "直播轮播图片")})
//    @GetMapping("liveCarousel")
//    public Result queryLiveCarousel(){
//        List<String> list =iAdvertisingService.queryLiveCarousel();
//        return  Result.success(list);
//    }
    
  @ApiOperation(value = "广告详情", response = Result.class)
  @GetMapping("queryLiveCarouselDetailInfo")
  public Result queryLiveCarouselDetailInfo(@RequestParam("adverId") Integer id){
	  
      return  Result.success(iAdvertisingService.queryLiveCarouselDetailInfo(id));
  }

}


