package com.bh.live.system;

import com.bh.live.model.cms.AuthResource;
import com.bh.live.service.system.IAuthResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName AuthResourceService
 * @description: AuthResourceService
 * @author: yq.
 * @date 2019-08-12 11:17:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthResourceService {

    @Autowired
    private IAuthResourceService authResourceService;

    @Test
    public void selectAuthCommonResTest() {
        List<AuthResource> authResources = authResourceService.selectAuthCommonRes();
        authResources.forEach(System.out::println);
    }
}
