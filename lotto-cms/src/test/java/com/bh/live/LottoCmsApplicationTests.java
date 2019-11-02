package com.bh.live;

import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.file.AliyunFileUploadUtils;
import com.bh.live.pojo.req.anchor.HostRoomReq;
import com.bh.live.pojo.req.user.LiveUserFullReq;
import com.bh.live.pojo.req.user.SendMessageReq;
import com.bh.live.service.anchor.IHostRoomService;
import com.bh.live.service.user.ILiveUserService;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoCmsApplicationTests {

    @Resource
    private RestTemplate httpClientTemplate;
    @Resource
    private ILiveUserService liveUserService;
    @Resource
    private IHostRoomService hostRoomService;
    /**
     *@author WuLong
     *@date 2019/7/30 13:48
     *@description restTemplate 请求用户中心发送短信
     */
    @Test
    public void sendRegisterCode(){
        SendMessageReq sendMessageReq = new SendMessageReq();
        sendMessageReq.setMobile("15989445213");
        sendMessageReq.setType(MessageCodeEnum.REGISTER.getCode());
        String url = "http://172.16.168.89:9350/send/registercode";
        ResponseEntity<Result> resultResponseEntity = httpClientTemplate.postForEntity(url, sendMessageReq, Result.class);
        Result body = resultResponseEntity.getBody();
        System.out.println(body);
    }

    @Test
    public void upLoadFile(){
        try {
            AliyunFileUploadUtils utils = new AliyunFileUploadUtils("live-prd", "http://oss-cn-hongkong.aliyuncs.com", "LTAI1v9Fxyy1qIht", "aVFTHSbQLrQPVihWLHy4VGBboOY6rz", "headImg");
//          AmazonS3FileUploadUtils utils = new AmazonS3FileUploadUtils(s3BucketName, s3AccessKeyId, s3AccessKeySecret);
            File file = new File("C:\\Users\\admin\\Desktop\\heard_gh10.png");
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile =new MockMultipartFile("file", file.getName(), "png", IOUtils.toByteArray(input));
            String fileName = utils.upload(multipartFile);
            String url = utils.getUrl(fileName);
            Map<String, String> map = Maps.newHashMap();
            map.put("fileName", fileName);
            map.put("url", url);
            System.out.println(map.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void queryLiveUserPage(){
        LiveUserFullReq liveUser = new LiveUserFullReq();
        liveUser.setPageNum(1);
        liveUser.setPageSize(20);
        liveUserService.queryLiveUserPage(liveUser);
    }

    @Test
    public void queryHostRoomList(){
        HostRoomReq req = new HostRoomReq();
        req.setPageNum(1);
        req.setPageSize(20);
        hostRoomService.getByCondition(req);
    }


}
