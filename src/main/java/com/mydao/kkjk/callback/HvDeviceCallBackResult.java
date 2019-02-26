package com.mydao.kkjk.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydao.kkjk.config.FTPConfig;
import com.mydao.kkjk.entity.DataSnap;
import com.mydao.kkjk.sdk.HvApiCallBack;
import com.mydao.kkjk.sdk.HvDeviceDataType;
import com.mydao.kkjk.sdk.HvDeviceSDK;
import com.mydao.kkjk.utils.FTPUtil;
import com.mydao.kkjk.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class HvDeviceCallBackResult{
	/**结果回调函数*/
	public static class ResultFunction extends Thread implements HvApiCallBack.HVAPI_CALLBACK_RESULT {

		ObjectMapper om = new ObjectMapper();
		@Override
		@Async
		public int result(HvDeviceDataType.HvResult.ByReference pUserData,
						  int dwResultFlag,
						  int dwResultType,
						  int dwCarID,
						  String pcPlateNo,
						  String pcAppendInfo,
						  long dw64TimeMS,
						  HvDeviceDataType.CImageInfo.ByValue pPlate,
						  HvDeviceDataType.CImageInfo.ByValue pPlateBin,
						  HvDeviceDataType.CImageInfo.ByValue pBestSnapshot,
						  HvDeviceDataType.CImageInfo.ByValue pLastSnapshot,
						  HvDeviceDataType.CImageInfo.ByValue pBeginCapture,
						  HvDeviceDataType.CImageInfo.ByValue pBestCapture,
						  HvDeviceDataType.CImageInfo.ByValue pLastCapture) {
			try {

				FTPConfig ftpConfig = FTPUtil.getFc();
				//不是有效数据就丢弃
				if(dwResultFlag != HvDeviceDataType.RESULT_FLAG_VAIL) {
					System.out.println("不是有效数据");
					return 0;
				}

				//不是实时结果数据就丢弃
				if(dwResultType != HvDeviceDataType.RECORD_TYPE_NORMAL) {
					System.out.println("不是实时结果");
					return 0;
				}

				Date current_time = new Date();
				//SimpleDateFormat sdf_current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				HvDeviceDataType.HvResult result = pUserData;
				//result.time = sdf_current.format(current_time);
				//result.carID = dwCarID;

				if(pcPlateNo.indexOf("无车牌") > -1){
					result.plateNo = pcPlateNo;
					result.plateNoColor = "";
					return 0;
				}
				else {
					int len = pcPlateNo.length();
					result.plateNoColor = pcPlateNo.substring(0, 1);
					result.plateNo = pcPlateNo.substring(1, len);
				}

				//附加信息
				/*int appendInfoLen = 10240;
				byte[] appendInfo = new byte[appendInfoLen];
				HvDeviceSDK._sdk1.HVAPIUTILS_ParsePlateXmlStringEx(pcAppendInfo, appendInfo, appendInfoLen);
				try {
					result.appendInfo = new String(appendInfo,"gbk");
				} catch (UnsupportedEncodingException e) {
					result.appendInfo = "附加信息处理异常！";
				}*/
				//appendInfo = null;

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				//存储图片目录
				//String Dir = System.getProperty("user.dir") + "\\Pic\\" + sdf.format(current_time) + "\\";
				String Dir = ftpConfig.getFtpfpath() + sdf.format(current_time) + "\\";

				//大图图片类型
				int big_image_type = 0;

				try
				{
					//车牌小图
					if(pPlate.fHasData == 1) {
						result.smallPicPath = Utils.SaveSmallImage(Dir, pPlate, current_time, result.plateNo);
					}
					else {
						result.smallPicPath = "";
					}

					/*//二值图
					result.binPicPath = "";

					//最清晰大图
					result.bigPicPath = "";*/

					//最后大图
					if(pLastSnapshot.fHasData == 1) {
						big_image_type = 0;
						result.lastBigPicPath = Utils.SaveBigImage(big_image_type, Dir, pLastSnapshot, current_time, result.plateNo);
					}
					else {
						result.lastBigPicPath = "";
					}
				}
				catch(Exception e)
				{
					System.out.println("回调数据整理异常！");
					System.out.println(e.getMessage());
					return 0;
				}
				System.out.println("车牌号：" + pUserData.plateNo);
				BufferedWriter out = null;
				//FileInputStream fis = null;
				try {
					DataSnap ds = new DataSnap();
					ds.setCameraIp(pUserData.ip+"");
					ds.setPicUrl(pUserData.lastBigPicPath);
					ds.setPlateColorCode(pUserData.plateNoColor);
					ds.setPlateNo(pUserData.plateNo);
					String res = om.writeValueAsString(ds);
					File writeName = new File(ftpConfig.getFtpfpath()+ftpConfig.getErrorFilePath()+new Date().getTime()+".txt");
					writeName.createNewFile();
					FileWriter writer = new FileWriter(writeName);
					out = new BufferedWriter(writer);
					out.write(res);
					out.flush(); // 把缓存区内容压入文件
					writer.flush();
					writer.close();
					out.close();
				}catch (Exception e){
					System.out.println("数据保存失败！" + e.getMessage());
				}finally {
					if (out != null){
						out.close();
					}
				}
				return 0;
			}catch (Exception e){
				System.out.println("回调异常！---"+e.getMessage());
				return 0;
			}
		}
	}
}
