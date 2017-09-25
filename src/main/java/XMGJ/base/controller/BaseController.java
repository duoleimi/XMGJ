/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package XMGJ.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * controller 基类
 * @author lll 2015年5月28日
 */
public class BaseController {
	
	/**
	 * 获取链接
	 */
	public static String getBaseUrl() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    if(null != attr && attr instanceof ServletRequestAttributes) {
	    	  HttpServletRequest request = ((ServletRequestAttributes)attr).getRequest();
	    	  return StringUtils.substringBeforeLast(request.getRequestURL().toString(), request.getServletPath());
    	}
    	else {
    		throw new ServiceException("错误调用，错误上下文");
    	}
	}

	
}
