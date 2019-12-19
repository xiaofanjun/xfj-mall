package com.xfj.commons.tool.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Map;


/**
 * @CLassNmae ImageUtil
 * @Description 图片相关工具类
 * @Author ZQ
 * @Date 2019/12/18 13:43
 * @Version 1.0
 **/
@Slf4j
public class ImageUtil {
    private String imageUrl;

    private Map<String, Object> configMap;

    public ImageUtil(Map<String, Object> configMap) {
        if (null == configMap || configMap.isEmpty()) {
            log.error("upload image url is null,please check config");
            return;
        }
        this.imageUrl = configMap.get("image_head_url").toString();
        this.configMap = configMap;
    }


    /**
     * @return java.lang.String 文件名称
     * @Author ZQ
     * @Description 上传Base64图片
     * @Date 2019/12/18 15:12
     * @Param [imageData base64数据, fileName 文件名称]
     **/
    public String upLoadImageBase64(String imageData, String fileName) {
        ByteArrayInputStream inputStream = null;
        String suffix = null;//后缀名
        try {
            suffix = imageData.split("base64,")[0].split("image/")[1]
                    .replaceAll(";", "").trim();
            //去除不是图片的信息，解决图片打不开问题
            byte[] byteData = Base64.decodeBase64(imageData.split("base64,")[1]);
            inputStream = new ByteArrayInputStream(byteData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "http://" + uploadFile(inputStream, fileName + "." + suffix);
    }

    /**
     * @return java.lang.String 返回存在服务器上的全路径
     * @Author ZQ
     * @Description 上传文件
     * @Date 2019/12/18 15:11
     * @Param [inputStream, fileName 文件全名称  lr ***.jpg,***.doc 等]
     **/
    private String uploadFile(InputStream inputStream, String fileName) {
        if (null == inputStream) {
            log.error("uploadFile.param InputStream is  null");
            return "";
        }
        if (StringUtils.isEmpty(fileName)) {
            log.error("uploadFile.param fileName is null");
            return "";
        }
        FileOutputStream out = null;
        SFTPUtil sftpUtil = new SFTPUtil(configMap);
        ChannelSftp sFtpChanel = sftpUtil.getSFtpChanel();
        if (!sFtpChanel.isConnected()) {
            log.error("SFtp Not Connected .");
            return "";
        }
        try {
            sFtpChanel.cd(imageUrl);//cd 到指定目录
            sFtpChanel.put(inputStream, fileName);
        } catch (Exception e) {
            try {
                //目录不存在,则创建目录
                sFtpChanel.mkdir(imageUrl);
                sFtpChanel.put(inputStream, fileName);
            } catch (SftpException ex) {
                ex.printStackTrace();
            }
        } finally {
            SFTPUtil.logout(sFtpChanel);//退出
        }
        return sftpUtil.getHostName() + "/" + fileName;
    }
}
