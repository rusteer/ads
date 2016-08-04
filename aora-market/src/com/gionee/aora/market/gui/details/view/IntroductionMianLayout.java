// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details.view;
import java.util.List;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.main.MainViewPagerAdapter;
import com.gionee.aora.market.gui.main.OnLoadData;
import com.gionee.aora.market.gui.view.tabview.UnderlinePageIndicator;

public class IntroductionMianLayout extends LinearLayout {
    private MainViewPagerAdapter adapter;
    private List fragmentItems;
    private UnderlinePageIndicator indicator;
    private OnLoadData loadData;
    private LinearLayout tabBarContainer;
    private View view;
    public ViewPager viewPager;
    public IntroductionMianLayout(Context context) {
        super(context);
        fragmentItems = null;
        loadData = null;
    }
    public IntroductionMianLayout(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        fragmentItems = null;
        loadData = null;
    }
    private void init(Context context) {
        setOrientation(1);
        view = View.inflate(context, biz.AR.layout.introduction_content_viewpager, null);
        addView(view, new android.widget.LinearLayout.LayoutParams(-1, -1));
        viewPager = (ViewPager) findViewById(biz.AR.id.viewpager);
        tabBarContainer = (LinearLayout) findViewById(biz.AR.id.tab_title_content);
        indicator = (UnderlinePageIndicator) findViewById(biz.AR.id.underlinePageIndicator);
        indicator.setOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float f, int j) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
            @Override
            public void onPageSelected(int i) {
                int j = tabBarContainer.getChildCount();
                int k = 0;
                while (k < j) {
                    View view1 = tabBarContainer.getChildAt(k);
                    if (view1 != null) {
                        boolean flag;
                        if (i == k) flag = true;
                        else flag = false;
                        view1.setSelected(flag);
                    }
                    k++;
                }
                if (fragmentItems != null) statrtLoadData((Fragment) fragmentItems.get(i));
            }
        });
    }
    public void setCenterViewInfo(Context context, FragmentManager fragmentmanager, List list, String as[]) {
        fragmentItems = list;
        if (viewPager == null) init(context);
        adapter = new MainViewPagerAdapter(fragmentmanager, list);
        int i = context.getResources().getDisplayMetrics().widthPixels;
        int j = 0;
        while (j < list.size()) {
            TextView textview = new TextView(context);
            textview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(i / list.size(), -2));
            textview.setGravity(17);
            textview.setText(as[j]);
            textview.setTextSize(16F);
            textview.setTag(Integer.valueOf(j));
            android.content.res.XmlResourceParser xmlresourceparser = getResources().getXml(biz.AR.drawable.tab_botton_textcolor);
            try {
                textview.setTextColor(ColorStateList.createFromXml(getResources(), xmlresourceparser));
            } catch (Exception exception) {
                DLog.e("IntroductionMianLayout", new StringBuilder().append(exception).append("").toString());
            }
            tabBarContainer.addView(textview);
            textview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    int k = ((Integer) view1.getTag()).intValue();
                    viewPager.setCurrentItem(k);
                }
            });
            j++;
        }
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        viewPager.setCurrentItem(1);
        tabBarContainer.getChildAt(1).setSelected(true);
    }
    private void statrtLoadData(Fragment fragment) {
        try {
            loadData = (OnLoadData) fragment;
            DLog.v("lung", new StringBuilder().append("fragment isAdded ").append(fragment.isAdded()).toString());
            loadData.onLoadData();
            return;
        } catch (ClassCastException classcastexception) {
            throw new ClassCastException(new StringBuilder().append(fragment.toString()).append(" must implement loadData").toString());
        }
    }
}
