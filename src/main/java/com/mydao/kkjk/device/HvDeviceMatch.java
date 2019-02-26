package com.mydao.kkjk.device;

import com.mydao.kkjk.callback.HvDeviceCallBackMatch;
import com.mydao.kkjk.sdk.HvDeviceDataType;
import com.mydao.kkjk.sdk.HvDeviceSDK;
import com.sun.jna.Pointer;

public class HvDeviceMatch {
	/**一体机设备句柄*/
	private Pointer _handle = null;
	/**车型设备句柄*/
	private Pointer _vfr_handle = null;
	/**匹配对象(回调用户参数)*/
	private HvDeviceDataType.HvMatchReult.ByReference _match = new HvDeviceDataType.HvMatchReult.ByReference();
	/**匹配回调函数*/
	private HvDeviceCallBackMatch.MatchFunction _match_callback = new HvDeviceCallBackMatch.MatchFunction();

	public HvDeviceMatch() {
		System.setProperty("jna.encoding", "GBK");
	}

	/**开始匹配结果
	 *camera_handle对应一体机设备句柄
	 *vfr_handle对应车型设备句柄
	 *iFirstOverTime第一级绑定超时时间,默认值120s
	 *iFirstBindRange第一级绑定前后时间范围,默认值120s
	 *iSecondOverTime第二级绑定超时时间,默认值120s
	 *iSecondBindRange第二级绑定前后时间范围,默认值120s
	 **/
	public boolean StartMatchResult(Pointer camera_handle,
									Pointer vfr_handle,
									int iFirstOverTime,
									int iFirstBindRange,
									int iSecondOverTime,
									int iSecondBindRange) {
		if(camera_handle == null || vfr_handle == null) {
			return false;
		}

		if(0 == HvDeviceSDK._sdk1.HVAPI_StartMatchResult(camera_handle, vfr_handle, _match_callback, _match, iFirstOverTime, iFirstBindRange, iSecondOverTime, iSecondBindRange, 0)) {
			_handle = camera_handle;
			_vfr_handle = vfr_handle;
			return true;
		}

		return false;
	}

	/**停止匹配结果*/
	public boolean StopMatchResult() {
		if(_handle == null || _vfr_handle == null) {
			return false;
		}

		return (0 == HvDeviceSDK._sdk1.HVAPI_StopMatchResult(_handle, _vfr_handle, 0));
	}

	/**停止接收所有匹配结果*/
	public static boolean StopAllMatchResult() {
		return (0 == HvDeviceSDK._sdk1.HVAPI_StopAllMatchResult(0));
	}
}
