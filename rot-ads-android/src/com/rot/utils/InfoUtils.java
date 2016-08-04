package com.rot.utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import com.rot.bean.Config;
import com.rot.bean.Device;
import com.rot.bean.Request;
import com.rot.main.ConfigManager;
import com.rot.utils.shell.ShellManager;

@SuppressLint("NewApi")
public class InfoUtils {
    public static String generatePassword(String seedBase) {
        if (seedBase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(seedBase.hashCode());
            while (sb.length() < 16) {
                sb.append(sb.toString().hashCode());
            }
            return sb.substring(0, 16);
        }
        return null;
    }
    private static Device getDevice(Context context) {
        Device device = new Device();
        device.imei = getImei(context);
        device.manufacturer = Build.MANUFACTURER;
        device.model = Build.MODEL;
        device.sdkVersion = Build.VERSION.SDK_INT;
        device.brand = Build.BRAND;
        device.isRooted = ShellManager.isRooted();
        device.isSystemApp = PackageUtils.isSystemApp(context);
        return device;
    }
    private static final String PHONE = "phone";
    private static String getImei(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(PHONE)).getDeviceId();
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return "";
    }
    private static int getVersionCode(Context context) {
        try {
            PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageinfo.versionCode;
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return 0;
    }
    public static <T extends Request> T initForm(Context context, Class<T> c) throws Exception {
        T form = c.newInstance();
        Config config = ConfigManager.getInstance(context).load();
        form.deviceId = config.deviceId;
        if (form.deviceId == 0) {
            form.device = getDevice(context);
        }
        form.packageName = context.getPackageName();
        form.versionCode = getVersionCode(context);
        return form;
    }
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
}
