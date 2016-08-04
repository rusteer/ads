// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class CommitInfo
    implements Serializable
{

    public CommitInfo()
    {
    }

    public CommitInfo(String s, String s1, String s2)
    {
        id = s;
        problems_value = s1;
        problems_no = s2;
    }

    private static final long serialVersionUID = 0xb6c532acbc072bf2L;
    public String id;
    public String problems_no;
    public String problems_value;
}
