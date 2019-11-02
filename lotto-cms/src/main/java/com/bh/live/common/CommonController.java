package com.bh.live.common;

import com.bh.live.common.constant.EnumConstants;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.core.Message;
import com.bh.live.common.limit.ILoadingCacheService;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.BASE64DecodedMultipartFile;
import com.bh.live.common.utils.IpUtil;
import com.bh.live.common.utils.MessageUtils;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.file.AliyunFileUploadUtils;
import com.bh.live.common.utils.file.FileUploadUtils;
import com.bh.live.common.utils.file.FileUtils;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.utils.PropUtil;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.bh.live.common.core.Message.Type.SUCCESS;

/**
 * 通用请求处理
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private RedisUtil redisManager;

    @Autowired
    private ILoadingCacheService loadingCacheService;

    /**
     * aliyun oss
     */
    @Value("${aliyun.oss.bucketName}")
    private String ossBucketName;
    @Value("${aliyun.oss.endpoint}")
    private String ossEndpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String ossAccessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ossAccessKeySecret;

    @Value("${ownName}")
    private String ownName;

    @Value("${filedir}")
    private String filedir;
    /**
     * amazon s3Client
     */
    /*@Value("${amazon.s3.bucketName}")
    private String s3BucketName;
    @Value("${amazon.s3.accessKeyId}")
    private String s3AccessKeyId;
    @Value("${amazon.s3.accessKeySecret}")
    private String s3AccessKeySecret;*/

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @ApiOperation(value = "通用下载")
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(MessageUtils.message("upload.filename.illegal", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = PropUtil.filePath + "download/" + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation(value = "通用上传")
    @PostMapping("/upload")
    public Message uploadFile(MultipartFile file, @ApiParam("图片上传文件夹，去枚举C1001拿文件夹值") String filedir) {
        try {
            AliyunFileUploadUtils utils = new AliyunFileUploadUtils(ossBucketName, ossEndpoint, ossAccessKeyId, ossAccessKeySecret, filedir);
//          AmazonS3FileUploadUtils utils = new AmazonS3FileUploadUtils(s3BucketName, s3AccessKeyId, s3AccessKeySecret);
            String fileName = utils.upload(file);
            String url = utils.getUrl(fileName);
            Map<String, String> map = Maps.newHashMap();
            map.put("fileName", fileName);
            map.put("url", url);
            return new Message<>().ok(SUCCESS.value(), SUCCESS.msg()).setData(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message().error(Message.Type.FAIL.value(), e.getMessage());
        }
    }

    /**
     * 上传 Base64 图片 请求
     */
    @ApiOperation(value = "上传 Base64 图片")
    @PostMapping("/uploadBase64")
    public Result uploadBase64(@RequestBody @ApiParam("file&filedir参数") Map<String, String> params) {
        String file = params.get("file");
        String filedir = params.get("filedir");
        if (StringUtils.isEmpty(file) && StringUtils.isEmpty(filedir)) {
            return Result.error(CodeMsg.E_90003);
        }
        //log.info("================= uploadBase64 =============:" + params.get("file"));
        try {
            AliyunFileUploadUtils utils = new AliyunFileUploadUtils(ossBucketName, ossEndpoint, ossAccessKeyId, ossAccessKeySecret, this.filedir + filedir);
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(file);
            if (multipartFile == null) {
                return Result.error(CodeMsg.E_500);
            }
            String fileName = utils.upload(multipartFile);
            String url = utils.getImgUrl(fileName);
            if (StringUtils.isEmpty(url)) {
                return Result.error(CodeMsg.E_500);
            }
            //把返回的图片地址前的域名改成公司自有域名
            String endpoint = ossEndpoint.substring(ossEndpoint.indexOf("//") + 2, ossEndpoint.length());
            System.out.println(endpoint);
            String substring = url.substring(url.indexOf(endpoint) + endpoint.length(), url.length());
            System.out.println(substring);
            url = ownName + substring;
            Map<String, String> map = Maps.newHashMap();
            map.put("fileName", fileName);
            map.put("url", url);
            System.out.println(map);
            return Result.success(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
    }

    /**
     * 上传渠道apk包文件
     */
    @PostMapping("/uploadApk")
    public Message uploadApk(MultipartFile file) {
        try {
            // 上传文件路径
            String filePath = PropUtil.filePath;
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.uploadApk(filePath, file);
            String url = PropUtil.fileAccessUrl + fileName;
            Map<String, String> map = Maps.newHashMap();
            map.put("fileName", fileName);
            map.put("url", url);
            return new Message<>().ok(SUCCESS.value(), SUCCESS.msg()).setData(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Message().error(Message.Type.FAIL.value(), e.getMessage());
        }
    }

    /**
     * 设置请求头
     *
     * @param request
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 枚举值
     *
     * @return
     */
    @ApiOperation("公用枚举接口")
    @GetMapping("/getAllEnum")
    public Result getAllEnumConstants() {
        return Result.success(EnumConstants.getAllEnumConstants());
    }

    /**
     * 获取tokenKey
     *
     * @param request
     * @return
     */
    @GetMapping("getKey")
    public Result getKey(HttpServletRequest request) {
        String host = IpUtil.getIpAddr(request).toUpperCase();
        try {
            RateLimiter limiter = loadingCacheService.getRateLimiter(host);
            if (limiter.tryAcquire()) {
                //获得令牌（不限制访问）
                //动态生成秘钥，redis存储秘钥供之后秘钥验证使用，设置有效期5秒用完即丢弃
                String tokenKey = StringUtils.getRandomString(16);
                String userKey = StringUtils.getRandomString(6);
                redisManager.setByFastJson(String.format(UserRedisKey.SYS_USER_TOKEN_KEY, host + userKey.toUpperCase()), tokenKey, 5, TimeUnit.SECONDS);
                Map<String, String> map = Maps.newHashMap();
                map.put("userKey", userKey.toUpperCase());
                map.put("tokenKey", tokenKey);
                return Result.success("签发tokenKey成功");
            } else {
                //未获得令牌（限制访问）
                return Result.success("访问过于频繁");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.warn("接口访问频率过滤器出错了" + e.getMessage(), e);
            return Result.error(CodeMsg.E_20008);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("签发动态秘钥失败" + e.getMessage(), e);
            return Result.error(CodeMsg.E_20009);
        }
    }
}
