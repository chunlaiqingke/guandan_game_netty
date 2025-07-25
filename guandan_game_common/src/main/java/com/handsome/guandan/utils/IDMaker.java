package com.handsome.guandan.utils;

import java.util.Random;

public class IDMaker {

    static Random RANDOM = new Random();

    public static String randomId(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0, current count: " + count);
        }

        // 预分配 StringBuilder 容量（优化性能，避免多次扩容）
        StringBuilder sb = new StringBuilder(count);

        // 循环生成随机数字并拼接
        for (int i = 0; i < count; i++) {
            // 生成 0-9 的随机整数（Random.nextInt(10) 直接返回 [0,10) 的整数）
            int randomNum = RANDOM.nextInt(10);
            // 将数字转为字符并拼接（数字 0-9 对应字符 '0'-'9'）
            sb.append(randomNum);
        }

        return sb.toString();
    }
}
