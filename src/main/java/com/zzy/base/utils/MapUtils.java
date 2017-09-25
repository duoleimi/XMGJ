package com.zzy.base.utils;

public class MapUtils {
	
	/**
	 * 地图坐标
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	private static final double RAD = Math.PI / 180.0;
	public static final double EARTH_RADIUS = 6378137.0;// 地球半径
	public static final double ONE_LATITUDE = 111319.0;//1维度 距离
	public static double getDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = lat1 * RAD;
		double radLat2 = lat2 * RAD;
		double a = radLat1 - radLat2;
		double b = (lng1 - lng2) * RAD;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 距离 转 纬度边界
	 * @param dist  距离（米）
	 * @return
	 */
	public static double toLatitude(double dist) {
		return Math.min(dist/ONE_LATITUDE, 90);
	}
	
	/**
	 * 距离 转 经度边界
	 * @param dist      距离（米）
	 * @param latitude  所在位置维度
	 * @return
	 */
	public static double toLongitude(double dist, double latitude) {
		return Math.min(dist/Math.abs(Math.cos(Math.toRadians(latitude))*ONE_LATITUDE),180);
	}
	
	public static void main(String[] args) {
		
		System.out.println(toLongitude(ONE_LATITUDE*180, 2));
		
		System.out.println(getDistance(1.8104041194, 2, 1.8104041194+1.000, 2));
		
		
		System.out.println(getDistance(2.8104041194, 4.8104041194, 2.8104041194, 5.8104041194));
		System.out.println(getDistance(25.8104041194, 119.0051793812, 25.7825325505, 119.0931763417));
		System.out.println(getDistance(25.8201760994, 119.0045648712, 25.7920523405, 119.0926629817));
		System.out.println(getDistance(25.8133660993, 119.0005148712, 25.7853123405, 119.0882429817));
	}
}
