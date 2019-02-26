package com.mydao.kkjk.vo;

/**
 * @program: kkjk
 * @description: 车道参数返回值
 * @author: Eyki
 * @create: 2019-02-19 14:00
 **/
public class DSVO {

    private String id;
    private String netSiteNo;
    private String createTime;
    private String dvrName;

    public String getDvrName() {
        return dvrName;
    }

    public void setDvrName(String dvrName) {
        this.dvrName = dvrName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetSiteNo() {
        return netSiteNo;
    }

    public void setNetSiteNo(String netSiteNo) {
        this.netSiteNo = netSiteNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
