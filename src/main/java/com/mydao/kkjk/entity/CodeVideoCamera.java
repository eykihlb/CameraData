package com.mydao.kkjk.entity;

public class CodeVideoCamera {
    private String id;

    private String dvrName;

    private String dvrDesc;

    private String dvrIp;

    private String dvrPort;

    private String dwSize;

    private String sPassWord;

    private String byRes;

    private String state;
    private Integer beginRow;
    private Integer endRow;
    private Integer pageIndex;
    private Integer pageSize;

    public Integer getBeginRow() {
        return beginRow;
    }

    public void setBeginRow(Integer beginRow) {
        this.beginRow = beginRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDvrName() {
        return dvrName;
    }

    public void setDvrName(String dvrName) {
        this.dvrName = dvrName == null ? null : dvrName.trim();
    }

    public String getDvrDesc() {
        return dvrDesc;
    }

    public void setDvrDesc(String dvrDesc) {
        this.dvrDesc = dvrDesc == null ? null : dvrDesc.trim();
    }

    public String getDvrIp() {
        return dvrIp;
    }

    public void setDvrIp(String dvrIp) {
        this.dvrIp = dvrIp == null ? null : dvrIp.trim();
    }

    public String getDvrPort() {
        return dvrPort;
    }

    public void setDvrPort(String dvrPort) {
        this.dvrPort = dvrPort == null ? null : dvrPort.trim();
    }

    public String getDwSize() {
        return dwSize;
    }

    public void setDwSize(String dwSize) {
        this.dwSize = dwSize == null ? null : dwSize.trim();
    }

    public String getsPassWord() {
        return sPassWord;
    }

    public void setsPassWord(String sPassWord) {
        this.sPassWord = sPassWord == null ? null : sPassWord.trim();
    }

    public String getByRes() {
        return byRes;
    }

    public void setByRes(String byRes) {
        this.byRes = byRes == null ? null : byRes.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}