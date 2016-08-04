// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.recommend;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import com.aora.base.util.DLog;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

// Referenced classes of package com.gionee.aora.market.gui.recommend:
//            PersonalRecommandAdapter

public class PersonalRecommandActivity extends Activity
{

    public PersonalRecommandActivity()
    {
        pageIndex = -1;
    }

    private void enter()
    {
        Animation animation = AnimationUtils.loadAnimation(this, biz.AR.anim.float_dialog_enter_anim);
        animation.setDuration(300L);
        animation.setFillAfter(true);
        findViewById(biz.AR.id.dialog_main).startAnimation(animation);
    }

    private void exit()
    {
        Animation animation = AnimationUtils.loadAnimation(this, biz.AR.anim.float_dialog_exit_anim);
        animation.setDuration(300L);
        animation.setFillAfter(true);
        findViewById(biz.AR.id.dialog_main).startAnimation(animation);
        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            public void onAnimationEnd(Animation animation1)
            {
                finish();
            }

            public void onAnimationRepeat(Animation animation1)
            {
            }

            public void onAnimationStart(Animation animation1)
            {
            }

            
        }
);
    }

    private ArrayList getNextAppInfos()
    {
        int i = 0;
        int j = data.size() / 4;
        int k;
        int l;
        ArrayList arraylist;
        if(data.size() % 4 == 0)
            k = 0;
        else
            k = 1;
        l = k + j;
        if(pageIndex >= l - 1)
            pageIndex = 0;
        else
            pageIndex = 1 + pageIndex;
        arraylist = new ArrayList();
        for(; i < 4; i++)
            arraylist.add(data.get(i + 4 * pageIndex));

        return arraylist;
    }

    public void onBackPressed()
    {
        exit();
    }

    protected void onCreate(Bundle bundle)
    {
        ColorStateList colorstatelist=null;android.content.res.XmlResourceParser xmlresourceparser;
        requestWindowFeature(1);
        setTheme(biz.AR.style.Float_Dialog);
        super.onCreate(bundle);
        setContentView(biz.AR.layout.personal_recommed_dialog_content);
        data = (ArrayList)getIntent().getSerializableExtra("data");
        pageIndex = -1;
        xmlresourceparser = getResources().getXml(biz.AR.drawable.exit_dialog_txt_color);
        try {
            ColorStateList colorstatelist1 = ColorStateList.createFromXml(getResources(), xmlresourceparser);
              colorstatelist = colorstatelist1;
        } catch ( Exception e) {
            e.printStackTrace();
        }
//_L2:
        listview = (ListView)findViewById(biz.AR.id.listview);
        TextView textview = (TextView)findViewById(biz.AR.id.btn_1);
        textview.setTextColor(colorstatelist);
        textview.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                exit();
            }

            
        }
);
        ((TextView)findViewById(biz.AR.id.refresh_btn)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                adapter = new PersonalRecommandAdapter(PersonalRecommandActivity.this, listview, getNextAppInfos(), null);
                listview.setAdapter(adapter);
            }

             
        }
);
        adapter = new PersonalRecommandAdapter(this, listview, getNextAppInfos(), null);
        listview.setAdapter(adapter);
        enter();
        return; 
        
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }

    PersonalRecommandAdapter adapter;
    ArrayList data;
    ListView listview;
    private final int numPerPage = 4;
    private int pageIndex;


}
