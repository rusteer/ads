// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.app.Activity;
import android.content.*;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.aora.base.util.DLog;
import com.gionee.aora.download.DownloadListener;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.control.UserManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.login.LoginActivity;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.control.*;
import com.gionee.aora.market.gui.download.DownloadManagerActivity;
import com.gionee.aora.market.gui.integral.ManagerActivity;
import com.gionee.aora.market.gui.integral.PaintEggTipDialog;
import com.gionee.aora.market.gui.search.SearchActivity;
import com.gionee.aora.market.module.SignInResult;
import com.gionee.aora.market.net.IntegralNet;
import com.gionee.aora.market.util.MarketAsyncTask;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            CircleCornerImageView

public class MainTitleView extends RelativeLayout
{
    class DownloadChanReceiver extends BroadcastReceiver
    {

        public void onReceive(Context context1, Intent intent)
        {
            DLog.d("denglh", "MainTitleView.DownloadChanReceiver");
            setDownloadCount();
            setUpdateCount();
        }

        
    }

    private class SignTask extends MarketAsyncTask<Integer,Integer,SignInResult>
    {

        protected   SignInResult doInBackground(Integer ainteger[])
        {
            return IntegralNet.integralSignIn(context, signInfo);
        }

        

        protected void onPostExecute(SignInResult signinresult)
        {
            super.onPostExecute(signinresult);
            if(signinresult != null)
            {
                if(signinresult.getResultcode() == 0)
                {
                    UserManager.getInstance(context).reFreshUserInfo(signinresult.getInfo());
                    UserStorage.saveUserInfo(context, signinresult.getInfo());
                    Toast.makeText(context, signinresult.getMsg(), 1).show();
                    if(!signinresult.getCaidanMsg().equals(""))
                    {
                        Intent intent = new Intent(context, PaintEggTipDialog.class);
                        intent.putExtra("message", signinresult.getCaidanMsg());
                        intent.putExtra("coin", signinresult.getCaidanCoin());
                        ((Activity)context).startActivityForResult(intent, 6758);
                    }
                    return;
                } else
                {
                    Toast.makeText(context, signinresult.getMsg(), 1).show();
                    return;
                }
            } else
            {
                Toast.makeText(context, "\u7B7E\u5230\u5931\u8D25\uFF0C\u7F51\u7EDC\u9519\u8BEF", 1).show();
                return;
            }
        }

       

        protected void onPreExecute()
        {
            signInfo = UserStorage.getDefaultUserInfo(context);
            super.onPreExecute();
        }

        UserInfo signInfo;

        

    }


    public MainTitleView(Context context1)
    {
        super(context1);
        isBegin30s = true;
        handler = new Handler();
        init(context1);
    }

    public MainTitleView(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        isBegin30s = true;
        handler = new Handler();
        init(context1);
    }

    public MainTitleView(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        isBegin30s = true;
        handler = new Handler();
        init(context1);
    }

