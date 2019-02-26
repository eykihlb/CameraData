package com.mydao.kkjk.sdk;

import com.sun.jna.Callback;

public interface HvApiCallBack{
	/**结果回调函数*/
	public static interface HVAPI_CALLBACK_RESULT extends Callback{
		int result(HvDeviceDataType.HvResult.ByReference pUserData,   //用户数据
				   int dwResultFlag,                 //结果标志
				   int dwResultType,                 //结果类型
				   int dwCarID,                      //结果ID
				   String pcPlateNo,                 //车牌字符串
				   String pcAppendInfo,              //附加信息
				   long dw64TimeMS,                  //结果时标
				   HvDeviceDataType.CImageInfo.ByValue pPlate,        //车牌小图
				   HvDeviceDataType.CImageInfo.ByValue pPlateBin,     //车牌二值图
				   HvDeviceDataType.CImageInfo.ByValue pBestSnapshot, //最清晰大图
				   HvDeviceDataType.CImageInfo.ByValue pLastSnapshot, //最后大图
				   HvDeviceDataType.CImageInfo.ByValue pBeginCapture, //第一张抓拍图
				   HvDeviceDataType.CImageInfo.ByValue pBestCapture,  //第二张抓拍图
				   HvDeviceDataType.CImageInfo.ByValue pLastCapture); //第三张抓拍图
	}

	/**匹配结果回调*/
	public static interface HVAPI_MATCH_CALLBACK_RESULT extends Callback{
		int result(HvDeviceDataType.HvMatchReult.ByReference pUserData,//用户数据
				   String cameraGUID,                 //一体机设备GUID
				   String vfrGuID,                    //车型设备GUID
				   int iReserveData);                 //预留参数,暂时不用,默认填0
	}
}
