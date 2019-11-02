package com.bh.live.controller.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.cms.AnnoSaveReq;
import com.bh.live.pojo.req.cms.AnnouncementReq;
import com.bh.live.service.announcement.IAnnouncementService;

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
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/announcement")
@Api(tags = "公告管理")
public class AnnouncementController {
	@Autowired
	private IAnnouncementService iAnnouncementService;

	@ApiOperation(value = "公告列表", response = Result.class)
	@ApiResponses(value = { @ApiResponse(response = AnnouncementReq.class, code = 0, message = "公告列表") })
	@PostMapping("/list")
	public Result queryAnnouncementList(@RequestBody AnnouncementReq req) {

		return Result.success(iAnnouncementService.queryAnnouncementList(req));
	}

	@ApiOperation(value = "保存", response = Result.class)
	@ApiResponses(value = { @ApiResponse(response = AnnoSaveReq.class, code = 0, message = "保存") })
	@PostMapping("/saveAnnouncement")
	public Result saveAnnouncement(@RequestBody AnnoSaveReq req) {

		return Result.success(iAnnouncementService.saveAnnouncement(req));
	}

	@ApiOperation(value = "修改", response = Result.class)
	@ApiResponses(value = { @ApiResponse(response = AnnoSaveReq.class, code = 0, message = "修改") })
	@PostMapping("/updateAnnouncement")
	public Result updateAnnouncement(@RequestBody AnnoSaveReq req) {

		return Result.success(iAnnouncementService.updateAnnouncement(req));
	}

	@ApiOperation(value = "删除", response = Result.class)
	@GetMapping("/deleteAnnouncement")
	public Result deleteAnnouncement(
			@ApiParam("公告id") @RequestParam(value = "annId", required = true) Integer annId) {

		return Result.success(iAnnouncementService.deleteAnnouncement(annId));
	}

//	@ApiOperation(value = "新闻资讯", response = Result.class)
//	@ApiResponses(value = { @ApiResponse(response = AnnouncementReq.class, code = 0, message = "新闻资讯") })
//	@GetMapping("/newsInformation")
//	public Result queryNewsInformation() {
//		List<NewsInformationRes> list = iAnnouncementService.queryNewsInformation();
//		return Result.success(list);
//	}

	@ApiOperation(value = "公告详情", response = Result.class)
	@GetMapping("/queryNewsInformationDetailInfo")
	public Result queryNewsInformationDetailInfo(@RequestParam("annId") Integer id) {

		return Result.success(iAnnouncementService.queryNewsInformationDetailInfo(id));
	}

}
