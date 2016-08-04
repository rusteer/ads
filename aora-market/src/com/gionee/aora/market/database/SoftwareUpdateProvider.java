// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.database;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import com.aora.base.util.DLog;

// Referenced classes of package com.gionee.aora.market.database:
//            DBHelp
public class SoftwareUpdateProvider extends ContentProvider {
    private static final UriMatcher MATCHER;
    public static final int SOFTWARE_UPDATES = 3;
    private static final int UPDATE = 2;
    private static final int UPDATES = 1;
    private DBHelp dbHelp;
    static {
        MATCHER = new UriMatcher(-1);
        MATCHER.addURI("com.gionee.aora.market", "miss_infos", 1);
        MATCHER.addURI("com.gionee.aora.market", "miss_infos/#", 2);
        MATCHER.addURI("com.gionee.aora.market", "softwares", 3);
    }
    public SoftwareUpdateProvider() {}
    @Override
    public int delete(Uri uri, String s, String as[]) {
        return 0;
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }
    @Override
    public Uri insert(Uri uri, ContentValues contentvalues) {
        return null;
    }
    @Override
    public boolean onCreate() {
        dbHelp = new DBHelp(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String as[], String s, String as1[], String s1) {
        DLog.i("lilijun", "\u7CFB\u7EDF\u67E5\u8BE2\u4E86\u53EF\u66F4\u65B0\u6570\u91CF\uFF01\uFF01\uFF01");
        SQLiteDatabase sqlitedatabase = dbHelp.getReadableDatabase();
        switch (MATCHER.match(uri)) {
            default:
                DLog.d("lilijun", new StringBuilder().append("Unknown URI").append(uri).toString());
                throw new IllegalArgumentException(new StringBuilder().append("Unknown URI").append(uri).toString());
            case 1: // '\001'
                return sqlitedatabase.query("miss_infos", as, s, as1, null, null, s1);
            case 2: // '\002'
                String s2 = uri.getPathSegments().get(1);
                StringBuilder stringbuilder = new StringBuilder().append("miss=").append(s2);
                String s3;
                if (!TextUtils.isEmpty(s)) s3 = new StringBuilder().append("AND(").append(s).append(')').toString();
                else s3 = "";
                sqlitedatabase.query("miss_infos", as, stringbuilder.append(s3).toString(), as1, null, null, s1);
                // fall through
            case 3: // '\003'
                return sqlitedatabase.query("softwares", as, "prompt_upgreade=0", as1, null, null, null);
        }
    }
    @Override
    public int update(Uri uri, ContentValues contentvalues, String s, String as[]) {
        return 0;
    }
}
