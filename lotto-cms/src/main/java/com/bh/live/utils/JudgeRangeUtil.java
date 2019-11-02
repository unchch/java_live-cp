package com.bh.live.utils;

import com.bh.live.model.anchor.Grade;
import com.bh.live.model.configuration.GradeDivideInto;

import java.util.List;

/**
 * @ClassName JudgeRangeUtil
 * @description: 判断输入的等级范围是否正确
 * @author: Morphon
 * @date 2019-08-12
 */
public class JudgeRangeUtil {

    public static void judgeRange(Object inParam, Object list, String type) {
        boolean flag = false;
        List<Grade> grades = null;
        List<GradeDivideInto> gradeDivideIntos = null;
        Grade grade = null;
        GradeDivideInto divideInto = null;
        String errorResult = "";
        int size = 0;
        Integer index = null;
        if (type.equals("grade")) {
            grades = (List<Grade>) list;
            grade = (Grade) inParam;
            size = grades.size();
            errorResult = grade.getLvValue();
            Integer id = grade.getId();
            //当等级修改的时候，跳过当前修改的一行
            if (id != null) {
                for (int i = 0; i < size; i++) {
                    if (grades.get(i).getId().compareTo(grade.getId()) == 0) {
                        index = i;
                    }
                }
            }
            if (grade.getLvMin() > grade.getLvMax()) {
                throw new RuntimeException("后值不能比前值大:" + errorResult);
            }
        }
        if (type.equals("divide")) {
            gradeDivideIntos = (List<GradeDivideInto>) list;
            divideInto = (GradeDivideInto) inParam;
            Integer id = divideInto.getId();
            size = gradeDivideIntos.size();
            errorResult = divideInto.getMinLv() + "-" + divideInto.getMaxLv();
            //当等级修改的时候，跳过当前修改的一行
            if (id != null) {
                for (int i = 0; i < size; i++) {
                    if (gradeDivideIntos.get(i).getId().compareTo(divideInto.getId()) == 0) {
                        index = i;
                    }
                }
            }
            if (divideInto.getMinLv() > divideInto.getMaxLv()) {
                throw new RuntimeException("后值不能比前值大:" + errorResult);
            }
        }
        for (int i = 0; i < size; i++) {
            //上一条的最大记录必须小于新增的最小值
            if (size == 1) {
                if (type.equals("grade")) {
                    if (grades.get(i).getLvMax() < grade.getLvMin()) {
                        flag = true;
                    }
                } else if (type.equals("divide")) {
                    if (gradeDivideIntos.get(i).getMaxLv() < divideInto.getMinLv()) {
                        flag = true;
                    }
                }
            } else {
                int next = (i + 1);
                if (next < size) {
                    //修改数据时进来
                    if (index != null) {
                        //如果修改的是第一条数据
                        if (index.compareTo(0) == 0) {
                            if (type.equals("grade")) {
                                if (grade.getLvMax() < grades.get(next).getLvMin()) {
                                    flag = true;
                                    break;
                                }
                            } else if (type.equals("divide")) {
                                if (divideInto.getMaxLv() < gradeDivideIntos.get(next).getMinLv()) {
                                    flag = true;
                                    break;
                                }
                            }
                            break;
                        }
                        //如果修改的是最后一条数据
                        if (index.compareTo(size - 1) == 0) {
                            if (type.equals("grade")) {
                                if (grade.getLvMin() > grades.get(size - 2).getLvMax()) { //最后一条的最小值大于倒数第二条的最大值
                                    flag = true;
                                    break;
                                }
                            } else if (type.equals("divide")) {
                                if (divideInto.getMinLv() > gradeDivideIntos.get(size - 2).getMaxLv()) {
                                    flag = true;
                                    break;
                                }
                            }
                            break;
                        }
                        if (index.compareTo(next) == 0) {
                            next = next + 1;
                            //continue;
                        }
                    }

                    if (type.equals("grade")) {
                        if (grade.getLvMin() > grades.get(size - 1).getLvMax()) {
                            flag = true;
                            break;
                        } else {
                            if ((grades.get(i).getLvMax() < grade.getLvMin()) &&
                                    (grade.getLvMax() < grades.get(next).getLvMin())) {
                                flag = true;
                                break;
                            }
                        }
                    } else if (type.equals("divide")) {
                        if (divideInto.getMinLv() > gradeDivideIntos.get(size - 1).getMaxLv()){
                            flag = true;
                            break;
                        }else {
                            if ((gradeDivideIntos.get(i).getMaxLv() < divideInto.getMinLv()) &&
                                    (divideInto.getMaxLv() < gradeDivideIntos.get(next).getMinLv())) {
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!flag) {
            throw new RuntimeException("请填写正确的等级范围：" + errorResult);
        }
    }

}
