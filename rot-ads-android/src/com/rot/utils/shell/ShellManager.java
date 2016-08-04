package com.rot.utils.shell;
import java.io.File;
import com.rot.bean.CommandResult;
import com.rot.utils.MyLogger;
import com.rot.utils.shell.normal.NormalShellManager;
import com.rot.utils.shell.root.RootShellManager;
import com.rot.utils.shell.root.Shell.Result;

public class ShellManager {
    private static Boolean isRooted = null;
    public static CommandResult execute(String command, boolean needRoot) {
        if (needRoot) {
            return executeAsRoot(command);
        } else {
            return NormalShellManager.execute(command);
        }
    }
    private static CommandResult executeAsRoot(String command) {
        RootShellManager.connect();
        Result rootResult = RootShellManager.execute(command);
        CommandResult r = new CommandResult();
        if (rootResult != null) {
            r.error = rootResult.getLine();
            r.exitValue = rootResult.getResultCode();
            r.success = r.exitValue == 0;
        } else {
            r.success = false;
        }
        return r;
    }
    /**
     * 获取当前手机是否有ROOT权限
     * @return
     */
    public static boolean isRooted() {
        RootShellManager.isRoot();
        if (isRooted == null) {
            synchronized (ShellManager.class) {
                isRooted = false;
                try {
                    for (String path : "/system/bin/,/system/xbin/,/system/sbin/,/sbin/,/vendor/bin/".split(",")) {
                        File file = new File(path + "su");
                        if (file.exists()) {
                            isRooted = true;
                            break;
                        }
                    }
                } catch (Throwable e) {
                    MyLogger.error(e);
                }
            }
        }
        return isRooted;
    }
}
