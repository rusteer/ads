// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            RoundProgressBar

@SuppressLint("NewApi")
public class KillProcessView extends LinearLayout
{

    public KillProcessView(Context context1)
    {
        super(context1);
        progressColor = 0xff359b63;
        warningColor = -29125;
        init(context1);
    }

    public KillProcessView(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        progressColor = 0xff359b63;
        warningColor = -29125;
        init(context1);
    }

    public KillProcessView(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        progressColor = 0xff359b63;
        warningColor = -29125;
        init(context1);
    }

    private void init(Context context1)
    {
        context = context1;
        View.inflate(context1, biz.AR.layout.kill_process_view, this);
        setGravity(16);
        setOrientation(0);
        roundBar = (RoundProgressBar)findViewById(biz.AR.id.kill_process_round_progress);
        killBtn = (Button)findViewById(biz.AR.id.kill_process_btn);
        finishTv = (TextView)findViewById(biz.AR.id.finish_text);
        keyongTv = (TextView)findViewById(biz.AR.id.keyong);
        progressTv = (TextView)findViewById(biz.AR.id.progress_tv);
        percentTv = (TextView)findViewById(biz.AR.id.percent_tv);
        remainMemTv = (TextView)findViewById(biz.AR.id.remain_mem);
        textLayer = findViewById(biz.AR.id.text_layer);
        roundBar.setParent(this);
        killBtn.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                roundBar.killProcessNow();
            }

            
        }
);
    }

    private void setColors(int i)
    {
        finishTv.setTextColor(i);
        keyongTv.setTextColor(i);
        progressTv.setTextColor(i);
        percentTv.setTextColor(i);
        roundBar.setCricleColor(i);
    }

    public void changeColorAtProgress(int i)
    {
        if(i <= 10)
        {
            setColors(-29125);
            return;
        } else
        {
            setColors(0xff359b63);
            return;
        }
    }

    public void showFinish()
    {
        textLayer.setVisibility(8);
        finishTv.setVisibility(0);
        postDelayed(new Runnable() {

            public void run()
            {
                textLayer.setVisibility(0);
                finishTv.setVisibility(8);
            }

             
        }
, 1000L);
    }

    public void startRefresh()
    {
        roundBar.startRefresh();
    }

    public void stopRefresh()
    {
        roundBar.stopRefresh();
    }

    Context context;
    private TextView finishTv;
    private TextView keyongTv;
    public Button killBtn;
    private TextView percentTv;
    private final int progressColor;
    public TextView progressTv;
    public TextView remainMemTv;
    RoundProgressBar roundBar;
    View textLayer;
    private final int warningColor;

}
