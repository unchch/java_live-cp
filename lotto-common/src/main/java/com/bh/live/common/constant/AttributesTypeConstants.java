package com.bh.live.common.constant;

import java.util.*;

public class AttributesTypeConstants {

    List<Map<Integer,Map<String,List<Integer>>>> mapList = new LinkedList<>();

    static {

        //生肖
        Map<Integer,List<Integer>> zodiacMap = new HashMap<Integer, List<Integer>>(){{ put(1, Arrays.asList(1,2,3)); }};



        //五行
        Map<Integer, Map<String,List<Integer>>> fiveElementsMap = new HashMap();
        //家禽野兽
        Map<Integer, Map<String,List<Integer>>> poultryBeastMap = new HashMap();
        //男女生肖
        Map<Integer, Map<String,List<Integer>>> malegirlXiaoMap = new HashMap();
        //天地生肖
        Map<Integer, Map<String,List<Integer>>> tdXiaoMap = new HashMap();
        //四季生肖
        Map<Integer, Map<String,List<Integer>>> sjXiaoMap = new HashMap();
        //琴棋书画
        Map<Integer, Map<String,List<Integer>>> chMap = new HashMap();
        //三色生肖
        Map<Integer, Map<String,List<Integer>>> ssdiacMap = new HashMap();
    }
}
