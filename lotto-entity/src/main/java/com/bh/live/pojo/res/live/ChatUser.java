package com.bh.live.pojo.res.live;

import lombok.Data;

/**
 * @Description: 聊天室用户信息
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/6 18:03
 */
@Data
public class ChatUser {
    private  Integer userId;
    private  String  nickName;
    private  String avatar;
    private  Integer level;
    private  String levelName;
    private  String levelIcon;
    /**
     * 用户角色  user/manager/anchor  用户/房管/主播
     */
    private  String role;
    private  String flag;
    private Integer atUser;

    public  ChatUser(){}
    public ChatUser(Integer userId,String  nickName){
        this.userId=userId;
        this.nickName=nickName;
    }
    public ChatUser(Integer userId,String  nickName,String role){
        this(userId,nickName);
        this.role=role;
    }

}
