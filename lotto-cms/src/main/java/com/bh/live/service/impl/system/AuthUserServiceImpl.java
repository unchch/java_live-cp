package com.bh.live.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.SystemConstants;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.security.GoogleAuthenticatorUtil;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.dao.system.AuthUserDao;
import com.bh.live.dao.system.AuthUserRoleDao;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.model.cms.AuthUserRole;
import com.bh.live.pojo.req.cms.SearchParamsReq;
import com.bh.live.pojo.res.cms.AuthUserSimpleRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.system.IAuthUserRoleService;
import com.bh.live.service.system.IAuthUserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserDao, AuthUser> implements IAuthUserService {

    @Resource
    private AuthUserDao userDao;

    @Resource
    private AuthUserRoleDao authUserRoleDao;

    @Autowired
    private IAuthUserRoleService authUserRoleService;

    /**
     * google host
     */
    @Value("${google.host}")
    private String googleHost;

    @Override
    public List<AuthUserSimpleRes> selectAuthUserSimpleList() {
        return userDao.selectAuthUserSimpleList();
    }

    @Override
    public TableDataInfo selectUserList(SearchParamsReq req) throws DataAccessException {
        Page<AuthUser> page = new Page<>(req.getPageNum(), req.getPageSize());
        if (SystemConstants.isAdmin(ShiroSubUtil.getLoginName())) {
            req.setIsAdmin(Boolean.TRUE);
        }
        page.setRecords(userDao.selectAuthUserPage(page, req));
        if (CommonUtil.isNotEmpty(page.getRecords())) {
            page.getRecords().forEach(authUser -> {
                authUser.setPassword(null);
                authUser.setSalt(null);
            });
        }
        return new TableDataInfo(page);
    }

    @Override
    public AuthUser selectByUid(String uid) throws DataAccessException {
        return userDao.selectById(uid);
    }

    @Override
    public boolean addUser(AuthUser authUser) {
        authUser.setCreateTime(new Date());
        return userDao.insert(authUser) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public String loadAccountRole(AuthUser authUser) throws DataAccessException {
        return userDao.selectUserRoles(authUser);
    }

    @Override
    public boolean checkUnique(String uid) {
        return CommonUtil.isEmpty(userDao.selectById(uid)) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean updateUser(AuthUser user) {
        return userDao.updateById(user) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean deleteUserRole(String uid) {
        QueryWrapper<AuthUserRole> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.lambda().eq(AuthUserRole::getUserId, uid);
        return authUserRoleDao.delete(deleteWrapper) > 0;
    }

    @Override
    public boolean addUserRole(String uid, Set<Integer> roles) {
        int ret = 0;
        for (Integer roleId : roles) {
            AuthUserRole authUserRole = new AuthUserRole();
            authUserRole.setUserId(uid);
            authUserRole.setRoleId(roleId);
            authUserRole.setCreateTime(new Date());
            authUserRole.setUpdateTime(new Date());
            ret += authUserRoleDao.insert(authUserRole);
        }
        return ret == roles.size();
    }

    @Override
    public boolean updateUserRole(String uid, Set<Integer> roles) {
        //先删除用户角色
        deleteUserRole(uid);
        //再赋予角色
        return addUserRole(uid, roles);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteUserById(String uid) {
        if (userDao.deleteById(uid) > 0) {
            //删除用户后 还要删除用户的所有角色
            QueryWrapper<AuthUserRole> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.lambda().eq(AuthUserRole::getUserId, uid);
            if (authUserRoleDao.delete(deleteWrapper) > 0) {
                return Boolean.TRUE;
            }
        }
        throw new ServiceRuntimeException(Result.error());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteUserByIds(String idsStr) {
        List<String> ids = Lists.newArrayList(idsStr.split(","));
        QueryWrapper<AuthUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(AuthUser::getUid, ids);
        List<AuthUser> authUsers = baseMapper.selectList(wrapper);
        // 检查是否都存在
        if (CommonUtil.isEmpty(authUsers)
                || authUsers.size() != ids.size()) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1001);
        }
        // 检查是否包含admin用户
        AuthUser admin = authUsers.stream()
                .filter(authUser -> SystemConstants.isAdmin(authUser.getUid()))
                .findFirst()
                .orElse(null);
        if (CommonUtil.isNotEmpty(admin)) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1000);
        }

        if (userDao.deleteBatchIds(ids) > 0) {
            if (authUserRoleDao.deleteByUserIds(ids, null) >= 0) {
                return Boolean.TRUE;
            }
        }
        throw new ServiceRuntimeException(Result.error());
    }

    @Override
    public boolean authorityUserRoleBatch(List<AuthUserRole> authUserRoleList) throws DataAccessException {
        for (AuthUserRole authUserRole : authUserRoleList) {
            if (CommonUtil.isNotEmpty(authUserRole.getRoleId())
                    && SystemConstants.ADMIN_ROLE_ID.equals(authUserRole.getRoleId())) {
                throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1100);
            }
            authUserRole.setCreateTime(new Date());
            authUserRole.setCreateBy(ShiroSubUtil.getLoginName());
        }
        return authUserRoleService.saveBatch(authUserRoleList);
    }

    @Override
    public boolean batchDeleteAuthorityUserRole(List<AuthUserRole> authUserRoleList) {
        if (CommonUtil.isEmpty(authUserRoleList)
                || CommonUtil.isEmpty(authUserRoleList.get(0))) {
            throw new ServiceRuntimeException(CodeMsg.E_501);
        }
        List<String> userIds = authUserRoleList.stream()
                .filter(item -> CommonUtil.isNotEmpty(item.getUserId()))
                .map(AuthUserRole::getUserId)
                .collect(Collectors.toList());
        Integer roleId = authUserRoleList.get(0).getRoleId();
        if (SystemConstants.isAdmin(userIds)) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1000);
        }
        if (SystemConstants.isAdminRole(roleId)) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1100);
        }
        int ret = authUserRoleDao.deleteByUserIds(userIds, roleId);
        return ret > 0;
    }

    @Override
    public String selectUserRoleIds(AuthUser authUser) throws DataAccessException {
        return userDao.selectUserRoleIds(authUser);
    }

    /**
     * 生成google签名
     *
     * @param uid
     * @return
     */
    @Override
    public boolean generateSecretKey(String uid) {
        AuthUser authUser = userDao.selectById(uid);
        if (CommonUtil.isEmpty(authUser)) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1001);
        }

        String secretKey = GoogleAuthenticatorUtil.generateSecretKey();
        String qrcode = GoogleAuthenticatorUtil.getQRBarcodeURL(authUser.getUid(), this.googleHost, secretKey);

        authUser.setSecretKey(secretKey);
        authUser.setSecretQrcode(qrcode);
        return userDao.updateById(authUser) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 根据角色id查询用户列表
     *
     * @param req
     * @return
     */
    @Override
    public TableDataInfo selectUserListByRoleId(Integer roleId, SearchParamsReq req) {
        Page<AuthUser> page = new Page<>(req.getPageNum(), req.getPageSize());
        page.setRecords(userDao.selectUserListByRoleId(page, roleId, req));
        return new TableDataInfo(page);
    }

    /**
     * 根据角色id获取未授权的用户
     *
     * @param roleId
     * @param req
     * @return
     */
    @Override
    public TableDataInfo selectUserListExtendByRoleId(Integer roleId, SearchParamsReq req) {
        Page<AuthUser> page = new Page<>(req.getPageNum(), req.getPageSize());
        page.setRecords(userDao.selectUserListExtendByRoleId(page, roleId, req));
        return new TableDataInfo(page);
    }
}
