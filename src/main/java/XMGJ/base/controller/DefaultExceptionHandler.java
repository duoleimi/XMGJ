/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package XMGJ.base.controller;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.BeanMapper;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.utils.Collections3;
import org.springside.modules.web.MediaTypes;

import com.zzy.base.utils.spring.WebUtil;

import XMGJ.base.model.CommonResult;

/**
 * 异常处理
 * @author lll 2015年3月11日
 */
@ControllerAdvice(assignableTypes = {BaseController.class})
public class DefaultExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
	private JsonMapper jsonMapper = new JsonMapper();
	
//	/**
//	* 没有权限 异常
//	*/
//	@ExceptionHandler({UnauthorizedException.class})
//	public Object processUnauthenticatedException(NativeWebRequest request, UnauthorizedException ex) {
//		logger.error("msg {}", ex.getMessage());
//		if (CommonUtil.useAjax(request)) {
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
//			return new ResponseEntity<Object>(ServiceResult.commError("您无权限做此操作"), headers, HttpStatus.OK);
//		} else {
//			return new ModelAndView("error/404", "error", "您无权限做此操作");
//		}
//	}
	
	/**
	 * 处理JSR311 Validation异常.
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public final Object handleValidException(ConstraintViolationException ex, WebRequest request) {
		logger.error("msg {}", ex.getMessage());
		
		Map<String, String> errors = BeanValidators.extractPropertyAndMessage(ex.getConstraintViolations());
		if (WebUtil.isAjax(request)) {
			CommonResult result = CommonResult.commError(Collections3.getFirst(errors.values()));
			String body = jsonMapper.toJson(result);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
			return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
		} else {
			return new ModelAndView("error/500", "error", Collections3.getFirst(errors.values()));
		}
	}
	
	/**
	 * 处理@Valid 验证错误返回
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public Object handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, WebRequest request) {
		logger.error("msg {}", ex.getMessage());
		BindingResult bingingResult = ex.getBindingResult();
		List<ErrorMsg> errorMsgs = BeanMapper.mapList(bingingResult.getFieldErrors(), ErrorMsg.class);
		if (WebUtil.isAjax(request)) {
			CommonResult result = CommonResult.commError(Collections3.getFirst(errorMsgs).getError());
			String body = jsonMapper.toJson(result);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
			return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
		} else {
			return new ModelAndView("error/500", "error", Collections3.getFirst(errorMsgs).getError());
		}
		
	}
	
	/**
	 * 异常
	 */
	@ExceptionHandler({Exception.class})
	public Object processException(NativeWebRequest request, Exception ex) {
		List<Throwable> exceptions = ExceptionUtils.getThrowableList(ex);
		for (Throwable throwable : exceptions) {
			logger.error("msg {}", throwable.getMessage());
		}
		if (WebUtil.isAjax(request)) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
			CommonResult result = CommonResult.commError(ex.getMessage());
			return new ResponseEntity<Object>(jsonMapper.toJson(result), headers, HttpStatus.OK);
		} else {
			return new ModelAndView("error/500", "error", ex.getMessage());
		}
	}
	
	public static class ErrorMsg {
		private String field;
		private String error;
		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}
		/**
		 * @param field the field to set
		 */
		public void setField(String field) {
			this.field = field;
		}
		/**
		 * @return the error
		 */
		public String getError() {
			return error;
		}
		/**
		 * @param error the error to set
		 */
		public void setError(String error) {
			this.error = error;
		}
	}
}
