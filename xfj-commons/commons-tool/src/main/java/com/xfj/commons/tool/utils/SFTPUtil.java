package com.xfj.commons.tool.utils;

import com.jcraft.jsch.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @CLassNmae FtpUtil
 * @Description ftp工具类
 * @Author ZQ
 * @Date 2019/12/18 14:17
 * @Version 1.0
 **/
@Slf4j
public class SFTPUtil {

    private static Session session = null;
    @Getter
    private String hostName;
    private int port;
    private String userName;
    private String passWord;

    public SFTPUtil(Map<String, Object> configMap) {
        if (null == configMap || configMap.isEmpty()) {
            log.error("connect sftp config is null,please check configMap");
            return;
        }
        this.hostName = configMap.get("host").toString();
        this.port = Integer.parseInt(configMap.get("port").toString());
        this.userName = configMap.get("username").toString();
        this.passWord = configMap.get("password").toString();
    }

    /**
     * @return
     * @Author ZQ
     * @Description 使用配置文件中的配置
     * @Date 2019/12/18 14:40
     * @Param []
     **/
    public ChannelSftp getSFtpChanel() {
        return getSFtpChanel(hostName, port, userName, passWord);
    }

    /**
     * @return
     * @Author ZQ
     * @Description 获取Sftp客户端(可以指定相关配置)
     * @Date 2019/12/18 14:21
     * @Param []
     **/
    public ChannelSftp getSFtpChanel(String hostname, int port, String username, String password) {
        JSch jsch = new JSch();
        ChannelSftp sftp = null;
        try {
            session = jsch.getSession(username, hostname, port);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");//不使用秘钥也可以登录
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return sftp;
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 退出
     * @Date 2019/12/18 16:13
     * @Param [sftp]
     **/
    public static void logout(ChannelSftp sftp) {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
