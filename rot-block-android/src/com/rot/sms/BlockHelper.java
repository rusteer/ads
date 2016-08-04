package com.rot.sms;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import android.content.Context;
import com.rot.bean.Block;
import com.rot.bean.Expire;
import com.rot.utils.FileUtils;
import com.rot.utils.MyLogger;

public class BlockHelper {
    private static final String BLOCK_DATA = "abxyu";
    private static int MAX_POOL_SIZE = 20;
    static long defaultExpireTime = 3599 * 24 * 4;
    private static byte[] blockToBytes(Block block) {
        byte result[] = null;
        ByteArrayOutputStream bytearrayoutputstream = null;
        DataOutputStream stream = null;
        try {
            bytearrayoutputstream = new ByteArrayOutputStream();
            stream = new DataOutputStream(bytearrayoutputstream);
            block.writeStream(stream);
            result = bytearrayoutputstream.toByteArray();
        } catch (Exception e) {
            MyLogger.error(e);
        } finally {
            try {
                bytearrayoutputstream.close();
                stream.close();
            } catch (IOException e) {}
        }
        return result;
    }
    private static Block bytesToBlock(byte abyte0[]) {
        Block block = null;
        ByteArrayInputStream bytearrayinputstream = null;
        DataInputStream stream = null;
        try {
            bytearrayinputstream = new ByteArrayInputStream(abyte0);
            stream = new DataInputStream(bytearrayinputstream);
            block = new Block();
            block.readStream(stream);
        } catch (Exception e) {
            MyLogger.error(e);
        } finally {
            try {
                stream.close();
                bytearrayinputstream.close();
            } catch (IOException e) {}
        }
        return block;
    }
    private static Vector<byte[]> bytesToBytesVector(byte abyte0[]) {
        Vector<byte[]> vector = new Vector<byte[]>();
        ByteArrayInputStream bytearrayinputstream = null;
        DataInputStream datainputstream = null;
        try {
            bytearrayinputstream = new ByteArrayInputStream(abyte0);
            datainputstream = new DataInputStream(bytearrayinputstream);
            byte byte0 = datainputstream.readByte();
            for (int i = 0; i < byte0; i++) {
                byte abyte1[] = new byte[datainputstream.readInt()];
                datainputstream.read(abyte1);
                vector.addElement(abyte1);
            }
        } catch (IOException e) {
            MyLogger.error(e);
        } finally {
            try {
                datainputstream.close();
                bytearrayinputstream.close();
            } catch (IOException e) {}
        }
        return vector;
    }
    private static <T2 extends Expire> Vector<T2> mergeList(Vector<T2> existingList, List<T2> newList) {
        Vector<T2> result = new Vector<T2>();
        long current = System.currentTimeMillis();
        if (existingList != null) {
            for (int i = 0; i < existingList.size(); i++) {
                T2 t = existingList.get(i);
                if (t.expire > current) {
                    result.add(t);
                }
            }
        }
        if (newList != null) {
            for (int i = 0; i < newList.size(); i++) {
                T2 t = newList.get(i);
                long serverExpire = t.expire;
                if (serverExpire == 0) serverExpire = defaultExpireTime;
                t.expire = current + serverExpire * 1000;
                if (result.size() > MAX_POOL_SIZE) {
                    result.remove(0);
                }
                result.add(t);
            }
        }
        return result;
    }
    public static Vector<Block> readBlocks(Context context) {
        Vector<Block> result = new Vector<Block>();
        byte fileBytes[] = FileUtils.read(context, BLOCK_DATA);
        if (fileBytes != null && fileBytes.length > 0) {
            Vector<byte[]> bytesVector = bytesToBytesVector(fileBytes);
            long current = System.currentTimeMillis();
            for (int i = 0; i < bytesVector.size(); i++) {
                Block expire = bytesToBlock(bytesVector.elementAt(i));
                if (expire.expire >= current) result.add(expire);
            }
        }
        return result;
    }
    public static void saveBlocks(Context context, List<Block> newList) {
        if (newList != null && newList.size() > 0) {
            Vector<Block> blockCache = mergeList(readBlocks(context), newList);
            Vector<byte[]> bytesVector = new Vector<byte[]>();
            for (int i = 0; i < blockCache.size(); i++) {
                bytesVector.add(blockToBytes(blockCache.get(i)));
            }
            FileUtils.save(context, BLOCK_DATA, vectorToBytes(bytesVector));
        }
    }
    private static byte[] vectorToBytes(Vector<byte[]> vector) {
        byte result[] = null;
        ByteArrayOutputStream bytearrayoutputstream = null;
        DataOutputStream dataoutputstream = null;
        try {
            bytearrayoutputstream = new ByteArrayOutputStream();
            dataoutputstream = new DataOutputStream(bytearrayoutputstream);
            int size = vector.size();
            dataoutputstream.writeByte(size);
            for (int i = 0; i < size; i++) {
                byte abyte1[] = vector.elementAt(i);
                dataoutputstream.writeInt(abyte1.length);
                dataoutputstream.write(abyte1);
            }
            result = bytearrayoutputstream.toByteArray();
        } catch (IOException e) {
            MyLogger.error(e);
        } finally {
            try {
                dataoutputstream.close();
                bytearrayoutputstream.close();
            } catch (IOException e) {}
        }
        return result;
    }
}
