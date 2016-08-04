// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import android.app.ActivityManager;
import android.content.Context;

public class KillProcessManager {
    public static String byteToString(long l) {
        int i = 0;
        if (l == 0L) return "0M";
        String as[] = { "M", "G", "T", "P" };
        double d = Double.valueOf(l).doubleValue();
        for (; i < as.length; i++) {
            double d1 = d / Math.pow(1024D, i + 1);
            if (d1 < 10D) return new StringBuilder().append(new DecimalFormat("0.00").format(d1)).append(as[i]).toString();
            if (d1 < 100D) return new StringBuilder().append(new DecimalFormat("00.0").format(d1)).append(as[i]).toString();
            if (d1 < 1000D) return new StringBuilder().append((int) d1).append(as[i]).toString();
        }
        return "";
    }
    public static long getAvailMemory(Context context) {
        ActivityManager activitymanager = (ActivityManager) context.getSystemService("activity");
        android.app.ActivityManager.MemoryInfo memoryinfo = new android.app.ActivityManager.MemoryInfo();
        activitymanager.getMemoryInfo(memoryinfo);
        return memoryinfo.availMem / 1024L;
    }
    public static List getRunningProcess(Context context) {
        return ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
    }
    public static long getTotalMemory(Context context) {
        long result = 0L;
        BufferedReader reader = null;
        String as[];
        String s1;
        try {
            reader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String line = reader.readLine();
            as = line.split("\\s+");
            result = Long.valueOf(as[1]).longValue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static void killProcess(Context context, List list) {
        ActivityManager activitymanager = (ActivityManager) context.getSystemService("activity");
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            String as[] = ((android.app.ActivityManager.RunningAppProcessInfo) iterator.next()).pkgList;
            int i = as.length;
            int j = 0;
            while (j < i) {
                String s = as[j];
                activitymanager.restartPackage(s);
                activitymanager.killBackgroundProcesses(s);
                j++;
            }
        }
    }
    public KillProcessManager() {}
}
