package com.bh.live.model.inform;


import lombok.Data;

import java.io.Serializable;

@Data
public class Dragon implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    private  Long ldId;
    /**
     * 排名
     */
    private String rank;

    /**
     * 长龍期数
     */
    private String ldPeriod;

    /**
     * 类型
     */
    private String ldType;

}
