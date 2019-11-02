package com.bh.live.service.impl;

import com.bh.live.dao.WalkAlarmDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.service.WalkAlarmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 号码走位报警
 *
 * @author Administrator
 * @title: WalkAlarmServiceImpl
 * @projectName live
 * @description: TODO
 * @date 2019/6/17  21:33
 */
@Service
public class WalkAlarmServiceImpl implements WalkAlarmService {

    @Resource
    private WalkAlarmDao walkAlarmDao;

    /**
     * 获取报警号码列表
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String,Object>> getWalkAlarmList(@Param("param") Map<String, Object> param) {
//        param.put("numberBall","1,2,3,4,5,6,7,8");
//        param.put("ranking","1,2,3,4,5,6,7,8");
        if (param.get("ranking") != null && param.get("numberBall") != null) {
            String[] ranking = param.get("ranking").toString().split(",");
            String numberBall = param.get("numberBall").toString();
            //用于拼接主要查询的排名
            StringBuffer mySql = new StringBuffer();
            StringBuffer numberBallsParams =new StringBuffer();
            for (String str : ranking) {
                switch (Integer.parseInt(str)) {
                    case 1:
                        String oneSql=",number_one_ball as numberOneBall";
                        mySql.append(oneSql);
                        String numberOneBall = " and number_one_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberOneBall);
                        break;
                    case 2:
                        String twoSql=",number_two_ball as numberTwoBall";
                        mySql.append(twoSql);
                        String numberTwoBall = " and number_two_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberTwoBall);
                        break;
                    case 3:
                        String threeSql=",number_three_ball as numberThreeBall";
                        mySql.append(threeSql);
                        String numberThreeBall = " and number_three_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberThreeBall);
                        break;
                    case 4:
                        String fourSql=",number_four_ball as numberFourBall";
                        mySql.append(fourSql);
                        String numberFourBall = " and number_four_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberFourBall);
                        break;
                    case 5:
                        String fiveSql=",number_five_ball as numberFiveBall";
                        mySql.append(fiveSql);
                        String numberFiveBall = " and number_five_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberFiveBall);
                        break;
                    case 6:
                        String sixSql=",number_six_ball as numberSixBall";
                        mySql.append(sixSql);
                        String numberSixBall = " and number_six_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberSixBall);
                        break;
                    case 7:
                        String sevenSql=",number_seven_ball as numberSevenBall";
                        mySql.append(sevenSql);
                        String numberSevenBall = " and number_seven_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberSevenBall);
                        break;
                    case 8:
                        String eightSql=",number_eight_ball as numberEightBall";
                        mySql.append(eightSql);
                        String numberEightBall = " and number_eight_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberEightBall);
                        break;
                    case 9:
                        String nineSql=",number_nine_ball as numberNineBall";
                        mySql.append(nineSql);
                        String numberNineBall = " and number_nine_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberNineBall);
                        break;
                    case 10:
                        String tenSql=",number_ten_ball as numberTenBall";
                        mySql.append(tenSql);
                        String numberTenBall = " and number_ten_ball in (" + numberBall + ")";
                        numberBallsParams.append(numberTenBall);
                        break;
                }
            }
            param.put("mySql",mySql.toString());
            param.put("numberBallsParams", numberBallsParams.toString());

        }
        List<ColorVarietyModelRes> walkAlarmList = walkAlarmDao.getWalkAlarmList(param);
        //赋值给前端
        List<Map<String ,Object>> list=new ArrayList<>();
        if(walkAlarmList==null || walkAlarmList.size()<2){
            return list;
        } else{
            for (ColorVarietyModelRes pkResoultVo:walkAlarmList) {
                Map<String ,Object> map=new HashMap<>();
                map.put("expect",pkResoultVo.getExpect());
                if (pkResoultVo.getNumberOneBall()!=null && !pkResoultVo.getNumberOneBall().equals("")){
                    map.put(pkResoultVo.getNumberOneBall().toString(),"冠军");
                }if(pkResoultVo.getNumberTwoBall()!=null && !pkResoultVo.getNumberTwoBall().equals("")){
                    map.put(pkResoultVo.getNumberTwoBall().toString(),"亚军");
                }if(pkResoultVo.getNumberThreeBall()!=null && !pkResoultVo.getNumberThreeBall().equals("")){
                    map.put(pkResoultVo.getNumberThreeBall().toString(),"第三名");
                }if(pkResoultVo.getNumberFourBall()!=null && !pkResoultVo.getNumberFourBall().equals("")){
                    map.put(pkResoultVo.getNumberFourBall().toString(),"第四名");
                }if(pkResoultVo.getNumberFiveBall()!=null && !pkResoultVo.getNumberFiveBall().equals("")){
                    map.put(pkResoultVo.getNumberFiveBall().toString(),"第五名");
                } if(pkResoultVo.getNumberSixBall()!=null && !pkResoultVo.getNumberSixBall().equals("")){
                    map.put(pkResoultVo.getNumberSixBall().toString(),"第六名");
                }if(pkResoultVo.getNumberSevenBall()!=null && !pkResoultVo.getNumberSevenBall().equals("")){
                    map.put(pkResoultVo.getNumberSevenBall().toString(),"第七名");
                }if(pkResoultVo.getNumberEightBall()!=null && !pkResoultVo.getNumberEightBall().equals("")){
                    map.put(pkResoultVo.getNumberEightBall().toString(),"第八名");
                }if(pkResoultVo.getNumberNineBall()!=null && !pkResoultVo.getNumberNineBall().equals("")){
                    map.put(pkResoultVo.getNumberNineBall().toString(),"第九名");
                }if (pkResoultVo.getNumberTenBall()!=null && !pkResoultVo.getNumberTenBall().equals("")){
                    map.put(pkResoultVo.getNumberTenBall().toString(),"第十名");
                }
                list.add(map);
            }

            return list;
        }

    }
}
