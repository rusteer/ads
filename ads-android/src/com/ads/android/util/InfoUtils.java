package com.ads.android.util;
import java.util.HashMap;
import java.util.Map;
import com.ads.android.bean.Device;
import com.ads.android.bean.Response;
import com.ads.android.bean.form.AbstractForm;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

@SuppressLint("NewApi")
public class InfoUtils {
    public static long appId;
    public static int channelId;
    protected static void clearData(Context context) {
        Store.delete(context, deviceIdPath);
        Store.delete(context, getInstantIdPath(context));
    }
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
    private static String getAndroidId(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), ANDROID_ID);
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return "";
    }
    private static Device getDevice(Context context) {
        Device device = new Device();
        device.imei = getImei(context);
        device.androidId = getAndroidId(context);
        device.macAddress = getMacAddress(context);
        try {
            device.serial = Build.SERIAL;
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        device.manufacturer = Build.MANUFACTURER;
        device.model = Build.MODEL;
        device.sdkVersion = Build.VERSION.SDK_INT;
        device.brand = Build.BRAND;
        return device;
    }
    public static boolean isInstalled(Context context, String packageName) {
        for (PackageInfo packageinfo : context.getPackageManager().getInstalledPackages(0)) {
            if (packageinfo.applicationInfo.packageName.equals(packageName)) return true;
        }
        return false;
    }
    private static long getDeviceId(Context context) {
        try {
            String data = Store.readAsString(context, deviceIdPath, deviceStorePassword);
            if (StringUtils.isNotBlank(data)) return Long.valueOf(data);
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return 0;
    }
    private static String getImei(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(PHONE)).getDeviceId();
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return "";
    }
    public static long getInstantId(Context context) {
        try {
            String data = Store.readAsString(context, getInstantIdPath(context), generatePassword(context.getPackageName()));
            if (StringUtils.isNotBlank(data)) return Long.valueOf(data);
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return 0;
    }
    private static String getInstantIdPath(Context context) {
        return ANDROID_DATA + context.getPackageName() + "/" //
                //+ String.valueOf(getVersionCode(context)).hashCode() + "/" //
                + INSTANT_FILE_NAME;
    }
    private static String getMacAddress(Context context) {
        try {
            return ((WifiManager) context.getSystemService(WIFI)).getConnectionInfo().getMacAddress();
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return "";
    }
    /**
     * Get online params
     * @param key
     * @return
     */
    public static String getOnlineParam(String key) {
        return onLineParams.get(key);
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
    public static void handleInfo(final Context context, Response response) {
        if (response != null) {
            if (response.clearData) {
                clearData(context);
            }
            if (response.deviceId > 0) {
                saveDeviceId(context, response.deviceId);
            }
            if (response.appInstanceId > 0) {
                saveInstantId(context, response.appInstanceId);
            }
        }
    }
    public static <T extends AbstractForm> T initForm(Context context, Class<T> c) throws Exception {
        T form = c.newInstance();
        form.appInstanceId = getInstantId(context);
        form.deviceId = getDeviceId(context);
        if (form.deviceId == 0) {
            form.device = getDevice(context);
        }
        form.appId = appId;
        form.packageName = context.getPackageName();
        form.versionCode = getVersionCode(context);
        form.channelId = channelId;
        return form;
    }
    private static void saveDeviceId(Context context, Long deviceId) {
        Store.save(context, deviceIdPath, String.valueOf(deviceId), deviceStorePassword);
    }
    private static void saveInstantId(Context context, Long instantId) {
        Store.save(context, getInstantIdPath(context), String.valueOf(instantId), generatePassword(context.getPackageName()));
    }
    private static final String PHONE = "phone";
    private static final String ANDROID_ID = "android_id";
    private static String WIFI = "wifi";
    private static final String INSTANT_FILE_NAME = "/datb.db";
    private static final String ANDROID_DATA = "/Android/data/";
    public static final String TIME = "time";
    private static String deviceIdPath = ANDROID_DATA + "data/data.db";
    private static String deviceStorePassword = "dehicef_dfd&fwfl";
    //
    private static Map<String, String> onLineParams = new HashMap<String, String>();
    public static final String FILTE_CONTENT_SPLIT = "#";
}
