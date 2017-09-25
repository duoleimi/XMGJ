/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.base.convertor;

import java.sql.Time;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 时间转换
 * @author lll 2015年7月24日
 */
@Component
public class TimeToStringConvertor implements Converter<Time, String> {

	@Override
	public String convert(Time source) {
		
		return source.toString();
	}

}
