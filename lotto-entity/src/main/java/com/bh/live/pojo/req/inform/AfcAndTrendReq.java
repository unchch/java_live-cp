package com.bh.live.pojo.req.inform;

import lombok.Data;

@Data
public class AfcAndTrendReq {
    private Integer expect;
    private int variety;

    public AfcAndTrendReq() {
    }

    public AfcAndTrendReq(Integer expect, int variety) {
        this.expect = expect;
        this.variety = variety;
    }
}
