package com.rot.service.build;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmdUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CmdUtil.class);
    public static int exeCmd(String cmd) {
        LOGGER.info("start cmd:" + cmd);
        int result = 0;
        try {
            Runtime run = Runtime.getRuntime();
            Process p = run.exec(cmd);
            BufferedReader inBr = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                LOGGER.info(lineStr);
            }
            while ((lineStr = stdError.readLine()) != null) {
                System.err.println(lineStr);
            }
            if (p.waitFor() != 0) {
                result = p.exitValue();
                if (result != 0) {
                    System.err.println("Error occurs while executing cmd {" + cmd + "} and the return value is " + result);
                }
            }
            inBr.close();
            stdError.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("end cmd:" + cmd);
        return result;
    }
}
