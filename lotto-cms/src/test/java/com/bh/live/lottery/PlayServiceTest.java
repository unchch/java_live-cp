package com.bh.live.lottery;

import com.bh.live.pojo.res.lottery.PlayOddsRes;
import com.bh.live.service.lottery.IPlayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PlayServiceTest
 * @description: PlayServiceTest
 * @author: yq.
 * @date 2019-07-29 11:07:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayServiceTest {

    @Autowired
    private IPlayService playService;

    @Test
    public void selectPlayOdds() {
        Map<String, List<PlayOddsRes>> ret = playService.selectPlayOdds(601);
        ret.forEach((k, v) -> System.out.println(String.format("%s:%s", k, v)));
    }

}
