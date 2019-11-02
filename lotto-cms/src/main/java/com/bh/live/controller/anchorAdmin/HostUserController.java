package com.bh.live.controller.anchorAdmin;


import com.bh.live.common.constant.JxlsTemplateConst;
import com.bh.live.common.enums.HostUserStatusEnum;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.jxls.JxlsExcelUtil;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.pojo.req.anchor.HostUserFormReq;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.pojo.res.anchor.HostUserRes;
import com.bh.live.pojo.res.anchor.HostUserResDetail;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.BindingAccountRes;
import com.bh.live.service.anchor.IHostRoomService;
import com.bh.live.service.anchor.IHostUserService;
import com.bh.live.service.user.IBindingAccountService;
import com.bh.live.service.wallet.IWalletService;
import com.bh.live.utils.PropUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主播表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@RestController
@RequestMapping("/hostUser")
@Slf4j
@Api(tags = "主播管理")
public class HostUserController {

    @Autowired
    private IHostUserService hostUserService;

    @Autowired
    private IHostRoomService hostRoomService;

    @Autowired
    private IBindingAccountService accountService;

    /**
     * 主播列表
     *
     * @return
     */
    @ApiOperation(value = "主播列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = HostUserRes.class, code = 0, message = "主播列表")})
    @GetMapping("/list")
    public Result list(HostUserReq req) {
        TableDataInfo hostUserList = null;
        try {
            hostUserList = (TableDataInfo) hostUserService.getHostUserList(req, "page");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success(hostUserList);
    }

    /**
     * 主播列表导出
     *
     * @return
     */
    @ApiOperation(value = "主播列表导出", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "主播列表导出")})
    @GetMapping("/export")
    public void export(HostUserReq req, HttpServletResponse response) {
        try {
            List<HostUserRes> list = (List<HostUserRes>) hostUserService.getHostUserList(req, "export");
            list.forEach(item -> {
                item.setSex(item.getSex() == null ? null : item.getSex().equals("0") ? "女" : "男");
                item.setStatusStr(HostUserStatusEnum.getEnumByCode(item.getStatus()).getName());
                item.setCreateTimeStr(item.getCreateTime() == null ? null : DateUtils.formatDateTime(item.getCreateTime()));
                item.setRecommHomePageStr(item.getRecommHomePage() == 0 ? "否" : "是");
                item.setLastLoginTimeStr(item.getLastLoginTime() == null ? null : DateUtils.formatDateTime(item.getLastLoginTime()));
                item.setIsUsableStr(item.getIsUsable() == 0 ? "禁用" : "启用");
            });
            new JxlsExcelUtil<>(PropUtil.fileExportUrl, list, JxlsTemplateConst.HOST_USER_INFO_TEMPLATE, response, null);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 主播审核
     *
     * @return
     */
    @ApiOperation(value = "主播审核", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "主播审核")})
    @GetMapping("/verify")
    public Result verify(@ApiParam("主键id") @RequestParam("id") Integer id,
                         @ApiParam("审核状态（0拒绝 1通过 2待审核）") @RequestParam("verifyStatus") Integer verifyStatus) {
        try {
            return hostUserService.verifyHostUser(id, verifyStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
    }

    /**
     * 主播详情
     *
     * @return
     */
    @ApiOperation(value = "主播详情", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = HostUserResDetail.class, code = 0, message = "主播详情")})
    @GetMapping("/detail/{userId}")
    public Result detail(@ApiParam("路径参数用户id") @PathVariable("userId") Integer userId) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            //主播基本信息
            HostUserResDetail hostUserDetail = hostUserService.getHostUserDetailByUserId(userId);
            resMap.put("hostUserDetail", hostUserDetail);
            //主播银行卡账户
            List<BindingAccountRes> accounts = accountService.getAccountByUserId(userId);
            resMap.put("accountList", accounts);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success(resMap);
    }

    /**
     * 主播修改
     *
     * @return
     */
    @ApiOperation(value = "主播修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "主播修改")})
    @PostMapping("/update")
    public Result update(@ApiParam("修改的主播信息") @RequestBody HostUserReq req) {
        try {
            hostUserService.updateHostUser(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Result(500, e.getMessage());
        }
        return Result.success();
    }

    /**
     * 主播添加
     *
     * @return
     */
    @ApiOperation(value = "主播添加", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "主播添加")})
    @PostMapping("/addAnchor")
    public Result addAnchor(@ApiParam("添加主播入参信息") @RequestBody HostUserFormReq req) {
        try {
            HostUser hostUser = new HostUser();
            BeanUtils.copyProperties(req, hostUser);
            hostUserService.save(hostUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 生成房间id返回前端
     *
     * @return
     */
    @ApiOperation(value = "生成房间id", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "生成房间id")})
    @GetMapping("/getRoomId")
    public Result getRoomId() {
        try {
            return Result.success(generatorRoomId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    //生成房间id并判断房间号是否存在
    private Integer generatorRoomId() {
        String roomId;
        roomId = DateUtils.parseDateToStr("yyyy", new Date()).substring(2, 4) + (int) ((Math.random() * 9 + 1) * 100000);
        boolean exist = hostRoomService.checkIdExist(Integer.valueOf(roomId));
        if (!exist) {
            generatorRoomId();
        } else {
            return Integer.valueOf(roomId);
        }
        return null;
    }
}

