package com.mydao.kkjk.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "servercfg")
public class FTPConfig {

    private String ftpfpath;

    public String getFtpfpath() {
        return ftpfpath;
    }

    public void setFtpfpath(String ftpfpath) {
        this.ftpfpath = ftpfpath;
    }

    private String ftpHost;
    private String ftpUserName;
    private String ftpPassword;
    private int ftpPort;
    private String ftpPath;
    private String errorFilePath;
    private String localPath;
    private String httpHost;
    private String httpPort;
    private String httpPath;
    private String dvrIp;
    private String netNo;

    public String getDvrIp() {
        return dvrIp;
    }

    public void setDvrIp(String dvrIp) {
        this.dvrIp = dvrIp;
    }

    public String getNetNo() {
        return netNo;
    }

    public void setNetNo(String netNo) {
        this.netNo = netNo;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    public String getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }
}
