package com.xfj.user.utils;

import com.xfj.user.entitys.SystemConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @CLassNmae SystemConfigUtil
 * @Description 系统配置工具类
 * @Author ZQ
 * @Date 2019/12/19 11:11
 * @Version 1.0
 **/
public class SystemConfigUtil {
    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author ZQ
     * @Description 获取图片上传相关配置
     * @Date 2019/12/19 11:18
     * @Param [systemConfigs]
     **/
    public static Map<String, Object> getConfigUploadImage(List<SystemConfig> systemConfigs) {
        Map<String, Object> map = new HashMap<>();
        for (SystemConfig systemConfig : systemConfigs) {//sftp相关配置
            if ("100".equals(systemConfig.getModule())) {
                String key = systemConfig.getModuleKey();
                String value = systemConfig.getModuleValue();
                if ("host".equals(key)) {
                    map.put(key, value);
                }
                if ("port".equals(key)) {
                    map.put(key, value);
                }
                if ("username".equals(key)) {
                    map.put(key, value);
                }
                if ("password".equals(key)) {
                    map.put(key, value);
                }
            }
            if ("101".equals(systemConfig.getModule())) {//图片上传路径
                String key = systemConfig.getModuleKey();
                String value = systemConfig.getModuleValue();
                if ("image_head_url".equals(key)) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }
}