    private void init(Context context1)
    {
        int i = biz.AR.id.main_title_search_hint;
        context = context1;
        contentView = View.inflate(context, biz.AR.layout.main_title_lay, this);
        signBtn = contentView.findViewById(biz.AR.id.sign_btn);
        signLayer = contentView.findViewById(biz.AR.id.sign_btn_layer);
        signLayer.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                toSignIn();
            }

            
        }
);
        userIcon = (CircleCornerImageView)contentView.findViewById(biz.AR.id.menu_img);
        userIcon.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(context,  ManagerActivity.class);
                context.startActivity(intent);
            }

            
        }
);
        contentView.findViewById(i).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(context,  SearchActivity.class);
                intent.setFlags(0x200000);
                context.startActivity(intent);
            }

           
        }
);
        searchText = (TextView)contentView.findViewById(i);
        searchText.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(context,  SearchActivity.class);
                intent.setFlags(0x200000);
                context.startActivity(intent);
            }

            
        }
);
        code = (Button)contentView.findViewById(biz.AR.id.main_title_qrcode_btn);
        code.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                QRCodeManager.startScan((Activity)context);
            }

             
        }
);
        ((ImageView)contentView.findViewById(biz.AR.id.main_title_download_img)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(context,  DownloadManagerActivity.class);
                context.startActivity(intent);
            }

            
        }
);
        updateTextView = (TextView)findViewById(biz.AR.id.main_tilte_update_count);
        downloadTextView = findViewById(biz.AR.id.main_title_download_count);
        setDownloadCount();
        setUpdateCount();
        downloadreceiver = new DownloadChanReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("com.gionee.aora.market.download.count.change");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_UPDATE");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_ADD");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_DELETE");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_IGNORE");
        context.registerReceiver(downloadreceiver, intentfilter);
        signLayer.postDelayed(new Runnable() {

            public void run()
            {
                int j;
                UserInfo userinfo;
                j = 1;
                userinfo = UserStorage.getDefaultUserInfo(context);
                refreshUserInfoUi(userinfo);
                if(!(userinfo.getUSER_TYPE_FLAG() == j || userinfo.getLOGIN_STATE() != 2)){
                    //L1_L1:
                    int ai[] = userinfo.getDATA_ISSING();
                    if(ai[-1 + ai.length] <= 0)
                        j = 0;
                    if(j != 0) {
                        //L3_L3:
                        isBegin30s = false;
                        signLayer.setVisibility(8);
                        userIcon.setVisibility(0);
                        setUpdateCount();
                    }else{
                        //L4_L4:
                        if(isBegin30s)
                        {
                            signLayer.setVisibility(0);
                            userIcon.setVisibility(8);
                            updateTextView.setVisibility(8);
                        }
                    }
                }else{
                    //L2_L2:
                    isBegin30s = false;
                    signLayer.setVisibility(0);
                    userIcon.setVisibility(8);
                    updateTextView.setVisibility(8);
                }


                setState(0);
                return;


            }

             
        }
, 3000L);
    }

    private void setState(int i)
    {
        switch(i)
        {
        default:
            return;

        case 0: // '\0'
            isBegin30s = true;
            handler.removeCallbacks(hideRunnable);
            handler.postDelayed(hideRunnable, 15000L);
            return;

        case 1: // '\001'
            isBegin30s = false;
            handler.removeCallbacks(hideRunnable);
            return;

        case 2: // '\002'
            isBegin30s = false;
            break;
        }
        signBtnShake();
    }

    private void signBtnShake()
    {
        if(signLayer.getVisibility() == 8)
        {
            setUpdateCount();
            return;
        } else
        {
            android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, biz.AR.anim.main_title_sign_button_shake);
            signBtn.clearAnimation();
            signBtn.startAnimation(animation);
            signBtn.postDelayed(new Runnable() {

                public void run()
                {
                    AlphaAnimation alphaanimation = new AlphaAnimation(1.0F, 0.0F);
                    alphaanimation.setDuration(500L);
                    alphaanimation.setRepeatCount(0);
                    alphaanimation.setFillAfter(false);
                    userIcon.clearAnimation();
                    userIcon.startAnimation(alphaanimation);
                    AlphaAnimation alphaanimation1 = new AlphaAnimation(0.0F, 1.0F);
                    alphaanimation.setDuration(500L);
                    alphaanimation.setRepeatCount(0);
                    alphaanimation.setFillAfter(false);
                    signLayer.clearAnimation();
                    signLayer.startAnimation(alphaanimation1);
                    userIcon.postDelayed(new Runnable() {

                        public void run()
                        {
                            signLayer.setVisibility(8);
                            userIcon.setVisibility(0);
                            setUpdateCount();
                        }

                         
                    }
, 500L);
                }

                
            }
, 5000L);
            return;
        }
    }

    private void toSignIn()
    {
        setState(1);
        AlphaAnimation alphaanimation = new AlphaAnimation(1.0F, 0.0F);
        alphaanimation.setDuration(500L);
        alphaanimation.setRepeatCount(0);
        alphaanimation.setFillAfter(false);
        signBtn.clearAnimation();
        signBtn.startAnimation(alphaanimation);
        signBtn.postDelayed(new Runnable() {

            public void run()
            {
                signLayer.setVisibility(8);
                userIcon.setVisibility(0);
                setUpdateCount();
            }

            
        }
, 500L);
        UserInfo userinfo = UserStorage.getDefaultUserInfo(context);
        if(userinfo.getUSER_TYPE_FLAG() != 1 && userinfo.getLOGIN_STATE() == 2)
        {
            signLayer.setEnabled(false);
            (new SignTask()).doExecutor(new Integer[0]);
            return;
        } else
        {
            Intent intent = new Intent(context,  LoginActivity.class);
            context.startActivity(intent);
            return;
        }
    }

    public void onDestory()
    {
        if(downloadreceiver != null)
            context.unregisterReceiver(downloadreceiver);
    }

    public void refreshUserInfoUi(UserInfo userinfo)
    {
        int i = biz.AR.drawable.main_left_user_icon;
        int ai[];
        if(userinfo.getLOGIN_STATE() == 4)
            userIcon.setSrc(i);
        else
        if(userinfo.getLOGIN_STATE() == 2 && userinfo.getPHONE().equals(""))
        {
            userIcon.setSrc(i);
        } else
        {
            userIcon.setSrc(i);
            com.nostra13.universalimageloader.core.DisplayImageOptions displayimageoptions = (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
            ImageLoaderManager.getInstance().loadImage(userinfo.getICON_URL(), displayimageoptions, new SimpleImageLoadingListener() {

                public void onLoadingCancelled(String s, View view)
                {
                    userIcon.setSrc(biz.AR.drawable.main_left_user_icon);
                }

                public void onLoadingComplete(String s, View view, Bitmap bitmap)
                {
                    if(bitmap != null)
                    {
                        userIcon.setSrc(bitmap);
                        return;
                    } else
                    {
                        userIcon.setSrc(biz.AR.drawable.main_left_user_icon);
                        return;
                    }
                }

                public void onLoadingFailed(String s, View view, FailReason failreason)
                {
                    userIcon.setSrc(biz.AR.drawable.main_left_user_icon);
                }

                 
            }
);
        }
        ai = userinfo.getDATA_ISSING();
        if(ai != null)
        {
            boolean flag;
            if(ai[-1 + ai.length] > 0)
                flag = true;
            else
                flag = false;
            if(flag && userinfo.getUSER_TYPE_FLAG() != 1)
            {
                isBegin30s = false;
                signLayer.setVisibility(8);
                userIcon.setVisibility(0);
                setUpdateCount();
            } else
            if(isBegin30s)
            {
                signLayer.setVisibility(0);
                userIcon.setVisibility(8);
                updateTextView.setVisibility(8);
                return;
            }
        }
    }

    public void setDownloadCount()
    {
        List list = DownloadManager.shareInstance().getAllTaskInfo();
        if(list != null && list.size() != 0)
        {
            downloadTextView.setVisibility(0);
            return;
        } else
        {
            downloadTextView.setVisibility(8);
            return;
        }
    }

    public void setSearchTextHint(String s)
    {
        searchText.setText(s);
    }

    public void setUpdateCount()
    {
        if(signLayer.getVisibility() == 0)
        {
            updateTextView.setVisibility(8);
            return;
        }
        if(SoftwareManager.getInstace().getUpdateSoftwaresCount() == 0)
        {
            updateTextView.setVisibility(8);
            return;
        } else
        {
            updateTextView.setVisibility(0);
            updateTextView.setText((new StringBuilder()).append(SoftwareManager.getInstace().getUpdateSoftwaresCount()).append("").toString());
            return;
        }
    }

    public Button code;
    private View contentView;
    private Context context;
    public View downloadTextView;
    public DownloadChanReceiver downloadreceiver;
    Handler handler;
    Runnable hideRunnable = new Runnable() {

        public void run()
        {
            setState(2);
        }
 
    }
;
    private boolean isBegin30s;
    public DownloadListener listener;
    public DownloadManager manager;
    public TextView searchText;
    private View signBtn;
    private View signLayer;
    public TextView updateTextView;
    private CircleCornerImageView userIcon;





/*
    static boolean access$202(MainTitleView maintitleview, boolean flag)
    {
        maintitleview.isBegin30s = flag;
        return flag;
    }

*/



}
