// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.aora.base.util.NetUtil;
import com.gionee.aora.market.gui.necessary.FirstInNecessaryActivity;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.login:
//            GuideAdapter

public class GuideActivity extends Activity
{

    public GuideActivity()
    {
    }

    private View initLayout1()
    {
        ImageView imageview = new ImageView(this);
        imageview.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
        imageview.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
        imageview.setImageResource(biz.AR.drawable.guide_image_1);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
        linearlayout.setGravity(17);
        linearlayout.addView(imageview);
        return linearlayout;
    }

    private View initLayout2()
    {
        View view = View.inflate(this, biz.AR.layout.guide_layout_2, null);
        guide_click = (ImageView)view.findViewById(biz.AR.id.guide_click);
        guide_click.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1)
            {
                finish();
            }

            
        }
);
        return view;
    }

    public void finish()
    {
        if(NetUtil.isNetworkAvailable(this))
            startActivity(new Intent(this,  FirstInNecessaryActivity.class));
        super.finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.guide_layout);
        viewPager = (ViewPager)findViewById(biz.AR.id.guide_viewpager);
        viewLists = new ArrayList();
        viewLists.add(initLayout1());
        viewLists.add(initLayout2());
        guideAdapter = new GuideAdapter(viewLists);
        viewPager.setAdapter(guideAdapter);
        viewPager.setOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int i)
            {
            }

            public void onPageScrolled(int i, float f, int j)
            {
            }

            public void onPageSelected(int i)
            {
                switch(i){
                    case 0://L2_L2:
                        if(guide_click.getVisibility() == 0)
                        {
                            guide_click.setVisibility(8);
                            guide_click.clearAnimation();
                            return;
                        }
                        break;
                    case 1://L3_L3:
                        guide_click.setVisibility(0);
                        AlphaAnimation alphaanimation = new AlphaAnimation(0.0F, 100F);
                        alphaanimation.setDuration(20000L);
                        guide_click.startAnimation(alphaanimation);
                        break;
                }
                

                return;
            }

            
        }
);
    }

    private GuideAdapter guideAdapter;
    private ImageView guide_click;
    private List viewLists;
    private ViewPager viewPager;

}
