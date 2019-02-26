package com.mydao.kkjk.sdk;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

public interface HvDeviceVideoSDK extends StdCallLibrary{
	/**动态库实例对象*/
	HvDeviceVideoSDK _video = (HvDeviceVideoSDK)Native.loadLibrary("HvDeviceVideo.dll", HvDeviceVideoSDK.class);

	/**视频接口*/
	//初始化播放组件
	public int hvdevicevideo_init();

	//创建一个播放器，返回一个句柄
	public HWND hvplayer_new(HWND hwnd,String url);

	//播放rtsp视频,成功返回0,失败-1
	public int hvplayer_play(HWND handle);

	//停止播放rtsp视频
	public void hvplayer_close(HWND handle);
}
