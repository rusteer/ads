// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.view;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.aora.base.util.Constants;
import com.aora.base.util.DLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import java.lang.reflect.Method;

public class MarketExpandListView extends ExpandableListView {
    public MarketExpandListView(Context context) {
        super(context);
        init(context);
    }
    public MarketExpandListView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        init(context);
    }
    public MarketExpandListView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        init(context);
    }
    private void init(Context context) {
        setCacheColorHint(0);
        setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        setDivider(null);
        setDividerHeight((int) context.getResources().getDimension(biz.AR.dimen.px1));
        setFadingEdgeLength(0);
        setScrollbarFadingEnabled(true);
        setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandablelistview, View view, int i, long l) {
                return true;
            }
            
        });
        setGroupIndicator(null);
    }
    public void addLoadEndView(Context context) {
        int i = context.getResources().getDimensionPixelSize(biz.AR.dimen.dip10);
        android.widget.AbsListView.LayoutParams layoutparams = new android.widget.AbsListView.LayoutParams(-1, -2);
        loadEndViewLayout = new LinearLayout(context);
        TextView textview = new TextView(context);
        textview.setPadding(0, i, 0, i);
        textview.setMaxLines(1);
        textview.setTextColor(Color.parseColor("#5e5e5e"));
        textview.setGravity(17);
        textview.setBackgroundDrawable(new ColorDrawable(0));
        textview.setText("\u5217\u8868\u5C3D\u5934,\u672C\u5C0A\u5230\u6B64\u4E00\u6E38");
        loadEndViewLayout.setLayoutParams(layoutparams);
        loadEndViewLayout.addView(textview);
        loadEndViewLayout.setGravity(17);
        addFooterView(loadEndViewLayout, null, false);
    }
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    public boolean onKeyDown(int i, KeyEvent keyevent) {
        boolean flag = super.onKeyDown(i, keyevent);
        if (Constants.isKeyboardVersion) {
            //L1_L1:
            if (i == 66 || i == 23) {
                //L3_L3:
                getSelectedView().performClick();
            } else {
                //L4_L4:
                if (getPackedPositionType(getSelectedPosition()) == 0) {
                    //L5_L5:
                    Method method;
                    Object aobj[];
                    try {
                        Class aclass[] = new Class[1];
                        aclass[0] = Integer.TYPE;
                        method = ListView.class.getDeclaredMethod("arrowScroll", aclass);
                        method.setAccessible(true);
                   
                    if (i == 19) {
                        aobj = new Object[1];
                        aobj[0] = Integer.valueOf(33);
                        method.invoke(this, aobj);
                    } else if (i == 20) {
                        Object aobj1[] = new Object[1];
                        aobj1[0] = Integer.valueOf(130);
                        method.invoke(this, aobj1);
                    }
                    } catch (Exception exception) {
                        DLog.e("MarketExpandListView", "onKeyDown()", exception);
                    }
                }
            }
        }
        return flag;
    }
    public void removeLoadEndView() {
        removeFooterView(loadEndViewLayout);
    }
    public void setHeard(Context context, boolean flag) {
        if (flag) {
            TextView textview = new TextView(context);
            textview.setBackgroundResource(0);
            textview.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, 3));
            addHeaderView(textview, null, false);
        }
    }
    public LinearLayout loadEndViewLayout;
}
