package com.bh.live.trade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.bh.live.pojo.res.trade.BitTabRes;
import com.bh.live.pojo.res.trade.ItemTabRes;
import com.bh.live.pojo.res.trade.PlayTabRes;
import com.bh.live.trade.dao.PlayBetDao;
import com.bh.live.trade.dao.PlayBitDao;
import com.bh.live.trade.dao.PlayDao;
import com.bh.live.trade.dao.PlayItemDao;
import com.bh.live.trade.service.IBetTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lgs
 * @title: BetTabServiceImpl
 * @projectName java_live-cp
 * @description: 竞猜 发布竞猜tab项
 * @date 2019/8/6  20:38
 */
@Service
public class BetTabServiceImpl implements IBetTabService {

    private final String parent = "0";
    @Autowired
    private PlayDao playDao;
    @Autowired
    private PlayItemDao playItemDao;
    @Autowired
    private PlayBitDao playBitDao;
    @Autowired
    private PlayBetDao playBetDao;

    @Override
    public Map<String, Object> selectTab(Integer seedNo) {
        List<PlayTabRes> playTabRes = playDao.selectPlayRes(seedNo);
        playTabRes.forEach(v -> {
            List<BitTabRes> bitTabRes = playBitDao.selectBitTabRes(v.getPlayNo());
            if (CollectionUtil.isNotEmpty(bitTabRes)) {
                bitTabRes.forEach(b -> {
                    List<ItemTabRes> itemTabRes = playItemDao.selectItemTabRes(b.getBitNo());
                    b.setItems(itemTabRes);
                });
                v.setBits(bitTabRes);
            }
        });
//        List<PlayTabRes> result = new ArrayList<>();
//        playTabRes.forEach(v -> {
//            if (parent.equals(v.getParentNo())) {
//                List<PlayTabRes> child = playTabRes.stream().filter(item -> item.getParentNo().equals(v.getPlayNo())).collect(Collectors.toList());
//                v.setChild(child);
//                result.add(v);
//            }
//        });
        List<PlayTabRes> result = new ArrayList<>();
        Map<String, Object> resultMap = MapUtil.newHashMap();
        playTabRes.forEach(v -> {
            if (parent.equals(v.getParentNo())) {
                List<PlayTabRes> child = playTabRes.stream().filter(item -> item.getParentNo().equals(v.getPlayNo())).collect(Collectors.toList());
//                Map<String, PlayTabRes> playTabResMap = MapUtil.newHashMap();
//                if (CollectionUtil.isNotEmpty(child)) {
//                    child.forEach(p -> {
//                        playTabResMap.put(p.getPlayNo(), p);
//                    });
//                } else {
//                    playTabResMap.put(v.getPlayNo(), v);
//                }
                if (CollectionUtil.isNotEmpty(child)) {
                    child.forEach(p -> resultMap.put(v.getPlayNo(), child));
                } else {
                    resultMap.put(v.getPlayNo(), v);
                }

            }
        });

        return resultMap;
    }
}
