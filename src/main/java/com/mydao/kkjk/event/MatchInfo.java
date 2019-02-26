package com.mydao.kkjk.event;


import com.mydao.kkjk.sdk.HvDeviceDataType;

public class MatchInfo {
	public String cameraGuid;                      //一体机设备GUID
	public String vfrGuid;                         //小黄人设备GUID
	public int iReserveData;                       //预留数据,默认0

	public MatchInfo(HvDeviceDataType.HvMatchReult.ByReference match) {
		this.cameraGuid = match.cameraGuid;
		this.vfrGuid = match.vfrGuid;
		this.iReserveData = match.iReserveData;
	}
}
