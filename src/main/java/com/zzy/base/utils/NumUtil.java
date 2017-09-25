package com.zzy.base.utils;

import java.math.BigDecimal;

/**
 * 数值计算工具
 * @author lxm 2015年1月20日
 */
public class NumUtil {
	private NumUtil(){}
	private static final int DEF_DIV_SCALE = 10;
	
	public static float add(float val1, float val2) {
		BigDecimal b1 = new BigDecimal(Float.toString(val1));
	    BigDecimal b2 = new BigDecimal(Float.toString(val2));
	    return b1.add(b2).floatValue();
	}
	
	public static double add(double val1, double val2) {
		BigDecimal b1 = new BigDecimal(Double.toString(val1));
	    BigDecimal b2 = new BigDecimal(Double.toString(val2));
	    return b1.add(b2).doubleValue();
	}
	
	public static float sub(float val1,float val2){
	    BigDecimal b1 = new BigDecimal(Float.toString(val1));
	    BigDecimal b2 = new BigDecimal(Float.toString(val2));
	    return b1.subtract(b2).floatValue();
	}
	
	public static double sub(double val1,double val2){
	    BigDecimal b1 = new BigDecimal(Double.toString(val1));
	    BigDecimal b2 = new BigDecimal(Double.toString(val2));
	    return b1.subtract(b2).doubleValue();
	}
	 
	public static float mul(float v1,float v2){
	       BigDecimal b1 = new BigDecimal(Float.toString(v1));
	       BigDecimal b2 = new BigDecimal(Float.toString(v2));
	       return b1.multiply(b2).floatValue();
	}
	
	public static double mul(double v1,double v2){
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return b1.multiply(b2).doubleValue();
    }
	
	/**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static float div(float v1,float v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
	
	/**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static float div(float v1, float v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale) {
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(float v,int scale) {
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Float.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    
    public static String print(double d) {
    	//如果为0，显示空
    	if (d == 0){
    		return "";
    	}
    	return Constant.currencyFormatter.format(d);
    }
}
