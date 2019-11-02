package com.bh.live.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CalcUtils {

    /**
     * 判断单双，大小，顺子，重复
     *
     * @param array
     * @return
     */
    public static String cheakNumber(int[] array) {
        //正序
        Arrays.sort(array);
        Set set = new HashSet();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }
        if (set.size() == array.length) {
            //无重复
            if (transit(array)) {
                return "全顺";
            } else {
                if (semiShun(array)) {
                    return "半顺";
                } else {
                    return "杂六";
                }
            }
        }
        //全部相同
        if (set.size() == 1) {
            return "豹子";
        }
        if (set.size() < array.length) {
            //有重复
            return "对子";
        }
        return "";
    }

    /**
     * 判断是否是全顺子
     *
     * @param arr
     * @return
     */
    public static boolean transit(int[] arr) {
        boolean b = false;
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] * 2 != arr[i - 1] + arr[i + 1]) {
                b = false;
                break;
            }
            if (Math.abs(arr[i + 1] - arr[i]) != 1) {
                b = false;
                break;
            }
            if ((arr[i + 1] - arr[i]) != (arr[i] - arr[i - 1])) {
                b = false;
                break;
            }
            b = true;
            continue;
        }
        if(arr[0]==0 && arr[1] == 8 && arr[2] ==9){
            b = true;
        }else if(arr[0]==0 && arr[1] == 1 && arr[2] ==9){
            b = true;
        }
        return b;
    }

    /**
     * 判断是否是半顺子
     *
     * @param arr
     * @return
     */
    public static boolean semiShun(int[] arr) {
        if(arr[0]==0 && arr[2] ==9){
            return true;
        }
        return (arr[0] + 1 == arr[1]) || (arr[1] + 1 == arr[2]);
    }

    /**
     * 充分利用类集的特性，Set中不允许有重复的元素
     *
     * @param array
     * @return
     */
    public static boolean cheakIsRepeat(String[] array) {
        Set set = new HashSet();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set.size() < array.length;
    }

    /**
     * 重复的数字
     *
     * @param a
     * @return
     */
    public static String findDupicateInArray(String[] a) {
        Set set = new HashSet();
        int count = 0;
        for (int j = 0; j < a.length; j++) {
            for (int k = j + 1; k < a.length; k++) {
                if (a[j].equals(a[k])) {
                    count++;
                }
            }
            if (count >= 1) {
                set.add(a[j]);
                count = 0;
            }
        }
        return String.join(",",set);
    }
}
