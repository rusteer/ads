// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.manager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.util.DLog;
import com.aora.base.util.NetUtil;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.market.control.ApkInstallManager;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.control.UpdateManager;
import com.gionee.aora.market.gui.share.ShareActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;

// Referenced classes of package com.gionee.aora.market.gui.manager:
//            MarketAboutActivity
public class MarketSettingActivity extends Activity {
    private RelativeLayout aboutLayout;
    private CheckBox autoUpdateCheck;
    private DataCollectInfo datainfo;
    private CheckBox deltetPackageCheck;
    private CheckBox isNoIconCheck;
    private TextView limtSize;
    private SeekBar limtseekbar;
    private ImageLoaderManager manager;
    private TextView pointUpdateText;
    private MarketPreferences pref;
    private CheckBox receiveRecommendCheck;
    private RelativeLayout setAutoUpdateLayout;
    private RelativeLayout setPointUpdateTimeLayout;
    private TextView setting_installed_location;
    private TextView setting_tip;
    private ChildTitleView setting_titleBarView;
    private RelativeLayout shareLayout;
    private TextView updateInfoTextView;
    private RelativeLayout updateLayout;
    public MarketSettingActivity() {
        setting_titleBarView = null;
        manager = null;
        setAutoUpdateLayout = null;
        setPointUpdateTimeLayout = null;
        pointUpdateText = null;
        shareLayout = null;
        updateLayout = null;
        aboutLayout = null;
        limtSize = null;
        limtseekbar = null;
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.setting);
        datainfo = DataCollectManager.getCollectInfo(this);
        manager = ImageLoaderManager.getInstance();
        pref = MarketPreferences.getInstance(this);
        setting_titleBarView = (ChildTitleView) findViewById(biz.AR.id.settingTitleBar);
        setting_titleBarView.setTitle("\u8BBE\u7F6E");
        setting_titleBarView.setRightViewVisibility();
        setting_tip = (TextView) findViewById(biz.AR.id.setting_nettype);
        CheckBox checkbox;
        boolean flag;
        if (NetUtil.getNetMode(this).equals("WIFI")) {
            setting_tip.setText("WLAN");
            setting_tip.setTextColor(getResources().getColor(biz.AR.color.title_bg_color));
        } else {
            setting_tip.setText(biz.AR.string.not_wifi);
            setting_tip.setTextColor(Color.parseColor("#ffb430"));
        }
        isNoIconCheck = (CheckBox) findViewById(biz.AR.id.setting_noicon_checkbox);
        checkbox = isNoIconCheck;
        if (!MarketPreferences.getInstance(this).isShowIcon()) flag = true;
        else flag = false;
        checkbox.setChecked(flag);
        isNoIconCheck.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag1) {
                if (flag1) manager.setShowImage(false);
                else manager.setShowImage(true);
                sendBroadcast(new Intent("com.gionee.aora.market.SHOW_ICON_STATE_CHANGED"));
            }
        });
        deltetPackageCheck = (CheckBox) findViewById(biz.AR.id.setting_delete_install_checkbox);
        deltetPackageCheck.setChecked(pref.getDeleteDownloadPackage().booleanValue());
        deltetPackageCheck.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag1) {
                if (flag1) {
                    pref.setDeleteDownloadPackage(Boolean.valueOf(true));
                    return;
                } else {
                    pref.setDeleteDownloadPackage(Boolean.valueOf(false));
                    return;
                }
            }
        });
        setting_installed_location = (TextView) findViewById(biz.AR.id.install_location_text);
        try {
            setting_installed_location.setText(new StringBuilder().append(Environment.getExternalStorageDirectory().getPath()).append("/")
                    .append(DownloadManager.shareInstance().getDefault_download_dir()).toString());
        } catch (Exception exception) {
            DLog.e("MarketSettingActivity", "Environment.getExternalStorageDirectory() error#exception =", exception);
            setting_installed_location.setText(new StringBuilder().append("/").append(DownloadManager.shareInstance().getDefault_download_dir()).toString());
        }
        updateInfoTextView = (TextView) findViewById(biz.AR.id.setting_auto_update_summy);
        autoUpdateCheck = (CheckBox) findViewById(biz.AR.id.setting_auto_update_checkbox);
        setSettingAutoUpdateSummy(pref.getAutoUpdateType());
        autoUpdateCheck.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag1) {
                pref.setFirstSettingAutoUpdate(Boolean.valueOf(false));
                int i;
                if (!flag1) i = 1;
                else if (ApkInstallManager.checkInstallPackagesPermission(MarketSettingActivity.this)) i = 2;
                else i = 3;
                if (i == 1) {
                    DataCollectInfo datacollectinfo1 = datainfo.clone();
                    datacollectinfo1.setPosition("1");
                    DataCollectManager.addRecord(datacollectinfo1, new String[0]);
                } else {
                    DataCollectInfo datacollectinfo = datainfo.clone();
                    datacollectinfo.setPosition("2");
                    DataCollectManager.addRecord(datacollectinfo, new String[0]);
                }
                pref.setAutoUpdateType(i);
                setSettingAutoUpdateSummy(i);
            }
        });
        setAutoUpdateLayout = (RelativeLayout) findViewById(biz.AR.id.setting_auto_update_lay);
        setAutoUpdateLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox1 = autoUpdateCheck;
                boolean flag1;
                if (!autoUpdateCheck.isChecked()) flag1 = true;
                else flag1 = false;
                checkbox1.setChecked(flag1);
            }
        });
        pointUpdateText = (TextView) findViewById(biz.AR.id.setting_point_update_text);
        switch (pref.getUpdatePointType()) {
            case 0://L2_L2:
                pointUpdateText.setText("\u4E00\u5929");
                break;
            case 2://L3_L3:
                pointUpdateText.setText("\u4E00\u5468");
                break;
            case 3://L4
                pointUpdateText.setText("\u5173\u95ED");
                break;
        }
        //_L5:
        setPointUpdateTimeLayout = (RelativeLayout) findViewById(biz.AR.id.setting_point_update_time_lay);
        setPointUpdateTimeLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;
                MarketFloateDialogBuilder marketfloatedialogbuilder;
                i = biz.AR.id.setting_point_update_time_radioBtn1;
                marketfloatedialogbuilder = new MarketFloateDialogBuilder(MarketSettingActivity.this);
                marketfloatedialogbuilder.setTitle("\u66F4\u65B0\u63D0\u793A\u5468\u671F");
                marketfloatedialogbuilder.setCancelable(true);
                switch (pref.getUpdatePointType()) {
                    case 0://L1
                        break;
                    case 1://L2_L2:
                        i = biz.AR.id.setting_point_update_time_radioBtn2;
                        break;
                    case 2://L3_L3:
                        i = biz.AR.id.setting_point_update_time_radioBtn3;
                        break;
                }
                View view1 = View.inflate(MarketSettingActivity.this, biz.AR.layout.setting_point_time_layout, null);
                ((RadioButton) view1.findViewById(i)).setChecked(true);
                marketfloatedialogbuilder.setCenterView(view1, null);
                marketfloatedialogbuilder.setDialogHeight((int) getResources().getDimension(biz.AR.dimen.dp210));
                final RadioGroup radioGroup = (RadioGroup) view1.findViewById(biz.AR.id.setting_point_update_time_radioGroup);
                final Dialog dialog = marketfloatedialogbuilder.crteate();
                ((LinearLayout) view1.findViewById(biz.AR.id.setting_point_ok_btn)).setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case biz.AR.id.setting_point_update_time_radioBtn1://L2_L2:
                                pref.setUpdatePointType(0);
                                pointUpdateText.setText("\u4E00\u5929");
                                break;
                            case biz.AR.id.setting_point_update_time_radioBtn2://L3_L3:
                                pref.setUpdatePointType(1);
                                pointUpdateText.setText("\u4E00\u5468");
                                break;
                            case biz.AR.id.setting_point_update_time_radioBtn3://L4_L4:
                                pref.setUpdatePointType(2);
                                pointUpdateText.setText("\u5173\u95ED");
                                break;
                        }
                        pref.saveUpdteCpTime(System.currentTimeMillis());
                        dialog.dismiss();
                        return;
                    }
                });
                dialog.show();
                return;
            }
        });
        receiveRecommendCheck = (CheckBox) findViewById(biz.AR.id.setting_receive_recommend);
        receiveRecommendCheck.setChecked(pref.getReceiveReommendRemind().booleanValue());
        receiveRecommendCheck.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag1) {
                if (flag1) {
                    DataCollectInfo datacollectinfo1 = datainfo.clone();
                    datacollectinfo1.setPosition("4");
                    pref.setReceiveReommendRemind(Boolean.valueOf(true));
                    DataCollectManager.addRecord(datacollectinfo1, new String[0]);
                    return;
                } else {
                    DataCollectInfo datacollectinfo = datainfo.clone();
                    datacollectinfo.setPosition("3");
                    pref.setReceiveReommendRemind(Boolean.valueOf(false));
                    DataCollectManager.addRecord(datacollectinfo, new String[0]);
                    return;
                }
            }
        });
        shareLayout = (RelativeLayout) findViewById(biz.AR.id.setting_share);
        updateLayout = (RelativeLayout) findViewById(biz.AR.id.setting_update);
        aboutLayout = (RelativeLayout) findViewById(biz.AR.id.setting_about);
        shareLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer stringbuffer = new StringBuffer();
                stringbuffer.append("\u6211\u6B63\u5728\u4F7F\u7528\u8D85\u7EA7\u9177\u70AB\u72C2\u62FD\u7684\u6613\u7528\u6C47\uFF0C\u4F60\u4E5F\u5FEB\u6765\u52A0\u5165\u5427");
                stringbuffer.append("http://adres.myaora.net:81/function/download_apk.php?softid=10901&chl=fenxiang");
                ShareActivity.share(MarketSettingActivity.this, stringbuffer.toString(), "http://adres.myaora.net:81/function/download_apk.php?softid=10901&chl=fenxiang",
                        "\u6211\u6B63\u5728\u4F7F\u7528\u8D85\u7EA7\u9177\u70AB\u72C2\u62FD\u7684\u6613\u7528\u6C47\uFF0C\u4F60\u4E5F\u5FEB\u6765\u52A0\u5165\u5427");
            }
        });
        updateLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateManager.getInstance().checkUpdate(MarketSettingActivity.this, getPackageName(), getString(biz.AR.string.app_name), DataCollectUtil.getVersionCode(), false);
            }
        });
        aboutLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketSettingActivity.this, MarketAboutActivity.class));
            }
        });
        limtSize = (TextView) findViewById(biz.AR.id.setting_download_limit_value);
        limtseekbar = (SeekBar) findViewById(biz.AR.id.setting_download_limit_seekbar);
        if (pref.getDownloadMaxSize() != 50) limtSize.setText(new StringBuilder().append(pref.getDownloadMaxSize()).append("M").toString());
        else limtSize.setText("\u4E0D\u9650");
        limtseekbar.setProgress(pref.getDownloadMaxSize());
        limtseekbar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar, int i, boolean flag1) {
                if (i == 50) limtSize.setText("\u4E0D\u9650");
                else limtSize.setText(new StringBuilder().append(i).append("M").toString());
                pref.setDownloadMaxSize(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar) {}
        });
        return;
    }
    public void setSettingAutoUpdateSummy(int i) {
        if (autoUpdateCheck != null) if (i == 1) autoUpdateCheck.setChecked(false);
        else autoUpdateCheck.setChecked(true);
        if (updateInfoTextView != null) {
            switch (i) {
                case 1://L3_L3:
                    updateInfoTextView.setText("\u5173\u95ED\u81EA\u52A8\u66F4\u65B0");
                    break;
                case 2://L4_L4:
                    updateInfoTextView
                    .setText("\u6E29\u99A8\u63D0\u793A\uFF1A\u624B\u673A\u7535\u91CF\u5C0F\u4E8E40%\u65F6\u3001\u5DF2\u52A0\u5165\u5FFD\u7565\u5217\u8868\u7684\u5E94\u7528\u4E0D\u4F1A\u81EA\u52A8\u4E0B\u8F7D");
                    break;
                case 3://L5_L5:
                    updateInfoTextView
                    .setText("\u6E29\u99A8\u63D0\u793A\uFF1A\u624B\u673A\u7535\u91CF\u5C0F\u4E8E40%\u65F6\u3001\u5DF2\u52A0\u5165\u5FFD\u7565\u5217\u8868\u7684\u5E94\u7528\u4E0D\u4F1A\u81EA\u52A8\u4E0B\u8F7D");
                    break;
            }
        }
    }
}
