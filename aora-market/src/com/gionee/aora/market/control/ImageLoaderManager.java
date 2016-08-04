// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import com.aora.base.util.DLog;
import com.gionee.aora.market.util.Util;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

// Referenced classes of package com.gionee.aora.market.control:
//            MarketPreferences
public class ImageLoaderManager {
    public class CheckIsWifeModeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) initNetWorkState(context);
        }
    }
    private enum NetWorkState {
        WIFI, FAST_MOBILE, SLOW_MOBILE, NONE
    }
    public static ImageLoaderManager getInstance() {
        if (imageLoaderManager == null) imageLoaderManager = new ImageLoaderManager();
        return imageLoaderManager;
    }
    private static boolean isFastMobileNetwork(Context context) {
        return true;
    }
    private static ImageLoaderManager imageLoaderManager = null;
    private CheckIsWifeModeReceiver checkIsWifeModeReceiver;
    private ImageLoader imageLoader;
    private boolean isShowImage;
    private NetWorkState nowWorkState;
    private MarketPreferences preferences;
    public ImageLoaderManager() {
        isShowImage = true;
        preferences = null;
        imageLoader = null;
        nowWorkState = NetWorkState.NONE;
    }
    public void displayImage(String s, ImageView imageview) {
        displayImage(s, imageview, null, null);
    }
    public void displayImage(String s, ImageView imageview, DisplayImageOptions displayimageoptions) {
        displayImage(s, imageview, displayimageoptions, null);
    }
    public void displayImage(String s, ImageView imageview, DisplayImageOptions displayimageoptions, ImageLoadingListener imageloadinglistener) {
        if (nowWorkState == NetWorkState.WIFI) {
            imageLoader.displayImage(s, imageview, displayimageoptions, imageloadinglistener);
            return;
        }
        if (isShowImage && nowWorkState == NetWorkState.FAST_MOBILE) {
            imageLoader.displayImage(s, imageview, displayimageoptions, imageloadinglistener);
            return;
        } else {
            imageLoader.displayImage(null, imageview, displayimageoptions, imageloadinglistener);
            return;
        }
    }
    public void displayImage(String s, ImageView imageview, ImageLoadingListener imageloadinglistener) {
        displayImage(s, imageview, null, imageloadinglistener);
    }
    public DisplayImageOptions getImageLoaderOptions() {
        int i = biz.AR.drawable.cp_defaulf;
        return new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc()
                .build();
    }
    public boolean getShowImage() {
        if (preferences != null) {
            if (nowWorkState == NetWorkState.WIFI) return true;
            if (nowWorkState == NetWorkState.FAST_MOBILE) return preferences.isShowIcon();
        }
        return false;
    }
    public void init(Context context) {
        initImageLoader(context);
        preferences = MarketPreferences.getInstance(context);
        imageLoader = ImageLoader.getInstance();
        isShowImage = preferences.isShowIcon();
        initNetWorkState(context);
        checkIsWifeModeReceiver = new CheckIsWifeModeReceiver();
        context.registerReceiver(checkIsWifeModeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
    private void initImageLoader(Context context) {
        int i = (int) (Runtime.getRuntime().maxMemory() / 8L);
        Object obj;
        java.io.File file;
        com.nostra13.universalimageloader.core.ImageLoaderConfiguration imageloaderconfiguration;
        if (android.os.Build.VERSION.SDK_INT >= 9) obj = new LruMemoryCache(i);
        else obj = new LRULimitedMemoryCache(i);
        file = Util.getCacheDirectory(context);
        DLog.v("lung", new StringBuilder().append(file).append("").toString());
        imageloaderconfiguration = new com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder(context).threadPriority(3)
                .memoryCache((com.nostra13.universalimageloader.cache.memory.MemoryCacheAware) obj).denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(file))
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(imageloaderconfiguration);
    }
    private void initNetWorkState(Context context) {
        NetworkInfo networkinfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkinfo == null) {
            nowWorkState = NetWorkState.NONE;
            return;
        }
        if (networkinfo.isConnected()) {
            if (networkinfo != null && networkinfo.getType() == 1) {
                nowWorkState = NetWorkState.WIFI;
                return;
            }
            if (isFastMobileNetwork(context)) {
                nowWorkState = NetWorkState.FAST_MOBILE;
                return;
            } else {
                nowWorkState = NetWorkState.SLOW_MOBILE;
                return;
            }
        } else {
            nowWorkState = NetWorkState.NONE;
            return;
        }
    }
    public void loadImage(String s, DisplayImageOptions displayimageoptions, ImageLoadingListener imageloadinglistener) {
        if (nowWorkState == NetWorkState.WIFI) {
            imageLoader.loadImage(s, displayimageoptions, imageloadinglistener);
            return;
        }
        if (isShowImage && nowWorkState == NetWorkState.FAST_MOBILE) {
            imageLoader.loadImage(s, displayimageoptions, imageloadinglistener);
            return;
        } else {
            imageLoader.loadImage(null, displayimageoptions, imageloadinglistener);
            return;
        }
    }
    public void setShowImage(boolean flag) {
        if (preferences != null) {
            isShowImage = flag;
            preferences.setShowIcon(flag);
        }
    }
}
