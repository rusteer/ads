// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.view;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.*;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            MarketListView
public class RefreshableView extends LinearLayout implements android.view.View.OnTouchListener {
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {
        protected Integer doInBackground(Void avoid[]) {
            int i = headerLayoutParams.topMargin;
            do {
                if ((i -= 20) <= hideHeaderHeight) return Integer.valueOf(hideHeaderHeight);
                Integer ainteger[] = new Integer[1];
                ainteger[0] = Integer.valueOf(i);
                publishProgress(ainteger);
            } while (true);
        }
        protected void onPostExecute(Integer integer) {
            headerLayoutParams.topMargin = integer.intValue();
            RefreshableView.header.setLayoutParams(headerLayoutParams);
            currentStatus = 3;
        }
        protected void onProgressUpdate(Integer ainteger[]) {
            headerLayoutParams.topMargin = ainteger[0].intValue();
            RefreshableView.header.setLayoutParams(headerLayoutParams);
        }
    }
    public static interface PullToRefreshListener {
        public abstract void onRefresh();
        public abstract void onRefreshFinished();
    }
    class RefreshingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void avoid[]) {
            int i = headerLayoutParams.topMargin;
            do {
                if ((i -= 20) <= 0) {
                    currentStatus = 2;
                    Integer ainteger1[] = new Integer[1];
                    ainteger1[0] = Integer.valueOf(0);
                    publishProgress(ainteger1);
                    if (mListener != null) mListener.onRefresh();
                    return null;
                }
                Integer ainteger[] = new Integer[1];
                ainteger[0] = Integer.valueOf(i);
                publishProgress(ainteger);
            } while (true);
        }
        protected void onCancelled() {
            super.onCancelled();
            finishRefreshing();
        }
        protected void onPostExecute(Void void1) {
            super.onPostExecute(void1);
            finishRefreshing();
            if (mListener != null) mListener.onRefreshFinished();
        }
        protected void onProgressUpdate(Integer ainteger[]) {
            updateHeaderView();
            headerLayoutParams.topMargin = ainteger[0].intValue();
            RefreshableView.header.setLayoutParams(headerLayoutParams);
        }
    }
    public RefreshableView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        currentStatus = 3;
        lastStatus = 3;
        header = LayoutInflater.from(context).inflate(biz.AR.layout.drop_down_refresh_layout, null, true);
        progressBar = (ImageView) header.findViewById(biz.AR.id.drop_down_refresh_pb);
        arrow = (ImageView) header.findViewById(biz.AR.id.drop_down_refresh_arrow);
        description = (TextView) header.findViewById(biz.AR.id.drop_down_refresh_description);
        setOrientation(1);
        addView(header, 0);
    }
    public static int getHeaderHeight() {
        return header.getHeight();
    }
    private Boolean getIsDropDown(MotionEvent motionevent) {
        View view = listView.getChildAt(0);
        if (view != null && (listView.getFirstVisiblePosition() != 0 || view.getTop() != 0)) {
            if (headerLayoutParams.topMargin != hideHeaderHeight) {
                headerLayoutParams.topMargin = hideHeaderHeight;
                header.setLayoutParams(headerLayoutParams);
            }
            return Boolean.valueOf(false);
        } else {
            return Boolean.valueOf(true);
        }
    }
    private void rotateArrow() {
        float f = 180F;
        float f1 = (float) arrow.getWidth() / 2.0F;
        float f2 = (float) arrow.getHeight() / 2.0F;
        float f3;
        RotateAnimation rotateanimation;
        if (currentStatus == 0) f3 = 360F;
        else if (currentStatus == 1) {
            f3 = f;
            f = 0.0F;
        } else {
            f3 = 0.0F;
            f = 0.0F;
        }
        rotateanimation = new RotateAnimation(f, f3, f1, f2);
        rotateanimation.setDuration(100L);
        rotateanimation.setFillAfter(true);
        arrow.startAnimation(rotateanimation);
    }
    private void updateHeaderView() {
        if (lastStatus != currentStatus) if (currentStatus == 0) {
            description.setText(getResources().getString(biz.AR.string.drop_dowm));
            arrow.setVisibility(0);
            progressBar.setVisibility(8);
            rotateArrow();
        } else {
            if (currentStatus == 1) {
                description.setText(getResources().getString(biz.AR.string.release_update));
                arrow.setVisibility(0);
                progressBar.setVisibility(8);
                rotateArrow();
                return;
            }
            if (currentStatus == 2) {
                description.setText(getResources().getString(biz.AR.string.refreshing));
                arrow.clearAnimation();
                arrow.setVisibility(8);
                progressBar.setVisibility(0);
                android.view.animation.Animation animation = AnimationUtils.loadAnimation(getContext(), biz.AR.anim.loadmore_animate);
                progressBar.startAnimation(animation);
                return;
            }
        }
    }
    public void finishRefreshing() {
        currentStatus = 3;
        progressBar.clearAnimation();
        progressBar.setVisibility(8);
        (new HideHeaderTask()).execute(new Void[0]);
    }
    protected void onLayout(boolean flag, int i, int j, int k, int l) {
        super.onLayout(flag, i, j, k, l);
        if (flag && !loadOnce) {
            hideHeaderHeight = -header.getHeight();
            headerLayoutParams = (android.view.ViewGroup.MarginLayoutParams) header.getLayoutParams();
            headerLayoutParams.topMargin = hideHeaderHeight;
            listView = (MarketListView) getChildAt(1);
            listView.setOnTouchListener(this);
            loadOnce = true;
        }
    }
    public boolean onTouch(View view, MotionEvent motionevent) {
        boolean flag;
        boolean flag1;
        flag = getIsDropDown(motionevent).booleanValue();
        flag1 = false;
        if (!flag) return flag1;
        //_L1:
        switch (motionevent.getAction()) {
            case 0://L4_L4:
                yDown = motionevent.getRawY();
                break;
            case 1://L5_L5:
                if (lastStatus == 2 && headerLayoutParams.topMargin != 0) {
                    headerLayoutParams.topMargin = 0;
                    header.setLayoutParams(headerLayoutParams);
                } else if (currentStatus == 1) (new RefreshingTask()).execute(new Void[0]);
                else if (currentStatus == 0) (new HideHeaderTask()).execute(new Void[0]);
                break;
            case 2://L6_L6:
                int j;
                int k;
                int l;
                j = (int) (motionevent.getRawY() - yDown);
                if (j > 0) break; /* Loop/switch isn't completed */
                k = headerLayoutParams.topMargin;
                l = hideHeaderHeight;
                flag1 = false;
                if (k <= l) return flag1;
                //_L9:
                if (currentStatus != 2) {
                    if (headerLayoutParams.topMargin > 0) currentStatus = 1;
                    else currentStatus = 0;
                    headerLayoutParams.topMargin = j / 2 + hideHeaderHeight;
                    header.setLayoutParams(headerLayoutParams);
                } else {
                    lastStatus = 2;
                }
                break;
        }
        //_L3:
        if (currentStatus != 0) {
            int i;
            i = currentStatus;
            flag1 = false;
            if (i != 1) return flag1;
        }
        //_L8:
        updateHeaderView();
        listView.setPressed(false);
        listView.setFocusable(false);
        listView.setFocusableInTouchMode(false);
        lastStatus = currentStatus;
        flag1 = true;
        //_L2:
        return flag1;
    }
    public void setOnRefreshListener(PullToRefreshListener pulltorefreshlistener) {
        mListener = pulltorefreshlistener;
    }
    public static final int SCROLL_SPEED = -20;
    public static final int STATUS_PULL_TO_REFRESH = 0;
    public static final int STATUS_REFRESHING = 2;
    public static final int STATUS_REFRESH_FINISHED = 3;
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    private static View header;
    private ImageView arrow;
    private int currentStatus;
    private TextView description;
    private android.view.ViewGroup.MarginLayoutParams headerLayoutParams;
    private int hideHeaderHeight;
    private int lastStatus;
    private MarketListView listView;
    private boolean loadOnce;
    private PullToRefreshListener mListener;
    private ImageView progressBar;
    private float yDown;
    /*
        static int access$102(RefreshableView refreshableview, int i)
        {
            refreshableview.currentStatus = i;
            return i;
        }

    */
}
