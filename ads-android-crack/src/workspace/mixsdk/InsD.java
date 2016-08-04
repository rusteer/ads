package workspace.mixsdk;
import java.io.File;
import java.util.List;
import java.util.Random;
import workspace.bean.App;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class InsD extends AlertDialog {
    private String appName;
    public InsD(Context context1) {
        super(context1);
    }
    void getInfo(Context context1, final List<App> apps, final int index) {
        App app=apps.get(index);
        appName = app.name;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context1);
        builder.setTitle("应用更新");
        builder.setMessage(new StringBuilder("发现您的系统存在未安装的应用,是否立刻安装应用").append(appName).append("?").toString());
        AlertDialog alertdialog;
        if (new Random().nextInt(2) == 0) {
            builder.setPositiveButton("马上安装", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    DownloadUtils.setImages(apps, index, 2, "");
                    String s = apps.get(index).appUrl;
                    String s1 = s.substring(1 + s.lastIndexOf("/"), s.length());
                    File file1 = new File(new StringBuilder("sdcard/").append(s1).toString());
                    CommonUtils.openFile(Constants.context, file1, apps.get(index).packageName);
                }
            });
            builder.setNegativeButton("取消安装", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });
        } else {
            builder.setNegativeButton("马上安装", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    DownloadUtils.setImages(apps, index, 2, "");
                    String s = apps.get(index).appUrl;
                    String s1 = s.substring(1 + s.lastIndexOf("/"), s.length());
                    File file1 = new File(new StringBuilder("sdcard/").append(s1).toString());
                    CommonUtils.openFile(Constants.context, file1, apps.get(index).packageName);
                }
            });
            builder.setPositiveButton("取消安装", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });
        }
        builder.setCancelable(false);
        alertdialog = builder.create();
        alertdialog.getWindow().setType(2010);
        alertdialog.show();
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }
}
