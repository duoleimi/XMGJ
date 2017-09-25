package com.zzy.base.utils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;

/**
 * 字符串处理
 * 
 * @author zwm
 */
public class StringUtilsEx {

    /**
     * 处理智能手机输入法表情乱码字符串 导入数据库出错问题
     */
    public static String getStrFromMobileLook(String lookInStr) throws Exception {
        byte[] bText = lookInStr.getBytes("UTF8");
        for (int i = 0; i < bText.length; i++) {
            if ((bText[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    bText[i + j] = 0x20;
                }
                i += 3;
            }
        }
        return new String(bText, "UTF-8");
    }

    /**
     * 获取随机字符串
     * 
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        if (length <= 0) {
            return "";
        }
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        Random random = new Random();
        char[] randomStr = new char[length];
        for (int i = 0; i < length; i++) {
            randomStr[i] = numbersAndLetters[random.nextInt(numbersAndLetters.length)];
        }
        return String.valueOf(randomStr);
    }

    /**
     * 获取随机字符串（仅含字符）
     */
    public static String getRandomLetterStr(int length) {
        if (length <= 0) {
            return "";
        }
        char[] numbersAndLetters = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        Random random = new Random();
        char[] randomStr = new char[length];
        for (int i = 0; i < length; i++) {
            randomStr[i] = numbersAndLetters[random.nextInt(numbersAndLetters.length)];
        }
        return String.valueOf(randomStr);
    }

    /**
     * 获取随机数字字符串
     * 
     * @param length
     * @return
     */
    public static String getRandomNumberStr(int length) {
        if (length <= 0) {
            return "";
        }
        char[] numbersAndLetters = ("0123456789").toCharArray();
        Random random = new Random();
        char[] randomStr = new char[length];
        for (int i = 0; i < length; i++) {
            randomStr[i] = numbersAndLetters[random.nextInt(numbersAndLetters.length)];
        }
        return String.valueOf(randomStr);
    }

    /**
     * 返回字符串的限制长度，默认后缀"..."
     * 
     * @param str
     * @param maxLength
     * @param endWith
     * @return
     */
    public static String limitStrLength(String str, int maxLength, String endWith) {
        if (str.length() <= maxLength) {
            return str;
        }
        endWith = endWith == null ? "..." : endWith;
        maxLength = maxLength - endWith.length();
        return str.substring(0, maxLength) + endWith;
    }

    /**
     * 将 s 进行 BASE64 编码
     * 
     * @param s
     * @return
     */
    public static String getBase64(String s) {
        if (s == null)
            return null;
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     * 
     * @param s
     * @return
     */
    public static String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 首字母小写
     * 
     * @param str
     * @return
     */
    public static String firstLetterToLower(String str) {
        char[] array = str.toCharArray();
        if (array[0] >= 'A' && array[0] <= 'Z') {
            str = str.replaceFirst(String.valueOf(array[0]), String.valueOf(array[0]).toLowerCase());
        }
        return str;
    }

    /**
     * 首字母大写
     * 
     * @param str
     * @return
     */
    public static String firstLetterToUpper(String str) {
        char[] array = str.toCharArray();
        array[0] -= 32;
        return String.valueOf(array);
    }

    /**
     * 获取字符串sha1编码
     * 
     * @param sourceString
     * @return
     */
    public static String SHA1Encode(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    /**
     * 获取字节sha1编码
     * 
     * @param bytes
     * @return
     */
    public static final String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString().toUpperCase();
    }

    /**
     * 去除html标签
     * 
     * @param html
     * @return
     */
    public static String removeHtmlTag(String html) {
        if (html == null || "".equals(html))
            return "";
        String textStr = "";
        java.util.regex.Pattern pattern;
        java.util.regex.Matcher matcher;

        try {
            String regEx_remark = "<!--.+?-->";
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            String regEx_html1 = "<[^>]+";
            html = html.replaceAll("\n", "");
            html = html.replaceAll("\t", "");

            pattern = Pattern.compile(regEx_remark);
            matcher = pattern.matcher(html);
            html = matcher.replaceAll("");

            pattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(html);
            html = matcher.replaceAll("");

            pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(html);
            html = matcher.replaceAll("");

            pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(html);
            html = matcher.replaceAll("");

            pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(html);
            html = matcher.replaceAll("");

            textStr = html.trim();

        } catch (Exception e) {
            System.out.println("获取HTML中的text出错:");
            e.printStackTrace();
        }
        return textStr;
    }

    /**
     * 处理Restrictions.like()中的参数
     * 
     * @param keyword
     * @return
     */
    public static String escapeCriteriaLikeString(String keyword) {
        keyword = keyword.replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_")
                .replaceAll("'", "\\\\'");
        return keyword;
    }

    /**
     * 通过url获取相应的host
     * 
     * @param url
     * @return
     */
    public static String getHostByUrl(String url) {
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group();
        } else {
            return url;
        }
    }

    /**
     * 解析url参数格式
     */
    public static Map<String, String> parseUrlParam(String paramString) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String[] params = paramString.split("&");
        for (String param : params) {
            String[] arr = param.split("=");
            if (arr.length == 2) {
                resultMap.put(arr[0], arr[1]);
            }
        }
        return resultMap;
    }

