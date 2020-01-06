package com.xfj.commons.tool.utils;

import com.xfj.commons.tool.domain.MinIODO;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.policy.PolicyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.naming.ldap.Rdn;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @CLassNmae MinIOUtil
 * @Description MinIO Object store server
 * @Author ZQ
 * @Date 2020/1/4 12:50
 * @Version 1.0
 **/
@Slf4j
public class MinIOUtil {

    private String endPoint;
    private String bucketName;
    private String accessKey;
    private String secretKey;

    public MinIOUtil(String endPoint, String bucketName, String accessKey, String secretKey) {
        this.endPoint = endPoint;
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public MinIOUtil(Map<String, Object> configMap) {
        if (null == configMap || configMap.isEmpty()) {
            log.error("connect MinIO config is null,please check configMap");
            return;
        }
        this.endPoint = "http://" + configMap.get("endpoint_host") + ":" + configMap.get("endpoint_port") + "";
        this.bucketName = configMap.get("bucketName") + "";
        this.accessKey = configMap.get("accessKey") + "";
        this.secretKey = configMap.get("secretKey") + "";
    }

    /**
     * @return java.lang.String
     * @Author ZQ
     * @Description upload file
     * @Date 2020/1/4 13:04
     * @Param [file]
     **/
    public MinIODO upload(MultipartFile file) {
        if (null == file) {
            log.error("upload's file not allow null");
            return null;
        }
        MinioClient minioClient = null;
        try {
            minioClient = initMinIO();
            String fileName = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //set store object name ===> file whole name
            String objectName = sdf.format(new Date()) + "/" + fileName;
            //use putObject upload file to store bucket
            minioClient.putObject(bucketName, objectName, file.getInputStream(), file.getContentType());
            log.info("File Upload Success !");
            return RData(fileName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Fail !");
        }
        return null;
    }

    private MinIODO RData(String fileName, String objectName) {
        MinIODO minDO = new MinIODO();
        minDO.setName(fileName);
        minDO.setUrl(endPoint + "/" + bucketName + "/" + objectName);
        return minDO;
    }

    /**
     * @return com.xfj.commons.tool.domain.MinIODO
     * @Author ZQ
     * @Description upload Base64 file(image)
     * @Date 2020/1/5 18:35
     * @Param [imageData, fileName]
     **/
    public MinIODO uploadBase64(String imageData, String fileName) {
        if (null == imageData) {
            log.error("update's imageData is not allow null");
            return null;
        }
        String contentType = "multipart/form-data";
        //获取文件后缀名
        String fileSuffixName64 = ImageUtil.getFileSuffixName64(imageData);
        //文件全名称
        fileName = fileName + "." + fileSuffixName64;
        try {
            MinioClient minioClient = initMinIO();
            //设置文件全名称
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //set store object name ===> file whole name
            String objectName = sdf.format(new Date()) + "/" + fileName;

            // 调用putObject方法上传文件
            minioClient.putObject(bucketName, objectName,
                    ImageUtil.getInputStreamFromBase64(imageData), contentType);

            return RData(fileName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private MinioClient initMinIO() throws Exception {
        MinioClient minioClient = null;
        minioClient = new MinioClient(endPoint, accessKey, secretKey);
        boolean isExist = minioClient.bucketExists(bucketName);
        if (isExist) {
            log.warn("store bucket already exist");
        } else {
            // create store bucket
            minioClient.makeBucket(bucketName);
            // set only read authority
            minioClient.setBucketPolicy(bucketName, "*.*", PolicyType.READ_ONLY);
        }
        return minioClient;
    }
}
