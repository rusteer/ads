package com.ads.android.bean;
public class CommandResult {
    public boolean success;
    public int exitValue;
    public String output;
    public String error;
    public CommandResult() {}
    public CommandResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
