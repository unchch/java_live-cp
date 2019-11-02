package com.bh.live.common.utils.file;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName AliyunFileUploadUtils
 * @description: aliyun oss File Upload Utils
 * @author: yq.
 * @date 2019-06-10 18:19:49
 */
@Slf4j
@Getter
@Setter
public final class AliyunFileUploadUtils {

    private String bucketName;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    private String filedir;

    private OSSClient ossClient;

    public AliyunFileUploadUtils(String bucketName, String endpoint, String accessKeyId, String accessKeySecret, String filedir) {
        this.bucketName = bucketName;
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.filedir = filedir;
        init();
    }

    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void upload(String url) {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("图片上传失败");
        }
    }

    public String upload(MultipartFile file) {
//        if (file.getSize() > 10 * 1024 * 1024) {
//            throw new BusinessException(ModuleType.BACK, "上传大小不能超过10M！");
//        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
    }


    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(FileUtils.getcontentType(fileName.substring(fileName.lastIndexOf(".") + 1)));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String key = this.filedir + split[split.length - 1];

            // 设置URL过期时间为10年 3600l* 1000*24*365*10
            Date expirationDate = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, key, expirationDate);
            if (url != null) {
                return url.toString();
            }
        }
        return null;
    }
}
