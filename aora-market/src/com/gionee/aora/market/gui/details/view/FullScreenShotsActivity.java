// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details.view;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.gui.view.tabview.CirclePageIndicator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class FullScreenShotsActivity extends Activity implements android.view.View.OnClickListener {
    private class FullScreenShotsViewPagerAdapter extends PagerAdapter {
        private List mListViews;
        public FullScreenShotsViewPagerAdapter(List list) {
            super();
            mListViews = list;
        }
        @Override
        public void destroyItem(ViewGroup viewgroup, int i, Object obj) {
            viewgroup.removeView((View) mListViews.get(i));
        }
        @Override
        public int getCount() {
            return mListViews.size();
        }
        @Override
        public Object instantiateItem(ViewGroup viewgroup, int i) {
            viewgroup.addView((View) mListViews.get(i), 0);
            return mListViews.get(i);
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }
    private CirclePageIndicator circlePageIndicator;
    private FullScreenShotsViewPagerAdapter fullScreenShotsViewPagerAdapter;
    private ImageLoaderManager imageLoaderManager;
    private LinearLayout linearLayout;
    private List listImageViews;
    private int nowPosition;
    private DisplayImageOptions options;
    private ArrayList screenShotUrls;
    private ViewPager viewPager;
    public FullScreenShotsActivity() {
        int i = biz.AR.drawable.soft_introduction_default_screenshot;
        nowPosition = 0;
        screenShotUrls = null;
        fullScreenShotsViewPagerAdapter = null;
        options = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().showImageForEmptyUri(i).showImageOnFail(i).showStubImage(i).cacheInMemory()
                .cacheOnDisc().build();
    }
    @Override
    public void onClick(View view) {
        finish();
    }
    @Override
    protected void onCreate(Bundle bundle) {
        int i = 0;
        super.onCreate(bundle);
        setContentView(biz.AR.layout.fullscreen_shots_layout);
        viewPager = (ViewPager) findViewById(biz.AR.id.viewpager);
        circlePageIndicator = (CirclePageIndicator) findViewById(biz.AR.id.indicator);
        Intent intent = getIntent();
        nowPosition = intent.getIntExtra("POSITION", 0);
        screenShotUrls = intent.getStringArrayListExtra("SCREENSHOTSURLS");
        listImageViews = new ArrayList();
        imageLoaderManager = ImageLoaderManager.getInstance();
        int _tmp = getResources().getDisplayMetrics().widthPixels;
        for (; i < screenShotUrls.size(); i++) {
            ImageView imageview = new ImageView(this);
            imageview.setOnClickListener(this);
            imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            imageview.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
            imageview.setBackgroundColor(-1);
            imageview.setId(i + 1000);
            linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
            linearLayout.setGravity(17);
            imageLoaderManager.displayImage((String) screenShotUrls.get(i), imageview, options);
            linearLayout.addView(imageview);
            listImageViews.add(linearLayout);
        }
        fullScreenShotsViewPagerAdapter = new FullScreenShotsViewPagerAdapter(listImageViews);
        viewPager.setAdapter(fullScreenShotsViewPagerAdapter);
        viewPager.setCurrentItem(nowPosition);
        circlePageIndicator.setViewPager(viewPager);
        circlePageIndicator.setCurrentItem(nowPosition);
        circlePageIndicator.setFillColor(Color.parseColor("#ffffff"));
        circlePageIndicator.setStrokeColor(Color.parseColor("#b6b6b6"));
        circlePageIndicator.setRadius(getResources().getDimension(biz.AR.dimen.dip5));
        android.widget.RelativeLayout.LayoutParams layoutparams = (android.widget.RelativeLayout.LayoutParams) circlePageIndicator.getLayoutParams();
        layoutparams.topMargin = 6 * getResources().getDisplayMetrics().heightPixels / 7;
        circlePageIndicator.setLayoutParams(layoutparams);
        circlePageIndicator.setOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int j, float f, int k) {}
            @Override
            public void onPageScrollStateChanged(int j) {}
            @Override
            public void onPageSelected(int j) {
                ImageView imageview1 = (ImageView) findViewById(j + 1000);
                if (imageview1 != null) imageLoaderManager.displayImage((String) screenShotUrls.get(j), imageview1);
            }
        });
    }
}
