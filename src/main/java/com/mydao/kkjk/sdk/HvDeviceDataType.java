package com.mydao.kkjk.sdk;

import java.util.List;
import java.util.Arrays;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class HvDeviceDataType {
    /**结果标识*/
    public static final int RESULT_FLAG_INVAIL      = (int)0xFFFF0600; //无效结果数据标识
    public static final int RESULT_FLAG_VAIL        = (int)0xFFFF0601; //有效结果数据标识
    public static final int RESULT_FLAG_HISTROY_END = (int)0xFFFF0602; //历史结果结束标识

    /**识别结果类型*/
    public static final int RECORD_TYPE_UNKNOWN     = (int)0xFFFF0300; //未知类型
    public static final int RECORD_TYPE_NORMAL      = (int)0xFFFF0301; //实时结果
    public static final int RECORD_TYPE_HISTORY     = (int)0xFFFF0302; //历史结果

    /**回调结果类型*/
    public static final int RESULT_RECV_FLAG_REALTIME              = (int)0xffff0500; //实时结果
    public static final int RESULT_RECV_FLAG_HISTORY               = (int)0xffff0501; //历史结果
    public static final int RESULT_RECV_FLAG_HISTROY_ONLY_PECCANCY = (int)0xffff0502; //历史违章结果
    public static final int H264_RECV_FLAG_REALTIME                = (int)0xFFFF0700; //实时H264视频
    public static final int H264_RECV_FLAG_HISTORY                 = (int)0xFFFF0701; //历史H264视频
    public static final int MJPEG_RECV_FLAG_DEBUG                  = (int)0xFFFF0900; //调试MJPEG码流
    public static final int MJPEG_RECV_FLAG_REALTIME               = (int)0xFFFF0901; //正常MJPEG码流

    /**设备连接状态*/
    public static final int CONN_STATUS_UNKNOWN      = (int)0xffff0400; //未知
    public static final int CONN_STATUS_NORMAL       = (int)0xffff0401; //正常
    public static final int CONN_STATUS_DISCONN      = (int)0xffff0402; //断开
    public static final int CONN_STATUS_RECONN       = (int)0xffff0403; //重连
    public static final int CONN_STATUS_RECVDONE     = (int)0xffff0404; //历史数据接收完成
    public static final int CONN_STATUS_CONNFIRST    = (int)0xffff0405; //连接初始化
    public static final int CONN_STATUS_CONNOVERTIME = (int)0xffff0406; //连接超时

    /**连接类型*/
    public static final int CONN_TYPE_UNKNOWN        = (int)0xffff0000; //未知
    public static final int CONN_TYPE_IMAGE          = (int)0xffff0001; //图像连接
    public static final int CONN_TYPE_VIDEO          = (int)0xffff0002; //视频连接
    public static final int CONN_TYPE_RECORD         = (int)0xffff0003; //识别结果连接

    /**图片结果信息*/
    public static class CImageInfo extends Structure{
        public int 			wSize = 0;			//结构体大小
        public int			wImgType = 0;		//图片类型
        public int			wWidth = 0;			//图片宽
        public int			wHeigth = 0;		//图片高度
        public Pointer		pbData;				//图片数据指针
        public int			dwDataLen = 0;		//图片数据长度
        public long			dw64TimeMS = 0;		//图片时标
        public int			fHasData = 0;       //图片数据是否有效标识

        public static class ByReference extends CImageInfo implements Structure.ByReference{};
        public static class ByValue extends CImageInfo implements Structure.ByValue{};

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[]{"wSize","wImgType","wWidth","wHeigth","pbData","dwDataLen","dw64TimeMS","fHasData"});
        }
    }

    /**结果推送数据*/
    public static class HvResult extends Structure{
        public int  type;                               //结果设备类型,0:一体机数据;1:车型数据
        public int  carID;                              //车辆ID
        public String plateNo;                          //车牌字符串
        public String plateNoColor;                     //车牌颜色
        public String ip;                               //IP地址
        public String appendInfo;                       //附加信息
        public String time;                             //获取结果时间
        public String smallPicPath;                     //小图路径
        public String binPicPath;                       //二值图路径
        public String bigPicPath;                       //大图路径
        public String lastBigPicPath;                   //最后大图路径
        public String headPicPath;                      //车头大图路径
        public String bodyPicPath;                      //车身大图路径
        public String tailPicPath;                      //车尾大图路径

        public static class ByReference extends HvResult implements Structure.ByReference{};
        public static class ByValue extends HvResult implements Structure.ByValue{};

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[]{"type","carID","plateNo","plateNoColor","ip","appendInfo","time","smallPicPath","binPicPath","bigPicPath",
                    "lastBigPicPath","headPicPath","bodyPicPath","tailPicPath"});
        }

    }

    /**匹配推送数据*/
    public static class HvMatchReult extends Structure{
        public String cameraGuid;                      //一体机设备GUID
        public String vfrGuid;                         //小黄人设备GUID
        public int iReserveData;                       //预留数据

        public static class ByReference extends HvMatchReult implements Structure.ByReference{};
        public static class ByValue extends HvMatchReult implements Structure.ByValue{};

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[]{"cameraGuid","vfrGuid","iReserveData"});
        }
    }
}
