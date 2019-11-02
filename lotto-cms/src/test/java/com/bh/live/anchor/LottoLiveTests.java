package com.bh.live.anchor;

import com.bh.live.controller.anchorAdmin.AnchorLevelController;
import com.bh.live.pojo.req.anchor.GradeReq;
import com.bh.live.pojo.res.anchor.GradeRes;
import com.bh.live.service.anchor.IGradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoLiveTests {
    @Autowired
    IGradeService gradeService;

    @Autowired
    AnchorLevelController controller;
    /**
     * 测试添加
     */
    @Test
    public void testAdd(){
        GradeReq req = new GradeReq();
        req.setLvName("2");
        req.setLvValue("2-200");
        req.setLvImage("http://www.baidu.com/2.png");
        gradeService.addGradeConf(req);
    }


    /**
     * 测试修改
     */
    @Test
    public void testUpdate(){
        GradeReq req = new GradeReq();
        req.setId(2);
        req.setLvName("2");
        req.setLvValue("2-2000");
        req.setLvImage("http://2.png");
        gradeService.updateGrade(req);
    }

    /**
     * 测试修改
     */
    @Test
    public void testDelete(){
        controller.delete(2);
    }

    /**
     * 查询列表
     */
    @Test
    public  void getList(){
        List<GradeRes> grades = (List<GradeRes>)gradeService.getGrades("controlCall");
        grades.stream().forEach(System.out::println);
    }

}
