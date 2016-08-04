package com.ads.entity.report.sp;
public abstract class AbstractStat {
    private int result;
    private int payCount;
    private int paySum;
    public int getPayCount() {
        return payCount;
    }
    public int getPaySum() {
        return paySum;
    }
    public int getResult() {
        return result;
    }
    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }
    public void setPaySum(int paySum) {
        this.paySum = paySum;
    }
    public void setResult(int result) {
        this.result = result;
    }
}
