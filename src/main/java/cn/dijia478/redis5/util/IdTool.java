package cn.dijia478.redis5.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * id生成工具
 *
 * @author dijia478
 * @date 2019-4-25 10:56
 */
public class IdTool {

    private static final String BASE_CHAR_NUMBER = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * UUID
     *
     * @return 36个字符的id
     */
    public static String getId1() {
        return UUID.randomUUID().toString();
    }

    /**
     * 13位时间戳+6位随机数
     *
     * @return 19位数字id
     */
    public static Long getId2() {
        return System.currentTimeMillis() * 1000000L + ThreadLocalRandom.current().nextInt(0, 1000000);
    }

    /**
     * 长度为n的随机字符串，包含大小写字母和数字
     *
     * @param length 长度
     * @return 长度为n的id
     */
    public static String getId3(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(BASE_CHAR_NUMBER.charAt(ThreadLocalRandom.current().nextInt(62)));
        }
        return sb.toString();
    }

    /**
     * 13位时间戳+9位随机数
     *
     * @return 22位数字id
     */
    public static String getId4() {
        return String.valueOf(System.currentTimeMillis()) + ThreadLocalRandom.current().nextInt(100000000, 1000000000);
    }

}
