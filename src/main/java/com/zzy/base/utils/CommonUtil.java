/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.base.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author lxm 2015年11月1日
 */
@Slf4j
public class CommonUtil {

    /**
     * 本地访问
     */
    public final static String LOCAL_IP = "127.0.0.1";

    public static void logException(Logger logger, Throwable e) {
    	logException(logger, e, true);
    }
    
    public static void logException(Logger logger, Throwable e, boolean showDetail) {
    	if (showDetail) {
    		e.printStackTrace();
		}
        List<Throwable> exceptions = ExceptionUtils.getThrowableList(e);
        for (Throwable throwable : exceptions) {
            logger.error("msg {}", throwable.getMessage());
        }
    }

    /**
     * id 数组
     * 
     * @param idsStr
     * @return
     */
    public static Set<Long> idSetWithStr(String idsStr) {
        return Sets.newHashSet(Collections2.transform(Lists.newArrayList(StringUtils.split(idsStr, ",")),
                new Function<String, Long>() {
                    public Long apply(String arg0) {
                        return Long.parseLong(arg0);
                    }
                }));
    }
    
    /**
     * id 数组
     * 
     * @param idsStr
     * @return
     */
    public static Set<Long> idSetWithStr(String idsStr, String separatorChars) {
        return Sets.newHashSet(Collections2.transform(Lists.newArrayList(StringUtils.split(idsStr, separatorChars)),
                new Function<String, Long>() {
                    public Long apply(String arg0) {
                        return Long.parseLong(arg0);
                    }
                }));
    }
    
    /**
     * String 数组
     * 
     * @param string
     * @return
     */
    public static Set<String> strSetWithStr(String str,String separatorChars) {
    	return Sets.newHashSet(Collections2.transform(Lists.newArrayList(StringUtils.split(str,separatorChars)), 
    			new Function<String,String>(){
					@Override
					public String apply(String arg0) {
						return String.valueOf(arg0);
					}
    		})); 	
    }
    
    
    
    public static boolean validFieldForString(String val, int maxSize) {
        if (StringUtils.isBlank(val)) {
            return false;
        }
        if (val.length() > maxSize) {
            return false;
        }
        return true;
    }

    /**
     * 字符串转list id以;分隔
     * 
     * @param ids
     * @return
     */
    public static List<Long> String2List(String ids, String sep) {
        String[] strids = ids.split(sep);
        List<Long> ret = new ArrayList<Long>();
        for (String sid : strids) {
            if (sid.trim().length() > 0)
                ret.add(Long.parseLong(sid));
        }
        return ret;
    }

    /**
     * 字符串转list id以;分隔
     * 
     * @param ids
     * @return
     */
    public static List<Long> String2List(String ids) {
        return String2List(ids, ";");
    }

    /**
     * 字符串转list "id{secondSeparator}other{firstSeparator}...."
     * 
     * @param ids
     * @return
     */
    public static List<KeyValueObj<String, String>> string2List(String ids, String firstSeparator, String secondSeparator) {
        String[] strids = ids.split(firstSeparator);
        List<KeyValueObj<String, String>> ret = new ArrayList<KeyValueObj<String, String>>();
        for (String sid : strids) {
            if (sid.trim().length() > 0) {
                String[] info = StringUtils.split(sid, secondSeparator);
                ret.add(new KeyValueObj<String, String>(info[0], info[1]));
            }
        }
        return ret;
    }
    
    /**
     * 字符串转list "id{secondSeparator}other{firstSeparator}...."
     * 
     * @param ids
     * @return
     */
    public static Map<String, String> string2Map(String ids, String firstSeparator, String secondSeparator) {
        String[] strids = ids.split(firstSeparator);
        Map<String, String> ret = Maps.newHashMap();
        for (String sid : strids) {
            if (sid.trim().length() > 0) {
                String[] info = StringUtils.split(sid, secondSeparator);
                ret.put(info[0], info[1]);
            }
        }
        return ret;
    }
    

    /**
     * 将一组数随机排序
     * 
     * @param no
     * @return
     */
    public static LinkedList<Integer> getSequence(int no) {
        LinkedList<Integer> sequence2 = new LinkedList<Integer>();
        for (int i = 0; i < no; i++) {
            sequence2.add(i);
        }
        Random random = new Random();
        for (int i = 0; i < no; i++) {
            int p = random.nextInt(no);
            int tmp2 = sequence2.get(i);
            sequence2.set(i, sequence2.get(p));
            sequence2.set(p, tmp2);
        }
        random = null;
        return sequence2;
    }

    /**
     * 获取ip
     * 
     * @return
     */
    public static String currentIp() {

        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get server host Exception e:", e);
        }
        return host;
    }

    /**
     * 获取客户来源ip地址
     * 
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = getRequestIpAddress(request);
        if (ipAddress.startsWith("0")) {
            return LOCAL_IP;
        } else {
            return ipAddress;
        }
    }

    /**
     * 获取ip地址
     * 
     * @param request
     * @return
     */
    private static String getRequestIpAddress(HttpServletRequest request) {
        // 负载均衡环境下获取原始请求ip地址
        if (!StringUtils.isBlank(request.getHeader("x-forwarded-for"))) {
            String xff = request.getHeader("x-forwarded-for");
            if (xff.indexOf(",") > 0) { // 做了多次重定向，取第一个ip地址
                return xff.substring(0, xff.indexOf(","));
            } else {
                return xff;
            }
        }
        return request.getRemoteAddr();
    }
}
