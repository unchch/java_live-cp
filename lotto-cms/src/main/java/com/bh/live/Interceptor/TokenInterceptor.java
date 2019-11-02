package com.bh.live.Interceptor;

import com.bh.live.common.config.JwtCmsProperties;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.common.utils.JwtHelper;
import com.bh.live.common.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

	@Resource
	JwtHelper jwtHelper;

	@Autowired
	JwtCmsProperties jwtCmsProperties;

	@Resource
	RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		/*String requestURI = request.getServletPath();
		String accessToken = request.getHeader(CommonConstants.ACCESS_TOKEN);
		if (CommonUtil.isEmpty(accessToken)) {
			throw new LiveException(CodeMsg.E_20006.code, CodeMsg.E_20006.message);
		}
		try {
			verifyToken(accessToken, request);
		} catch (LiveException e) {
			log.error(e.getMessage(), e);
			throw new LiveException(e.errCode, e.errMsg);
		}*/
		request.setAttribute(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_USER_ID,"7340a515c49143d5be51c7ed6100e40b"
				);
		request.setAttribute(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_ROLE_ID,
				"35bb7444b1f040df9644485288554bff");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*String accessToken = request.getHeader(CommonConstants.ACCESS_TOKEN);
		settingTokenExpireTime(accessToken);*/
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * @param accessToken 前端header里面的accessToken
	 * @return Result
	 * @description 验证token是否有效
	 * @author WuLong
	 * @date 2019/8/2 12:07
	 */
	public void verifyToken(String accessToken, HttpServletRequest request) {
		Map<String, String> tokenKeyMap = jwtHelper.verifyCmsToken(accessToken);
		if (CommonUtil.isNotEmpty(tokenKeyMap)) {
			String issue = tokenKeyMap.get("iss");
			if (!issue.equals(jwtCmsProperties.getIssuer())) {
				throw new ServiceRuntimeException(CodeMsg.E_20001.code, CodeMsg.E_20001.message);
			}
			// 验证token在redis是否过期
			String jwtTokenCacheKey = RedisUtil.getJWTCmsTokenCacheKey(
					tokenKeyMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT),
					tokenKeyMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_USER_ID));
			Object o = redisUtil.get(jwtTokenCacheKey);
			// 验证缓存是否失效
			if (o == null) {
				throw new ServiceRuntimeException(CodeMsg.E_20001.code, CodeMsg.E_20001.message);
			}
			Map<String, String> accountMap = jwtHelper.verifyCmsToken(o.toString());
			String is_usable = accountMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_IS_USABLE);
			// 用户属性判断 是否已经作废
			if (is_usable.equals("0")) {
				throw new ServiceRuntimeException(CodeMsg.E_10018.code, CodeMsg.E_10018.message);
			}
			// 重置失效时间
			redisUtil.expire(jwtTokenCacheKey, jwtCmsProperties.getExpireTime());
			// TODO 这里需要封装登录用户属性
			request.setAttribute(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_USER_ID,
					accountMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_USER_ID));
			request.setAttribute(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_ROLE_ID,
					accountMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_ROLE_ID));
		}
	}

	/**
	 * @param request
	 * @description 重置token有效时间
	 * @author WuLong
	 * @date 2019/8/2 13:38
	 */
	private void settingTokenExpireTime(String accessToken) {
		try {
			if (CommonUtil.isNotEmpty(accessToken)) {
				Map<String, String> tokenKeyMap = jwtHelper.verifyCmsToken(accessToken);
				if (CommonUtil.isNotEmpty(tokenKeyMap)) {
					String issue = tokenKeyMap.get("iss");
					// issuer是否匹配
					if (!issue.equals(jwtCmsProperties.getIssuer())) {
						log.info("token重置过期时间异常:issuer不匹配:{}", JsonUtil.obj2json(tokenKeyMap));
						return;
					}
					// 验证token在redis是否过期
					String jwtTokenCacheKey = RedisUtil.getJWTCmsTokenCacheKey(
							tokenKeyMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT),
							tokenKeyMap.get(CommonConstants.LOING_CMS_CLAIMS_ACCOUNT_USER_ID));
					Object o = redisUtil.get(jwtTokenCacheKey);
					// 验证缓存是否失效
					if (o == null) {
						log.info("token重置过期时间异常:缓存不存在:{}", jwtTokenCacheKey);
					}
					Map<String, String> accountMap = jwtHelper.verifyCmsToken(o.toString());
					long expireTime = jwtCmsProperties.getExpireTime();
					// 重新设置redis过期时间
					redisUtil.expire(jwtTokenCacheKey, expireTime);
					log.info("token重置过期时间成功：{}", JsonUtil.obj2json(tokenKeyMap));
				}
			}
		} catch (Exception e) {
			log.error("token重置过期时间异常：{},exception message :{},e:{}", accessToken, e.getMessage(), e);
		}
	}
}
