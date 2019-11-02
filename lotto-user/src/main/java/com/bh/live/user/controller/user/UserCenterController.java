package com.bh.live.user.controller.user;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.cms.Keyword;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.req.user.RestPasswordReq;
import com.bh.live.pojo.res.user.UserBaseInfoRes;
import com.bh.live.user.service.IAnchorRoomService;
import com.bh.live.user.service.IHostUserService;
import com.bh.live.user.service.IKeywordService;
import com.bh.live.user.service.IUserCenterService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @Description: 用户个人中心信息类
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/31 10:29
 */
@RestController
@RequestMapping("/center")
@Slf4j
@Api(tags = "用户个人中心信息")
public class UserCenterController extends BaseController {

    @Autowired
    private IUserCenterService userCenterService;

    @Autowired
    private IKeywordService keywordService;

    @Autowired
    private IAnchorRoomService anchorRoomService;

    @Autowired
    private  IHostUserService hostUserService;


    @ApiOperation(value = "用户信息", notes = "用户基础信息", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = UserBaseInfoRes.class, code = 0, message = "用户基础信息")})
    @GetMapping("/info")
    public Result getUserInfo() {
        // 判断登录
        LiveUser user = getHeaderLiveUser();
        if (user == null) {
            return Result.error(CodeMsg.E_20001);
        }
        return Result.success(userCenterService.queryUserById(user.getId()));
    }

    @ApiOperation(value = "用户信息", notes = "用户详情", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = UserBaseInfoRes.class, code = 0, message = "用户基础信息")})
    @GetMapping("/infoById")
    public Result geInfoById(@ApiParam("用户Id") @RequestParam("targetId") Integer targetId) {
        Integer userId = null;
        // 判断登录
        LiveUser user = getHeaderLiveUser();
        if (user != null) {
            userId = user.getId();
        }
        return Result.success(userCenterService.queryUserInfo(userId, targetId));
    }

    @ApiOperation(value = "修改用户昵称", notes = "修改用户昵称", response = Result.class)
    @GetMapping("/updateNickname")
    public Result update(@ApiParam("用户昵称") @RequestParam("nickname") String nickname,
                         @ApiParam("昵称修改次数") @RequestParam("count") Integer count) {
        LiveUser user = getHeaderLiveUser();
        if (user == null) {
            return Result.error(CodeMsg.E_20001);
        }
        // 过滤敏感词
        boolean flag = excludeKeyword(nickname);
        if (flag) {
            return Result.error(CodeMsg.E_10030);
        }
        try {
            userCenterService.updateNickname(user.getId(), nickname, count);
            if (user.getIsAnchor()==1){
                anchorRoomService.updateUserNickname(nickname,user.getId());
            }
            HostUserReq req=new HostUserReq();
            req.setUserId(user.getId());
            req.setUsername(nickname);
            hostUserService.updateHostUser(req);
        }catch (Exception ex){
            return  Result.error(CodeMsg.E_500);
        }
        return Result.success(1);
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码", response = Result.class)
    @PostMapping("/updatePassword")
    public Result updatePass(@RequestBody RestPasswordReq restPasswordReq) {
        LiveUser user = getHeaderLiveUser();
        if (user == null) {
            return Result.error(CodeMsg.E_20001);
        }
        if (!restPasswordReq.getPassword().equals(restPasswordReq.getConfirmPassword())) {
            //判断两次密码是否一致
            return Result.error(CodeMsg.E_20013);
        }
        // 解密
        Result result = decrypt(restPasswordReq.getPassword(), restPasswordReq.getIv());
        if (CodeMsg.SUCCESS.code != result.getCode()) {
            return result;
        }
        return Result.success(userCenterService.updatePassword(user.getId(), result.getData().toString()));
    }

    /**
     * 过滤敏感词
     */
    private boolean excludeKeyword(String sensitiveWord) {
        // 平台
        List<Keyword> keywords = keywordService.getUsableKeywords();
        if (keywords != null && keywords.size() > 0) {
            for (Keyword keyword : keywords) {
                if (sensitiveWord.contains(keyword.getKwName())) {
                    return true;
                }
            }
        }
        return false;
    }


}
