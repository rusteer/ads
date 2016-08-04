// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.ArrayList;
import java.util.Iterator;
import android.content.Context;
import android.database.MatrixCursor;
import com.gionee.aora.market.database.LocalSearchDBHelper;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.LocalSearchNet;
import com.gionee.aora.market.util.HanziToPinyin;
import com.gionee.aora.market.util.MarketAsyncTask;

public class LocalSearchManager {
    private static class GetLocalSearchTask extends MarketAsyncTask<Context, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(Context acontext[]) {
            return Boolean.valueOf(LocalSearchNet.getLocalSearchData(acontext[0]));
        }
        @Override
        protected void onPostExecute(Boolean boolean1) {
            super.onPostExecute(boolean1);
            if (!boolean1.booleanValue()) ;
        }
    }
    public static void getLocalSearchData(Context context) {
        new GetLocalSearchTask().doExecutor(new Context[] { context });
    }
    public static MatrixCursor getNetSearchData(Context context, String s) {
        ArrayList arraylist = LocalSearchNet.searchKeyWordAtNet(context, s);
        if (arraylist == null) return null;
        MatrixCursor matrixcursor = new MatrixCursor(LocalSearchDBHelper.columNames, arraylist.size());
        Iterator iterator = arraylist.iterator();
        int i = 0;
        AppInfo appinfo;
        android.database.MatrixCursor.RowBuilder rowbuilder;
        for (; iterator.hasNext(); rowbuilder.add(appinfo.getPackageName())) {
            appinfo = (AppInfo) iterator.next();
            i++;
            rowbuilder = matrixcursor.newRow();
            rowbuilder.add(Integer.valueOf(i));
            rowbuilder.add(appinfo.getSoftId());
            rowbuilder.add(appinfo.getName());
            String as[] = HanziToPinyin.getPinYinAndShengMu(appinfo.getName());
            rowbuilder.add(as[0]);
            rowbuilder.add(as[1]);
            rowbuilder.add(appinfo.getDownload_region());
            rowbuilder.add(Integer.valueOf(appinfo.getUpdateSoftSize()));
        }
        return matrixcursor;
    }
    public LocalSearchManager() {}
}
