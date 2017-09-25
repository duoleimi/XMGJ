package com.zzy.base.utils.spring;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * spring security util
 * 
 * @author lll 2016年8月18日
 */
public class SecureUtil {

	/**
	 * 是否登录
	 * 
	 * @return
	 */
	public static boolean isAuthenticated(SecurityContext context) {
		if (null == context || null == context.getAuthentication()) {
			return false;
		}
		if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
			return false;
		}

		if (!context.getAuthentication().isAuthenticated()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否登录
	 * 
	 * @return
	 */
	public static boolean isAuthenticated() {
		if (null == SecurityContextHolder.getContext() || null == SecurityContextHolder.getContext().getAuthentication()) {
			return false;
		}
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			return false;
		}

		if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			return false;
		}
		return true;
	}

	public static boolean isApi() {
		return SecureUtil.isApi(null);
	}

	/**
	 * 判断当前请求是否api请求
	 * 
	 * @return
	 */
	public static boolean isApi(HttpServletRequest request) {
		if (request == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			request = attr.getRequest();
		}
		String token = request.getHeader("x-auth-token");
		if (StringUtils.isBlank(token) && !request.getRequestURI().startsWith("/api")) {
			return false;
		} else {
			return true;
		}
	}

}
