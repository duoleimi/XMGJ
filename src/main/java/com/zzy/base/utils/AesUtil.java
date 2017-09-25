package com.zzy.base.utils;

import org.springside.modules.security.utils.Cryptos;
import org.springside.modules.utils.Encodes;

public class AesUtil {
	
	private static final byte[] PRIVATE_KEY = {-23, 29, -45, 63, 84, -5, -39, 105,
		45, 19, 99, -29, 43, 37, -57, 4}; 
	
	/**
	 * 加密
	 * */
	public static String encryption(String content){
		byte[] result = Cryptos.aesEncrypt(content.getBytes(),PRIVATE_KEY);		
		return Encodes.encodeBase64(result);
	}	
	
	/**
	 *解密 
	 **/
	public static String decryption(String content){
		return Cryptos.aesDecrypt(Encodes.decodeBase64(content) , PRIVATE_KEY);
	}
	
}
