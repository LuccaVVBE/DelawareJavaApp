package util;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CuidGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final SecureRandom random = new SecureRandom();

    public static String createCuid() {
        String timestamp = Long.toHexString(System.currentTimeMillis());
        String counterStr = Integer.toHexString(counter.getAndIncrement() & 0xffffff);
        String randomStr = Long.toHexString(random.nextLong() & 0xffffffffL);

        return "c" + padLeft(timestamp, 8) + padLeft(counterStr, 6) + padLeft(randomStr, 8);
    }

    private static String padLeft(String str, int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = str.length(); i < length; i++) {
            builder.append('0');
        }
        builder.append(str);
        return builder.toString();
    }

    public static String getRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,./-=*!";
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }

    public static String getRandomNumber(int length) {
        String chars = "0123456789";
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }
}