// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.aora.base.util.DLog;

public class DBHelp extends SQLiteOpenHelper {
    public static class Columns implements BaseColumns {
        public static final String CUR_STATE = "cur_state";
        public static final String CUR_VERSION_CODE = "cur_version_code";
        public static final String CUR_VERSION_NAME = "cur_version_name";
        public static final String DIFFERENCE_UPGRADE = "difference_upgrade";
        public static final String DOWNLOAD_URL = "download_url";
        public static final String ICON_URL = "icon_url";
        public static final String IS_INLAY = "is_inlay";
        public static final String IS_SHOW_INSTALLED_LIST = "is_show_installed_list";
        public static final String NAME = "name";
        public static final String NEW_UPDATE_INFOS = "new_update_infos";
        public static final String PACKAGE_NAME = "package_name";
        public static final String PROMPT_UPGREADE = "prompt_upgreade";
        public static final String REL_APK_URL = "rel_apk_url";
        public static final String SAME_SIGN = "same_sign";
        public static final String SIGN = "sign";
        public static final String SOFT_ID = "soft_id";
        public static final String TABLE_NAME = "softwares";
        public static final String UPDATE_APK_SIZE = "update_apk_size";
        public static final String UPDATE_PERCENT = "update_percent";
        public static final String UPDATE_SOFT_SIZE = "update_soft_size";
        public static final String UPDATE_VERSION_NAME = "update_version_name";
        public Columns() {}
    }
    private static final String CREATE_SOFTWARES_TABLE = "create table if not exists softwares (_id integer primary key autoincrement, name text, package_name text, soft_id text, cur_version_code integer, cur_version_name text,update_version_name text,icon_url text,download_url text,update_percent text,new_update_infos text,update_soft_size integer,update_apk_size integer,cur_state integer,same_sign bit,is_inlay bit,is_show_installed_list bit,sign text,rel_apk_url text,difference_upgrade bit,prompt_upgreade bit);";
    private static final String CREATE_UPDATE_COUNT_TABLE = "create table if not exists miss_infos (_id integer primary key autoincrement, miss integer);";
    private static final String DATABASE_NAME = "SoftwaresDB";
    private static final int DATABASE_VERSION = 10;
    private static final String DELETE_SOFTWARE_TABLE = "drop table softwares";
    private static final String DELETE_UPDATE_COUNT_TABLE = "drop table miss_infos;";
    private static final String TAG = "DBHelp";
    public DBHelp(Context context) {
        super(context, "SoftwaresDB", null, 10);
    }
    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        sqlitedatabase
                .execSQL("create table if not exists softwares (_id integer primary key autoincrement, name text, package_name text, soft_id text, cur_version_code integer, cur_version_name text,update_version_name text,icon_url text,download_url text,update_percent text,new_update_infos text,update_soft_size integer,update_apk_size integer,cur_state integer,same_sign bit,is_inlay bit,is_show_installed_list bit,sign text,rel_apk_url text,difference_upgrade bit,prompt_upgreade bit);");
        sqlitedatabase.execSQL("create table if not exists miss_infos (_id integer primary key autoincrement, miss integer);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
        sqlitedatabase.execSQL("drop table softwares");
        sqlitedatabase
                .execSQL("create table if not exists softwares (_id integer primary key autoincrement, name text, package_name text, soft_id text, cur_version_code integer, cur_version_name text,update_version_name text,icon_url text,download_url text,update_percent text,new_update_infos text,update_soft_size integer,update_apk_size integer,cur_state integer,same_sign bit,is_inlay bit,is_show_installed_list bit,sign text,rel_apk_url text,difference_upgrade bit,prompt_upgreade bit);");
        try {
            sqlitedatabase.execSQL("drop table miss_infos;");
        } catch (Exception exception) {
            DLog.i("lilijun", "\u8981\u5220\u9664\u7684\u8868\u4E0D\u5B58\u5728\uFF01\uFF01");
            DLog.e("DBHelp", "onUpgrade()#exception", exception);
        }
        DLog.i("lilijun", "\u521B\u5EFA\u4E86\u66F4\u65B0\u6570\u91CF\u8868\uFF01\uFF01");
        sqlitedatabase.execSQL("create table if not exists miss_infos (_id integer primary key autoincrement, miss integer);");
        DLog.i("DBHelp", new StringBuilder().append("oldVersion=").append(i).append(", newVersion=").append(j).toString());
    }
}
