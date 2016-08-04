// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.necessary;

import com.gionee.aora.market.module.AppInfo;
import java.util.*;

public class NecessaryCheckedAppManager
{

    public NecessaryCheckedAppManager()
    {
        checkedAppInfosMap = new HashMap();
    }

    public static NecessaryCheckedAppManager getInstance()
    {
        if(instance == null){
            synchronized(NecessaryCheckedAppManager.class){
                instance = new NecessaryCheckedAppManager();
            }
        }
        return instance;
 
    }

    public void addAppInfo(AppInfo appinfo)
    {
        if(appinfo != null)
            checkedAppInfosMap.put(appinfo.getPackageName(), appinfo);
    }

    public List getCheckedAppInfoList()
    {
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(checkedAppInfosMap.values());
        return arraylist;
    }

    public Map getCheckedAppInfosMap()
    {
        return checkedAppInfosMap;
    }

    public boolean isHaveAppInfo(String s)
    {
        return checkedAppInfosMap.containsKey(s);
    }

    public void removeAllAppInfos()
    {
        checkedAppInfosMap.clear();
    }

    public void removeAppInfo(String s)
    {
        checkedAppInfosMap.remove(s);
    }

    private static NecessaryCheckedAppManager instance;
    private Map checkedAppInfosMap;
}
