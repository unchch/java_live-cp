package com.bh.live.user.utils.live;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.pojo.req.live.BaseMsgReq;
import com.bh.live.pojo.res.live.*;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;


/**
 * @Description: 直播间发言
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/7 17:27
 */
public class LiveChatUtil {

    private static String merchantKey = "test";

    private static String merchantSecret = "test123";

    private static String msgType = "chat_room";

    private static String channel = "chat_room";

    public static String msgUrl = "api/sendMsg";

    /**
     * 设置请求data数据
     *
     * @param con
     * @param req
     */
    public static void setConectData(Connection con, BaseMsgReq req) {
        con.header("merchantKey", merchantKey);
        con.header("merchantSecret", merchantSecret);
        con.data("msgType", msgType);
        con.data("token", req.getToken());
        con.data("room", req.getRoom());
        if (req.getToken().equals("")) {
            con.data("channel", "lottery");
        } else {
            con.data("channel", channel);
        }

        con.data("event", req.getEvent());
    }

    /**
     * 获取连接
     *
     * @param url
     * @return
     */
    public static Connection getConect(String url) {
        Connection con = Jsoup.connect(PropChatUtil.remoteAddress + url);
        con.header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).ignoreContentType(true);
        return con;
    }

    /**
     * 执行发送消息
     * {
     * sysType:
     * user:{}
     * content:
     * atUser:
     * gift:{}
     * manager:{}
     * openCode:{}
     * guess:{}
     * }
     *
     * @return
     */
    public static String execute(BaseData data) {
        Connection con = getConect(msgUrl);
        setConectData(con, data);
        String str = JSONObject.toJSON(data).toString();
        System.out.println(">>>" + str);
        con.data("data", str);
        Connection.Response res = null;
        try {
            res = con.execute();
            System.out.println(res.body());
            MsgResult result = JSON.toJavaObject(JSONObject.parseObject(res.body()), MsgResult.class);
            if (result.getCode() == 500) {
                throw new ServiceRuntimeException(CodeMsg.E_90011);
            }
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceRuntimeException(CodeMsg.E_90012);
        }
    }

    /**
     * 封装数据
     *
     * @param msg
     * @param user
     * @param gift
     * @param type
     * @return
     */
    public static String sendMsg(BaseMsgReq msg, ChatUser user,
                                 ChatGift gift, Integer type) {
        BaseData data = new BaseData();
        CommonUtil.beanCopy(msg, data);
        data.setSendTime(new Date());
        data.setUser(user);
        data.setGift(gift);
        data.setSysType(type);
        return execute(data);
    }

}
