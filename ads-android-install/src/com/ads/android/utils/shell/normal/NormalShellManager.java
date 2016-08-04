package com.ads.android.utils.shell.normal;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.ads.android.bean.CommandResult;

/**
 * modify form <a href="http://www.trinea.cn" target="_blank">Trinea</a>
 *  @author trinea
 * @date 2014-12-10
 */
public class NormalShellManager {
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    private static final boolean needResponse = true;
    public static CommandResult execute(String command) {
        return execCommand(command, false);
    }
    private static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[] { command }, isRoot);
    }
    private static CommandResult execCommand(String[] commands, boolean isRoot) {
        int exitValue = -1;
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();
            exitValue = process.waitFor();
            if (needResponse) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errorResult != null) {
                    errorResult.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
        }
        CommandResult result = new CommandResult();
        result.exitValue = exitValue;
        result.output = successMsg == null ? null : successMsg.toString();
        result.error = errorMsg == null ? null : errorMsg.toString();
        result.success=exitValue==0;
        return result;
    }
}
