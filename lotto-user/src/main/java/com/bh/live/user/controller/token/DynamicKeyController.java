package com.bh.live.user.controller.token;

import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.limit.ILoadingCacheService;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.pojo.res.user.token.DynamicKeyRes;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Api(tags = "获取动态加密key类")
@RequestMapping("/key")
public class DynamicKeyController extends BaseController{
    @Resource
    private ILoadingCacheService loadingCacheService;
    /**
     *@description 生成动态加密秘钥
     *@author WuLong
     *@date 2019/7/31 11:58
     *@param
     *@return Result
     */
    @GetMapping("/getDynamicKey")
    @ApiOperation(value = "获取动态加密key", notes = "加密key",response = DynamicKeyRes.class)
    public Result<DynamicKeyRes> getDynamicKey(){
        try {
            String host = getAddr();
            RateLimiter limiter = loadingCacheService.getRateLimiter(host);
            if (limiter.tryAcquire()) {
                //获得令牌（不限制访问）
                //动态生成秘钥，redis存储秘钥供之后秘钥验证使用，设置有效期5秒用完即丢弃
                String tokenKey = StringUtils.getRandomString(16);
                String userKey = StringUtils.getRandomString(16);
                redisUtil.setByFastJson(String.format(UserRedisKey.SYS_USER_TOKEN_KEY, host + userKey), tokenKey, 5, TimeUnit.SECONDS);
                DynamicKeyRes dynamicKeyRes = new DynamicKeyRes();
                dynamicKeyRes.setKey(tokenKey);
                dynamicKeyRes.setIv(userKey);
                return Result.success(dynamicKeyRes);
            } else {
                //未获得令牌（限制访问）
                log.error("访问过于频繁");
                return Result.error(CodeMsg.E_10020);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.warn("签发动态秘钥失败" + e.getMessage(), e);
            return Result.error(CodeMsg.E_20009);
        }
    }
}
