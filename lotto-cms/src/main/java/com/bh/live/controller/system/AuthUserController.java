package com.bh.live.controller.system;

import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.constant.SystemConstants;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.security.ShiroMd5Util;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.common.utils.token.JsonWebTokenUtil;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.model.cms.AuthUserRole;
import com.bh.live.pojo.req.cms.AuthUserReq;
import com.bh.live.pojo.req.cms.SearchParamsReq;
import com.bh.live.service.system.IAuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author yq.
 * @date 2019/4/11 8:55 PM
 * @description 后台用户控制器
 * @since
 **/
@RestController
@RequestMapping("/sysuser")
@Api(tags = "管理系统-用户")
public class AuthUserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUserController.class);

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private RedisUtil redisManager;

    @ApiOperation(value = "用户列表", response = AuthUser.class)
    @GetMapping
    public Result list(SearchParamsReq req) {
        return Result.success(authUserService.selectUserList(req));
    }

    @ApiOperation(value = "修改后台用户", response = Boolean.class)
    @PutMapping
    public Result update(@RequestBody AuthUserReq req) {
        AuthUser user = new AuthUser();
        CommonUtil.beanCopy(req, user);
        if (CommonUtil.isNotEmpty(user.getUid()) && SystemConstants.isAdmin(user.getUid())) {
            return Result.error("不允许修改超级管理员用户");
        }
        user.setUpdateBy(ShiroSubUtil.getLoginName());
        user.setUpdateTime(new Date());
        boolean ret = authUserService.updateUser(user);
        //修改用户角色
        boolean ret1 = authUserService.updateUserRole(req.getUid(), req.getRoles());
        return ret && ret1 ? Result.success() : Result.error();
    }


    @ApiOperation(value = "重置用户密码", response = Boolean.class)
    @PostMapping("/resetPass")
    public Result resetPass(@RequestBody AuthUser authUser) {
        //如果修改的是admin 需要验证是否是admin
        if (SystemConstants.isAdmin(authUser.getUid()) && !SystemConstants.isAdmin(ShiroSubUtil.getLoginName())) {
            return Result.error("你不是超级管理员用户，无法修改超级管理员密码");
        }
        String salt = StringUtils.getRandomString(6);
        authUser.setSalt(salt);

        authUser.setPassword(ShiroMd5Util.genMd5Pass(authUser.getPassword(), salt));
        boolean ret = authUserService.updateUser(authUser);

        //修改用户密码成功后，清除Redis中的jwt 让用户重新登录
        if (ret) {
            redisManager.del(String.format(UserRedisKey.SYS_USER_JWT_TOKEN, authUser.getUid()));
        }
        return ret ? Result.success() : Result.error();
    }

    @ApiOperation(value = "获取用户信息", response = AuthUserReq.class)
    @GetMapping("/{uid}")
    public Result findUser(@PathVariable(value = "uid") String uid) {
        AuthUser user = authUserService.selectByUid(uid);
        AuthUserReq ret = new AuthUserReq();
        if (CommonUtil.isEmpty(user)) {
            return Result.error(CodeMsg.CMS_SYS_1001);
        }
        CommonUtil.beanCopy(user, ret);
        //查询用户角色
        String roleIds = authUserService.selectUserRoleIds(new AuthUser().setUid(uid));
        if (CommonUtil.isNotEmpty(roleIds)) {
            Set<Integer> roleSet = StringUtils.splitToSetInt(roleIds);
            ret.setRoles(roleSet);
        }
        return Result.success(ret);
    }

    @ApiOperation(value = "删除后台用户", response = Boolean.class)
    @DeleteMapping("/{uid}")
    public Result delete(@PathVariable(name = "uid") String uid) {
        Assert.isNotNull(uid, CodeMsg.E_501);
        if (CommonUtil.isNotEmpty(uid) && SystemConstants.isAdmin(uid)) {
            return Result.error(CodeMsg.CMS_SYS_1000);
        }
        if (authUserService.checkUnique(uid)) {
            return Result.error(CodeMsg.CMS_SYS_1001);
        }
        return authUserService.deleteUserById(uid) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "批量删除用户", response = Boolean.class)
    @DeleteMapping
    public Result deleteBatch(@RequestParam(name = "ids") String ids) {
        Assert.isNotNull(ids, CodeMsg.E_501);
        return authUserService.deleteUserByIds(ids) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "检验用户名是否重复", response = AuthUser.class)
    @GetMapping("/checkUnique/{uid}")
    public Result checkUnique(@PathVariable("uid") @NotBlank String uid) {
        return Result.success(authUserService.checkUnique(uid));

    }

    @ApiOperation(value = "添加用户", response = Boolean.class)
    @PostMapping
    public Result add(@RequestBody AuthUserReq req) {
        AuthUser user = new AuthUser();
        CommonUtil.beanCopy(req, user);
        String uid = user.getUid();
        if (!authUserService.checkUnique(uid)) {
            return Result.error(CodeMsg.CMS_SYS_1002);
        }
        String password = user.getPassword();
        String username = user.getUsername();
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(username)) {
            // 必须信息缺一不可,返回账户信息缺失
            return Result.error(CodeMsg.CMS_SYS_1003);
        }
        String salt = StringUtils.getRandomString(6);
        user.setSalt(salt);
        //将用户密码进行md5加密
        String passMd5 = ShiroMd5Util.genMd5Pass(password, salt);
        user.setPassword(passMd5);
        user.setStatus(LotteryConstants.VALUE_1);
        user.setCreateBy(ShiroSubUtil.getLoginName());

        //添加用户
        boolean ret1 = authUserService.addUser(user);
        boolean ret2 = false;
        if (ret1) {
            //添加角色
            ret2 = authUserService.addUserRole(uid, req.getRoles());
        }
        return ret1 && ret2 ? Result.success() : Result.error();
    }

    @ApiOperation(value = "获取用户角色列表", response = String.class)
    @GetMapping("/role/{uid}")
    public Result getUserRoleList(@PathVariable(value = "uid") String uid) {
        AuthUser param = new AuthUser();
        param.setUid(uid);
        String roles = authUserService.loadAccountRole(param);
        Set<String> roleSet = JsonWebTokenUtil.split(roles);
        LOGGER.info(roleSet.toString());
        return Result.success(roleSet);
    }


    @ApiOperation(value = "给用户赋予角色", response = Boolean.class)
    @PostMapping("/authority/role")
    public Result authorityUserRole(@RequestBody List<AuthUserRole> authUserRoleList) {
        return authUserService.authorityUserRoleBatch(authUserRoleList) ? Result.success() : Result.error();
    }

    /**
     * 删除已经授权的用户角色
     *
     * @param authUserRoleList
     * @return
     */
    @ApiOperation(value = "删除已经授权的用户角色", response = Boolean.class)
    @PostMapping("/authority/role/cancel")
    public Result deleteAuthorityUserRole(@RequestBody List<AuthUserRole> authUserRoleList) {
        return authUserService.batchDeleteAuthorityUserRole(authUserRoleList) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "后台用户登出", response = Boolean.class)
    @PostMapping("/logout")
    public Result accountExit() {
        String uid = ShiroSubUtil.getLoginName();
        if (CommonUtil.isEmpty(uid)) {
            return Result.error("用户未登录无法登出");
        }
        String jwt = redisManager.getByFastJson(String.format(UserRedisKey.SYS_USER_JWT_TOKEN, uid), String.class);
        if (CommonUtil.isEmpty(jwt)) {
            return Result.error("用户未登录无法登出");
        }
        redisManager.del(String.format(UserRedisKey.SYS_USER_JWT_TOKEN, uid));
        // TODO 登录登出日志
        // LogExeManager.getInstance().executeLogTask(LogTaskFactory.exitLog(uid, getAddr(), (short) 1, ""));

        SecurityUtils.getSubject().logout();
        return Result.success();

    }

    @ApiOperation(value = "生成谷歌验证码", response = Boolean.class, hidden = true)
    @GetMapping("/generateSecretKey/{uid}")
    public Result generateSecretKey(@PathVariable("uid") @NotBlank String uid) {
        return Result.success(authUserService.generateSecretKey(uid));
    }

    @ApiOperation(value = "获取用户信息", response = Boolean.class, hidden = true)
    @GetMapping("/queryUserList")
    public Result queryUserList() {
        return Result.success(authUserService.selectAuthUserSimpleList());
    }
}
