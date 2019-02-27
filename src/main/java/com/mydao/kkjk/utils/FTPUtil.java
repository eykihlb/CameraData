package com.mydao.kkjk.utils;

import com.mydao.kkjk.config.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.SocketException;

@Component
public class FTPUtil {

    private static FTPConfig fc;

    @Autowired
    public void setFtpConfig(FTPConfig ftpConfig){
        FTPUtil.fc = ftpConfig;
    }
    public static FTPConfig getFc(){
        return fc;
    }

    /**
     * 获取FTPClient对象
     */
    private static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                          String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 杩炴帴FTP鏈嶅姟鍣�
            ftpClient.login(ftpUserName, ftpPassword);// 鐧婚檰FTP鏈嶅姟鍣�
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
            } else {

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 从FTP服务器下载文件
     */
    public static String downloadFtpFile() throws Exception{
        OutputStream os = null;
        try {
            if(fc == null) {
                return "";
            }
            FTPClient ftpClient = getFTPClient(fc.getFtpHost(),fc.getFtpUserName()
                    , fc.getFtpPassword(), fc.getFtpPort());
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(fc.getErrorFilePath());
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                File localFile = new File(fc.getLocalPath() + "/" + file.getName());
                os = new FileOutputStream(localFile);
                if (ftpClient.retrieveFile(file.getName(), os)){//下载文件
                    ftpClient.deleteFile(file.getName());//下载成功后删除文件
                }
                os.flush();
                os.close();
            }

            ftpClient.logout();
        } catch (Exception e) {
            System.out.println("文件下载失败！");
            System.out.println(e.getMessage());
        }finally {
            if (os != null){
                os.flush();
                os.close();
            }
        }
        return "";

    }

    /**
     * Description: 向FTP服务器上传文件
     * @param fileName ftp文件名称
     //* @param dd 文件流
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    /*public static String uploadFile(String fileName, InputStream input,boolean flag) throws IOException {
        //String n = fileName.substring(0,fileName.indexOf("_"));
        //String b = fileName.substring(fileName.indexOf("."),fileName.length());
        //fileName = n+b;
        String success = "0";
        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient = getFTPClient(fc.getFtpHost(),fc.getFtpUserName(),fc.getFtpPassword(),fc.getFtpPort());
            if (ftpClient == null){
                System.out.println("FTP链接失败！");
                return "FTP链接失败！";
            }
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return "FTP异常！";
            }
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            if (flag){
                ftpClient.changeWorkingDirectory(fc.getFtpPath());
            }else{
                ftpClient.changeWorkingDirectory(fc.getErrorFilePath());
            }

            //ftpClient.enterLocalPassiveMode();
            fileName = new String(fileName.getBytes("GBK"),"iso-8859-1");
            if (ftpClient.storeFile(fileName, input)){
                success = "http://"+fc.getHttpHost()+":"+fc.getHttpPort()+fc.getHttpPath()+fileName;
            }else{
                success = "文件上传失败！";
            }
            input.close();
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return success;
    }*/

    public static String getPath(String fileName,String ctime){
        return "http://"+fc.getHttpHost()+":"+fc.getHttpPort()+fc.getHttpPath()+ctime.substring(0,8)+"/"+fileName;
    }

}
