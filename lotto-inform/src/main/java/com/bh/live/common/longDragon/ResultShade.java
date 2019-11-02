package com.bh.live.common.longDragon;

import lombok.Data;

@Data
public class ResultShade {

    /**
     * 球号
     */
    private Integer ballNum;

    /**
     * 大小
     */
    private String bs;

    /**
     * 龍虎
     */
    private String lh;

    /**
     * 单双
     */
    private String ds;

    /**
     * 总和单双(亚冠和 等 。。。。。)
     */
    private String zhSingleDouble;

    /**
     * 总和大小
     */
    private String zhBigSmall;

    /**
     * 质数
     */
    private String zs;

    /**
     * 合数
     */
    private String hs;

    /**
     * 尾号 --- 大小
     */
    private String tailNumber;

    /**
     * 和单双
     */
    private String hSingleDouble;

    /**
     * 尾大小
     */
    private String wBigSmall;

    public ResultShade(int ballNum, String bs, String lh, String ds, String zhSingleDouble,
                       String zhBigSmall, String zs, String hs,String tailNumber,String hSingleDouble,String wBigSmall) {
        super();
        this.ballNum = ballNum;
        this.bs = bs;
        this.lh = lh;
        this.ds = ds;
        this.zhSingleDouble = zhSingleDouble;
        this.zhBigSmall = zhBigSmall;
        this.zs = zs;
        this.hs = hs;
        this.tailNumber = tailNumber;
        this.hSingleDouble = hSingleDouble;
        this.wBigSmall = wBigSmall;
    }
}
