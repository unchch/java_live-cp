package com.bh.live.common.utils;


import cn.hutool.core.util.ObjectUtil;
import com.bh.live.common.constant.CommonConstants;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @Date 2016年12月1日
 * @Desc 数组操作工具
 */
public class ArrayUtil extends ArrayUtils {

    /**
     * @param arr 数组
     * @return true/false
     * @Desc 数组中的元素是否重复
     */
    public static <T> boolean isRepeat(T[] arr) {
        if (isEmpty(arr)) {
            return false;
        }
        Map<T, Boolean> temp = new HashMap<T, Boolean>();
        for (int i = 0; i < arr.length; i++) {
            // 存在
            if (temp.containsKey(arr[i])) {
                return true;
            }
            temp.put(arr[i], true);
        }
        return false;
    }

    /**
     * @param sup
     * @param sub
     * @return
     * @desc 判断sup是否全部包含sub
     * @create 2017年6月13日
     */
    public static <T> boolean containsAll(T[] sup, T[] sub) {
        if (isEmpty(sup) || isEmpty(sub)) {
            return false;
        }
        for (T tmp : sub) {
            if (!contains(sup, tmp)) {
                return false;
            }
        }
        return true;
    }


    /**
     * @param arr
     * @return
     * @desc 数组元素是否都一样
     * @create 2017年3月30日
     */
    public static <T> boolean isSame(T[] arr) {
        if (isEmpty(arr)) {
            return true;
        }
        int i = arr.length;
        if (i == 1) {
            return true;
        }
        for (i = 1; i < arr.length; i++) {
            if (!Objects.equals(arr[i - 1], arr[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param srcArr    源数组
     * @param targetArr 目标数组
     * @return true/false
     * @Desc 比较两个数组中的数组元素是否存在重复
     */
    public static <T> boolean isRepeatCompareTo(T[] srcArr, T[] targetArr) {
        if (isEmpty(srcArr) || isEmpty(targetArr)) {
            return false;
        }
        Map<T, Boolean> temp = new HashMap<T, Boolean>();
        for (int i = 0; i < srcArr.length; i++) {
            temp.put(srcArr[i], true);
        }
        for (int i = 0; i < targetArr.length; i++) {
            // 存在
            if (temp.containsKey(targetArr[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param arr 数组
     * @return 数组
     * @Desc 对于数字元素, 大于0小于10的数左补0, 非该范围的数字删除左边0;非数字的不处理
     */
    public static String[] numLeftAddZ(String[] arr) {
        if (isEmpty(arr)) {
            return arr;
        }
        String[] target = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (NumberUtil.isDigits(arr[i])) {
                if (Integer.parseInt(arr[i]) > 0 && Integer.parseInt(arr[i]) < 10) {
                    target[i] = "0" + Integer.parseInt(arr[i]);
                } else {
                    target[i] = String.valueOf(Integer.parseInt(arr[i]));
                }
            } else {
                target[i] = arr[i];
            }
        }
        return target;
    }

    public static <T> String[] numLeftAddZ(T[] arr) {
        if (isEmpty(arr)) {
            return new String[0];
        }
        String[] target = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                target[i] = "";
                continue;
            }
            if (NumberUtil.isDigits(arr[i].toString())) {
                if (Integer.parseInt(arr[i].toString()) > 0 && Integer.parseInt(arr[i].toString()) < 10) {
                    target[i] = "0" + Integer.parseInt(arr[i].toString());
                } else {
                    target[i] = String.valueOf(arr[i]);
                }
            } else {
                target[i] = arr[i].toString();
            }
        }
        return target;
    }

    /**
     * @param arr 数组
     * @return 数组
     * @Desc 对于数字元素前面有0的删掉0，非数字元素不处理
     */
    public static String[] numLeftDelZ(String[] arr) {
        if (isEmpty(arr)) {
            return arr;
        }
        String[] target = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (NumberUtil.isDigits(arr[i])) {
                target[i] = String.valueOf(Integer.parseInt(arr[i]));
            } else {
                target[i] = arr[i];
            }
        }
        return target;

    }

    /**
     * 返回多个字符串数组的交集
     *
     * @param arr
     * @return
     */
    public static String[] intersect(String[]... arr) {
        ConcurrentHashMap<String, Integer> statis = occurrence(arr);
        if (statis.size() == 0) {
            return new String[]{};
        }
        List<String> intersect = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : statis.entrySet()) {
            if (entry.getValue() == arr.length) {
                intersect.add(entry.getKey());
            }
        }
        return intersect.toArray(new String[intersect.size()]);
    }

    /**
     * 统计多个数组中各种值的出现次数
     *
     * @param arr
     * @return
     */
    public static ConcurrentHashMap<String, Integer> occurrence(String[]... arr) {
        ConcurrentHashMap<String, Integer> statis = new ConcurrentHashMap<>();
        for (String[] tmpArr : arr) {
            if (tmpArr == null || tmpArr.length == 0) {
                return statis;
            }
        }
        for (String[] tmpArr : arr) {
            for (String tmp : tmpArr) {
                statis.putIfAbsent(tmp, 0);
                statis.put(tmp, statis.get(tmp) + 1);
            }
        }
        return statis;
    }

    /**
     * @param arr 目标数组
     * @return 过滤数组中的重复元素
     * @desc 过滤数组中的重复元素
     * @date 2017年6月16日
     */
    public static <T> Set<T> filterDuplicate(T[] arr) {
        if (isEmpty(arr)) {
            return new HashSet<T>();
        }
        Set<T> result = new HashSet<T>();
        for (int i = 0; i < arr.length; i++) {
            result.add(arr[i]);
        }
        return result;
    }

    /**
     * @param arr 目标数组
     * @return 元素分组，将目标数组中的元素分组，并记录每组元素出现次数
     * @desc 元素分组，将目标数组中的元素分组，并记录每组元素出现次数
     * @date 2017年7月5日
     */
    public static <T> Map<T, Integer> group(T[] arr) {
        if (isEmpty(arr)) {
            return new HashMap<>();
        }
        Map<T, Integer> result = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                continue;
            }
            if (result.containsKey(arr[i])) {
                result.put(arr[i], result.get(arr[i]) + CommonConstants.ONE);
            } else {
                result.put(arr[i], CommonConstants.ONE);
            }
        }
        return result;
    }

    /**
     * 判断数组字符串种，是否包含同时出现的字符
     *
     * @param strs
     * @param charaters 出现的字符
     * @return
     * @date 2017年7月26日
     */
    public static boolean judeIfContains(String[] strs, String... charaters) {
        if (ObjectUtil.isEmpty(strs)) {
            return true;
        }
        boolean[] found = new boolean[charaters.length];
        for (int i = 0; i < strs.length; i++) {
            for (int j = 0; j < charaters.length; j++) {
                found[j] = found[j] ? found[j] : strs[i].contains(charaters[j]);
            }
        }
        for (int k = 0; k < found.length; k++) {
            if (!found[k] == true) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//		Object []arr=new Object[]{1,2,"sdfs",'e',234.43};
//		numLeftAddZ(arr);
//		String []s1 = {"0","1","2","3"};
//		String []s2 = {"1","2","3","4"};
//		String []s3 = null;
//		System.out.println(intersect(s1,s2,s3).length);
//		String []s4 = {"1","0","1"};
        //Integer [] s5 = {1,1,0,0,4,3};
//		System.out.println(group(s4));

//		String[] uploadDatas = FileUtil.readFileLine("C:\\1111.txt", EncodingType.UFT8.getShortName());
//		boolean flag1 = false;
//		boolean flag2 = false;
//		long begin = System.currentTimeMillis();
//	    for(String ss : uploadDatas){
//	    	flag1 = flag1?flag1:ss.contains(SymbolConstants.NUMBER_SIGN);
//	    	flag2 = flag2?flag2:ss.contains(SymbolConstants.STAR);
//	    }
//	    if(flag1 && flag2){
//	    	System.out.println("方案包含#和*");
//	    }
//	    System.out.println("耗时："+ (System.currentTimeMillis() - begin)/1000 + "秒");
//		System.out.println(judeIfContents(null, "a","b","c"));
//		System.out.println(judeIfContains(uploadDatas, SymbolConstants.ADD));
    }

}