    /**
     * 将传入数值转化为K、M、G可读格式
     * 
     * @param $size
     *            number
     */
    public static String transUnit(long size) {
        return transUnit(size, "#0.##");
    }

    /**
     * 将传入数值转化为K、M、G可读格式
     * 
     * @param $size
     *            number
     */
    public static String transUnit(long size, String format) {
        DecimalFormat formater = new DecimalFormat(format);
        if (size == 0) {
            return "0 B";
        }
        double g_size = 1024 * 1024 * 1024;
        if (size >= g_size) {
            return formater.format(size / g_size) + " GB";
        }
        double m_size = 1024 * 1024;
        if (size >= m_size) {
            return formater.format(size / m_size) + " MB";
        }
        double k_size = 1024;
        if (size >= k_size) {
            return formater.format(size / k_size) + " KB";
        }
        return formater.format(size) + " B";
    }

    /**
     * 获取系统路径
     * 
     * @return
     */
    public static String getContextPath() {
        try {
            return URLDecoder.decode(StringUtilsEx.class.getResource("/").getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字符长度，一个汉字作为 2 个字符, 一个英文字母作为 1 个字符
     * 
     * @param text
     */
    public static int getByteLength(String string) {
        int len = 0;
        for (int i = 0; i < string.length(); i++) {
            len += String.valueOf(string.charAt(i)).getBytes().length;
        }
        return len;
    }

    /**
     * 判断字符串为null or 空
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * 获取前length个字符
     * 
     * @param str
     * @param length
     * @return
     */
    public static String getSubStringByLen(String str, int length) {
        if (isNullOrEmpty(str))
            return "";
        if (str.length() <= length)
            return str;
        return str.substring(0, length);
    }

    /**
     * 字符串阶段
     */
    public static String getLastString(String string, int length) {
        return string.substring(string.length() - length, string.length());
    }

    /**
     * 数组转字符串
     */
    public static String join(Object[] objects, String delimiter) {
        if (objects == null || objects.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String _delimiter = "";
        for (Object object : objects) {
            stringBuffer.append(_delimiter + String.valueOf(object));
            _delimiter = delimiter;
        }
        return stringBuffer.toString();

    }

    /**
     * md5加密
     * 
     * @param s
     * @return
     */
    public static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 过滤字符串
     * 
     * @param string
     * @param tag
     * @return String
     */
    public static String trim(String string, String tag) {
        if (ParameterChecker.isNullOrEmpty(string)) {
            return string;
        }
        return rtrim(ltrim(string, tag), tag);
    }

    /**
     * 过滤字符串右侧字符
     * 
     * @param string
     * @param tag
     * @return String
     */
    public static String rtrim(String string, String tag) {
        if (ParameterChecker.isNullOrEmpty(string)) {
            return string;
        }
        Pattern pattern = Pattern.compile(tag + "$", Pattern.MULTILINE);
        Matcher sourceMatcher = pattern.matcher(string);
        while (sourceMatcher.find()) {
            string = string.substring(0, string.length() - tag.length());
            string = rtrim(string, tag);
        }
        return string;
    }

    /**
     * 过滤字符串左侧字符
     * 
     * @param string
     * @param tag
     * @return String
     */
    public static String ltrim(String string, String tag) {
        if (ParameterChecker.isNullOrEmpty(string)) {
            return string;
        }
        Pattern pattern = Pattern.compile("^" + tag, Pattern.MULTILINE);
        Matcher sourceMatcher = pattern.matcher(string);
        while (sourceMatcher.find()) {
            string = string.substring(tag.length());
            string = ltrim(string, tag);
        }
        return string;
    }

    /**
     * 转义字符串，对单引号双引号和转义符做处理
     * 
     * @param str
     * @return
     */
    public static String getJsEncodeString(String str) {
        return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'");
    }

    public static String concat(int... ids) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int id : ids) {
            stringBuffer.append(id);
            stringBuffer.append("_");
        }
        return stringBuffer.toString().replaceAll("_$", "");
    }

    /**
     * 关联String数组各个字串，中间用,分割
     * 
     * @param stringArray
     *            String数组
     * @return String 连接后的字串a,b
     */
    public static String contactStringByComma(String[] stringArray) {
        if (stringArray == null || stringArray.length == 0)
            return "";
        StringBuilder result = new StringBuilder();
        for (String s : stringArray) {
            if (s.trim().length() > 0)
                result.append(s).append(",");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    /**
     * 过滤字符串中，js不支持的字符
     * 
     * @return
     */
    public static String clearUnsupportedCharacter(String input) {
        return input.replaceAll("[\\u2029\\u2028]", "");
    }

    /**
     * 数组拼接成字符串
     * 
     * @param collection
     * @param ch
     * @return
     */
    public static <T> String join(Collection<T> collection, String ch) {
        String format = "";
        while (collection.iterator().hasNext()) {
            format += collection.iterator().toString();
        }
        return format;
    }

    /**
     * 生成订单号
     * 
     * @return
     */
    public static String getSerialNo() {
        return String.valueOf(System.currentTimeMillis()) + getRandomStr(5);
    }

    /**
     * 获取超链接 带http
     * 
     * @param url
     * @return
     */
    public static String getHTTPString(String url) {
        if (url == null)
            return "";
        if (url.toUpperCase().startsWith("HTTP://") || url.toUpperCase().startsWith("HTTPS://")) {
            return url;
        }
        return "http://" + url;
    }

    /**
     * 电话号码加密
     * 
     * @param cellphone
     * @return
     */
    public static String getEncodeCellphone(String cellphone) {
        return cellphone == null ? null : cellphone.replaceAll("^(\\d{3})(\\d{4})(\\d{4})$", "$1****$3");
    }

    /**
     * 根据正则表达式匹配字符串，默认返回第一个位置的第一个字符串
     * 
     * @param patternString
     * @return
     */
    public static String getMatchStringByPattern(String matchString, String patternString) {
        Pattern chinaPattern = Pattern.compile(patternString);
        Matcher matcher = chinaPattern.matcher(matchString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 将输入字符串逐行读取生成数组
     * 
     * @param orginString
     * @return
     */
    public static String[] getLineArray(String orginString) {
        String[] lineArray = null;
        if (StringUtils.isBlank(orginString)) {
            lineArray = new String[0];
        } else {
            try {
                BufferedReader rdr = new BufferedReader(new StringReader(orginString));
                List<String> lines = new ArrayList<String>();
                for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
                    lines.add(line);
                }
                rdr.close();
                String[] resultArary = new String[lines.size()];
                lineArray = lines.toArray(resultArary);
            } catch (Exception ex) {
                ex.printStackTrace();
                lineArray = new String[0];
            }
        }
        return lineArray;
    }

    /**
     * 根据输入字符串拆分查询关键字
     * 
     * @param str
     * @return
     */
    public static Set<String> getSplitQueryWordFromString(String str) {
        Pattern ChinesePattern = Pattern.compile("[\u4e00-\u9fa5]");
        Pattern EnglishPattern = Pattern.compile("[^a-zA-Z0-9]");

        Set<String> splitResult = new HashSet<String>();
        Matcher matcher = ChinesePattern.matcher(str);
        while (matcher.find()) {
            splitResult.add(matcher.group());
        }
        String filterWords = EnglishPattern.matcher(str).replaceAll(" ").trim().toLowerCase();

        String[] aa = filterWords.trim().split("\\s+");
        for (String t : aa) {
            splitResult.add(t);
        }
        return splitResult;
    }
    
    
    /**
     * 去掉除-+.数字类的字符
     * @param str
     * @return
     */
    public static String stripNotNumberChar(String str){
        return str.replaceAll("[^-+.\\d]", "");
    }
}
