package com.bh.live.controller.system;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.TreeUtil;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.model.cms.AuthResource;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.pojo.res.cms.MenuTreeNodeRes;
import com.bh.live.service.system.IAuthResourceService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资源URL管理
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@RestController
@RequestMapping("/resource")
@Slf4j
@Api(tags = "管理系统-菜单资源")
public class AuthResourceController extends BaseController {

    @Autowired
    private IAuthResourceService authResourceService;

    /**
     * 获取用户被授权菜单
     * 通过uid获取对应用户被授权的菜单列表,获取完整菜单树形结构
     *
     * @return
     */
    @GetMapping("/authorityMenu")
    public Result getAuthorityMenu() {
        Map map = Maps.newHashMap();
        List<MenuTreeNodeRes> treeNodes = new ArrayList<>();
        AuthUser param = new AuthUser();
        param.setUid(ShiroSubUtil.getLoginName());
        List<AuthResource> resources = authResourceService.getAuthorityMenusByUid(param);
        for (AuthResource resource : resources) {
            MenuTreeNodeRes node = new MenuTreeNodeRes();
            BeanUtils.copyProperties(resource, node);
            treeNodes.add(node);
        }
        List<MenuTreeNodeRes> menuTreeNodes = TreeUtil.buildTreeBy2Loop(treeNodes, -1);
        map.put("menu", menuTreeNodes);

        //查询用户所有权限  不包括菜单，目录
        List<AuthResource> apis = authResourceService.getAuthorityApisByUid(param);
        map.put("apis", apis);
        return Result.success(map);
    }

    /**
     * 获取全部资源
     *
     * @return
     */
    @GetMapping
    public Result getRes() {
        List<MenuTreeNodeRes> treeNodes = new ArrayList<>();
        List<AuthResource> resources = authResourceService.getAll();
        for (AuthResource resource : resources) {
            MenuTreeNodeRes node = new MenuTreeNodeRes();
            BeanUtils.copyProperties(resource, node);
            treeNodes.add(node);
        }
        List<MenuTreeNodeRes> menuTreeNodes = TreeUtil.buildTreeBy2Loop(treeNodes, -1);
        return Result.success(menuTreeNodes);
    }
}
