package com.bh.live.common.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WuLong
 * @Version 1.0
 * @CreatDate 2016-11-11 下午3:46:32
 * @Desc 用于类之间的copy (主要用户po 和 bo 之间的copy)
 */
@Slf4j
public class CopyUtil {
    /**
     * 把集合origList copy 到类型为T的ArrayList中
     *
     * @param c
     * @param origList
     * @return ArrayList 的集合
     */
    public static <T> List<T> copyPropertiesList(Class<T> c, List<?> origList) {
        if (origList == null || origList.size() < 1) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (Object obj : origList) {
            try {
                T bo = c.newInstance();
                BeanUtils.copyProperties(obj, bo);
                result.add(bo);
            } catch (Exception e) {
                throw new RuntimeException("copy error");
            }
        }
        return result;
    }

    /**
     * 根据传入的属性名字符串，修改对应的属性值
     *
     * @param clazz 类的Class对象
     * @param name  属性名
     * @param obj   要修改的实例对象
     * @param value 修改后的新值
     */
    public static void setField(Class<?> clazz, String name, Object obj, Object value)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = clazz.getDeclaredField(name);
        if (ObjectUtil.isNull(field)) {
            log.error("clazz name {} is not find field {}", clazz.getName(), name);
            return;
        }
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		/*DicDataPO po = new DicDataPO();
		po.setCreateTime(new Date());
		List<DicDataPO> list = new ArrayList<DicDataPO>();
		list.add(po);
		
		List<DicDataBO> result = copyPropertiesList(DicDataBO.class,list);
		
		System.out.println(result.get(0).getCreateTime());*/

    }
}
