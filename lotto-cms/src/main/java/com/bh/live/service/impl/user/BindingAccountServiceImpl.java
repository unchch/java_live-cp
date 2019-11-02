package com.bh.live.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.user.BindingAccountDao;
import com.bh.live.model.user.BindingAccount;
import com.bh.live.pojo.res.user.BindingAccountRes;
import com.bh.live.service.user.IBindingAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户绑定账户表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-30
 */
@Service
public class BindingAccountServiceImpl extends ServiceImpl<BindingAccountDao, BindingAccount> implements IBindingAccountService {

    @Override
    public List<BindingAccountRes> getAccountByUserId(Integer userId) {
        List<BindingAccountRes> resList = new ArrayList<>();
        QueryWrapper<BindingAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("is_delete",1);
        //wrapper.eq("is_usable",1);
        List<BindingAccount> bindingAccounts = baseMapper.selectList(wrapper);
        bindingAccounts.forEach(account -> {
            BindingAccountRes res = new BindingAccountRes();
            BeanUtils.copyProperties(account, res);
            resList.add(res);
        });
        return resList;
    }
}
