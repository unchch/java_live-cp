package com.bh.live.common.utils;

import cn.hutool.core.util.ObjectUtil;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author
 *
 * @Date 2016年12月1日
 *
 * @Desc 数字工具
 */
public class NumberUtil {

	/**
	 * @param str
	 *            字符串
	 * @return true/false
	 * @Desc 目标字符串是否是数字
	 */
	public static boolean isDigits(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param str
	 *            字符串
	 * @return true/false
	 * @Desc 目标字符串是否是小数
	 */
	public static boolean isDouble(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		try {
			Double.valueOf(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @param subscript
	 *            下标
	 * @param superscript
	 *            上标
	 * @return 组合数
	 * @Desc 组合公式，获取组合数；eg:Cnm n是下标m是上标,公式是[n（n-1)···（n-m+1)]/m!
	 */
	public static long getCombinationCount(int subscript, int superscript) {
		if (subscript <= 0 || superscript <= 0 || subscript < superscript) {
			return 0;
		}
		long subscriptMultiply = 1;
		for (int i = subscript; i >= (subscript - superscript + 1); i--) {
			subscriptMultiply *= i;
		}
		long superscriptMultiply = 1;
		for (int i = superscript; i >= 1; i--) {
			superscriptMultiply *= i;
		}
		return subscriptMultiply / superscriptMultiply;
	}
	
	/**
	 * @desc   排列数
	 * @param sub
	 * @param sup
	 * @return 
	 */
	public static long getPermutation(int sub, int sup) {
		if (sub <= 0 || sup <= 0 || sub < sup) {
			return 0;
		}
		if(sub == sup) {
			return 1;
		}
		long permutation = 1;
		for(int i = 0 ; i < sup ; i++) {
			permutation *= sub--;
		}
		return permutation;
	}
 
	/**
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数).
	 * @param roundingMode
	 *            精度取值方式.(如截取，四舍五入等)
	 * @return 精度计算后的数据.
	 * @Desc 对double数据进行取精度.
	 */
	public static double round(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		return bd.doubleValue();
	}

	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点相加，精度计算后的数据.
	 * @Desc 对double数据进行相加并取精度.
	 */
	public static double sum(double d1, double d2) {
		try {
			BigDecimal bd1 = new BigDecimal(Double.toString(d1));
			BigDecimal bd2 = new BigDecimal(Double.toString(d2));
			return bd1.add(bd2).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static double sum(double d1,double d2,double d3,double ...d4s){
		try {
			BigDecimal bd1 = new BigDecimal(Double.toString(d1));
			BigDecimal bd2 = new BigDecimal(Double.toString(d2));
			BigDecimal bdn = null;
			BigDecimal result = new BigDecimal(0);
			for (double d : d4s) {
				bdn = new BigDecimal(Double.toString(d));
				result = result.add(bdn);
			}
			return bd1.add(bd2).add(result).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点相减，精度计算后的数据.
	 * @Desc 对double数据进行相减并取精度.
	 */
	public static double sub(double d1, double d2) {
		try {
			BigDecimal bd1 = new BigDecimal(Double.toString(d1));
			BigDecimal bd2 = new BigDecimal(Double.toString(d2));
			return bd1.subtract(bd2).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点相乘，精度计算后的数据.
	 * @Desc 对double数据进行相乘并取精度.
	 */
	public static double mul(double d1, double d2) {
		try {
			BigDecimal bd1 = new BigDecimal(Double.toString(d1));
			BigDecimal bd2 = new BigDecimal(Double.toString(d2));
			return bd1.multiply(bd2).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点相除，精度计算后的数据.
	 * @Desc 对double数据进行相除并取精度.
	 */
	public static double div(double d1, double d2, int scale) {
		try {
			BigDecimal bd1 = new BigDecimal(Double.toString(d1));
			BigDecimal bd2 = new BigDecimal(Double.toString(d2));
			return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @desc TODO
	 * @date 2017年3月9日
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @param scale
	 *            保留小数位
	 * @param roundingMode
	 *            取舍模式
	 * @return 浮点相除，精度计算后的数据.
	 */
	public static BigDecimal div(double d1, double d2, int scale, int roundingMode) {
		try {
			BigDecimal bd1 = new BigDecimal(Double.toString(d1));
			BigDecimal bd2 = new BigDecimal(Double.toString(d2));
			return bd1.divide(bd2, scale, roundingMode);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点数比较的返回结果
	 * @Desc 浮点数取精度比较
	 */
	public static int compareTo(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.compareTo(bd2);
	}

	public static boolean valInRange(int target, int min, int max) {
		return (target >= min) && (target <= max);
	}
	
	/**
	 * @desc 生成随机数列表(统一集合返回，根据不同条件生成)
	 * @date 2017年4月27日
	 * @param count
	 *            生成随机数个数
	 * @param repeatable
	 *            是否可重复 true/false
	 * @param under
	 *            随机数范围最小值
	 * @param over
	 *            随机数范围最大值
	 * @param filterRandomArr
	 *            要过滤的随机数列表
	 * @return 生成随机数列表
	 */
	public static Integer[] genRamdomNum(int count, boolean repeatable, int under, int over, int[] filterRandomArr) {
		if (count < 1) {
			return new Integer[0];
		}
		// 随机数列表
		Integer[] data = new Integer[count];
		// 临时数字
		int temp = 0;
		if (filterRandomArr != null && filterRandomArr.length > 0) {
			// 有过滤列表，生成under~over(含under,over)以内随机数,且控制是否重复,且过滤filterRandomArr中的数
			int filterLen = filterRandomArr.length;
			for (int i = 0; i < count; i++) {
				temp = (int) Math.floor(Math.random() * (over - under + 1) + under);
				// 如果包含过滤的随机数且生成个数不大于over-under+1-filterLen(因为最多可生成over-under+1-filterLen的数量)，则重新生成
				if (filterLen > 0 && count <= (over - under + 1 - filterLen)) {
					while (ArrayUtil.contains(filterRandomArr, temp)) {
						temp = (int) Math.floor(Math.random() * (over - under + 1) + under);
					}
				}
				// 不可重复且生成个数不大于over-under+1-filterLen(因为最多可生成over-under+1-filterLen的数量)
				if (!repeatable && count <= (over - under + 1 - filterLen)) {
					while (ArrayUtil.contains(data, temp) || ArrayUtil.contains(filterRandomArr, temp)) {
						temp = (int) Math.floor(Math.random() * (over - under + 1) + under);
					}
				}
				data[i] = temp;
			}
		} else {
			// 无过滤列表，则生成under~over(含under,over)以内随机数,且控制是否重复
			for (int i = 0; i < count; i++) {
				temp = (int) Math.floor(Math.random() * (over - under + 1) + under);
				// 不可重复且生成个数不大于over - under + 1(因为最多可生成 over - under + 1 的数量)
				if (!repeatable && count <= (over - under + 1)) {
					while (ArrayUtil.contains(data, temp)) {
						temp = (int) Math.floor(Math.random() * (over - under + 1) + under);
					}
				}
				data[i] = temp;
			}
		}
		// 升序排列
		Arrays.sort(data);
		return data;
	}

	/**
	 * @desc 判断是否是正整数
	 * @date 2017年7月20日
	 * @param s
	 *            目标数字
	 * @return 判断是否是正整数
	 */
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim())) {
			return s.matches("^[0-9]*$");
		}
		return false;
	}

    public static Float parseString(String string) {
        return "".equals(string) || null == string ? 0f : Float.valueOf(string);
    }
    
	public static Double parseDouble(String str) {
		if (!ObjectUtil.isEmpty(str)) {
			try {
				return Double.parseDouble(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Integer parseInt(String str) {
		if (!ObjectUtil.isEmpty(str)) {
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Integer[] aa = genRamdomNum(6, false, 1, 33, new int[]{2});
	    for (Integer i : aa) {
			System.out.print(i+",");
		}
	}
}
