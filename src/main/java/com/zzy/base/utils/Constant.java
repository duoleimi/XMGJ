package com.zzy.base.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 静态变量类
 * 
 * @author zwm
 * 
 */
public class Constant {
	
	// application.properties   environment
	public final static String ENV_DEV = "dev";
	public final static String ENV_PRO = "pro";
	
	
	/** 判断运行模式 */
	public final static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
		    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
	
	public final static DecimalFormat currencyFormatter = new DecimalFormat();
    static {
    	currencyFormatter.applyPattern("#0.00");
    	currencyFormatter.setGroupingUsed(false);
    	currencyFormatter.setRoundingMode(RoundingMode.HALF_UP);
    }
    
	
	/**
	 * Controller返回错误页面常量
	 */
	public final static String ERROR_PAGE = "/error/500"; // 操作错误页
	public final static String NOTFOUND_PAGE = "/error/404"; // 操作错误页

	/**
	 * 分页大小 10
	 */
	public final static int PAGE_SIZE = 10;
	public final static int PAGE_SIZE_20 = 20;
	public final static int PAGE_SIZE_30 = 30;
	
	/**
	 * 分页大小
	 */
	public final static int PAGE_SIZE_5 = 5;

	public static final Map<String, String> PROVINCES = new TreeMap<String, String>() {
		{
			put("110000", "北京市");
			put("120000", "天津市");
			put("130000", "河北省");
			put("140000", "山西省");
			put("150000", "内蒙古自治区");
			put("210000", "辽宁省");

			put("220000", "吉林省");
			put("230000", "黑龙江省");
			put("310000", "上海市");
			put("320000", "江苏省");
			put("330000", "浙江省");
			put("340000", "安徽省");

			put("350000", "福建省");
			put("360000", "江西省");
			put("370000", "山东省");
			put("410000", "河南省");
			put("420000", "湖北省");
			put("430000", "湖南省");

			put("440000", "广东省");
			put("450000", "广西壮族自治区");
			put("460000", "海南省");
			put("500000", "重庆市");
			put("510000", "四川省");
			put("520000", "贵州省");

			put("530000", "云南省");
			put("540000", "西藏自治区");
			put("610000", "陕西省");
			put("620000", "甘肃省");
			put("630000", "青海省");
			put("640000", "宁夏回族自治区");
			put("650000", "新疆维吾尔自治区");
		}
	};

	/**
	 * 表情数据
	 */
	public final static Map<String, String> EMOTION_DATA = new HashMap<String, String>() {
		{
			put("0", "微笑");
			put("1", "撇嘴");
			put("2", "色");
			put("3", "发呆");
			put("4", "得意");
			put("5", "流泪");
			put("6", "害羞");
			put("7", "闭嘴");
			put("8", "睡");
			put("9", "大哭");
			put("10", "尴尬");
			put("11", "发怒");
			put("12", "调皮");
			put("13", "呲牙");
			put("14", "惊讶");
			put("15", "难过");
			put("16", "酷");
			put("17", "冷汗");
			put("18", "抓狂");
			put("19", "吐");
			put("20", "偷笑");
			put("21", "可爱");
			put("22", "白眼");
			put("23", "傲慢");
			put("24", "饥饿");
			put("25", "困");
			put("26", "惊恐");
			put("27", "流汗");
			put("28", "憨笑");
			put("29", "大兵");
			put("30", "奋斗");
			put("31", "咒骂");
			put("32", "疑问");
			put("33", "嘘");
			put("34", "晕");
			put("35", "折磨");
			put("36", "衰");
			put("37", "骷髅");
			put("38", "敲打");
			put("39", "再见");
			put("40", "擦汗");
			put("41", "抠鼻");
			put("42", "鼓掌");
			put("43", "糗大了");
			put("44", "坏笑");
			put("45", "左哼哼");
			put("46", "右哼哼");
			put("47", "哈欠");
			put("48", "鄙视");
			put("49", "委屈");
			put("50", "快哭了");
			put("51", "阴险");
			put("52", "亲亲");
			put("53", "吓");
			put("54", "可怜");
			put("55", "菜刀");
			put("56", "西瓜");
			put("57", "啤酒");
			put("58", "篮球");
			put("59", "乒乓");
			put("60", "咖啡");
			put("61", "饭");
			put("62", "猪头");
			put("63", "玫瑰");
			put("64", "凋谢");
			put("65", "示爱");
			put("66", "爱心");
			put("67", "心碎");
			put("68", "蛋糕");
			put("69", "闪电");
			put("70", "炸弹");
			put("71", "刀");
			put("72", "足球");
			put("73", "瓢虫");
			put("74", "便便");
			put("75", "月亮");
			put("76", "太阳");
			put("77", "礼物");
			put("78", "拥抱");
			put("79", "强");
			put("80", "弱");
			put("81", "握手");
			put("82", "胜利");
			put("83", "抱拳");
			put("84", "勾引");
			put("85", "拳头");
			put("86", "差劲");
			put("87", "爱你");
			put("88", "NO");
			put("89", "OK");
			put("90", "爱情");
			put("91", "飞吻");
			put("92", "跳跳");
			put("93", "发抖");
			put("94", "怄火");
			put("95", "转圈");
			put("96", "磕头");
			put("97", "回头");
			put("98", "跳绳");
			put("99", "挥手");
			put("100", "激动");
			put("101", "街舞");
			put("102", "献吻");
			put("103", "左太极");
			put("104", "右太极");
		}
	};
}
