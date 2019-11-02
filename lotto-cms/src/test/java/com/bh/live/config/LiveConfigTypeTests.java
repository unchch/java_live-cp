package com.bh.live.config;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bh.live.controller.configuration.LiveConfigurationController;
//import com.bh.live.controller.configuration.LiveConfigurationController;
import com.bh.live.dao.configuration.LiveConfigurationDao;
import com.bh.live.model.configuration.LiveConfiguration;
import com.bh.live.service.configuration.ILiveConfigurationService;

//全局配置类型设置
@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveConfigTypeTests {
	
	@Resource
	private LiveConfigurationDao dao;
	
	@Resource
	private ILiveConfigurationService service;
	
	@Resource
	private LiveConfigurationController controller;
	
	@Before
	public void before(){
		System.out.println("开始测试。。。。");
	}
	
	@After
	public void after() {
		System.out.println("结束测试。。。。");
	}
	
    @Test
    public void contextLoads() {
    	
    	List<LiveConfiguration> list = new LinkedList<LiveConfiguration>();
//		LiveConfiguration user = new LiveConfiguration();
//		//user.setId(1);
//		user.setConfigName("用户");
//		user.setConfigValue("1");
//		user.setTypeName("用户类型");
//		user.setTypeValue("user_type");
//		
//		LiveConfiguration specialist = new LiveConfiguration();
//		//specialist.setId(2);
//		specialist.setConfigName("专家");
//		specialist.setConfigValue("2");
//		specialist.setTypeName("用户类型");
//		specialist.setTypeValue("user_type");
//		
//		LiveConfiguration anchor = new LiveConfiguration();
//		//anchor.setId(3);
//		anchor.setConfigName("主播");
//		anchor.setConfigValue("3");
//		anchor.setTypeName("用户类型");
//		anchor.setTypeValue("user_type");
//		
//		list.add(user);
//		list.add(specialist);
//		list.add(anchor);
    	
    	LiveConfiguration chongzhi = new LiveConfiguration();
    	chongzhi.setConfigName("充值");
//    	chongzhi.setConfigValue("1");
//    	chongzhi.setTypeName("交易类型");
//    	chongzhi.setExtendName("可提现");
//    	chongzhi.setExtendValue("1");
//    	chongzhi.setExtendDescribe("是否可以提现");
//    	chongzhi.setTypeValue("transaction_type");
    	
    	LiveConfiguration zengsong = new LiveConfiguration();
    	zengsong.setConfigName("赠送");
    	zengsong.setConfigValue("2");
    	zengsong.setTypeName("交易类型");
    	zengsong.setExtendName("可提现");
    	zengsong.setExtendValue("1");
    	zengsong.setExtendDescribe("是否可以提现");
    	zengsong.setTypeValue("transaction_type");
    	
    	LiveConfiguration kouchu = new LiveConfiguration();
    	kouchu.setConfigName("扣除");
    	kouchu.setConfigValue("3");
    	kouchu.setTypeName("交易类型");
    	kouchu.setExtendName("可提现");
    	kouchu.setExtendValue("1");
    	kouchu.setExtendDescribe("是否可以提现");
    	kouchu.setTypeValue("transaction_type");
    	
    	list.add(chongzhi);
    	list.add(zengsong);
    	list.add(kouchu);
//		System.out.println(service.addGlobalConfig(list));
//    	System.out.println(service.updateGlobalConfig(list));
    	System.out.println(service.queryUsableConfigByCondition(chongzhi));
//		System.out.println(controller.addGlobalConfig(list));
		
		
    }

}
