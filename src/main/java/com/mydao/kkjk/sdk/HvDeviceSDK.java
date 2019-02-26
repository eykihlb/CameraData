package com.mydao.kkjk.sdk;

import com.sun.jna.Native;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Callback;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface HvDeviceSDK extends StdCallLibrary{

    /**动态库实例对象*/
    HvDeviceSDK _sdk1 = (HvDeviceSDK)Native.loadLibrary("HvDevice.dll", HvDeviceSDK.class);
    /**SDK接口*/
    //打开一体机设备
    public Pointer HVAPI_OpenEx(String szIp, String szApiVer);

    //打开车型设备
    public Pointer HVAPI_OpenChannel(String szIp, String szApiVer, int nAddedPortNum);

    //关闭设备
    public int HVAPI_CloseEx(Pointer hHandle);

    //设置接收结果回调
    public int HVAPI_StartRecvResult(Pointer hHandle, Callback pFunc, HvDeviceDataType.HvResult.ByReference pUserData, int iVideoID,
                                     long dw64BeginTimeMS, long dw64EndTimeMS, int dwStartIndex, int dwRecvFlag);

    //停止接收结果
    public int HVAPI_StopRecvResult(Pointer hHandle);

    //获取设备连接状态
    public int HVAPI_GetConnStatusEx(Pointer hHandle, int nStreamType, IntByReference status);

    //结果车牌小图数据转换成BMP格式
    public int HVAPIUTILS_SmallImageToBitmapEx(Pointer pbSmallImageData, int nSmallImageWidth,int nSmallImageHeight,
                                               Memory pbBitmapData,IntByReference pnBitmapDataLen);

    //从结果附加信息中提取全部信息,每项目信息使用回车符区分(xml格式转文本格式)
    public int HVAPIUTILS_ParsePlateXmlStringEx(String pszXmlPlateInfo, byte[] pszPlateInfoBuf, int iPlateInfoBufLen);

    //设置一体机与小黄人匹配回调
    public int HVAPI_StartMatchResult(Pointer hHandle, Pointer hVFRHandle,Callback pFunc,HvDeviceDataType.HvMatchReult.ByReference pUserData,
                                      int iFirstOverTime, int iFirstBindRange, int iSecondOverTime, int iSecondBindRange, int iReserveData);

    //停止一组一体机与小黄人匹配结果接收
    public int HVAPI_StopMatchResult(Pointer hHandle, Pointer hVFRHandle, int iReserveData);

    //停止所有一体机与小黄人匹配结果接收
    public int HVAPI_StopAllMatchResult(int iReserveData);

    //设置字符叠加开关
    public int HVAPI_SetOSDEnable(Pointer hHandle, int nStreamId, boolean fOSDEnable);

    //设置字符叠加字体
    public int HVAPI_SetOSDFont(Pointer hHandle, int nStreamId, int nFontSize, int nColorR, int nColorG, int nColorB);

    //设置字符叠加位置
    public int HVAPI_SetOSDPos(Pointer hHandle, int nStreamId, int nPosX, int nPosY);

    //设置字符叠加字符串
    public int HVAPI_SetOSDText(Pointer hHandle, int nStreamId, String szText);

    //导入黑白名单
    public int HVAPI_InportNameList(Pointer hHandle, String szWhiteNameList, int iWhiteListLen, String szBlackNameList, int iBlackListLen);

    //触发开闸
    public int HVAPI_TriggerSignal(Pointer hHandle, int iVideoID);

    //触发抓拍
    public int HVAPI_SoftTriggerCapture(Pointer hHandle);
}
