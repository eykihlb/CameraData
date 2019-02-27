package com.mydao.kkjk.device;

import com.mydao.kkjk.callback.HvDeviceCallBackResult;
import com.mydao.kkjk.config.FTPConfig;
import com.mydao.kkjk.dao.DataSnapMapper;
import com.mydao.kkjk.sdk.HvDeviceDataType;
import com.mydao.kkjk.sdk.HvDeviceSDK;
import com.mydao.kkjk.utils.Utils;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class HvDevice {
	/**设备IP*/
	private String _ip = "";
	/**设备句柄*/
	private Pointer _handle = null;
	/**设备类型,0:一体机设备，1,车型设备*/
	private int _type = 0;
	/**结果对象(回调用户参数)*/
	private HvDeviceDataType.HvResult.ByReference _result = new HvDeviceDataType.HvResult.ByReference();
	/**结果回调函数*/
	private HvDeviceCallBackResult.ResultFunction _result_callback = new HvDeviceCallBackResult.ResultFunction();

	public HvDevice() {
		System.setProperty("jna.encoding", "GBK");
	}

	/**获取设备句柄*/
	public Pointer GetDeviceHandle(){
		return this._handle;
	}

	/**打开设备*/
	public boolean OpenDevice(String ip,int type) {
		if(!Utils.IsIP(ip)) {
			return false;
		}
		if(type == 0) {
			_handle = HvDeviceSDK._sdk1.HVAPI_OpenEx(ip, null);
		}
		else {
			_handle = HvDeviceSDK._sdk1.HVAPI_OpenChannel(ip, null, 0);
		}
		_ip = ip;
		_type = type;
		System.out.println("设备已连接,IP:" + _ip);
		return true;
	}

	/**关闭设备*/
	public void CloseDevice() {
		if(_handle != null) {
			HvDeviceSDK._sdk1.HVAPI_CloseEx(_handle);
			_handle = null;
			System.out.println("设备已关闭");
		}
	}

	/**开始接收结果*/
	public boolean StartResult(FTPConfig ftpConfig,DataSnapMapper dataSnapMapper) {
		if(_handle == null) {
			return false;
		}
		_result.ip = _ip;
		_result.type = _type;
		ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
		LocalDateTime localDateTime = LocalDateTime.of(2019,2,26,0,0);
		String carId = "0";
		if (ftpConfig!=null&&dataSnapMapper!=null){
			 carId = dataSnapMapper.selectLastData(ftpConfig.getNetNo());
			 if (carId == null){
			 	carId = "0";
			 }
		}
		if(0 == HvDeviceSDK._sdk1.HVAPI_StartRecvResult(_handle, _result_callback, _result, 0, localDateTime.toEpochSecond(zoneOffset), 0L, Integer.parseInt(carId)+1, HvDeviceDataType.RESULT_RECV_FLAG_HISTORY))
		{
			System.out.println("开始接收结果！设备IP：" + _ip);
			return true;
		}
		return false;
	}

	/**停止接收结果*/
	public boolean StopResult() {
		if(_handle == null) {
			return false;
		}
		System.out.println("停止接收结果！设备IP：" + _ip);
		return (0 == HvDeviceSDK._sdk1.HVAPI_StopRecvResult(_handle));
	}

	/**获取设备连接状态*/
	public boolean GetConnectStatus() {
		if(_handle == null){
			return false;
		}
		IntByReference status = new IntByReference(HvDeviceDataType.CONN_STATUS_UNKNOWN);
		if(0 == HvDeviceSDK._sdk1.HVAPI_GetConnStatusEx(_handle, HvDeviceDataType.CONN_TYPE_RECORD, status)) {
			if(status.getValue() == HvDeviceDataType.CONN_STATUS_NORMAL) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	/**一体机功能(只针对一体机):设置SOD*/
	public boolean SetSOD(String text,int font_size) {
		if(_handle == null || _type != 0) {
			return false;
		}
		int nStreamId = 0;
		boolean fOSDEnable = true;
		if (0 == HvDeviceSDK._sdk1.HVAPI_SetOSDEnable(_handle, nStreamId, fOSDEnable)){
			int nColorR = 255;
			int nColorG = 0;
			int nColorB = 0;
			if(0 != HvDeviceSDK._sdk1.HVAPI_SetOSDFont(_handle, nStreamId, font_size, nColorR, nColorG, nColorB)){
				return false;
			}
			int nPosX = 10;
			int nPosY = 10;
			if(0 != HvDeviceSDK._sdk1.HVAPI_SetOSDPos(_handle, nStreamId, nPosX, nPosY)){
				return false;
			}
			if(0 != HvDeviceSDK._sdk1.HVAPI_SetOSDText(_handle, nStreamId, text)){
				return false;
			}
			return true;
		}
		return false;
	}

	/**一体机功能(只针对一体机):下载黑白名单到设备,字符串必须符合规定的格式
	 * 格式：车牌号-日期(年月日)
	 * 多个结果中间用"$"分开
	 * 如：京AA0006-20150625$京AA1234-20150625$京AA1235-20150625$
	 * */
	public boolean InportNameList(String szWhiteNameList,int iWhiteListLen,String szBlackNameList,int iBlackListLen) {
		if(_handle == null || _type != 0) {
			return false;
		}
		if(0 == HvDeviceSDK._sdk1.HVAPI_InportNameList(_handle, szWhiteNameList, iWhiteListLen, szBlackNameList, iBlackListLen)) {
			return true;
		}
		return false;
	}
	/**一体机功能(只针对一体机):开闸*/
	public boolean TriggerBarrierSignal() {
		if(_handle == null || _type != 0) {
			return false;
		}
		if(0 == HvDeviceSDK._sdk1.HVAPI_TriggerSignal(_handle, 0)) {
			return true;
		}
		return false;
	}
	/**一体机功能(只针对一体机):软触发一个结果*/
	public boolean SoftTriggerCapture() {
		if(_handle == null || _type != 0) {
			return false;
		}
		if(0 == HvDeviceSDK._sdk1.HVAPI_SoftTriggerCapture(_handle)) {
			return true;
		}
		return false;
	}
}
