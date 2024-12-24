package com.designershop.utils;

import java.security.SecureRandom;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
public final class RandomTokenUtil {

    public static String randomTokenGenerate(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length / 2];
        random.nextBytes(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
