package com.ads.android.utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import android.content.Context;

public class CommonUtils {
    public static String getExcption(Throwable throwable) {
        String result = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            result = sw.toString();
        } catch (Exception e1) {
            MyLogger.error(e1);
        }
        return result;
    }
    public static String getPassword(Context context) {
        String packageName = context.getPackageName();
        if (packageName.length() >= 16) { return packageName.substring(0, 16); }
        while (packageName.length() < 16) {
            packageName += "w";
        }
        return packageName;
    }
}
