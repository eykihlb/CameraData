package com.mydao.kkjk.entity;

public class DataSnap {
    private String id;

    private String cameraIp;

    private String picUrl;

    private String createTime;

    private String plateNo;

    private String plateColorCode;
    private String carId;
    private String state;
    private String netSiteNo;

    private String dvrName;
    private String fullNetSiteNo;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getNetSiteNo() {
        return netSiteNo;
    }

    public void setNetSiteNo(String netSiteNo) {
        this.netSiteNo = netSiteNo;
    }

    public String getDvrName() {
        return dvrName;
    }

    public void setDvrName(String dvrName) {
        this.dvrName = dvrName;
    }

    public String getFullNetSiteNo() {
        return fullNetSiteNo;
    }

    public void setFullNetSiteNo(String fullNetSiteNo) {
        this.fullNetSiteNo = fullNetSiteNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp == null ? null : cameraIp.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public String getPlateColorCode() {
        return plateColorCode;
    }

    public void setPlateColorCode(String plateColorCode) {
        this.plateColorCode = plateColorCode == null ? null : plateColorCode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}