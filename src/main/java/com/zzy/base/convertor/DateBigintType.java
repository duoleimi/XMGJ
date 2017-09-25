/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.base.convertor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.DateType;

/**
 * bigint to Date
 * @author lll 2015年11月9日
 */
public class DateBigintType extends DateType {
	
	private static final long serialVersionUID = -8523945994480815508L;
	
	@Override
	public Object get(ResultSet rs, String name, SessionImplementor session)
			throws HibernateException, SQLException {
		return new Date(rs.getLong(name));
	}
	
	@Override
	public void set(PreparedStatement st, Date value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		st.setLong(index, value.getTime());
	}
	
	@Override
	public Date stringToObject(String xml) {
		
		return new Date(Long.valueOf(xml));
	}
	
	@Override
	public String objectToSQLString(Date value, Dialect dialect)
			throws Exception {
		
		return String.valueOf(value.getTime());
	}
	
}
