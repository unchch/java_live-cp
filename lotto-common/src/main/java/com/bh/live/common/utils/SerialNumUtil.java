package com.bh.live.common.utils;

import cn.hutool.core.date.DateUtil;
import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;

import java.util.Date;

/**
 * @author lgs
 * @title: SerialNumUtil
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/7/27  16:25
 */
public class SerialNumUtil {


    /**
     * 订单
     */
    private static final Number ORDER = new Number(99999, 5);
    /**
     * 流水
     */
    private static final Number WATER = new Number(99999, 5);


    private static final String SERVICE_ID;

    static {
        //服务ip取本机ip第4段，不足3位用0填充
        String ip = IpUtil.getLocalIP();
        ip = ip.substring(ip.lastIndexOf(".") + 1);
        if (ip.length() == 1) {
            ip = "00" + ip;
        } else if (ip.length() == 2) {
            ip = "0" + ip;
        }
        SERVICE_ID = ip;
    }


    /**
     * 获取交易流水号
     *
     * @param transTypeEnum
     * @return
     */
    public static String getSerialNum(TransEnum.TransTypeEnum transTypeEnum) {
        String serialNum = "";
        switch (transTypeEnum) {
            case RECHARGE:
                serialNum = "R" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            case REWARD:
                serialNum = "G" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            case RETURN_PRIZE:
                serialNum = "I" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            case REFUND_MONEY:
                serialNum = "E" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            case DRAWING:
                serialNum = "D" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            case SYSTEM_DEDUCTION:
            case SYSTEM_PRESENTATION:
                serialNum = "S" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            case BUY:
                serialNum = "B" + transTypeEnum.getCode() + ORDER.getNo(SERVICE_ID);
                break;
            case ANCHOR_COMMISSION:
                serialNum = "A" + transTypeEnum.getCode() + WATER.getNo(SERVICE_ID);
                break;
            default:
                throw new ResultJsonException(CodeMsg.E_50006);
        }
        return serialNum;
    }

    /**
     * 获取订单流水号
     *
     * @return
     */
    public static String getOrderNo() {
        return "O" + ORDER.getNo(SERVICE_ID);
    }

//    public static void main(String[] args) {
//        final Number number = new Number(99999, 5);
//        /*
//         * long start = System.currentTimeMillis(); Set<String> set = new
//         * HashSet<>(); for(int i = 0; i<1000_000;i++){
//         * set.add(number.getNo("88")); }
//         * System.out.println(System.currentTimeMillis() - start);
//         * System.out.println(set.size());
//         */
//        final Set<String> set = new ConcurrentSkipListSet<>();
//        for (int i = 0; i < 100; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < 10000; i++) {
//                        String string = SerialNumUtil.getSerialNum(TransEnum.TransTypeEnum.RECHARGE);
//                        set.add(string);
//                        System.out.println(string);
//                    }
//                    System.out.println(set.size());
//                }
//            }).start();
//        }
//    }

    /**
     * @author lgs
     * @desc 生成唯一数
     */
    private static class Number {
        /**
         * 填充数
         */
        private final String ZERO = "0";
        /**
         * 最大递增数
         */
        private final int MAX;
        /**
         * 上次递增数替换时间
         */
        private long lastTime;
        /**
         * 递增数
         */
        private int num = 0;
        /**
         * 数字长度，判断是否需要填充
         */
        private int length;
        /**
         * 是否添加格式化日期
         */
        private boolean addDate;

        public Number(int max, int length) {
            this(max, true, length);
        }

        public Number(int max, boolean addDate, int length) {
            super();
            this.MAX = max;
            lastTime = System.currentTimeMillis() / 1000;
            this.length = length;
            this.addDate = addDate;
        }

        /**
         * 同步生成唯一订单号
         *
         * @param serviceId
         * @return
         * @author WuLong
         * @Version 1.0
         * @CreatDate 2017年3月9日 下午4:03:01
         */
        public synchronized String getNo(String serviceId) {
            int mantissa = 0;
            long nowTime = 0;
            synchronized (this) {
                mantissa = ++num;
                nowTime = System.currentTimeMillis() / 1000;
                if (num > MAX) {
                    mantissa = num = 1;
                    // 需要判断本次递增周期与上次是否相同，相同需要循环等待下个周期
                    while (nowTime == lastTime) {
                        nowTime = System.currentTimeMillis() / 1000;
                    }
                    lastTime = nowTime;
                }
            }
            String str = String.valueOf(mantissa);
            for (int i = str.length(); i < length; i++) {
                str = ZERO + str;
            }
            if (addDate) {
                return DateUtil.format(new Date(nowTime * 1000), "yyMMddHHmmss") + serviceId + str;
            }
            return serviceId + str;
        }
    }

}
