package com.rot.utils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import com.rot.bean.CommandResult;
import com.rot.utils.shell.ShellManager;

public class PackageUtils {
    public static boolean isInstalled(Context context, String packageName) {
        boolean hasInstalled = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            if (packageName != null && packageName.equals(p.packageName)) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;
    }
    public static CommandResult stopApp(Context context, String packageName) {
        CommandResult result = ShellManager.execute("am force-stop " + packageName, true);
        if (isRunning(context, packageName)) {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            try {
                Class<?> cls = Class.forName("android.app.ActivityManager");
                try {
                    Method method = cls.getMethod("killBackgroundProcesses", String.class);
                    method.invoke(manager, packageName);
                } catch (Throwable e) {
                    MyLogger.error(e);
                    result.error = result.error + "\n" + CommonUtils.getExcption(e);
                }
                try {
                    Method method = cls.getMethod("forceStopPackage", String.class);
                    method.invoke(manager, packageName); //packageName是需要强制停止的应用程序包名
                } catch (Throwable e) {
                    MyLogger.error(e);
                    result.error = result.error + "\n" + CommonUtils.getExcption(e);
                }
            } catch (Exception e) {
                MyLogger.error(e);
                result.error = result.error + "\n" + CommonUtils.getExcption(e);
            }
        }
        result.success = !isRunning(context, packageName);
        return result;
    }
    private static String PM_UNINSTALL = "pm uninstall ";
    public static CommandResult unInstallApp(Context context, String packageName) {
        boolean needRoot = !isSystemApp(context);
        return ShellManager.execute(PM_UNINSTALL + packageName, needRoot);
    }
    public static CommandResult installApp(Context context, String path) {
        boolean needRoot = !isSystemApp(context);
        return ShellManager.execute("pm install -r " + path, needRoot);
    }
    /**
     * 是否是用户安装的程序
     *
     * @param appInfo
     *
     * @return false:系统程序，true :用户程序
     */
    public static boolean isSystemApp(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        boolean userApp = false;
        try {
            if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {// 表示是系统程序，但用户更新过，也算是用户安装的程序
                userApp = true;
            } else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {// 一定是用户安装的程序
                userApp = true;
            }
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return !userApp;
    }
    public static String ERR = "err";
    public static CommandResult startActivity(Context context, String command) {
        CommandResult result = new CommandResult();
        try {
            String[] fields = command.split(",");
            Intent intent = new Intent().setComponent(new ComponentName(fields[0], fields[1]));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            result.success = true;
        } catch (Throwable e) {
            MyLogger.error(e);
            result.error = CommonUtils.getExcption(e);
        }
        return result;
    }
    public static CommandResult startService(Context context, String command) {
        CommandResult result = new CommandResult();
        try {
            String[] fields = command.split(",");
            Intent intent = new Intent().setComponent(new ComponentName(fields[0], fields[1]));
            context.startService(intent);
            result.success = true;
        } catch (Throwable e) {
            MyLogger.error(e);
            result.error = CommonUtils.getExcption(e);
        }
        return result;
    }
    private static boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) { return true; }
        }
        return false;
    }
    public static String getInstalledPackages(Context context) {
        StringBuilder sb = new StringBuilder();
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo info : list) {
            if ((info.applicationInfo.flags & 1) == 0) {
                sb.append(info.packageName).append(",");
            }
        }
        return sb.toString();
    }
    public static PackageInfo getPackageByServiceName(Context context, String serviceName) {
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(PackageManager.GET_SERVICES)) {
            if (packageInfo != null) {
                ServiceInfo[] array = packageInfo.services;
                if (array != null) {
                    for (ServiceInfo serviceInfo : array) {
                        if (serviceInfo != null && serviceName.equals(serviceInfo.name)) return packageInfo;
                    }
                }
            }
        }
        return null;
    }
    public static Set<String> getFilteredInstalledPackages(Context context, String[] packageFilter) {
        Set<String> result = new HashSet<String>();
        Set<String> filter = new HashSet<String>();
        if (packageFilter != null) {
            for (String packageName : packageFilter) {
                if (StringUtils.isNotBlank(packageName)) filter.add(packageName.trim());
            }
        }
        if (filter.size() > 0) {
            final PackageManager manager = context.getPackageManager();
            List<PackageInfo> infoList = manager.getInstalledPackages(0);
            for (PackageInfo info : infoList) {
                String packageName = info.packageName;
                if (filter.contains(packageName)) {
                    result.add(packageName);
                }
            }
        }
        return result;
    }
}
