package com.ads.entity.report.cp;
public abstract class AbstractCpStat {
    private String group;
    private int payCount;
    private int payMoney;
    private int successMoney;
    private int paySum;
    public String getGroup() {
        return group;
    }
    public int getPayCount() {
        return payCount;
    }
    public int getPayMoney() {
        return payMoney;
    }
    public int getPaySum() {
        return paySum;
    }
    public int getSuccessMoney() {
        return successMoney;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }
    public void setPayMoney(int payMoney) {
        this.payMoney = payMoney;
    }
    public void setPaySum(int paySum) {
        this.paySum = paySum;
    }
    public void setSuccessMoney(int successMoney) {
        this.successMoney = successMoney;
    }
}
