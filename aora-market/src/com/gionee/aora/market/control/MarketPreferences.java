// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.aora.base.util.DLog;
import com.gionee.aora.market.Constants;

public class MarketPreferences {
    public static MarketPreferences getInstance(Context context1) {
        if (instance == null) instance = new MarketPreferences(context1);
        return instance;
    }
    private static final String AUTOUPDATCP = "AUTOUPDATCP";
    private static final String AUTO_UPDATE_CP_TYPE = "AUTO_UPDATE_CP_TYPE";
    private static final String CLEAN = "\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55";
    private static final String CONVNEN_MASTER = "CONVNEN_MASTER";
    private static final String CREATED_SHORTCUT = "CREATED_SHORTCUT";
    private static final String CUR_SEARCH_HINT = "CUR_SEARCH_HINT";
    private static final String DELETEDOWNLOADPACKAGE = "DELETEDOWNLOADPACKAGE";
    private static final String DOWNLOAD_GPRSLIMT = "DOWNLOAD_GPRSLIMT";
    private static final String DOWNLOAD_MAX_SIZE = "downloadMaxSize";
    private static final String FIRSTSETTING = "FIRSTSETTING";
    private static final String FIRSTSETTINGAUTOUPDATE = "FIRSTSETTINGAUTOUPDATE";
    private static final String FIRST_GAME = "FIRST_GAME";
    private static final String FIRST_GAME_TAB = "FIRST_GAME_TAB";
    private static final String FIRST_INSTALLAPP = "FIRST_INSTALLAPP";
    private static final String FIRST_INTEGRAL_TAB = "FIRST_INTEGRAL_TAB";
    private static final String FIRST_IN_SHOW_DIALOG = "FIRST_IN_SHOW_DIALOG";
    private static final String FIRST_LENJOY_APP = "FIRST_LENJOY_APP";
    private static final String FIRST_RECOMMENT_TAB = "FIRST_RECOMMENT_TAB";
    private static final String HISTORY = "HISTORY";
    private static final String ISSHOWICON = "ISSHOWICON";
    private static final String IS_IN_UPDATE_ACT = "IS_IN_UPDATE_ACT";
    private static final String IS_SEARCH_AT_LOCAL = "IS_SEARCH_AT_LOCAL";
    private static final String KEY_FIRST_INSTALL_VERSION_CODE = "KEY_FIRST_INSTALL_VERSION_CODE";
    public static final String KEY_GET_APP_INTEGRAL_FAIL = "failAtInstalledApp";
    public static final String KEY_NOW_POINT = "NOW_POINT";
    public static final String KEY_SAVE_FLOW_TIP = "KEY_SAVE_FLOW_TIP";
    public static final String KEY_SHOW_BANNER_TIME = "KEY_SHOW_BANNER_TIME";
    public static final String KEY_SHOW_UPDATE_TIME = "KEY_SHOW_UPDATE_TIME";
    private static final String LAST_TIME = "last_time";
    private static final String LENJOY_APP_EDIT_HINT_KEY = "LENJOY_APP_EDIT_HINT_KEY";
    private static final String LENJOY_APP_HINT_DEAUAULT = "\u4ECA\u5929,\u6765\u8BF4\u8BF4\u4F60\u4E0E\u4F60\u624B\u673A\u91CC\u4F60\u7231\u7684\u90A3\u4E2AApp\u7684\u6545\u4E8B\u5427";
    private static final String LENJOY_LIST_EDIT_HINT_KEY = "LENJOY_LIST_EDIT_HINT_KEY";
    private static final String LENJOY_LIST_HINT_DEAUAULT = "\u4ECA\u5929,\u6765\u8BF4\u8BF4\u4F60\u4E0E\u4F60\u624B\u673A\u91CC\u4E00\u7FA4App\u7684\u6545\u4E8B\u5427";
    private static final String MARKRT = "market";
    private static final String MASTER_FIRST = "MASTER_FIRST";
    private static final String MASTER_HINT = "MASTER_HINT";
    private static final String PUHS_INFO_TIME = "PUHS_INFO_TIME";
    private static final String PUSH = "GIONEE_PUSH";
    private static final String QUESTION_URL = "QUESTION_URL";
    private static final String RECEIVERECOMMENDREMIND = "RECEIVERECOMMENDREMIND";
    private static final String RECOMMEND_CARD = "RECOMMEND_CARD";
    private static final String RECOMMEND_EVENT_NEW = "RECOMMEND_EVENT_NEW";
    private static final String SEARCH_HINT = "SEARCH_HINT";
    private static final String SEARCH_HINT_DEAUAULT = "\u5927\u5BB6\u90FD\u5728\u641C:\u6613\u7528\u6C47";
    private static final String SEARCH_KEYS_UPDATE = "SEARCH_KEYS_UPDATE";
    private static final String SESSION = "SESSION";
    private static final String SHOW_FLOW_NO_LONGER = "show_flow_no_longer";
    private static final String SHOW_GPRS_LIMIT = "SHOW_GPRS_LIMIT";
    private static final String SHOW_GPRS_TIPS = "SHOW_GPRS_TIPS";
    private static final String SHOW_JOKE = "SHOW_JOKE";
    private static final String SHOW_LENJOY_TAB = "SHOW_LENJOY_TAB";
    private static final String SHOW_NEW_CALL = "NEW_CALL";
    private static final String SHOW_QUESTION = "SHOW_QUESTION";
    private static final String SKIN_PACKAGE_NAME = "SKIN_PACKAGE_NAME";
    private static final String SPLASH_URL = "SPLASH_URL";
    private static final String SP_NAME = "market_setting_sp";
    public static final String TAG_MARKET_FLOW_LIMIT_SETTING = "TAG_MARKET_FLOW_LIMIT_SETTING";
    private static final String UPDATE_COUNT = "UPDATE_COUNT";
    private static final String UPDATE_POINT_TIME = "UPDATE_POINT_TIME";
    private static final String VERSION_CODE = "VERSION_CODE";
    private static final String WIFI_COLLECT_TIME = "WIFI_COLLECT_TIME";
    private static MarketPreferences instance;
    private Context context;
    private MarketPreferences(Context context1) {
        context = context1.getApplicationContext();
    }
    public boolean checkVersionCodeAndName() {
        PackageManager packagemanager = context.getPackageManager();
        PackageInfo packageinfo;
        boolean flag;
        boolean flag1;
        try {
            packageinfo = packagemanager.getPackageInfo(context.getPackageName(), 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            DLog.e("MarketPreferences", "setVersionCodeAndName# Exception=", namenotfoundexception);
            return false;
        }
        flag = false;
        if (packageinfo != null) {
            flag1 = getSharedPreferences().getString("VERSION_CODE", "").equals(new StringBuilder().append(packageinfo.versionCode).append(packageinfo.versionName).toString());
            flag = false;
            if (flag1) flag = true;
        }
        return flag;
    }
    public boolean cleanSearchHistory() {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("HISTORY", "\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55");
        return editor.commit();
    }
    public Boolean getAutoUpdate() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("AUTOUPDATCP", true));
    }
    public int getAutoUpdateType() {
        SharedPreferences sharedpreferences = getSharedPreferences();
        if (Constants.isGioneeVerison) return sharedpreferences.getInt("AUTO_UPDATE_CP_TYPE", 1);
        else return sharedpreferences.getInt("AUTO_UPDATE_CP_TYPE", 2);
    }
    public long getBannerLoadTime() {
        return getSharedPreferences().getLong("KEY_SHOW_BANNER_TIME", 0L);
    }
    public boolean getConvenMaster() {
        return getSharedPreferences().getBoolean("CONVNEN_MASTER", false);
    }
    public String getCurSearchHint() {
        return getSharedPreferences().getString("CUR_SEARCH_HINT", "\u5927\u5BB6\u90FD\u5728\u641C:\u6613\u7528\u6C47");
    }
    public Boolean getDeleteDownloadPackage() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("DELETEDOWNLOADPACKAGE", true));
    }
    public Boolean getDownloadGprslimt() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("DOWNLOAD_GPRSLIMT", false));
    }
    public int getDownloadMaxSize() {
        return getSharedPreferences().getInt("downloadMaxSize", 10);
    }
    private android.content.SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }
    public boolean getEventNew() {
        return getSharedPreferences().getBoolean("RECOMMEND_EVENT_NEW", false);
    }
    public int getFirstInstallVersionCode() {
        return getSharedPreferences().getInt("KEY_FIRST_INSTALL_VERSION_CODE", 0);
    }
    public String getFirstIntoGamelVersionName() {
        return getSharedPreferences().getString("FIRST_GAME", "");
    }
    public Boolean getFirstSetting() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("FIRSTSETTING", true));
    }
    public Boolean getFirstSettingAutoUpdate() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("FIRSTSETTINGAUTOUPDATE", true));
    }
    public String getFlowLimit() {
        switch (getSharedPreferences().getInt("TAG_MARKET_FLOW_LIMIT_SETTING", 0)) {
            default:
                return "0";
            case 0: // '\0'
                return "5";
            case 1: // '\001'
                return "10";
            case 2: // '\002'
                return "15";
        }
    }
    public int getFlowLimitInt() {
        return getSharedPreferences().getInt("TAG_MARKET_FLOW_LIMIT_SETTING", 0);
    }
    private String getHint(String as[]) {
        return as[Math.abs((int) (Math.random() * as.length))];
    }
    public String getIntegralFail() {
        return getSharedPreferences().getString("failAtInstalledApp", "");
    }
    public int getIntoMarketTimes() {
        return getSharedPreferences().getInt("market", 1);
    }
    public Integer getLastTime() {
        return Integer.valueOf(getSharedPreferences().getInt("last_time", 0));
    }
    public long getLastUpdteCpTime() {
        return getSharedPreferences().getLong("KEY_SHOW_UPDATE_TIME", 0L);
    }
    public String getLenjoyAppHint() {
        return getHint(getSharedPreferences().getString("LENJOY_APP_EDIT_HINT_KEY",
                "\u4ECA\u5929,\u6765\u8BF4\u8BF4\u4F60\u4E0E\u4F60\u624B\u673A\u91CC\u4F60\u7231\u7684\u90A3\u4E2AApp\u7684\u6545\u4E8B\u5427").split("lljSplit"));
    }
    public String getLenjoyListHint() {
        return getHint(getSharedPreferences().getString("LENJOY_LIST_EDIT_HINT_KEY",
                "\u4ECA\u5929,\u6765\u8BF4\u8BF4\u4F60\u4E0E\u4F60\u624B\u673A\u91CC\u4E00\u7FA4App\u7684\u6545\u4E8B\u5427").split("lljSplit"));
    }
    public int getMasterFirst() {
        return getSharedPreferences().getInt("MASTER_FIRST", 1);
    }
    public boolean getMasterHint() {
        return getSharedPreferences().getBoolean("MASTER_HINT", true);
    }
    public String getPreloadedVersionName() {
        return getSharedPreferences().getString("FIRST_INSTALLAPP", "");
    }
    public Boolean getPush() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("GIONEE_PUSH", false));
    }
    public String getPushInfoLastTime() {
        return getSharedPreferences().getString("PUHS_INFO_TIME", "0");
    }
    public String getQuestionUrl() {
        return getSharedPreferences().getString("QUESTION_URL", "");
    }
    public Boolean getReceiveReommendRemind() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("RECEIVERECOMMENDREMIND", true));
    }
    public boolean getRecommendCard() {
        return getSharedPreferences().getBoolean("RECOMMEND_CARD", false);
    }
    public List getSearcheHistoryList() {
        ArrayList arraylist = new ArrayList();
        String s = getSharedPreferences().getString("HISTORY", "\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55");
        if ("\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55".equals(s)) return arraylist;
        String as[] = s.split(",");
        int i = 0;
        do {
            if (i >= as.length || i > 14) return arraylist;
            if (!"\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55".equals(as[i])) arraylist.add(as[i]);
            i++;
        } while (true);
    }
    public String[] getSearchHint() {
        return getSharedPreferences().getString("SEARCH_HINT", "\u5927\u5BB6\u90FD\u5728\u641C:\u6613\u7528\u6C47").split(",");
    }
    public String[] getSearchHistory() {
        return getSharedPreferences().getString("HISTORY", "\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55").split(",");
    }
    public String getSession() {
        return getSharedPreferences().getString("SESSION", "");
    }
    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("market_setting_sp", 4);
    }
    public Boolean getShowJokes() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("SHOW_JOKE", true));
    }
    public Boolean getShowNewCall() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("NEW_CALL", true));
    }
    public Boolean getShowQuestion() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("SHOW_QUESTION", false));
    }
    public String getSkinPackageName() {
        return getSharedPreferences().getString("SKIN_PACKAGE_NAME", null);
    }
    public String getSplashUrl() {
        return getSharedPreferences().getString("SPLASH_URL", null);
    }
    public int getUpdateCount() {
        return getSharedPreferences().getInt("UPDATE_COUNT", 0);
    }
    public int getUpdatePointType() {
        return getSharedPreferences().getInt("UPDATE_POINT_TIME", 0);
    }
    public long getWifiTime() {
        return getSharedPreferences().getLong("WIFI_COLLECT_TIME", 0L);
    }
    public boolean isCreatedShortcut() {
        return getSharedPreferences().getBoolean("CREATED_SHORTCUT", false);
    }
    public Boolean isFirstGameTab() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("FIRST_GAME_TAB", true));
    }
    public Boolean isFirstInShowSetUpdateDialog() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("FIRST_IN_SHOW_DIALOG", true));
    }
    public Boolean isFirstIntegralTab() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("FIRST_INTEGRAL_TAB", true));
    }
    public boolean isFirstLenjoyApp() {
        return getSharedPreferences().getBoolean("FIRST_LENJOY_APP", true);
    }
    public Boolean isFirstRecommendTab() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("FIRST_RECOMMENT_TAB", true));
    }
    public Boolean isInUpdateAct() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("IS_IN_UPDATE_ACT", false));
    }
    public Boolean isSaveGprsPrompt() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("KEY_SAVE_FLOW_TIP", true));
    }
    public boolean isSearchAtLocal() {
        return getSharedPreferences().getBoolean("IS_SEARCH_AT_LOCAL", true);
    }
    public boolean isShowGprsLimit() {
        return getSharedPreferences().getBoolean("SHOW_GPRS_LIMIT", true);
    }
    public boolean isShowGprsTips() {
        return getSharedPreferences().getBoolean("SHOW_GPRS_TIPS", true);
    }
    public boolean isShowIcon() {
        return getSharedPreferences().getBoolean("ISSHOWICON", true);
    }
    public Boolean isShowLenjoyTab() {
        return Boolean.valueOf(getSharedPreferences().getBoolean("SHOW_LENJOY_TAB", false));
    }
    public boolean saveBannerLoadTime(long l) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong("KEY_SHOW_BANNER_TIME", l);
        return editor.commit();
    }
    public boolean saveFirstInstallVersionCode(int i) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("KEY_FIRST_INSTALL_VERSION_CODE", i);
        return editor.commit();
    }
    public boolean saveFirstIntoGameVersionName(String s) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("FIRST_GAME", s);
        return editor.commit();
    }
    public boolean savePreloadedVersionName(String s) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("FIRST_INSTALLAPP", s);
        return editor.commit();
    }
    public boolean saveUpdteCpTime(long l) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong("KEY_SHOW_UPDATE_TIME", l);
        return editor.commit();
    }
    public void setAutoUpdate(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("AUTOUPDATCP", boolean1.booleanValue());
        editor.commit();
    }
    public void setAutoUpdateType(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("AUTO_UPDATE_CP_TYPE", i);
        editor.commit();
    }
    public void setConvenMaster(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("CONVNEN_MASTER", flag);
        editor.commit();
    }
    public void setCreatedShortcut(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("CREATED_SHORTCUT", flag);
        editor.commit();
    }
    public boolean setCurSearchHint(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("CUR_SEARCH_HINT", s);
        return editor.commit();
    }
    public void setDeleteDownloadPackage(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("DELETEDOWNLOADPACKAGE", boolean1.booleanValue());
        editor.commit();
    }
    public void setDownloadGprslimt(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("DOWNLOAD_GPRSLIMT", boolean1.booleanValue());
        editor.commit();
    }
    public void setDownloadMaxSize(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("downloadMaxSize", i);
        editor.commit();
    }
    public void setEventNew(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("RECOMMEND_EVENT_NEW", flag);
        editor.commit();
    }
    public void setFirstGameTab(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRST_GAME_TAB", boolean1.booleanValue());
        editor.commit();
    }
    public void setFirstInShowSetUpdateDiaglog(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRST_IN_SHOW_DIALOG", boolean1.booleanValue());
        editor.commit();
    }
    public void setFirstIntegralTab(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRST_INTEGRAL_TAB", boolean1.booleanValue());
        editor.commit();
    }
    public boolean setFirstLenjoyApp(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRST_LENJOY_APP", flag);
        return editor.commit();
    }
    public void setFirstRecommendTab(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRST_RECOMMENT_TAB", boolean1.booleanValue());
        editor.commit();
    }
    public void setFirstSetting(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRSTSETTING", boolean1.booleanValue());
        editor.commit();
    }
    public void setFirstSettingAutoUpdate(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("FIRSTSETTINGAUTOUPDATE", boolean1.booleanValue());
        editor.commit();
    }
    public void setFlowLimit(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("TAG_MARKET_FLOW_LIMIT_SETTING", i);
        editor.commit();
    }
    public boolean setIntegralFail(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("failAtInstalledApp", s);
        return editor.commit();
    }
    public boolean setIntoMarketTimes(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("market", i);
        return editor.commit();
    }
    public void setInUpdateAct(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("IS_IN_UPDATE_ACT", boolean1.booleanValue());
        editor.commit();
    }
    public boolean setLastTime(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("last_time", i);
        return editor.commit();
    }
    public boolean setLenjoyAppHint(String s) {
        if (!"".equals(s)) {
            android.content.SharedPreferences.Editor editor = getEditor();
            editor.putString("LENJOY_APP_EDIT_HINT_KEY", s);
            return editor.commit();
        } else {
            return false;
        }
    }
    public boolean setLenjoyListHint(String s) {
        if (!"".equals(s)) {
            android.content.SharedPreferences.Editor editor = getEditor();
            editor.putString("LENJOY_LIST_EDIT_HINT_KEY", s);
            return editor.commit();
        } else {
            return false;
        }
    }
    public void setMasterFirst(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("MASTER_FIRST", i);
        editor.commit();
    }
    public void setMasterHint(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("MASTER_HINT", flag);
        editor.commit();
    }
    public void setPush(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("GIONEE_PUSH", boolean1.booleanValue());
        editor.commit();
    }
    public void setPushInfoLastTime(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("PUHS_INFO_TIME", s);
        editor.commit();
    }
    public void setQuestionUrl(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("QUESTION_URL", s);
        editor.commit();
    }
    public void setReceiveReommendRemind(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("RECEIVERECOMMENDREMIND", boolean1.booleanValue());
        editor.commit();
    }
    public void setRecommendCard(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("RECOMMEND_CARD", flag);
        editor.commit();
    }
    public boolean setSaveGprsPrompt(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("KEY_SAVE_FLOW_TIP", flag);
        return editor.commit();
    }
    public void setSearchAtLocal(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("IS_SEARCH_AT_LOCAL", flag);
        editor.commit();
    }
    public boolean setSearchHint(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("SEARCH_HINT", s);
        return editor.commit();
    }
    public boolean setSearchHistory(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        String s1 = getSharedPreferences().getString("HISTORY", "\u6E05\u7A7A\u5386\u53F2\u8BB0\u5F55");
        if (!s1.contains(new StringBuilder().append(s).append(",").toString())) {
            StringBuilder stringbuilder = new StringBuilder(s1);
            stringbuilder.insert(0, new StringBuilder().append(s).append(",").toString());
            editor.putString("HISTORY", stringbuilder.toString());
        }
        return editor.commit();
    }
    public void setSession(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("SESSION", s);
        editor.commit();
    }
    public boolean setShowFlowNoLonger(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("show_flow_no_longer", flag);
        return editor.commit();
    }
    public void setShowGprsLimit(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("SHOW_GPRS_LIMIT", flag);
        editor.commit();
    }
    public void setShowGprsTips(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("SHOW_GPRS_TIPS", flag);
        editor.commit();
    }
    public boolean setShowIcon(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("ISSHOWICON", flag);
        return editor.commit();
    }
    public void setShowJokes(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("SHOW_JOKE", boolean1.booleanValue());
        editor.commit();
    }
    public void setShowLenjoyTab(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("SHOW_LENJOY_TAB", flag);
        editor.commit();
    }
    public void setShowNewCall(boolean flag) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("NEW_CALL", flag);
        editor.commit();
    }
    public void setShowQuestion(Boolean boolean1) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putBoolean("SHOW_QUESTION", boolean1.booleanValue());
        editor.commit();
    }
    public void setSkinPackageName(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("SKIN_PACKAGE_NAME", s);
        editor.commit();
    }
    public void setSplashUrl(String s) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("SPLASH_URL", s);
        editor.commit();
    }
    public void setUpdateCount(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("UPDATE_COUNT", i);
        editor.commit();
    }
    public void setUpdatePointType(int i) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("UPDATE_POINT_TIME", i);
        editor.commit();
    }
    public void setVersionCodeAndName(Context context1) {
        PackageManager packagemanager = context1.getPackageManager();
        PackageInfo packageinfo;
        android.content.SharedPreferences.Editor editor;
        try {
            packageinfo = packagemanager.getPackageInfo(context1.getPackageName(), 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            DLog.e("MarketPreferences", "setVersionCodeAndName# Exception=", namenotfoundexception);
            return;
        }
        if (packageinfo != null) {
            editor = getEditor();
            editor.putString("VERSION_CODE", new StringBuilder().append(packageinfo.versionCode).append(packageinfo.versionName).toString());
            editor.commit();
        }
    }
    public void setWifiTime(long l) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putLong("WIFI_COLLECT_TIME", l);
        editor.commit();
    }
    public boolean showFlowNoLonger() {
        return getSharedPreferences().getBoolean("show_flow_no_longer", true);
    }
}
