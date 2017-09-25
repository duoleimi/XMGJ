package XMGJ.zsgj.model;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.util.DigestUtils;

import com.zzy.base.utils.JsonUtil;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class HttpProcessor {

	private static final String AES = "AES";
//	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String AES_CBC = "AES/CBC/NoPadding";
//	private static final String AES_CBC = "AES/CBC/PKCS7Padding";
	
	private static OkHttpClient client = new OkHttpClient();
	private static String URL = "http://0592.mygolbs.com:8081/XMMyGoWeb/servlet/MyGoServer.HttpPool.HttpHandlerServlet";
	
	public static void sendReq(IBusParser req, int cmd) {
		
		DataOutputStream out = new DataOutputStream();
		out.writeInt(cmd);
		byte[] datas = req.encode();
		out.writeData(datas);
		byte[] content = out.getData().array();
		System.out.println(HexUtils.toHexString(content));
		
		Request.Builder requestBuild = new Request.Builder().url(URL);
		RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), content, 0, content.length);
		
//		0000005800000029000b303030303030303030303000063030303030300000000d31353035373538383134373934000131
//		0000005800000029000b303030303030303030303000063030303030300000000d31353035373538383134373934000131
		
		Request request = addHeaders(requestBuild, datas).post(body).build();
		
		client.newCall(request).enqueue(new Callback(){

			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println(e);
			}

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				System.out.println("recv:"+response.body().contentLength());
				decryptResp(response);
//				char[] resp = new char[28];
//				response.body().charStream().read(resp);
//				System.out.println("recv:"+resp);
			}
			
		});
		
	}
	
	
	private static Request.Builder addHeaders(Request.Builder builder, byte[] content) {
		
		Date now = new Date();
		String timeStamp = DateFormatUtils.format(now, "yyyyMMddHHmmss");
		String sign = sign(content, timeStamp);
//		String format = "{\"lng\":\"%f\",\"lat\":\"%f\",\"imei\":\"%s\" ,\"visittype\":\"5\","
//				+ "\"citycode\":\"%s\",\"cityname\":\"%s\",\"channel\":\"appstore\","
//				+ "\"package\":\"%s\",\"appname\":\"%s\"}";
//		String.format(format, args);
		
		
		builder
		.addHeader("Content-Encoding", "gzip,deflate")
		.addHeader("Accept-Encoding", "gzip")
		.addHeader("visittype", "5")
		.addHeader("version", "22-Jun-2001/11:30:00-CST")
		.addHeader("aversion", "1.0")
		.addHeader("version-name", "5.2.0") // CFBundleShortVersionString
		.addHeader("version-code", "58") // CFBundleVersion
		.addHeader("sign", sign)
		.addHeader("timeStamp", timeStamp)
//		.addHeader("attachs", "") // TODO 
		.addHeader("mybus-phoneid", "B98B8FF6-F6A7-4B61-81C8-013C1C06E778") // TODO mobile or Apple AD UUID
//		.addHeader("Cookie", "JSESSIONID=A730CAEAA93033C7A18FC3A6919DC3AB") // TODO 
		.addHeader("User-Agent", "MyBus 58 (iPhone; iPhone OS 8.0.2; zh_CN)")
		.addHeader("handset-type", "iPhone_iOS8.0.2")
		.addHeader("handset-os", "iPhone_iOS8.0.2")
		;
		return builder;
	}
	
	@SneakyThrows
	private static void decryptResp(Response response) {
		DataInputStream stream = new DataInputStream(response.body().byteStream());
		int status = stream.readInt();
		if (status != 1) {
			// TODO FAIL
			return;
		}
		int cmd = stream.readInt();
		int len = stream.readInt();
		byte[] datas = new byte[len];
		stream.read(datas, 0, len);
		if (datas.length > 0) {
			String aversion = response.header("aversion");
			if (aversion.equals("1.0")) {
				String token = response.header("token");
				String sessionId = response.header("sessionId");
				String signature = response.header("signature");//AES IV
				String aesKey = DigestUtils.md5DigestAsHex(String.format("%s%s%s", DigestUtils.md5DigestAsHex(token.getBytes()), 
													DigestUtils.md5DigestAsHex(sessionId.getBytes()), 
													DigestUtils.md5DigestAsHex(signature.getBytes())).getBytes()).substring(0, 16);
				
				String aesIv = signature.substring(0, 16);
				
				byte[] decryptBytes = aesDecrypt(datas, aesKey.getBytes(), aesIv.getBytes());
				OnlineStationObj obj = new OnlineStationObj();
				obj.decode(decryptBytes);
				System.out.println(JsonUtil.bean2Json(obj));
				
			} else {
				
			}
		} else {
			
		}
		
		
		
	}
	
	
	private static String sign(byte[] bytes, String timeStamp) {
		byte[] bytesT = bytes;
		if (bytesT == null || bytesT.length == 0) {
			bytesT = "".getBytes();
		}
		String mixStrMd5AndReverse = StringUtils.reverse(DigestUtils.md5DigestAsHex(bytesT));
		String uuid = UUID.randomUUID().toString().toLowerCase().replace("-", "");
//		String uuid = "5CA2A1CF-23F1-484C-A37E-FD942BA0BA59".toString().toLowerCase().replace("-", "");
		String subUuid = uuid.substring(8, 24);
		
		
		String ret = DigestUtils.md5DigestAsHex(StringUtils.join(subUuid, "|sougukeji|", timeStamp, "|", mixStrMd5AndReverse).getBytes());
		ret = uuid + ret;
		return ret.substring(0, ret.length()-8);
	}
	
	public static void main(String[] args) {
//		000b3030303030303030303030000630303030303000023538000000013100000000
//		byte[] bytes = {0x00,0x0b,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
//				0x30,0x00,0x06,0x30,0x30,0x30,0x30,0x30,0x30,0x00,0x00,0x00,0x0d,
//				0x31,0x35,0x30,0x35,0x36,0x37,0x33,0x34,0x39,0x31,0x31,0x31,0x30,0x00,0x01,0x31};
//		System.out.println(HttpProcessor.sign(bytes, "20170918173158"));
		
		VersionParamObj param = new VersionParamObj();
		param.setStationVersion("");
		param.setIPhoneVersion("");
		
		sendReq(param, 88);
		
		
//		String token = "cc7d414f3c57460d8b77f2d8f249114a";
//		String sessionId = "dbc6a550035640d5baff3777279c1eb7";
//		String signature = "6e72eb3eefec49b683a52032ee4bfdb9";//AES IV
//		String aesKey = DigestUtils.md5Hex(String.format("%s%s%s", DigestUtils.md5Hex(token), 
//											DigestUtils.md5Hex(sessionId), 
//											DigestUtils.md5Hex(signature))).substring(0, 16);
//		System.out.println(aesKey);
		
//		byte [] data = {(byte) 0x35,(byte) 0x45,(byte) 0x32,(byte) 0xc7,(byte) 0x44,(byte) 0x1b,(byte) 0x65,(byte) 0x6c,(byte) 0xed,(byte) 0x00,(byte) 0x2f,(byte) 0x06,(byte) 0x71,(byte) 0xe5,(byte) 0x9c,(byte) 0xc8,(byte) 0x4b,(byte) 0xca,(byte) 0x83,(byte) 0x92,(byte) 0x09,(byte) 0x39,(byte) 0x16,(byte) 0xaf,(byte) 0xcf,(byte) 0x1d,(byte) 0x0e,(byte) 0x1d,(byte) 0xea,(byte) 0xb2,(byte) 0x3f,(byte) 0xda,(byte) 0xac,(byte) 0x73,(byte) 0x98,(byte) 0x66,(byte) 0x8c,(byte) 0x5b,(byte) 0xa3,(byte) 0x28,(byte) 0xe1,(byte) 0x4f,(byte) 0x45,(byte) 0x07,(byte) 0xd8,(byte) 0xdf,(byte) 0x3b,(byte) 0x8f,(byte) 0xe3,(byte) 0x0c,(byte) 0x04,(byte) 0x75,(byte) 0x95,(byte) 0xb4,(byte) 0x50,(byte) 0x7c,(byte) 0x35,(byte) 0x82,(byte) 0xd3,(byte) 0x92,(byte) 0xe6,(byte) 0x08,(byte) 0x2e,(byte) 0xc8,(byte) 0x66,(byte) 0xed,(byte) 0x0a,(byte) 0x2b,(byte) 0x17,(byte) 0xd5,(byte) 0xce,(byte) 0xca,(byte) 0x67,(byte) 0xa9,(byte) 0x95,(byte) 0x9b,(byte) 0xc3,(byte) 0xfb,(byte) 0x79,(byte) 0x93,(byte) 0x5e,(byte) 0xaf,(byte) 0xbf,(byte) 0x9d,(byte) 0xb3,(byte) 0xf4,(byte) 0x13,(byte) 0x32,(byte) 0x02,(byte) 0xd2,(byte) 0x89,(byte) 0x4b,(byte) 0x74,(byte) 0xe9,(byte) 0x7a,(byte) 0xde,(byte) 0xd5,(byte) 0x6c,(byte) 0x8e,(byte) 0x45,(byte) 0x91,(byte) 0xaf,(byte) 0x08,(byte) 0x63,(byte) 0xa4,(byte) 0x40,(byte) 0x7d,(byte) 0x84,(byte) 0xd6,(byte) 0x62,(byte) 0xba,(byte) 0xe7};
//		String key = "ee7ca85f7e96b1a2aa6d1d4f00ba58fa".substring(0,16);
//		String iv = "ac6946effcd04300aeeb0d42afb71ab2".substring(0,16);
//		
//		System.out.println(aesDecrypt(data, key.getBytes(), iv.getBytes()));
		
	}
	
	
	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.DECRYPT_MODE);
	}
	
	@SneakyThrows
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		SecretKey secretKey = new SecretKeySpec(key, AES);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(AES_CBC);
//		cipher.init(mode, secretKey);
		cipher.init(mode, secretKey, ivSpec);
		return cipher.doFinal(input);
	}
	
}
