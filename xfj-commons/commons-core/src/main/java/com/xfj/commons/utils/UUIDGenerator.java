package com.xfj.commons.utils;

import java.util.UUID;

/**
 * @ClassName UUIDGenerator
 * @Description 自动生成id
 * @Author ZQ
 * @Date 2019/3/22 15:51
 */
public class UUIDGenerator {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
