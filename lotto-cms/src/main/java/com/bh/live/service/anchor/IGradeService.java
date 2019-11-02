package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.Grade;
import com.bh.live.pojo.req.anchor.GradeReq;
import com.bh.live.pojo.res.anchor.GradeRes;

import java.util.List;

/**
 * <p>
 * 用户、主播等级表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
public interface IGradeService extends IService<Grade> {

    /**
     * 新增等级配置
     *
     * @param grade
     */
    void addGradeConf(GradeReq grade);

    /**
     * 获取等级列表（不分页）
     * callType : 在那里调用，返回的结果不一样
     * @return
     */
    Object getGrades(String callType);

    /**
     * 修改等级
     * @param grade
     */
    void updateGrade(GradeReq grade);

}
