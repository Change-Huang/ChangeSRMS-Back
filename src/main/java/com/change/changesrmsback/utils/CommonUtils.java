package com.change.changesrmsback.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 一些通用工具类
 * @author Change
 */
public class CommonUtils {

    /**
     * 生成英文大写的32位UUID
     * @return 返回英文大写的32位UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 获得当前14位日期，精确到秒
     * @return 如2019年12月8号16时05分28秒777毫秒，则显示为20191208160528777
     */
    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateFormat.format(date);
    }

    /**
     * 获得当前14位日期，精确到秒
     * @return 如2019年12月8号16时05分28秒，则显示为20191208160528
     */
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }

    /**
     * 随机生成四位数字字符串
     * @return 包含四位数的随机字符串
     */
    public static String getRandomCode() {
        Random random = new Random();
        String codes = "1234567890";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(codes.length());
            result.append(codes.charAt(index));
        }
        return result.toString();
    }
}
