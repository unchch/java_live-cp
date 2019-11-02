package com.bh.live.common.utils;


import com.bh.live.pojo.res.inform.AfcAndTrendRes;

public class ModelUtil {

    public static AfcAndTrendRes setValue(AfcAndTrendRes dto, int ball, int ballValue, int count){
        switch (ball){
            case 3:
                dto.setThreeCount(count);
                break;
            case 4:
                dto.setFourCount(count);
                break;
            case 5:
                dto.setFiveCount(count);
                break;
            case 6:
                dto.setSixCount(count);
                break;
            case 7:
                dto.setSevenCount(count);
                break;
            case 8:
                dto.setEightCount(count);
                break;
            case 9:
                dto.setNineCount(count);
                break;
            case 10:
                dto.setTenCount(count);
                break;
            case 11:
                dto.setElevenCount(count);
                break;
            case 12:
                dto.setTwelveCount(count);
                break;
            case 13:
                dto.setThirteenCount(count);
                break;
            case 14:
                dto.setFourteenCount(count);
                break;
            case 15:
                dto.setFifteenCount(count);
                break;
            case 16:
                dto.setSixteenCount(count);
                break;
            case 17:
                dto.setSeventeenCount(count);
                break;
            case 18:
                dto.setEighteenCount(count);
                break;
            case 19:
                dto.setNineteenCount(count);
                break;
        }
        return dto;
    }

    public static String getPosition(int position){
        switch (position){
            case 1:
                return "冠军";
            case 2:
                return "亚军";
            case 3:
                return "第三名";
            case 4:
                return "第四名";
            case 5:
                return "第五名";
            case 6:
                return "第六名";
            case 7:
                return "第七名";
            case 8:
                return "第八名";
            case 9:
                return "第九名";
            case 10:
                return "第十名";
        }
        return "";
    }

    public static String getSscPosition(int position){
        switch (position){
            case 1:
                return "第一球";
            case 2:
                return "第二球";
            case 3:
                return "第三球";
            case 4:
                return "第四球";
            case 5:
                return "第五球";
            case 6:
                return "第六球";
            case 7:
                return "第七球";
            case 8:
                return "第八球";
        }
        return "";
    }
}
