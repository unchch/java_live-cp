package com.bh.live.controller.system;

import com.bh.live.common.constant.SystemConstants;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.model.cms.AuthResource;
import com.bh.live.model.cms.AuthRole;
import com.bh.live.model.cms.AuthRoleResource;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.pojo.req.cms.SearchParamsReq;
import com.bh.live.pojo.res.cms.SysRoleRes;
import com.bh.live.service.system.IAuthResourceService;
import com.bh.live.service.system.IAuthRoleService;
import com.bh.live.service.system.IAuthUserService;
import com.bh.live.shiro.filter.ShiroFilterChainManager;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.bh.live.common.constant.SystemConstants.ADMIN_ROLE_ID;


/**
 * @author yq.
 * @date 2019/4/16 11:58 AM
 * @description 后台角色控制器
 * @since
 **/
@RestController
@RequestMapping("/role")
@Slf4j
@Api(tags = "管理系统-角色")
public class AuthRoleController extends BaseController {

    @Autowired
    private IAuthRoleService authRoleService;

    @Autowired
    private IAuthResourceService authResourceService;

    @Autowired
    private ShiroFilterChainManager shiroFilterChainManager;

    @Autowired
    private IAuthUserService authUserService;

    @ApiOperation(value = "获取角色列表", response = AuthRole.class)
    @GetMapping
    public Result getRolesList() {
        return Result.success(authRoleService.selectRoleList());
    }

    @ApiOperation(value = "获取简单角色列表", response = SysRoleRes.class)
    @GetMapping("/forUser")
    public Result getRolesForUser() {
        List<AuthRole> roles = authRoleService.selectRoleList();
        List<SysRoleRes> ret = Lists.newArrayList();
        roles.forEach(role -> {
            if (CommonUtil.isNotEmpty(role.getId())
                    && SystemConstants.ADMIN_ROLE_ID.compareTo(role.getId()) == 0) {
                return;
            }
            SysRoleRes sysRoleRes = new SysRoleRes();
            CommonUtil.beanCopy(role, sysRoleRes);
            ret.add(sysRoleRes);
        });
        return Result.success(ret);
    }

    @ApiOperation(value = "获取角色信息", response = AuthRole.class)
    @GetMapping("/{id}")
    public Result findRole(@PathVariable Integer id) {
        return Result.success(authRoleService.selectRoleById(id));
    }

    @ApiOperation(value = "更新角色", response = Boolean.class)
    @PutMapping
    public Result updateRole(@RequestBody AuthRole role) {
        if (CommonUtil.isNotEmpty(role.getId()) && ADMIN_ROLE_ID.equals(role.getId())) {
            return Result.error("不允许删除超级管理员角色");
        }
        if (CommonUtil.isEmpty(authRoleService.selectRoleById(role.getId()))) {
            return Result.error("你要修改的角色不存在");
        }
        role.setUpdateTime(new Date());
        role.setUpdateBy(ShiroSubUtil.getLoginName());
        return authRoleService.updateRole(role) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "添加角色", response = Boolean.class)
    @PostMapping
    public Result addRole(@RequestBody AuthRole role) {
        if (!authRoleService.checkUnique(role.getCode(), role.getName())) {
            return Result.error(Result.Type.FAIL.value(), "角色编码或者角色名称重复");
        }
        role.setCreateBy(ShiroSubUtil.getLoginName());
        role.setCreateTime(new Date());
        return authRoleService.addRole(role) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "根据角色ID删除角色", response = Boolean.class)
    @DeleteMapping("/{roleId}")
    public Result deleteRoleByRoleId(@PathVariable Integer roleId) {
        if (CommonUtil.isNotEmpty(roleId) && ADMIN_ROLE_ID.equals(roleId)) {
            return Result.error("不能操作超级管理员角色");
        }
        return authRoleService.deleteByRoleId(roleId) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "获取角色(roleId)所被授权的API资源", response = AuthResource.class)
    @GetMapping("/{roleId}/res")
    public Result getRestApiExtendByRoleId(@PathVariable Integer roleId) {
        if (CommonUtil.isNotEmpty(roleId) && ADMIN_ROLE_ID.equals(roleId)) {
            return Result.error("不允许修改超级管理员角色");
        }
        AuthRoleResource param = new AuthRoleResource();
        param.setRoleId(roleId);
        return Result.success(authResourceService.getAuthorityApisNotParentByRoleId(param));
    }

    @PutMapping("/{roleId}/res")
    @ApiOperation(value = "修改角色权限", response = Boolean.class)
    public Result updateRoleResources(@PathVariable Integer roleId, String ids) {
        if (CommonUtil.isNotEmpty(roleId) && ADMIN_ROLE_ID.equals(roleId)) {
            return Result.error("不允许修改超级管理员角色的权限");
        }
        boolean ret = authRoleService.updateRoleResources(roleId, ids);
        //重置过滤链
        shiroFilterChainManager.reloadFilterChain();
        return ret ? Result.success() : Result.error();
    }

    @ApiOperation(value = "获取角色关联的(roleId)对应用户列表", response = AuthUser.class)
    @GetMapping("/sysuser/{roleId}")
    public Result getUserListByRoleId(@PathVariable Integer roleId, SearchParamsReq req) {
        return Result.success(authUserService.selectUserListByRoleId(roleId, req));
    }

    @ApiOperation(value = "获取角色未关联的用户列表", response = AuthUser.class)
    @GetMapping("/sysuser/-/{roleId}")
    public Result selectUserListExtendByRoleId(@PathVariable Integer roleId, SearchParamsReq req) {
        return Result.success(authUserService.selectUserListExtendByRoleId(roleId, req));
    }
}
