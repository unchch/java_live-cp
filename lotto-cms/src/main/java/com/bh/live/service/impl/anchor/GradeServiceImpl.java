package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.anchor.GradeDao;
import com.bh.live.model.anchor.Grade;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.req.anchor.GradeReq;
import com.bh.live.pojo.res.anchor.GradeRes;
import com.bh.live.service.anchor.IGradeService;
import com.bh.live.utils.JudgeRangeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户、主播等级表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeDao, Grade> implements IGradeService {

    @Override
    public void addGradeConf(GradeReq req) {
        Grade grade = new Grade();
        CommonUtil.beanCopy(req, grade);
        String[] split = req.getLvValue().split("-");
        grade.setLvMin(Integer.valueOf(split[0]));
        grade.setLvMax(Integer.valueOf(split[1]));
        grade.setLvType(1);
        List<Grade> grades = (List<Grade>) getGrades("serviceCall");
        if (grades == null || grades.size() == 0) {
            this.save(grade);
        }else {
            //判断输入的等级范围是否正确
            JudgeRangeUtil.judgeRange(grade, grades, "grade");
            this.save(grade);
        }
    }


    @Override
    public Object getGrades(String callType) {
        QueryWrapper<Grade> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Grade::getIsDel, 0);
        List<Grade> list = this.list(wrapper);
        //排序返回
        list = list.stream().sorted(Comparator.comparingInt(Grade::getLvMin)).collect(Collectors.toList());
        if (callType.equals("serviceCall")) {
            return list;
        }
        List<GradeRes> resList = new ArrayList<>();
        list.forEach(item -> {
            GradeRes res = new GradeRes();
            CommonUtil.beanCopy(item, res);
            res.setLvValue(item.getLvMin() + "-" + item.getLvMax());
            resList.add(res);
        });
        return resList;
    }

    @Override
    public void updateGrade(GradeReq req) {
        Grade grade = new Grade();
        CommonUtil.beanCopy(req, grade);
        String[] split = req.getLvValue().split("-");
        grade.setLvMin(Integer.valueOf(split[0]));
        grade.setLvMax(Integer.valueOf(split[1]));
        //判断输入的等级范围是否正确
        List<Grade> grades = (List<Grade>) getGrades("serviceCall");
        JudgeRangeUtil.judgeRange(grade, grades, "grade");

        this.updateById(grade);
    }
}
