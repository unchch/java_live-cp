package com.bh.live.user;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.bh.live.controller.user.LiveUserController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bh.live.model.user.LiveUser;
import com.bh.live.service.user.ILiveUserService;

//全局配置类型设置
@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveUserTests {

	@Resource
	private ILiveUserService liveUserService;
	@Resource
	private LiveUserController liveUserController;

	@Before
	public void before() {
		System.out.println("开始测试。。。。");
	}

	@After
	public void after() {
		System.out.println("结束测试。。。。");
	}

	@Test
	public void contextLoads() {
		System.out.println(liveUserController.queryLiveUserInfoByID(null));
		//System.out.println(liveUserService.queryLiveUserAllInfoByID(1));
	}

//	public static void main(String[] args) throws NoSuchMethodException, SecurityException,
//			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		LiveUser liveUser = new LiveUser();
//		liveUser.setNickname("wang");
////		params(map, obj)
//	}
//
//	public static Map params(Map map, Object obj) {
//		map = map != null ? map : new HashMap<String, Object>();
//		Field[] fields = obj.getClass().getDeclaredFields();
//		for (int i = 0; i < fields.length; i++) {
//			Field field = fields[i];
//			String fieldName = field.getName();
//			if (!"serialVersionUID".equals(fieldName)) {
//				try {
//					field.setAccessible(true);
//					Object value = changeValueType(field, fieldName, field.getType().getTypeName());
//					if (value != null) {
//						map.put(fieldName, value);
//					}
//				} catch (IllegalArgumentException | IllegalAccessException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return map.size() > 0 ? map : null;
//	}
//
//	public static Object changeValueType(Field field, String fieldName, String fieldType)
//			throws IllegalArgumentException, IllegalAccessException {
//		switch (fieldType) {
//		case "short":
//			return field.getShort(fieldName);
//		case "long":
//			return field.getLong(fieldName);
//		case "int":
//			return field.getInt(fieldName);
//		case "float":
//			return field.getFloat(fieldName);
//		case "double":
//			return field.getDouble(fieldName);
//		case "char":
//			return field.getChar(fieldName);
//		case "byte":
//			return field.getByte(fieldName);
//		case "boolean":
//			return field.getBoolean(fieldName);
//		default:
//			return field.get(fieldName);
//		}
//	}

}
