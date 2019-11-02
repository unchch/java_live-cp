package com.bh.live.user;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

/*
*

http://172.16.168.16:7071/api/getToken?userId=307&room=room01
*/



public class TestApiController {
/*
*
	 * 发送普通消息*/


	public static void sendMsg_Normal(String role,String flag,String content) {
		Connection con = Jsoup.connect("http://172.16.168.16:7071/api/sendMsg");
		con.ignoreContentType(true);
		con.header("merchantKey", "test");
		con.header("merchantSecret", "test123");
		con.data("msgType","mes_normal");
		con.data("token","2244a7a0cebb422bba75c8b9e6b825a9");
		con.data("room","room01");
		con.data("channel","chat_room");
		con.data("event","mes_normal");
		Map<String,Object> data = new TreeMap<>();
		Map<String,Object> user = new TreeMap<>();
		user.put("userId", RandomUtil.randomInt(5,6));
		user.put("nickname", "我是王者"+RandomUtil.randomInt(3));
		user.put("avatar", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2912447981,1234512645&fm=85&app=63&f=JPEG?w=121&h=75&s=1F324B805823580347146CC80300A092");
		user.put("level", RandomUtil.randomInt(1,10));
		user.put("levelName", "优秀青铜");
		user.put("levelIcon", "http://www.17sucai.com/static/i/hot-icon.png");
		user.put("role", role);
		user.put("flag", flag);
		
		data.put("user", user);
		data.put("content", content);
		data.put("atUserId", null);
		data.put("sendTime", DateUtil.now());
		String str = JSONObject.toJSON(data).toString();
		System.out.println(">>>"+str);
		con.data("data",str);
		con.method(Method.POST);
		try {
			Response res = con.execute();
			if(res.statusCode() == 200) {
				System.out.println(res.body());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
/*
*//**
	 * 发送普通消息
	 *//*

	public static void sendGift() {
		Connection con = Jsoup.connect("http://http.im.com:7071/api/sendMsg");
		con.ignoreContentType(true);
		con.header("merchantKey", "test");
		con.header("merchantSecret", "test123");
		con.data("msgType",MsgTypeEnum.CHAT_ROOM.getCode());
		con.data("token","8ac51f07c4be4b089f090e9c5a52cabb");
		con.data("room","room01");
		con.data("channel",MsgChannelEnum.CHAT_ROOM.getCode());
		con.data("event",MsgEventEnum.GIFT_REWARD.getCode());
		Map<String,Object> data = new TreeMap<>();
		Map<String,Object> user = new TreeMap<>();
		user.put("userId", RandomUtil.randomInt(5,6));
		user.put("nickname", "我是王者"+RandomUtil.randomInt(3));
		user.put("avatar", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2912447981,1234512645&fm=85&app=63&f=JPEG?w=121&h=75&s=1F324B805823580347146CC80300A092");
		user.put("level", RandomUtil.randomInt(1,10));
		user.put("levelName", "优秀青铜");
		user.put("levelIcon", "http://www.17sucai.com/static/i/hot-icon.png");
		
		
		Map<String,Object> gift = new TreeMap<>();
		gift.put("name", "兰博基尼跑车");
		gift.put("icon", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2912447981,1234512645&fm=85&app=63&f=JPEG?w=121&h=75&s=1F324B805823580347146CC80300A092");
		gift.put("resFull", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2912447981,1234512645&fm=85&app=63&f=JPEG?w=121&h=75&s=1F324B805823580347146CC80300A092");
		gift.put("resLg", null);
		gift.put("resGif", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2912447981,1234512645&fm=85&app=63&f=JPEG?w=121&h=75&s=1F324B805823580347146CC80300A092");
		gift.put("description", "要想开跑车，就得搏一搏");
		gift.put("count", 10);
		gift.put("dateTime", DateUtil.now());
		
		data.put("user", user);
		data.put("gift", gift);
		data.put("sendTime", DateUtil.now());
		String str = JsonKit.toJson(data);
		System.out.println(">>>"+str);
		con.data("data",str);
		con.method(Method.POST);
		try {
			Response res = con.execute();
			if(res.statusCode() == 200) {
				System.out.println(res.body());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}*/
	
	public static void main(String[] args) {
		/*while(true) {*/
			sendMsg_Normal("user","none","随机呵呵哒咯"+DateUtil.formatTime(new Date()));
			/*sendMsg_Normal("user","gift","我送过礼物咯"+DateUtil.formatTime(new Date()));
			sendMsg_Normal("manager","none","管理员！！除了管理员可以渲染html，其他角色都不行哟<br/>兄弟们如果想要富就要买CP，CP反着买别墅靠大海<a href=\"http://www.qq.com\">点击这里可以看别墅</a><br/><u>我们可以玩的很多东西哟</u>");
			
			if(RandomUtil.randomInt()%2==0)
				sendGift();

			try {
				Thread.sleep(RandomUtil.randomLong(800, 3000));
			}catch(Exception e){
				e.printStackTrace();
			}
		}*/
	}

}
