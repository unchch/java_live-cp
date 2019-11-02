package com.bh.live.pojo.req.live;

import com.bh.live.pojo.res.live.ChatUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/7 14:16
 */
@Data
@NoArgsConstructor
public class MsgRcpReq {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "彩种")
    private String seedNo;

    @ApiModelProperty(value = "文本内容")
    private String content;

    @ApiModelProperty(value = "消息对象 开奖结果(ChatOpenCode) 竞猜(ChatGuess)")
    private Object obj;

    @ApiModelProperty(value = "用户信息 ")
    private ChatUser user;

    @ApiModelProperty(value = "类型 参考 ChatMsgTypeEnum 枚举类")
    private Integer sysType;

    public  MsgRcpReq(Object obj,Integer sysType,String seedNo){
        this.obj=obj;
        this.sysType=sysType;
        this.seedNo=seedNo;
    }

    public  MsgRcpReq(Object obj,Integer sysType,String seedNo,ChatUser user){
        this(obj,sysType, seedNo);
        this.user=user;

    }

}
