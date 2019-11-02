package com.bh.live.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

/**
 * 封装了一些公共方法，例如对字符串和日期的一些操作
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class CommonUtil {

    /**
     * 判断对象是否为空
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if ("".equals(((String) o).trim())) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象是否不为空(新增)
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 不同BEAN 之间的属性赋值.
     *
     * @param src    源
     * @param target 目标
     */
    public static void beanCopy(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 找到不为空的属性，进行拷贝.
     *
     * @param source 原对象
     * @return java.lang.String[] 为空的属性名
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    /**
     * 随机指定范围内N个不重复的数
     * 在初始化的无重复待选数组中随机产生一个数放入结果中，
     * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
     * 然后从len-2里随机产生下一个随机数，如此类推
     *
     * @param max 指定范围最大值
     * @param min 指定范围最小值
     * @param n   随机数个数
     * @return int[] 随机数结果集
     */
    public static int[] randomArray(int min, int max, int n) {
        int len = max - min + 1;
        if (max < min || n > len) {
            return null;
        }
        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }


    /**
     * 生成随机机字母数字组合字符串
     * @param length 需要的字符长度
     * @return 返回随机字母数字组合字符串
     */
    public static String getRandomStr(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            //随机数由0-9，a-z,A-Z组成，数字占10个，字母占52个，数字、字母占比1:5（标准的应该是10:52）
            //random.nextInt(6) 0-5中6个数取一个
            String charOrNum = (random.nextInt(6)+6) % 6 >=1 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母，输出比例为1:1
                int temp = random.nextInt(2) % 2 == 0 ? 97 : 65;
                //char（65）-char(90) 为大写字母A-Z；char(97)-char(122)为小写字母a-z
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;

    }

}
