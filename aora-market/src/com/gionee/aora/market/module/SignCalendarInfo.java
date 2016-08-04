// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.integral.module.UserInfo;

public class SignCalendarInfo
{

    public SignCalendarInfo(UserInfo userinfo)
    {
label0:
        {
            isTodaySign = false;
            lastSignTime = 0L;
            continuSignDays = 0;
            takeCoinIfSign = 0;
            nowServerTime = 0L;
            beforeSignState = new int[7];
            if(userinfo != null)
            {
                if(userinfo.getDATA_ISSING() == null)
                    break label0;
                beforeSignState = userinfo.getDATA_ISSING();
                int i = beforeSignState[-1 + beforeSignState.length];
                boolean flag = false;
                if(i > 0)
                    flag = true;
                isTodaySign = flag;
                lastSignTime = userinfo.getSIGN_TIME();
                continuSignDays = userinfo.getSIGN_DAYS();
                nowServerTime = Long.valueOf(userinfo.getSING_DATA()).longValue();
                takeCoinIfSign = Integer.valueOf(userinfo.getSIGN_GOLD()).intValue();
            }
            return;
        }
        isTodaySign = false;
        lastSignTime = 0L;
        continuSignDays = 0;
        nowServerTime = System.currentTimeMillis();
        takeCoinIfSign = 0;
    }

    public int beforeSignState[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0
    };
    public int continuSignDays;
    public boolean isTodaySign;
    public long lastSignTime;
    public long nowServerTime;
    public int takeCoinIfSign;
}
