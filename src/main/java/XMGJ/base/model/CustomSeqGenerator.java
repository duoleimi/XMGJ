/*******************************************************************************
 * Copyright (c) 2005, 2017 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package XMGJ.base.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IncrementGenerator;

/**
 * 自定义id生成
 * @author lll 2015年10月23日
 */
public class CustomSeqGenerator extends IncrementGenerator {
	@Override
	public synchronized Serializable generate(SessionImplementor session,
			Object object) throws HibernateException {
		
		Serializable seq = super.generate(session, object);
		
		seq = StringUtils.join(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"),seq.toString());
		
		return seq;
	}
}
