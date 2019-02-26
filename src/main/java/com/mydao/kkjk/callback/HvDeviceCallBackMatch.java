package com.mydao.kkjk.callback;


import com.mydao.kkjk.event.DoEvent;
import com.mydao.kkjk.event.MatchDispatch;
import com.mydao.kkjk.event.MatchInfo;
import com.mydao.kkjk.sdk.HvApiCallBack;
import com.mydao.kkjk.sdk.HvDeviceDataType;

public class HvDeviceCallBackMatch {
	/**匹配回调函数*/
	public static class MatchFunction implements HvApiCallBack.HVAPI_MATCH_CALLBACK_RESULT {

		@Override
		public int result(HvDeviceDataType.HvMatchReult.ByReference pUserData,
						  String cameraGUID,
						  String vfrGuID,
						  int iReserveData) {

			HvDeviceDataType.HvMatchReult match = pUserData;
			match.cameraGuid = cameraGUID;
			match.vfrGuid = vfrGuID;
			match.iReserveData = iReserveData;

			//整理数据
			MatchInfo match_info = new MatchInfo(pUserData);
			DoEvent.MatchEvent event = new DoEvent.MatchEvent(this,match_info);
			MatchDispatch dispatch = new MatchDispatch();
			//分发数据
			dispatch.MatchEvent(event);
			return 0;
		}
	}
}
