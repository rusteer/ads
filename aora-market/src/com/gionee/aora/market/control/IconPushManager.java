// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.integral.util.MarketAsyncTask;
import com.gionee.aora.market.gui.special.SpecialInfomationActivity;
import com.gionee.aora.market.module.NotificationPushInfo;
import com.gionee.aora.market.net.NotificationPushNet;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

// Referenced classes of package com.gionee.aora.market.control:
//            ImageLoaderManager
public class IconPushManager {
    private DataCollectInfo action;
    private ImageView icon;
    public IconPushManager(ImageView imageview) {
        action = null;
        icon = imageview;
    }
    public void checkPushAndShow(final Context context) {
        new MarketAsyncTask<Void, NotificationPushInfo, NotificationPushInfo>() {
            @Override
            protected NotificationPushInfo doInBackground(Void avoid[]) {
                return NotificationPushNet.getPushIconInfo(context);
            }
            @Override
            protected void onPostExecute(NotificationPushInfo notificationpushinfo) {
                super.onPostExecute(notificationpushinfo);
                if (notificationpushinfo != null && icon != null) loadImageAndShow(context, notificationpushinfo);
            }
            @Override
            protected void onPreExecute() {
                if (icon != null) icon.setVisibility(8);
                super.onPreExecute();
            }
        }.doExecutor(new Void[0]);
    }
    private void jumpToActivity(Context context, NotificationPushInfo notificationpushinfo) {
        if (context != null && icon != null) {
            if (notificationpushinfo.getType() == 1) {
                Intent intent = new Intent(context, SpecialInfomationActivity.class);
                intent.setFlags(0x10000000);
                intent.putExtra("specialId", notificationpushinfo.getTopicId());
                intent.putExtra("DATACOLLECT_INFO", action);
                context.startActivity(intent);
                return;
            }
            if (notificationpushinfo.getType() == 2) {
                Intent intent1 = new Intent();
                intent1.setFlags(0x10000000);
                intent1.putExtra("EvaluateInfo", notificationpushinfo.getEvaluatInfo());
                intent1.putExtra("DATACOLLECT_INFO", action);
                intent1.putExtra("IS_FROM_OTHER_APP", false);
                intent1.setAction("com.gionee.aora.market.action.ExerciseDetailsActivity");
                context.startActivity(intent1);
                return;
            }
            if (notificationpushinfo.getUrl() != null && !notificationpushinfo.getUrl().equals("")) {
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.VIEW");
                intent2.setFlags(0x10000000);
                intent2.setData(Uri.parse(notificationpushinfo.getUrl()));
                context.startActivity(intent2);
                return;
            }
        }
    }
    private void loadImageAndShow(final Context context, final NotificationPushInfo info) {
        int i = biz.AR.drawable.cp_defaulf;
        icon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToActivity(context, info);
            }
        });
        com.nostra13.universalimageloader.core.DisplayImageOptions displayimageoptions = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().showStubImage(i)
                .showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
        ImageLoaderManager.getInstance().displayImage(info.getIcon(), icon, displayimageoptions, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap != null && icon != null) icon.setVisibility(0);
                super.onLoadingComplete(s, view, bitmap);
            }
        });
    }
}
