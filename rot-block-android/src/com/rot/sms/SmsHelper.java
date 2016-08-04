package com.rot.sms;
import java.util.Vector;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import com.rot.bean.Block;
import com.rot.listener.BlockListener;
import com.rot.utils.MyLogger;

public class SmsHelper {
    public static Block checkBlock(Context context, String address, String text, BlockListener listener) {
        Block match = null;
        if (address != null && text != null) {
            Vector<Block> blockVector = BlockHelper.readBlocks(context);
            for (int i = blockVector.size() - 1; i >= 0; i--) {
                Block block = blockVector.get(i);
                if (matchBlock(address, text, block)) {
                    match = block;
                    listener.stopBroadcast();
                    break;
                }
            }
            blockVector.clear();
            System.gc();
        }
        return match;
    }
    public static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static void doSmsReceiver(BroadcastReceiver receiver, Context context, Intent intent) {
        MyLogger.debug("SmsReceiver onReceive,action=" + intent.getAction());
        String action = intent.getAction();
        if (SMS_RECEIVED.equals(action)) {
            SmsHelper.onSmsRecieved(context.getApplicationContext(), receiver, intent);
        }
    }
    private static boolean matchBlock(String address, String text, Block block) {
        boolean matchPort = false;
        boolean matchContent = false;
        String blockPort = block.port;
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(blockPort)) {
            matchPort = true;
        } else {
            String array[] = blockPort.split("\\|");
            for (String port : array) {
                if (!TextUtils.isEmpty(port) && address.contains(port.trim())) {
                    matchPort = true;
                    break;
                }
            }
        }
        if (matchPort) {
            String blockContent = block.content;
            if (TextUtils.isEmpty(blockContent)) {
                matchContent = true;
            } else if (text != null) {
                if (blockContent.contains("&")) {
                    matchContent = true;
                    for (String field : blockContent.split("\\&")) {
                        if (!text.contains(field)) {
                            matchContent = false;
                            break;
                        }
                    }
                } else if (blockContent.contains("|")) {
                    for (String field : blockContent.split("\\|")) {
                        if (text.contains(field)) {
                            matchContent = true;
                            break;
                        }
                    }
                } else {
                    matchContent = text.contains(blockContent);
                }
            }
        }
        boolean result = matchContent && matchPort;
        return result;
    }
    public static void onSmsRecieved(Context context, final BroadcastReceiver broadcastreceiver, Intent intent) {
        if (intent != null) {
            Object obj = intent.getExtras().get("pdus");
            if (obj != null) {
                Object[] pdus = (Object[]) obj;
                SmsMessage[] msgArray = new SmsMessage[pdus.length];
                String address = "";
                StringBuffer messageBody = new StringBuffer();
                for (int i = 0; i < pdus.length; i++) {
                    if (pdus[i] != null) {
                        msgArray[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        if (msgArray[i] != null) {
                            address = msgArray[i].getOriginatingAddress();
                            messageBody.append(msgArray[i].getMessageBody());
                        }
                    }
                }
                String text = messageBody.toString();
                MyLogger.debug(address, text);
                BlockListener listener = new BlockListener() {
                    private boolean blocked = false;
                    @Override
                    public void stopBroadcast() {
                        if (!blocked) {
                            broadcastreceiver.abortBroadcast();
                            blocked = true;
                        }
                    }
                };
                checkBlock(context, address, text, listener);
            }
        }
    }
}
