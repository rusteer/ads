package com.rot.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.rot.bean.CommandResult;

public class ConsoleBak {
    public static CommandResult exeCmd(String command, boolean enableRoot) {
        CommandResult result = new CommandResult();
        StringBuilder out = new StringBuilder();
        StringBuilder error = new StringBuilder();
        BufferedReader outputReader = null;
        BufferedReader errorReader = null;
        try {
            if (enableRoot) command = "su & " + command;
            Process process = Runtime.getRuntime().exec(command);
            outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = outputReader.readLine()) != null) {
                out.append(line).append("\n");
                MyLogger.info(line);
            }
            while ((line = errorReader.readLine()) != null) {
                error.append(line).append("\n");
                MyLogger.info(line);
            }
            if (process.waitFor() != 0) {
                result.exitValue = process.exitValue();
            }
        } catch (Throwable e) {
            String ex = CommonUtils.getExcption(e);
            error.append(ex);
            MyLogger.error(ex);
        } finally {
            if (outputReader != null) try {
                outputReader.close();
            } catch (IOException e) {
                MyLogger.error(e);
            }
            if (errorReader != null) try {
                errorReader.close();
            } catch (IOException e) {
                MyLogger.error(e);
            }
        }
        result.output = out.toString();
        result.error = error.toString();
        result.success = result.exitValue == 0;
        return result;
    }
}
