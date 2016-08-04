// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.evaluation.EvaluationDetailsActivity;
import com.gionee.aora.market.gui.exercise.ExerciseDetailsActivity;
import com.gionee.aora.market.gui.game.ClassifyDetailActivity;
import com.gionee.aora.market.gui.lenjoy.LenjoyActivity;
import com.gionee.aora.market.gui.special.*;
import com.gionee.aora.market.module.*;
import java.util.List;

public class BannerstartUtil
{

    public BannerstartUtil()
    {
    }

    public static void startBannerDetails(RecommendAdInfo recommendadinfo, Context context, DataCollectInfo datacollectinfo)
    {
        if(recommendadinfo == null)
            return;
        int i = recommendadinfo.getType();
        datacollectinfo.setType((new StringBuilder()).append(i).append("").toString());
        switch(i)
        {
        default:
            return;

        case 1: // '\001'
            IntroductionDetailActivity.startIntroductionActivityForSoftId(context, (String)recommendadinfo.getContent(), datacollectinfo);
            return;

        case 2: // '\002'
            Intent intent4 = new Intent(context,  SpecialInfomationActivity.class);
            intent4.putExtra("specialId", recommendadinfo.getId());
            intent4.putExtra("vid", recommendadinfo.getId());
            intent4.putExtra("DATACOLLECT_INFO", datacollectinfo);
            context.startActivity(intent4);
            return;

        case 3: // '\003'
            Intent intent3 = new Intent("android.intent.action.VIEW");
            intent3.setData(Uri.parse((String)recommendadinfo.getContent()));
            context.startActivity(intent3);
            DataCollectInfo datacollectinfo1 = datacollectinfo.clone();
            datacollectinfo1.setAction("9");
            String as[] = new String[2];
            as[0] = "vid";
            as[1] = recommendadinfo.getId();
            DataCollectManager.addRecord(datacollectinfo1, as);
            return;

        case 4: // '\004'
            EvaluationDetailsActivity.startEvaluationDetailsActivity(context, (EvaluatInfo)recommendadinfo.getContent(), datacollectinfo);
            return;

        case 5: // '\005'
            ExerciseDetailsActivity.startExerciseDetailsActivity(context, (EvaluatInfo)recommendadinfo.getContent(), datacollectinfo);
            return;

        case 6: // '\006'
            Intent intent2 = new Intent(context,  IndividuationActivity.class);
            intent2.putExtra("specialId", recommendadinfo.getId());
            intent2.putExtra("NAME", recommendadinfo.getName());
            intent2.putExtra("INTRO", recommendadinfo.getDescription());
            intent2.putExtra("DATACOLLECT_INFO", datacollectinfo);
            context.startActivity(intent2);
            return;

        case 7: // '\007'
            Intent intent1 = new Intent(context,  ExerciseInfomationActivity.class);
            intent1.putExtra("skipUrl", (String)recommendadinfo.getContent());
            intent1.putExtra("vid", recommendadinfo.getId());
            intent1.putExtra("NAME", recommendadinfo.getName());
            intent1.putExtra("DATACOLLECT_INFO", datacollectinfo);
            context.startActivity(intent1);
            return;

        case 8: // '\b'
            LenjoyInfo lenjoyinfo = (LenjoyInfo)recommendadinfo.getContent();
            if(lenjoyinfo != null)
            {
                LenjoyActivity.startLenjoyActivity(context, datacollectinfo, lenjoyinfo.getId());
                return;
            } else
            {
                LenjoyActivity.startLenjoyActivity(context, datacollectinfo, 0);
                return;
            }

        case 9: // '\t'
            Intent intent = new Intent(context,  SuperSpecialActivity.class);
            intent.putExtra("skipUrl", (String)recommendadinfo.getContent());
            intent.putExtra("vid", recommendadinfo.getId());
            intent.putExtra("NAME", recommendadinfo.getName());
            intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
            context.startActivity(intent);
            return;
        }
    }

    public static void startClassify(Context context, AppInfo appinfo, DataCollectInfo datacollectinfo)
    {
        boolean flag = true;
        String as[] = new String[1 + appinfo.getClassifyThree().size()];
        String as1[] = new String[1 + appinfo.getClassifyThree().size()];
        as[0] = (new StringBuilder()).append(appinfo.getClassifyId()).append("").toString();
        as1[0] = "\u5168\u90E8";
        for(int i = ((flag) ? 1 : 0); i < 1 + appinfo.getClassifyThree().size(); i++)
        {
            as[i] = ((CategoryInfo)appinfo.getClassifyThree().get(i - 1)).getSortId();
            as1[i] = ((CategoryInfo)appinfo.getClassifyThree().get(i - 1)).getSortName();
        }

        int j;
        if(appinfo.getClassifyThreeId() != 0)
        {
            int k = 0;
            j = 0;
            for(; k < appinfo.getClassifyThree().size(); k++)
                if((new StringBuilder()).append(appinfo.getClassifyThreeId()).append("").toString().equals(((CategoryInfo)appinfo.getClassifyThree().get(k)).getSortId()))
                    j = k + 1;

        } else
        {
            j = 0;
        }
        datacollectinfo.setPosition("3");
        datacollectinfo.setAction("");
        datacollectinfo.setType("");
        datacollectinfo.setModel("3");
        Intent intent = new Intent(context,  ClassifyDetailActivity.class);
        intent.putExtra("defaultPage", j);
        intent.putExtra("CLASSIFY_ID", as);
        intent.putExtra("CLASSIFY_NAME", as1);
        if(appinfo.getClassify() != 1)
            flag = false;
        intent.putExtra("IS_GAME", flag);
        intent.putExtra("TITLE", appinfo.getClassifyName());
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        context.startActivity(intent);
    }
}
