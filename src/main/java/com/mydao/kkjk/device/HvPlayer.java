package com.mydao.kkjk.device;

import com.mydao.kkjk.sdk.HvDeviceVideoSDK;
import com.mydao.kkjk.utils.Utils;
import com.sun.jna.platform.win32.WinDef.HWND;

public class HvPlayer {
	//播放句柄
	private HWND _handle = null;

	public HvPlayer() {
		HvDeviceVideoSDK._video.hvdevicevideo_init();
	}

	/**初始化播放器*/
	public boolean Init(HWND hwnd,String ip) {
		if(hwnd == null || !Utils.IsIP(ip)) {
			return false;
		}

		//rtsp地址
		String url = "rtsp://" + ip + ":554/h264ESVideoTest";

		_handle = HvDeviceVideoSDK._video.hvplayer_new(hwnd, url);
		if(_handle == null) {
			return false;
		}

		return true;
	}

	/**播放视频*/
	public boolean Play() {
		if(_handle == null) {
			return false;
		}

		return (0 == HvDeviceVideoSDK._video.hvplayer_play(_handle));
	}

	/**关闭视频*/
	public void Stop() {
		if(_handle != null) {
			HvDeviceVideoSDK._video.hvplayer_close(_handle);
			_handle = null;
		}
	}

}
