package com.ads.entity.report.sp;
public class SpReport {
    private String group;
    private int successCount;
    private int successSum;
    private int failureCount;
    private int failureSum;
    private int cancelCount;
    private int cancelSum;
    private int unknownCount;
    private int unknownSum;
    public int getCancelCount() {
        return cancelCount;
    }
    public int getCancelSum() {
        return cancelSum;
    }
    public int getFailureCount() {
        return failureCount;
    }
    public int getFailureSum() {
        return failureSum;
    }
    public String getGroup() {
        return group;
    }
    public int getSuccessCount() {
        return successCount;
    }
    public int getSuccessSum() {
        return successSum;
    }
    public int getUnknownCount() {
        return unknownCount;
    }
    public int getUnknownSum() {
        return unknownSum;
    }
    public void setCancelCount(int cancelCount) {
        this.cancelCount = cancelCount;
    }
    public void setCancelSum(int cancelSum) {
        this.cancelSum = cancelSum;
    }
    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
    public void setFailureSum(int failureSum) {
        this.failureSum = failureSum;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
    public void setSuccessSum(int successSum) {
        this.successSum = successSum;
    }
    public void setUnknownCount(int unknownCount) {
        this.unknownCount = unknownCount;
    }
    public void setUnknownSum(int unknownSum) {
        this.unknownSum = unknownSum;
    }
}
