package com.bh.live.common.helper;/**
 * Create by Administrator ON 2019/7/4
 */


import com.bh.live.model.inform.Omission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/4 15:03
 * @desc
 * @Version 1.0
 */
public class NumberHandlerHelper {

    /**
     * 根据彩种获取号码范围
     *
     * @param varietyType
     * @return
     */
    public static Integer[] getVarietyNumber(int varietyType) {
        final Integer[] str = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] str2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] str3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Integer[] str4 = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        //幸运快艇/pk10
        if (varietyType == 1 || varietyType == 0) {
            return str;
        }
        //重时/圣地彩
        if (varietyType == 2 || varietyType == 3) {
            return str2;
        }
        if (varietyType == 9) {//11选5
            return str3;
        }
        if (varietyType == 4) {//江苏快3
            return str4;
        }
        return null;
    }
    public static List<String> getCrownSubFiled(Integer number) {
        List<String> res = new ArrayList<>();
        String field = "";
        String namingHump = "";
        switch (number) {
            case 1:
                field = "single_double";
                namingHump = "numberOneBall";
                break;
            case 2:
                field = "big_small";
                namingHump = "numberTwoBall";
                break;
        }
        res.add(field);
        res.add(namingHump);
        return res;
    }
    
    public static List<String> getDragonTigerFiled(Integer number) {
        List<String> res = new ArrayList<>();
        String field = "";
        String namingHump = "";
        switch (number) {
            case 1:
                field = "first_dragon_tiger";
                namingHump = "numberOneBall";
                break;
            case 2:
                field = "second_dragon_tiger";
                namingHump = "numberTwoBall";
                break;
            case 3:
                field = "third_dragon_tiger";
                namingHump = "numberThreeBall";
                break;
            case 4:
                field = "fourth_dragon_tiger";
                namingHump = "numberFourBall";
                break;
            case 5:
                field = "fifth_dragon_tiger";
                namingHump = "numberFiveBall";
                break;
        }
        res.add(field);
        res.add(namingHump);
        return res;
    }
    
    public static List<String> getBallFiled(Integer number) {
        List<String> res = new ArrayList<>();
        String field = "";
        String namingHump = "";
        switch (number) {
            case 1:
                field = "number_one_ball";
                namingHump = "numberOneBall";
                break;
            case 2:
                field = "number_two_ball";
                namingHump = "numberTwoBall";
                break;
            case 3:
                field = "number_three_ball";
                namingHump = "numberThreeBall";
                break;
            case 4:
                field = "number_four_ball";
                namingHump = "numberFourBall";
                break;
            case 5:
                field = "number_five_ball";
                namingHump = "numberFiveBall";
                break;
            case 6:
                field = "number_six_ball";
                namingHump = "numberSixBall";
                break;
            case 7:
                field = "number_seven_ball";
                namingHump = "numberSevenBall";
                break;
            case 8:
                field = "number_eight_ball";
                namingHump = "numberEightBall";
                break;
            case 9:
                field = "number_nine_ball";
                namingHump = "numberNineBall";
                break;
            case 10:
                field = "number_ten_ball";
                namingHump = "numberTenBall";
                break;
            case 11:
                field = "crown_sub";
                namingHump = "crownSub";
                break;
        }
        res.add(field);
        res.add(namingHump);
        return res;
    }

    public static String getNumberDesc(int varietyType, Integer number) {
        String numberDesc = "";
        switch (number) {
            case 1:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "冠军";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第一球";
                } else if (varietyType == 9) {
                    numberDesc = "01";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 2:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "亚军";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第二球";
                } else if (varietyType == 9) {
                    numberDesc = "02";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 3:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第三名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第三球";
                } else if (varietyType == 9) {
                    numberDesc = "03";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 4:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第四名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第四球";
                } else if (varietyType == 9) {
                    numberDesc = "04";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 5:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第五名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第五球";
                } else if (varietyType == 9) {
                    numberDesc = "05";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 6:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第六名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第六球";
                } else if (varietyType == 9) {
                    numberDesc = "06";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 7:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第七名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第七球";
                } else if (varietyType == 9) {
                    numberDesc = "07";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 8:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第八名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第八球";
                } else if (varietyType == 9) {
                    numberDesc = "08";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 9:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第九名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第九球";
                } else if (varietyType == 9) {
                    numberDesc = "09";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 10:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第十名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第十球";
                } else if (varietyType == 9) {
                    numberDesc = "10";
                } else {
                    numberDesc = number.toString();
                }
                break;
        }
        return numberDesc;
    }
    public static String getdragonTigerDesc(int varietyType, Integer number) {
        String numberDesc = "";
        switch (number) {
            case 1:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "冠军";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第一球";
                } else if (varietyType == 9) {
                    numberDesc = "01";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 2:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "亚军";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第二球";
                } else if (varietyType == 9) {
                    numberDesc = "02";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 3:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第三名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第三球";
                } else if (varietyType == 9) {
                    numberDesc = "03";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 4:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第四名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第四球";
                } else if (varietyType == 9) {
                    numberDesc = "04";
                } else {
                    numberDesc = number.toString();
                }
                break;
            case 5:
                if (varietyType == 0 || varietyType == 1) {
                    numberDesc = "第五名";
                } else if (varietyType == 2 || varietyType == 3) {
                    numberDesc = "第五球";
                } else if (varietyType == 9) {
                    numberDesc = "05";
                } else {
                    numberDesc = number.toString();
                }
                break;
        }
        return numberDesc;
    }
    public static String getFieldValue(Omission omission, int number, String statType) {
        String result = "";
        if (!(statType == null)) {
            if (statType.equals("singleDouble")) {
                result = omission.getSingleDouble();
            }
            if (statType.equals("bigSmall")) {
                result = omission.getBigSmall();
            }
            if (statType.equals("numberBall")) {
                result = omission.getNumberBall().toString();
            }
            if (statType.equals("crownSub")) {
                result = omission.getCrownSub().toString();
            }
        } else {
            if (number == 1) {
                result = omission.getNumberOneBall();
            }
            if (number == 2) {
                result = omission.getNumberTwoBall();
            }
            if (number == 3) {
                result = omission.getNumberThreeBall();
            }
            if (number == 4) {
                result = omission.getNumberFourBall();
            }
            if (number == 5) {
                result = omission.getNumberFiveBall();
            }
            if (number == 6) {
                result = omission.getNumberSixBall();
            }
            if (number == 7) {
                result = omission.getNumberSevenBall();
            }
            if (number == 8) {
                result = omission.getNumberEightBall();
            }
            if (number == 9) {
                result = omission.getNumberNineBall();
            }
            if (number == 10) {
                result = omission.getNumberTenBall();
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String fieldCondition = "";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i <= 5; i++) {
            String ifStr = "if(" + NumberHandlerHelper.getBallFiled(i).get(0) + "%2=0,'双','单') " + NumberHandlerHelper.getBallFiled(i).get(1) + ",";
            stringBuffer.append(ifStr);
        }
        fieldCondition = stringBuffer.substring(0, stringBuffer.length() - 1);
        System.out.println(fieldCondition);
    }
}
