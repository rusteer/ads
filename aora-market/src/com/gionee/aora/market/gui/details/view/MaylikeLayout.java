// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details.view;
import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.view.LoadingNewView2;
import com.gionee.aora.market.module.AppInfo;

public class MaylikeLayout extends RelativeLayout implements android.view.View.OnClickListener {
    private Context context;
    private HorizontalScrollView horizontalScrollView;
    private ImageLoaderManager imageLoaderManager;
    private ImageView leftButton;
    public LoadingNewView2 loadingNewView2;
    private int pageCount;
    private ImageView rightButton;
    private ViewGroup viewGroups[];
    public MaylikeLayout(Context context1) {
        super(context1);
        pageCount = 1;
        context = context1;
        init(context1);
    }
    public MaylikeLayout(Context context1, AttributeSet attributeset) {
        super(context1, attributeset);
        pageCount = 1;
        context = context1;
        init(context1);
    }
    public MaylikeLayout(Context context1, AttributeSet attributeset, int i) {
        super(context1, attributeset, i);
        pageCount = 1;
        context = context1;
        init(context1);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionevent) {
        if (viewGroups != null) {
            for (ViewGroup viewGroup : viewGroups)
                viewGroup.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(motionevent);
    }
    private void init(Context context1) {
        addView(View.inflate(context1, biz.AR.layout.introduction_maylike_layout, null));
        leftButton = (ImageView) findViewById(biz.AR.id.maylike_left);
        rightButton = (ImageView) findViewById(biz.AR.id.maylike_right);
        horizontalScrollView = (HorizontalScrollView) findViewById(biz.AR.id.maylike_horizontalScrollView);
        imageLoaderManager = ImageLoaderManager.getInstance();
        loadingNewView2 = (LoadingNewView2) findViewById(biz.AR.id.progressBar_maylike);
        isInEditMode();
    }
    @Override
    public void onClick(View view) {
        AppInfo appinfo = (AppInfo) view.getTag();
        IntroductionDetailActivity.startIntroductionActivity(context, appinfo, null);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionevent) {
        if (viewGroups != null) {
            for (ViewGroup viewGroup : viewGroups)
                viewGroup.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(motionevent);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        if (viewGroups != null) {
            for (ViewGroup viewGroup : viewGroups)
                viewGroup.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(motionevent);
    }
    public void setMyLikeData(Context context1, List list, ViewGroup aviewgroup[]) {
        DLog.i("lilijun", "\u8BBE\u7F6E\u53EF\u80FD\u559C\u6B22\u6570\u636E\uFF01\uFF01");
        loadingNewView2.setVisibilyView(false);
        while (list == null || list.size() == 0)
            return;
        viewGroups = aviewgroup;
        int i = list.size();
        LinearLayout linearlayout = new LinearLayout(context1);
        linearlayout.setGravity(16);
        int j;
        final int leftSpace;
        if (list.size() % 4 == 0) pageCount = list.size() / 4;
        else pageCount = 1 + list.size() / 4;
        j = (horizontalScrollView.getWidth() - (int) getResources().getDimension(biz.AR.dimen.dip30)) / 4;
        leftSpace = (horizontalScrollView.getWidth() - j * 4) / 3;
        for (int k = 0; k < i; k++) {
            View view = View.inflate(context1, biz.AR.layout.maylike_item, null);
            ImageView imageview1 = (ImageView) view.findViewById(biz.AR.id.maylike_icon);
            TextView textview = (TextView) view.findViewById(biz.AR.id.maylike_appname_text);
            android.widget.LinearLayout.LayoutParams layoutparams1 = new android.widget.LinearLayout.LayoutParams(j, -1);
            if (k != 0) layoutparams1.leftMargin = leftSpace;
            view.setLayoutParams(layoutparams1);
            textview.setText(((AppInfo) list.get(k)).getName());
            imageview1.setOnClickListener(this);
            imageview1.setTag(list.get(k));
            linearlayout.addView(view);
            ImageLoaderManager.getInstance().displayImage(((AppInfo) list.get(k)).getIconUrl(), imageview1, imageLoaderManager.getImageLoaderOptions());
        }
        int l = list.size() % 4;
        if (l != 0) {
            for (int i1 = 0; i1 < l; i1++) {
                ImageView imageview = new ImageView(context1);
                android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(j, j);
                layoutparams.leftMargin = leftSpace;
                imageview.setLayoutParams(layoutparams);
                imageview.setBackgroundResource(biz.AR.drawable.cp_defaulf);
                imageview.setVisibility(4);
                linearlayout.addView(imageview);
            }
        }
        horizontalScrollView.removeAllViews();
        horizontalScrollView.addView(linearlayout);
        leftButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                horizontalScrollView.smoothScrollBy(-horizontalScrollView.getWidth() - leftSpace, 0);
            }
        });
        rightButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                horizontalScrollView.smoothScrollBy(horizontalScrollView.getWidth() + leftSpace, 0);
            }
        });
    }
}
