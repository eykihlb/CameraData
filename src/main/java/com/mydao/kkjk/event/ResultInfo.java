package com.mydao.kkjk.event;


import com.mydao.kkjk.sdk.HvDeviceDataType;

public class ResultInfo {
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

    public ResultInfo(HvDeviceDataType.HvResult.ByReference result) {
        this.type = result.type;
        this.carID = result.carID;
        this.plateNo = result.plateNo;
        this.plateNoColor = result.plateNoColor;
        this.ip = result.ip;
        this.appendInfo = result.appendInfo;
        this.time = result.time;
        this.smallPicPath = result.smallPicPath;
        this.bigPicPath = result.bigPicPath;
        this.binPicPath = result.binPicPath;
        this.lastBigPicPath = result.lastBigPicPath;
        this.headPicPath = result.headPicPath;
        this.bodyPicPath = result.bodyPicPath;
        this.tailPicPath = result.tailPicPath;
    }
}
