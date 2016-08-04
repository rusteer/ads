// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.view;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.aora.base.util.Constants;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.download.NewDownloadBaseAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MarketListView extends ListView {
    public MarketListView(Context context) {
        super(context);
        lastIndex = -1;
        init(context);
    }
    public MarketListView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        lastIndex = -1;
        init(context);
    }
    public MarketListView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        lastIndex = -1;
        init(context);
    }
    private void init(Context context) {
        setCacheColorHint(0);
        setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        setDivider(context.getResources().getDrawable(biz.AR.drawable.driver_color));
        setDividerHeight((int) context.getResources().getDimension(biz.AR.dimen.px1));
        setFadingEdgeLength(0);
        setScrollbarFadingEnabled(true);
    }
    public void addLoadEndView(Context context) {
        int i = context.getResources().getDimensionPixelSize(biz.AR.dimen.dip10);
        android.widget.AbsListView.LayoutParams layoutparams = new android.widget.AbsListView.LayoutParams(-1, -2);
        if (loadEndViewLayout != null) removeFooterView(loadEndViewLayout);
        loadEndViewLayout = new LinearLayout(context);
        TextView textview = new TextView(context);
        textview.setPadding(0, i, 0, i);
        textview.setMaxLines(1);
        textview.setTextColor(Color.parseColor("#5e5e5e"));
        textview.setGravity(17);
        textview.setBackgroundResource(biz.AR.color.transparent);
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
        if (getAdapter() != null && (getAdapter() instanceof NewDownloadBaseAdapter)) ((NewDownloadBaseAdapter) getAdapter()).onDestory();
        super.onDetachedFromWindow();
    }
    public boolean onKeyDown(int i, KeyEvent keyevent) {
        boolean flag;
        Log.e("MarketListView", (new StringBuilder()).append("onKeyUp,keycode=").append(i).toString());
        flag = super.onKeyDown(i, keyevent);
        if (Constants.isKeyboardVersion) {
            if (i == 66 || i == 23) {
                getSelectedView().performClick();
            } else {
                if (!(i != 20 && i != 19 || lastIndex != getSelectedItemPosition())) {
                    Class aclass[] = new Class[1];
                    aclass[0] = Integer.TYPE;
                    try {
                        Method method = ListView.class.getDeclaredMethod("arrowScroll", aclass);
                        method.setAccessible(true);
                        if (i == 19) {
                            
                                Object aobj[] = new Object[1];
                                aobj[0] = Integer.valueOf(33);
                                method.invoke(this, aobj);
                             
                        } else {
                            if (i == 20) {
                                Object aobj1[] = new Object[1];
                                aobj1[0] = Integer.valueOf(130);
                                method.invoke(this, aobj1);
                            }
                        }
                    } catch ( Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        lastIndex = getSelectedItemPosition();
        Log.e("MarketListView", (new StringBuilder()).append("onKeyDown,keycode=").append(i).append(",position=").append(getSelectedItemPosition()).toString());
        return flag;
    }
    public void removeLoadEndView() {
        if (loadEndViewLayout != null) removeFooterView(loadEndViewLayout);
    }
    public void setHeard(Context context) {
        TextView textview = new TextView(context);
        textview.setBackgroundResource(0);
        textview.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, 3));
        addHeaderView(textview, null, false);
    }
    private int lastIndex;
    public LinearLayout loadEndViewLayout;
}
