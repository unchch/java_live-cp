package com.bh.live.common.utils;

import java.math.BigDecimal;

/**
 * @author longguoyou
 * @date 2017年2月9日 上午11:17:50
 * @desc 彩票相关计算
 */
public class CalculatorUtil {

    /**
     * @param num
     * @return
     * @author longguoyou
     * @date 2017年2月9日 上午11:23:27
     * @desc 阶乘
     */
    public static int factorial(int num) {
        return (num > 1) ? num * factorial(num - 1) : 1;
    }

    /**
     * @param total 总个数
     * @param get   取个数
     * @return
     * @author longguoyou
     * @date 2017年2月9日 上午11:27:10
     * @desc 计算组合数
     */
    public static int combination(int total, int get) {
        if (total < get) {
            return 0;
        }
        return (total > get) ? factorial(total) / (factorial(total - get) * factorial(get)) : 1;
    }


    public static void main(String[] args) {
        System.out.println(combination(5,3));
    }
//    /**
//     * 计算投注金额
//     *
//     * @param betNum       注数
//     * @param multiple     倍数
//     * @param singleAmount 单注金额
//     * @return 总金额
//     * @author longguoyou
//     * @date 2017年3月15日
//     */
////    public static double calculateAmount(int betNum, int multiple, double singleAmount) {
////        return MathUtil.mul((betNum * multiple), singleAmount);
////    }

//    /**
//     * 奖金优化和非奖金优化金额计算
//     *
//     * @param betNum
//     * @param multiple
//     * @param singleAmount
//     * @param flag         是否奖金优化
//     * @return
//     * @author longguoyou
//     * @date 2017年5月31日
//     */
//    public static double calculateAmount(int betNum, int multiple, double singleAmount, boolean flag) {
//        if (!flag) {
//            return calculateAmount(betNum, 1, singleAmount);
//        } else {
//            return calculateAmount(betNum, multiple, singleAmount);
//        }
//    }

//    /**
//     * @param list 投注列表
//     * @param len  投注列表size
//     * @param flag 是否混合
//     * @return
//     * @author longguoyou
//     * @date 2017年2月9日 上午11:34:13
//     * @desc 计算单一过关组合注数
//     */
//    public static int combinationZS(List<JczqBuySelectVO> list, int len, boolean flag) {
//        if (flag) {
//            return (len >= 1) ? (list.get(len - 1).getListRelationSp().size() * combinationZS(list, len - 1, flag)) : 1;
//        }
//        return (len >= 1) ? (list.get(len - 1).getListBuySp().size() * combinationZS(list, len - 1, flag)) : 1;
//    }

//    /**
//     * @param list 投注列表
//     * @param len  投注列表size
//     * @param flag 是否混合
//     * @return
//     * @author yuanshangbing
//     * @date 2017年2月9日 上午11:34:13
//     * @desc 计算单一过关组合注数
//     */
//    public static int combinationLQZS(List<JCLQBuySelectVO> list, int len, boolean flag) {
//        if (flag) {
//            return (len >= 1) ? (list.get(len - 1).getListRelationSp().size() * combinationLQZS(list, len - 1, flag)) : 1;
//        }
//        return (len >= 1) ? (list.get(len - 1).getListBuySp().size() * combinationLQZS(list, len - 1, flag)) : 1;
//    }

//    /**
//     * @param map 组合后的投注列表
//     * @return
//     * @author longguoyou
//     * @date 2017年2月9日 上午11:50:16
//     * @desc 计算混合过关注数
//     */
//    public static int combinationMixZS(Map<String, List<JczqBuySelectVO>> map) {
//        int total = 0;
//        for (Map.Entry<String, List<JczqBuySelectVO>> entry : map.entrySet()) {
//            int zs = 1;
//            List<JczqBuySelectVO> listBuySelectVO = entry.getValue();
//            for (JczqBuySelectVO buySelectVO : listBuySelectVO) {
//                zs *= buySelectVO.getListRelationSp().size();
//            }
//            total += zs;
//        }
//        return total;
//    }

    /**
     * 对于位数很多的近似数，当有效位数确定后，其后面多余的数字应该舍去，只保留有效数字最末一位，这种修约（舍入）规则是“四舍六入五成双”，
     * 也即“4舍6入5凑偶”这里“四”是指≤4 时舍去，"六"是指≥6时进上，
     * "五"指的是根据5后面的数字来定，当5后有数时，舍5入1；
     * 当5后无有效数字时，需要分两种情况来讲：①5前为奇数，舍5入1；②5前为偶数，舍5不进。（0是偶数）
     * mod  保留几位有效小数。
     * big 需要处理的数值
     * @return
     */
    public static double cauScale(int mod,BigDecimal big){
        //四舍六入
        if(mod<=0 || big.compareTo(BigDecimal.valueOf(0))<=0){
            return big.doubleValue();
        }
        String mathstr = big+"";
        int dian = mathstr.indexOf(".");
        if(dian>0 && mathstr.length()- dian-1 >mod){
            String base = mathstr.substring(0,dian);
            String adress = mathstr.substring(dian+1,mathstr.length());
            if(adress.length()<=mod){
                base = base+"."+adress;
            }else if(adress.length()>=mod+1){
                int v = Integer.valueOf(adress.substring(mod,mod+1));//精确位小数后一位
                int v1 = Integer.valueOf(adress.substring(mod-1,mod));//精确位小数。
                int m =0 ;//是否需要进位。
                if(v>=6){ //精确位后大于等于6，精确位进一
                    m++;
                }else if(v<=4){//精确位后小于等于4，精确位后舍弃
                }else if(v==5 && v1%2==0){//精确位后为5时，精确位前为偶时，精确位后一位舍弃。
                }else{//精确位后为5时，精确位前为奇时，精确位进一
                    m++;
                }
                String s =adress.substring(0,mod-1);
                base = base+"."+s+v1;
                if(m>0){
                    big = BigDecimal.valueOf(Double.valueOf(base)).add(BigDecimal.valueOf(Math.pow(0.1, mod)));
                }else{
                    big = BigDecimal.valueOf(Double.valueOf(base));
                }
            }
        }
        return big.doubleValue();
    }

    /**
     * 返回盈利率
     * @param profit 利润
     * @param capital 本金
     * @return BigDecimal
     */
    public static BigDecimal getProfitRate(BigDecimal profit,BigDecimal capital){
        if(profit.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO;
        }
        return profit.divide(capital,2,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
