package com.bh.live;

import com.bh.live.common.result.Result;
import com.bh.live.service.AfcAndTrendService;
import com.bh.live.service.DewdropService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    DewdropService dewdropService;

    @Resource
    AfcAndTrendService afcAndTrendSerice;


    @Test
    public void test(){
       // System.out.println(new File("/").getClass().getResource("/").toString());
  //      afcAndTrendSerice.queryAfcAndTrendPage(30,1);
        Result aa = dewdropService.queryInfoDome("", "");
        Map<String, Object> bbb= dewdropService.queryDewdropChampion("2019-07-22","1");
        System.out.println("a"+aa +" bb"+bbb);


    }

}
