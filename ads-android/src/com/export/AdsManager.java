package com.export;
import com.ads.android.ExportHelper;
import android.content.Context;

public class AdsManager {
    public static void init(Context context, long appId, int channelId) {
        ExportHelper.handleMainInit(context, appId, channelId);
    }
}
