package com.bh.live.lottery;

import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.service.lottery.ISeedCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName SeedCategoryServiceTest
 * @description: SeedCategoryServiceTest
 * @author: yq.
 * @date 2019-07-26 15:33:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SeedCategoryServiceTest {

    @Autowired
    private ISeedCategoryService seedCategoryService;

    @Test
    public void buildSeedCategoryTreeTest() {
        List<SeedCategoryRes> list = seedCategoryService.buildSeedCategoryTree();
        list.forEach(System.out::println);
    }
}
