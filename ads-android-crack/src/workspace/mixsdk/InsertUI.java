package workspace.mixsdk;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import workspace.bean.App;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.ReportUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InsertUI extends AlertDialog {
    //private static final int AD_DIALOGDISMISS = 2;
    //private static final int ICO_GETIMGCANCLE = 5;
    //private static final int MYDIALOG_GETIMGCANCLE = 4;
    //private static final int MYDIALOG_SHOWIMGS = 1;
    private static Handler handler;
    private static int insertShowIndexMax;
    private android.content.SharedPreferences.Editor aDCountED;
    private SharedPreferences aDCountSP;
    private AlertDialog alertDialog;
    private String appid;
    private RelativeLayout bannerb;
    private Context context;
    private int height;
    private ImageView icoCancle;
    private ImageView imgCancle;
    private List<App> imgsInfos;
    private RelativeLayout layout;
    private RelativeLayout rlICOcancle;
    private RelativeLayout rlImgcancle;
    private RelativeLayout rlicon;
    private android.widget.RelativeLayout.LayoutParams layoutparams;
    private android.widget.RelativeLayout.LayoutParams rlpICOcancle;
    private android.widget.RelativeLayout.LayoutParams rlpImgcancle;
    private android.widget.RelativeLayout.LayoutParams rlpicon;
    private int width;
    private WindowManager wm;
    @SuppressWarnings("deprecation")
    protected InsertUI(Context context1) {
        super(context1);
        context = context1;
        appid = context1.getApplicationContext().getSharedPreferences("CheckInit", 0).getString("appid", "");
        createADCount(2);
        width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        bannerb = new RelativeLayout(context1);
        wm = (WindowManager) context1.getSystemService("window");
        alertDialog = new android.app.AlertDialog.Builder(context1).create();
        alertDialog.requestWindowFeature(1);
        alertDialog.getWindow().setFlags(256, 256);
        alertDialog.getWindow().setType(2010);
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialoginterface, int i, KeyEvent keyevent) {
                return i == 84 || i == 4;
            }
        });
        layout = new RelativeLayout(context1);
        handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        showImgsList((List<App>) message.obj);
                        return;
                    case 2:
                        alertDialog.dismiss();
                        return;
                    case 5:
                        try {
                            rlICOcancle.removeViewInLayout(icoCancle);
                            rlICOcancle.addView(icoCancle, rlpICOcancle);
                            alertDialog.show();
                            return;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                }
            }
        };
    }
    private void aDshowSPAdd(int i, int j, int k) {
        if (i != 0 && !aDCountSP.contains(imgsInfos.get(i).id)) {
            aDCountED.putInt(imgsInfos.get(i).id, 1);
            aDCountED.putInt("adType", k);
            aDCountED.commit();
            return;
        } else {
            aDCountED.putInt(imgsInfos.get(i).id, 1 + aDCountSP.getInt(imgsInfos.get(i).id, 0));
            aDCountED.commit();
            return;
        }
    }
    private boolean checkDownLoading() {
        Map<String, ?> map = Constants.context.getSharedPreferences("downLoadingApk", 0).getAll();
        return map != null && map.size() > 2;
    }
    private void createADCount(int i) {
        aDCountSP = context.getSharedPreferences("InsertADCount", 0);
        boolean flag = aDCountSP.getBoolean("isFirstRun", true);
        aDCountED = aDCountSP.edit();
        if (flag) {
            aDCountED.putBoolean("isFirstRun", false);
            aDCountED.putInt("times", 0);
            aDCountED.putLong("preTime", System.currentTimeMillis());
            aDCountED.commit();
        } else {
            long l = aDCountSP.getLong("preTime", 0L);
            if ((System.currentTimeMillis() - l) / 60000L >= 10L) {
                CommonUtils.responseShowADCount(context, appid);
                return;
            }
        }
    }
    private void getNextShow5() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("insertShow", 0);
        if (sharedpreferences.getInt("insertShowIndex", 0) == insertShowIndexMax) {
            sharedpreferences.edit().putInt("insertShowIndex", 1).commit();
            return;
        } else {
            sharedpreferences.edit().putInt("insertShowIndex", 1 + sharedpreferences.getInt("insertShowIndex", 0)).commit();
            return;
        }
    }
    public boolean isScreenChange() {
        int i = context.getResources().getConfiguration().orientation;
        if (i == 2) { return true; }
        return i != 1 ? false : false;
    }
    void showImages(List<App> arraylist, int i) {
        imgsInfos = arraylist;
        insertShowIndexMax = i;
        Message message = Message.obtain();
        message.what = 1;
        message.obj = arraylist;
        handler.sendMessage(message);
    }
    private void showImgsList(List<App> list) {
        if (list.size() < 1) { return; }
        showPop(list.get(new Random().nextInt(list.size())));
        showBanner(list.get(new Random().nextInt(list.size())));
        handler.sendEmptyMessageDelayed(5, 3000L);
        return;
    }
    public void showPop(final  App app) {
        layout.removeAllViewsInLayout();
        ImageView imageview = new ImageView(context);
        Window window = alertDialog.getWindow();
        android.view.WindowManager.LayoutParams layoutparams1 = window.getAttributes();
        layoutparams1.alpha = 1.0F;
        layoutparams1.dimAmount = 1.0F;
        window.setAttributes(layoutparams1);
        alertDialog.show();
        int k = width / 10;
        
        String screenUrl = app.screenUrl;
        Bitmap bitmap1 = BitmapFactory.decodeFile(new File(new StringBuilder().append(Environment.getExternalStorageDirectory()).append("/insertimage/")
                .append(screenUrl.substring(1 + screenUrl.lastIndexOf("/"), screenUrl.length())).toString()).getAbsolutePath());
        Bitmap bitmap = bitmap1;
        if (isScreenChange()) {
            layoutparams = new android.widget.RelativeLayout.LayoutParams(height, width);
            Matrix matrix = new Matrix();
            matrix.setRotate(-90F);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            k = height / 10;
        } else {
            layoutparams = new android.widget.RelativeLayout.LayoutParams(width, height);
        }
        imageview.setImageBitmap(bitmap);
        imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        aDshowSPAdd(0, 0, 2);
        alertDialog.setContentView(imageview, layoutparams);
        imageview.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                float x1 = 0;
                if (motionevent.getAction() == 0) {
                    x1 = motionevent.getX();
                }
                if (motionevent.getAction() == 1) {
                    float x2 = motionevent.getX();
                    if (Math.abs(x1 - x2) <= 10F) {
                        String s2 =app.packageName;
                        if (CommonUtils.apkExits(context, s2)) {
                            alertDialog.dismiss();
                            android.content.Intent intent = context.getPackageManager().getLaunchIntentForPackage(s2);
                            context.startActivity(intent);
                        } else {
                            String s3 = app.appUrl;
                            String s4 = s3.substring(1 + s3.lastIndexOf("/"), s3.length());
                            File file = new File(new StringBuilder("sdcard/").append(s4).toString());
                            CommonUtils.openFile(context, file, s2);
                        }
                        alertDialog.dismiss();
                    }
                }
                return true;
            }
        });
        imgCancle = new ImageView(context);
        try {
            imgCancle.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("cancel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgCancle.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        imgCancle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                try {
                   // new InsD(context).getInfo(context, imgsInfos, ads1);
                    if (!checkDownLoading() && CommonUtils.isNotEmpty(appid)) {
                        ReportUtils.adCloseReport();
                    }
                    return;
                } catch (Exception exception3) {
                    exception3.printStackTrace();
                }
            }
        });
        rlImgcancle = new RelativeLayout(context);
        rlImgcancle.setLayoutParams(new android.view.WindowManager.LayoutParams(-1, -1));
        rlpImgcancle = new android.widget.RelativeLayout.LayoutParams(k, k);
        rlpImgcancle.addRule(10);
        if (new Random().nextInt(2) == 0) {
            rlpImgcancle.addRule(9);
        } else {
            rlpImgcancle.addRule(11);
        }
        alertDialog.addContentView(rlImgcancle, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    rlImgcancle.removeViewInLayout(imgCancle);
                    rlImgcancle.addView(imgCancle, rlpImgcancle);
                    alertDialog.show();
                    return;
                } catch (Exception exception1) {
                    exception1.printStackTrace();
                }
                return;
            }
        }, 3000L);
    }
    private void showBanner(final App app) {
        android.view.WindowManager.LayoutParams layoutparams = new android.view.WindowManager.LayoutParams(1024, height / 10);
        layoutparams.type = 2003;
        layoutparams.format = 1;
        layoutparams.gravity = 80;
        layoutparams.flags = 8;
        layoutparams.height = height / 10;
        bannerb.setBackgroundColor(Color.parseColor("#80000000"));
        ImageView imageview1 = new ImageView(context);
        try {
            
            String s = app.iconUrl;
            imageview1.setImageBitmap(BitmapFactory.decodeFile(new File(new StringBuilder().append(Environment.getExternalStorageDirectory()).append("/insertimage/")
                    .append(s.substring(1 + s.lastIndexOf("/"), s.length())).toString()).getAbsolutePath()));
        } catch (Exception exception) {}
        imageview1.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        rlicon = new RelativeLayout(context);
        rlicon.setLayoutParams(new android.view.WindowManager.LayoutParams(-1, -1));
        int i = 8 * height / 100;
        rlpicon = new android.widget.RelativeLayout.LayoutParams(i, i);
        rlpicon.addRule(9);
        rlicon.addView(imageview1, rlpicon);
        TextView textview = new TextView(context);
        textview.setText(app.name);
        textview.setTextColor(-1);
        textview.setTextSize(20F);
        textview.setPadding(height / 10, height / 100, 0, 0);
        bannerb.addView(rlicon, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        bannerb.addView(textview, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        bannerb.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannerb.setVisibility(8);
                String s2;
                s2 = app.packageName;
                if (CommonUtils.apkExits(context, s2)) {
                    android.content.Intent intent = context.getPackageManager().getLaunchIntentForPackage(s2);
                    context.startActivity(intent);
                    wm.removeView(bannerb);
                } else {
                    try {
                        String s3 =app.appUrl;
                        String s4 = s3.substring(1 + s3.lastIndexOf("/"), s3.length());
                        File file = new File(new StringBuilder("sdcard/").append(s4).toString());
                        CommonUtils.openFile(context, file, s2);
                    } catch (Exception exception3) {
                        exception3.printStackTrace();
                        return;
                    }
                }
                wm.removeView(bannerb);
                return;
            }
        });
        bannerb.setPadding(height / 100, height / 100, height / 100, height / 100);
        try {
            wm.addView(bannerb, layoutparams);
            //aDshowSPAdd(ads2, 0, 2);
            icoCancle = new ImageView(context);
            icoCancle.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("cancel.png")));
            icoCancle.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            icoCancle.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bannerb.setVisibility(8);
                    try {
                        if (!checkDownLoading() && CommonUtils.isNotEmpty(appid)) {
                            //String adId = ((ImageInfo) imgsInfos.get(ads2)).getId();
                            ReportUtils.adCloseReport();
                        }
                        return;
                    } catch (Exception exception3) {
                        exception3.printStackTrace();
                    }
                }
            });
            rlICOcancle = new RelativeLayout(context);
            rlICOcancle.setLayoutParams(new android.view.WindowManager.LayoutParams(-1, -1));
            int j = 1 * width / 15;
            rlpICOcancle = new android.widget.RelativeLayout.LayoutParams(j, j);
            rlpICOcancle.addRule(10);
            rlpICOcancle.addRule(11);
            getNextShow5();
            bannerb.addView(rlICOcancle, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
