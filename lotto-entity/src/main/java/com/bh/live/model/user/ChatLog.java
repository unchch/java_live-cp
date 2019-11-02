package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播间聊天记录
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-08-7
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@TableName("lotto_chat_log")
public class ChatLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * token
     */
    private String token;

    /**
     * 发送者
     */
    private Integer userId;

    /**
     * json内容
     */
    private String content;

    public ChatLog(Integer userId, String json, String token, Date sendTime,String room) {
        this.userId=userId;
        this.content=json;
        this.token=token;
        this.sendTime=sendTime;
        this.roomId=room;
    }
}
