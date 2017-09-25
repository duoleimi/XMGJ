/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package XMGJ.base.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页结果DTO
 * 
 * @author lll 2015年11月9日
 */
@Data
@AllArgsConstructor
public class PageWrapper<T> {
	// 结果列表
	protected List<T> result;
	protected boolean hasNext;

}
