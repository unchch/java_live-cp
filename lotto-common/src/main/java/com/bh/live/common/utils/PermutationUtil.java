package com.bh.live.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PermutationUtil {
	

	public static void take(String s, int total, List lst, StringBuffer num) {

		String str = null;
		for (int i = 0; i < lst.size(); i++) {
			// System.out.println("i="+i);
			List templst = new LinkedList(lst);
			String n = (String) templst.remove(i);// 取出来的数字
			if (!"".equals(s)) {
				str = s + "," + n;
			} else {
				str = s + n;
			}

			if (total == 1) {
				num.append(str + "|"); //以最极端 n个里面只取一个，直接把取出来的结果输出即可
			} else {
				int temp = total - 1;// 在同一层中total总量不能减,不能再原有变量的基础上
				take(str, temp, templst, num);// 这里的temp以及templst都是全新的变量和集合
			}
		}

	}
	
	
	/**
	 * 给产生号码排序
	 * 
	 * @param num
	 */
	private static List<String> orderNum(StringBuffer num) {
		String buff = num.substring(0, num.length() - 1);
		String[] str = buff.toString().split("\\|");
		StringBuffer orderBuff = new StringBuffer();
		for (String s : str) {
			String[] strIndex = s.split(",");

			int length = strIndex.length;
			String temp;
			for (int a = 0; a < length - 1; a++) {
				for (int b = 0; b < length - 1 - a; b++) {
					if (Integer.parseInt(strIndex[b]) > Integer.parseInt(strIndex[b + 1])) {
						temp = strIndex[b + 1];
						strIndex[b + 1] = strIndex[b];
						strIndex[b] = temp;
					}
				}
			}

			for (String v : strIndex) {
				orderBuff.append(v + ",");
			}
			orderBuff.deleteCharAt(orderBuff.length() - 1);
			orderBuff.append("|");
		}
		orderBuff.deleteCharAt(orderBuff.length() - 1);

		String[] strArray = orderBuff.toString().split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < strArray.length; i++) {
			if (!list.contains(strArray[i])) {
				list.add(strArray[i]);
			}
		}

		System.out.println(list.toString());
		return list;
	}
	
    /***
              * 给定连续数字集合 和任选几位数的排列组合
     * @param n  给定的连续数字集合数组 如{ "1", "2", "3", "4"}
     * @param m  任选几位数
     */
	public static List<String> getPermutation(String[] n, int m) {
		StringBuffer num = new StringBuffer();
		List lst = Arrays.asList(n);
		take("", m, lst, num);
		
		List<String> list = orderNum(num);
    	System.out.println("num ==>" + list.size());
	    return list;
	}
	
	public static void main(String[] args) {
		String[] n = { "1", "2", "3", "4","5","6","7","8","9","10"};//,"5","6","7","8","9","10" 
		getPermutation( n, 4);
	}

}
