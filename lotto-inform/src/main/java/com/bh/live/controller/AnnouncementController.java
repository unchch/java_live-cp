package com.bh.live.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.result.Result;
import com.bh.live.model.announcement.Announcement;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.pojo.res.cms.AnnouncementRes;
import com.bh.live.service.IAnnouncementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/announcement")
@Api(tags = "公告")
public class AnnouncementController {
	
	@Resource
	private IAnnouncementService announcementService;

	@ApiOperation(value = "新闻资讯", response = Result.class)
	@ApiResponses(value = { @ApiResponse(response = AnnouncementReq.class, code = 0, message = "新闻资讯") })
	@GetMapping("/newsInformation")
	public Result queryNewsInformation(AnnouncementReq req) {
		
		return Result.success(announcementService.queryNewsInformation(req));
	}
	
	
	@ApiOperation(value = "新闻资讯详情", response = AnnouncementRes.class)
	@GetMapping("/queryNewsInformationById")
	public Result queryNewsInformationById(@ApiParam("资讯id")@RequestParam(value="id",required = true)Integer id) {
		
		return Result.success(announcementService.queryNewsInformationDetailById(id));
	}
}

