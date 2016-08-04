// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.integral.util.MarketAsyncTask;
import com.gionee.aora.market.gui.integral.DisCountCenter;
import com.gionee.aora.market.gui.integral.ManagerActivity;
import com.gionee.aora.market.gui.main.MainNewActivity;
import com.gionee.aora.market.gui.special.ExerciseInfomationActivity;
import com.gionee.aora.market.gui.special.SpecialInfomationActivity;
import com.gionee.aora.market.gui.special.SuperSpecialActivity;
import com.gionee.aora.market.module.EvaluatInfo;
import com.gionee.aora.market.module.NotificationPushInfo;
import com.gionee.aora.market.net.NotificationPushNet;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

// Referenced classes of package com.gionee.aora.market.control:
//            ImageLoaderManager, SoftwareManager, MarketPreferences
@SuppressLint("NewApi")
public class NotificationPushManager {
    public static NotificationPushManager getInstance(Context context) {
        if (instance == null) instance = new NotificationPushManager(context);
        return instance;
    }
    private static NotificationPushManager instance;
    private final int ID_APP_FAST_UPDATE = 422;
    private final int ID_APP_UPDATE = 423;
    private final int ID_MARKET_UPDATE = 421;
    private final int ID_TOPIC = 424;
    NotificationManager nm;
    private NotificationPushManager(Context context) {
        nm = (NotificationManager) context.getSystemService("notification");
    }
    private boolean addShowedId(Context context, String s) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("notify_push_id_save", 4);
        String s1 = sharedpreferences.getString("ids2", "");
        if (!s1.contains(s)) {
            String s2 = new StringBuilder().append(s1).append(",").append(s).toString();
            android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("ids2", s2);
            return editor.commit();
        } else {
            return false;
        }
    }
    public void checkForPushNotification(Context context) {
        final Context appContext = context.getApplicationContext();
        if (MarketPreferences.getInstance(context).getReceiveReommendRemind().booleanValue() && System.currentTimeMillis() - getLastShowTime(context) >= 0x5265c00L) new MarketAsyncTask<Void, NotificationPushInfo, NotificationPushInfo>() {
            @Override
            protected NotificationPushInfo doInBackground(Void avoid[]) {
                return NotificationPushNet.getPushNotifyInfo(appContext, getShowedIds(appContext));
            }
            @Override
            protected void onPostExecute(NotificationPushInfo notificationpushinfo) {
                super.onPostExecute(notificationpushinfo);
                if (notificationpushinfo != null) {
                    setLastShowTime(appContext, System.currentTimeMillis());
                    addShowedId(appContext, notificationpushinfo.getId());
                    loadImageAndShowNotify(appContext, notificationpushinfo);
                }
            }
        }.doExecutor(new Void[0]);
    }
    private long getLastShowTime(Context context) {
        return context.getSharedPreferences("notify_push_id_save", 4).getLong("time", 0L);
    }
    private String getShowedIds(Context context) {
        return context.getSharedPreferences("notify_push_id_save", 4).getString("ids2", "");
    }
    private void loadImageAndShowNotify(final Context context, final NotificationPushInfo info) {
        int i = biz.AR.drawable.cp_defaulf;
        com.nostra13.universalimageloader.core.DisplayImageOptions displayimageoptions = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().showStubImage(i)
                .showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
        if (info.getIcon().length() > 0) ImageLoaderManager.getInstance().loadImage(info.getIcon(), displayimageoptions, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                switch (info.getType()) {
                        case 3: // '\003'
                        case 4: // '\004'
                        case 5: // '\005'
                        case 6: // '\006'
                        default:
                            return;
                    case 1: // '\001'
                            showTopicNotify(context, info.getTitle(), info.getText(), bitmap, 1, info.getTopicId());
                            return;
                    case 2: // '\002'
                            showTopicNotify(context, info.getTitle(), info.getText(), bitmap, 2, info.getEvaluatInfo());
                            return;
                    case 7: // '\007'
                            showTopicNotify(context, info.getTitle(), info.getText(), bitmap, 7, info.getUrl());
                            return;
                    case 8: // '\b'
                            showTopicNotify(context, info.getTitle(), info.getText(), bitmap, 8, info);
                            return;
                    case 9: // '\t'
                            showTopicNotify(context, info.getTitle(), info.getText(), bitmap, 9, info);
                            break;
                }
            }
        });
        switch (info.getType()) {
            default:
                return;
            case 3: // '\003'
                showMarketUpdateNotify(context, info.getTitle(), info.getText(), 1, "", "");
                return;
            case 4: // '\004'
                showMarketUpdateNotify(context, info.getTitle(), info.getText(), 2, "", "");
                return;
            case 5: // '\005'
                showMarketUpdateNotify(context, info.getTitle(), info.getText(), 3, info.getSoftId(), info.getUrl());
                return;
            case 6: // '\006'
                showMarketUpdateNotify(context, info.getTitle(), info.getText(), 4, info.getSoftId(), info.getUrl());
                break;
        }
    }
    private boolean setLastShowTime(Context context, long l) {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences("notify_push_id_save", 4).edit();
        editor.putLong("time", l);
        return editor.commit();
    }
    private void showMarketUpdateNotify(Context context, String s, String s1, int i, String s2, String s3) {
        int j;
        DataCollectInfo datacollectinfo;
        Intent aintent1[];
        PendingIntent pendingintent;
        DataCollectInfo datacollectinfo1;
        Intent aintent2[];
        DataCollectInfo datacollectinfo2;
        Intent aintent3[];
        Intent aintent[] = null;
        switch (i) {
            case 1://L2_L2:
                datacollectinfo2 = new DataCollectInfo("", "9", "5", "5", "");
                aintent3 = new Intent[2];
                aintent3[0] = new Intent(context, MainNewActivity.class);
                aintent3[0].setFlags(0x10000000);
                aintent3[0].putExtra("MAIN_FALG", 0);
                aintent3[0].putExtra("isShowPersonalityRecommend", false);
                aintent3[1] = new Intent(context, DisCountCenter.class);
                aintent3[1].putExtra("DATACOLLECT_INFO", datacollectinfo2);
                aintent = aintent3;
                break;
            case 2://L3_L3:
                datacollectinfo1 = new DataCollectInfo("", "9", "0", "", "");
                aintent2 = new Intent[2];
                aintent2[0] = new Intent(context, MainNewActivity.class);
                aintent2[0].setFlags(0x10000000);
                aintent2[0].putExtra("MAIN_FALG", 0);
                aintent2[0].putExtra("isShowPersonalityRecommend", false);
                aintent2[0].putExtra("DATACOLLECT_INFO", datacollectinfo1);
                aintent2[1] = new Intent(context, ManagerActivity.class);
                aintent2[1].putExtra("DATACOLLECT_INFO", datacollectinfo1);
                aintent = aintent2;
                break;
            case 3://L4
            case 4://L4
                if (s3.length() > 0) {
                    aintent = new Intent[1];
                    aintent[0] = new Intent("android.intent.action.VIEW");
                    aintent[0].setData(Uri.parse(s3));
                } else {
                    j = s2.length();
                    aintent = null;
                    if (j > 0) {
                        datacollectinfo = new DataCollectInfo("", "9", "0", "", "1");
                        aintent1 = new Intent[2];
                        aintent1[0] = new Intent(context, MainNewActivity.class);
                        aintent1[0].setFlags(0x10000000);
                        aintent1[0].putExtra("MAIN_FALG", 2);
                        aintent1[0].putExtra("isShowPersonalityRecommend", false);
                        aintent1[1] = new Intent();
                        aintent1[1].setAction("com.gionee.aora.market.action.GoSoftIntroductionActivity");
                        aintent1[1].putExtra("SOFTID", s2);
                        aintent1[1].putExtra("DATACOLLECT_INFO", datacollectinfo);
                        aintent = aintent1;
                    }
                }
                break;
        }
        if (android.os.Build.VERSION.SDK_INT >= 11) pendingintent = PendingIntent.getActivities(context, 0, aintent, 0x8000000);
        else pendingintent = PendingIntent.getActivity(context, 0, aintent[-1 + aintent.length], 0x8000000);
        SoftwareManager.showSystemStyleNotifiy(context, s, s1, s1, pendingintent, 421);
        return;
    }
    private void showTopicNotify(Context context, String s, String s1, Bitmap bitmap, int i, Object obj) {
        DataCollectInfo datacollectinfo;
        Intent aintent1[];
        NotificationPushInfo notificationpushinfo;
        PendingIntent pendingintent;
        DataCollectInfo datacollectinfo1;
        Intent aintent2[];
        NotificationPushInfo notificationpushinfo1;
        Uri uri;
        DataCollectInfo datacollectinfo2;
        Intent aintent3[];
        Intent aintent4[];
        DataCollectInfo datacollectinfo3;
        Notification notification;
        Intent aintent[];
        Date date = new Date(System.currentTimeMillis());
        String s2 = new SimpleDateFormat("HH:mm").format(date);
        notification = new Notification();
        notification.icon = biz.AR.drawable.icon_notification;
        notification.contentView = new RemoteViews(context.getPackageName(), biz.AR.layout.notification_content_topics);
        notification.contentView.setTextViewText(biz.AR.id.notification_title, s);
        notification.contentView.setTextViewText(biz.AR.id.notification_text, s1);
        notification.contentView.setTextViewText(biz.AR.id.notification_date, s2);
        notification.contentView.setImageViewBitmap(biz.AR.id.notification_icon, bitmap);
        notification.tickerText = s;
        notification.defaults = 1;
        notification.flags = 0x10 | notification.flags;
        aintent = null;
        switch (i) {
            case 3://L1
            case 4://L1
            case 5://L1
            case 6://L1
                break;
            case 1://L2_L2:
                aintent4 = new Intent[2];
                aintent4[0] = new Intent(context, MainNewActivity.class);
                aintent4[0].setFlags(0x10000000);
                aintent4[0].putExtra("MAIN_FALG", 3);
                aintent4[0].putExtra("isShowPersonalityRecommend", false);
                datacollectinfo3 = new DataCollectInfo("", "9", "", "", "2");
                aintent4[0].putExtra("DATACOLLECT_INFO", datacollectinfo3);
                aintent4[1] = new Intent(context, SpecialInfomationActivity.class);
                aintent4[1].putExtra("specialId", (String) obj);
                aintent4[1].putExtra("DATACOLLECT_INFO", datacollectinfo3);
                aintent = aintent4;
                break;
            case 2://L3_L3:
                datacollectinfo2 = new DataCollectInfo("9", "9", "", "", "5");
                aintent3 = new Intent[2];
                aintent3[0] = new Intent(context, MainNewActivity.class);
                aintent3[0].setFlags(0x10000000);
                aintent3[0].putExtra("MAIN_FALG", 3);
                aintent3[0].putExtra("isShowPersonalityRecommend", false);
                aintent3[0].putExtra("DATACOLLECT_INFO", datacollectinfo2);
                aintent3[1] = new Intent();
                aintent3[1].putExtra("EvaluateInfo", (EvaluatInfo) obj);
                aintent3[1].putExtra("DATACOLLECT_INFO", datacollectinfo2);
                aintent3[1].putExtra("IS_FROM_OTHER_APP", false);
                aintent3[1].setAction("com.gionee.aora.market.action.ExerciseDetailsActivity");
                aintent = aintent3;
                break;
            case 7://L4_L4:
                aintent = new Intent[1];
                aintent[0] = new Intent();
                aintent[0].setAction("android.intent.action.VIEW");
                uri = Uri.parse((String) obj);
                aintent[0].setData(uri);
                break;
            case 8://L5_L5:
                datacollectinfo1 = new DataCollectInfo("9", "9", "", "", "7");
                aintent2 = new Intent[1];
                notificationpushinfo1 = (NotificationPushInfo) obj;
                aintent2[0] = new Intent(context, ExerciseInfomationActivity.class);
                aintent2[0].putExtra("skipUrl", notificationpushinfo1.getUrl());
                aintent2[0].putExtra("vid", notificationpushinfo1.getId());
                aintent2[0].putExtra("NAME", notificationpushinfo1.getTitle());
                aintent2[0].putExtra("DATACOLLECT_INFO", datacollectinfo1);
                aintent = aintent2;
                break;
            case 9://L6
                datacollectinfo = new DataCollectInfo("9", "9", "", "", "9");
                aintent1 = new Intent[2];
                aintent1[0] = new Intent(context, MainNewActivity.class);
                aintent1[0].setFlags(0x10000000);
                aintent1[0].putExtra("MAIN_FALG", 0);
                aintent1[0].putExtra("isShowPersonalityRecommend", false);
                aintent1[0].putExtra("DATACOLLECT_INFO", datacollectinfo);
                notificationpushinfo = (NotificationPushInfo) obj;
                aintent1[1] = new Intent(context, SuperSpecialActivity.class);
                aintent1[1].putExtra("skipUrl", notificationpushinfo.getUrl());
                aintent1[1].putExtra("vid", notificationpushinfo.getId());
                aintent1[1].putExtra("DATACOLLECT_INFO", datacollectinfo);
                aintent = aintent1;
                break;
        }
        if (android.os.Build.VERSION.SDK_INT >= 11) pendingintent = PendingIntent.getActivities(context, 0, aintent, 0x8000000);
        else pendingintent = PendingIntent.getActivity(context, 0, aintent[-1 + aintent.length], 0x8000000);
        notification.contentIntent = pendingintent;
        nm.notify(424, notification);
        return;
    }
}
