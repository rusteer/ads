package workspace.mixsdk;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import workspace.bean.App;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.ReportUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

@SuppressWarnings("unchecked")
public class PopUI {
    public static void getBms(List<Bitmap> arraylist, int i) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = arraylist;
        message.arg1 = i;
        handler.sendMessage(message);
    }
    public static void getImgs(List<App> list, int i) {
        Message message = Message.obtain();
        message.what = 1;
        message.arg1 = i;
        message.obj = list;
        handler.sendMessage(message);
    }
    public static void UpdataNotify(int i) {
        Message message = Message.obtain();
        message.what = 6;
        message.obj = Integer.valueOf(i);
        handler.sendMessage(message);
    }
    public static void UpdataNotifyDone(File file1) {
        Message message = Message.obtain();
        message.what = 7;
        message.obj = file1;
        handler.sendMessage(message);
    }
    private static Context context;
    private static AlertDialog dialog;
    private static Handler handler;
    private android.content.SharedPreferences.Editor aDCountED;
    private SharedPreferences aDCountSP;
    private String appid;
    private ArrayList<Integer> bmOrder;
    private List<App> dowingLoading;
    private File file;
    private ViewFlipper flipper;
    private List<App> imgs_show5;
    private Integer index;
    private boolean isShowing;
    private ImageView ivCancle;
    private RelativeLayout layoutIvCancle;
    private int popShowIndexMax;
    private android.widget.RelativeLayout.LayoutParams rlpIvCancle;
    private int width;
    private static final int AD_DIALOGDISMISS = 3;
    private static final int AD_GETBMS = 2;
    private static final int AD_GETIMGS = 1;
    private static final int APK_DOWNLOADED = 5;
    private static final int NOTIFY_CHANGED = 6;
    private static final int NOTIFY_CHANGEDONE = 7;
    private static final int SHOWIVCANCLE = 8;
    public PopUI() {
        index = Integer.valueOf(0);
        dowingLoading = new ArrayList<App>();
        isShowing = false;
    }
    @SuppressWarnings("deprecation")
    public PopUI(Context context1) {
        index = Integer.valueOf(0);
        dowingLoading = new ArrayList<App>();
        isShowing = false;
        width = ((Activity) context1).getWindow().getWindowManager().getDefaultDisplay().getWidth();
        context = context1;
        createHandler();
    }
    private void adShowSPAdd(List<Integer> list, int i, int j) {
        if (list != null && !aDCountSP.contains(imgs_show5.get(list.get(i).intValue()).id)) {
            aDCountED.putInt(imgs_show5.get(list.get(i).intValue()).id, 1);
            aDCountED.putInt("adType", j);
            aDCountED.commit();
            return;
        } else {
            aDCountED.putInt(imgs_show5.get(list.get(i).intValue()).id, 1 + aDCountSP.getInt(imgs_show5.get(list.get(i).intValue()).id, 0));
            aDCountED.commit();
            return;
        }
    }
    private boolean checkDownLoading() {
        Map<String, ?> hashmap = Constants.context.getSharedPreferences("downLoadingApk", 0).getAll();
        return hashmap != null && hashmap.size() > 2;
    }
    private void CreateADCountSP(int i) {
        aDCountSP = context.getSharedPreferences("popADCount", 0);
        boolean flag = aDCountSP.getBoolean("isFirstRun", true);
        aDCountED = aDCountSP.edit();
        if (flag) {
            aDCountED.putBoolean("isFirstRun", false);
            aDCountED.putInt("times", 0);
            aDCountED.putLong("time", System.currentTimeMillis());
            aDCountED.commit();
        } else {
            long l = aDCountSP.getLong("time", 0L);
            if (Math.abs(System.currentTimeMillis() - l) / 60000L >= 10L) {
                CommonUtils.responseShowADCount(context, appid);
                return;
            }
        }
    }
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case AD_GETIMGS://L_L2_L2:
                        List<App> arraylist1 = (List<App>) message.obj;
                        imgs_show5 = new ArrayList<App>();
                        SharedPreferences sharedpreferences = PopUI.context.getSharedPreferences("popShow", 0);
                        popShowIndexMax = arraylist1.size();
                        int popShowIndex = sharedpreferences.getInt("popShowIndex", 1);
                        if (popShowIndex > arraylist1.size()) {
                            int k = new Random().nextInt(arraylist1.size());
                            imgs_show5.add(arraylist1.get(k));
                        } else {
                            try {
                                imgs_show5.add(arraylist1.get(popShowIndex - 1));
                            } catch (Exception exception1) {
                                exception1.printStackTrace();
                            }
                        }
                        PopImgUtil.downloadScreen(imgs_show5, message.arg1);
                        return;
                    case AD_GETBMS://L _L3 _L3:
                        List<Bitmap> arraylist = (List<Bitmap>) message.obj;
                        if (arraylist.size() >= 1 && CommonUtils.isRunningForeground(PopUI.context)) {
                            updateView(arraylist, message.arg1);
                        } else {
                            arraylist.clear();
                            PopImgUtil.clear();
                        }
                        return;
                    case AD_DIALOGDISMISS://L_L4 _L4:
                        PopUI.dialog.dismiss();
                        return;
                    case APK_DOWNLOADED://L_L5 _L5:
                        int i = message.arg2;
                        dowingLoading.remove(imgs_show5.get(i).id);
                        file = (File) message.obj;
                        if (file.exists()) {
                            CommonUtils.openFile(PopUI.context, file, imgs_show5.get(i).packageName);
                        } else {
                            try {
                                ((NotificationManager) PopUI.context.getSystemService("notification")).cancel(Integer.parseInt(imgs_show5.get(i).id));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                        ResponseDownLoaded(message.arg1);
                        return;
                    case SHOWIVCANCLE://L _L6_L6:
                        layoutIvCancle.addView(ivCancle, rlpIvCancle);
                        return;
                    case 4://L_L1
                    case NOTIFY_CHANGED://L_L1
                    case NOTIFY_CHANGEDONE://L_L1
                        break;
                }
            }
        };
    }
    private void getNextShow5() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("popShow", 0);
        if (sharedpreferences.getInt("popShowIndex", 0) == popShowIndexMax) {
            sharedpreferences.edit().putInt("popShowIndex", 1).commit();
            return;
        } else {
            sharedpreferences.edit().putInt("popShowIndex", 1 + sharedpreferences.getInt("popShowIndex", 0)).commit();
            return;
        }
    }
    public void initView() {
        if (!isShowing) {
            SharedPreferences sharedpreferences = context.getSharedPreferences("popShow", 0);
            appid = sharedpreferences.getString("appid", "");
            android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
            int i = sharedpreferences.getInt("times", 0);
            sharedpreferences.getInt("frequency", 0);
            showAd();
            editor.putInt("times", i + 1);
            editor.putLong("preTime", System.currentTimeMillis());
            editor.commit();
            CreateADCountSP(1);
        }
    }
    private void ResponseDownLoaded(int i2) {
        //Tools.sendDataToService(new StringBuilder(CS.apkDownLoaded + "app_id=").append(appid).append("&uuid=").append(uuid).append("&ad_id=") .append(((ImageInfo) imgs_show5.get(index.intValue())).getId()).append("&ad_type=").append(1).toString());
        ReportUtils.apkDownLoadedReport();
    }
    private void showAd() {
        String url = Constants.getAdsListUrl(context, appid, "1", "ad");
        if (CommonUtils.isNotEmpty(appid)) {
            PopImgUtil.getImgs(url, 1);
        }
        dialog = new android.app.AlertDialog.Builder(context).create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setFlags(1024, 1024);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialoginterface, int j, KeyEvent keyevent) {
                return j == 84 || j == 4;
            }
        });
    }
    private void updateView(List<Bitmap> list, int i) {
        bmOrder = new ArrayList<Integer>();
        bmOrder.add(Integer.valueOf(0));
        Collections.shuffle(bmOrder);
        ImageView imageview = new ImageView(context);
        imageview.setImageBitmap(list.get(bmOrder.get(0).intValue()));
        imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        if (flipper == null) {
            flipper = new ViewFlipper(context.getApplicationContext());
        }
        flipper.addView(imageview, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        flipper.setDisplayedChild(0);
        adShowSPAdd(bmOrder, 0, i);
        flipper.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                float x1 = 0, x2 = 0;
                if (motionevent.getAction() == 0) {
                    x1 = motionevent.getX();
                }
                if (motionevent.getAction() == 1) {
                    x2 = motionevent.getX();
                    if (Math.abs(x1 - x2) <= 10F) {
                        isShowing = false;
                        index = bmOrder.get(0);
                        String s = imgs_show5.get(index.intValue()).packageName;
                        if (CommonUtils.apkExits(PopUI.context, s)) {
                            PopUI.dialog.dismiss();
                            android.content.Intent intent = PopUI.context.getPackageManager().getLaunchIntentForPackage(s);
                            PopUI.context.startActivity(intent);
                        } else {
                            DownloadUtils.setImages(imgs_show5, index.intValue(), 1, "");
                        }
                        PopUI.dialog.dismiss();
                    }
                }
                return true;
            }
        });
        ivCancle = new ImageView(context.getApplicationContext());
        try {
            java.io.InputStream inputStream = context.getAssets().open("cancel.png");
            //_L1:
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ivCancle.setImageBitmap(bitmap);
            ivCancle.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            layoutIvCancle = new RelativeLayout(context);
            layoutIvCancle.removeAllViews();
            layoutIvCancle.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(-1, -1));
            rlpIvCancle = new android.widget.RelativeLayout.LayoutParams(width / 10, width / 10);
            rlpIvCancle.addRule(10);
            rlpIvCancle.addRule(11);
            android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(-1, -1);
            layoutIvCancle.addView(flipper, layoutparams);
            Window window = dialog.getWindow();
            android.view.WindowManager.LayoutParams layoutparams1 = window.getAttributes();
            layoutparams1.gravity = 17;
            window.setAttributes(layoutparams1);
            layoutparams1.alpha = 1.0F;
            layoutparams1.dimAmount = 1.0F;
            window.setAttributes(layoutparams1);
            dialog.show();
            dialog.getWindow().setLayout(-1, -1);
            dialog.setContentView(layoutIvCancle, new android.widget.RelativeLayout.LayoutParams(-1, -1));
            getNextShow5();
            handler.sendEmptyMessageDelayed(8, 3000L);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ivCancle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowing = false;
                PopUI.dialog.dismiss();
                if (!checkDownLoading()) {
                    //int j = bmOrder.get(0).intValue();
                    //String adId = ((ImageInfo) imgs_show5.get(j)).getId();
                    //String url = new StringBuilder(CS.adClose + "app_id=").append(appid).append("&uuid=").append(Tools.getIMEI(PopUI.context)).append("&ad_id=").append(adId) .append("&ad_type=").append(1).toString();
                    ReportUtils.adCloseReport();
                }
            }
        });
        return;
    }
}
