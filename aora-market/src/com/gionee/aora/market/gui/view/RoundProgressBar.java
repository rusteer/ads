// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import com.gionee.aora.market.RRstyleable;
import com.gionee.aora.market.control.KillProcessManager;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            KillProcessView

public class RoundProgressBar extends View
{

    public RoundProgressBar(Context context1)
    {
        this(context1, null);
    }

    public RoundProgressBar(Context context1, AttributeSet attributeset)
    {
        this(context1, attributeset, 0);
    }

    public RoundProgressBar(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        animationTime = 1000L;
        animationStepTime = 17L;
        progress = 50;
        remainMem = "1.1G";
        init(context1);
        TypedArray typedarray = context1.obtainStyledAttributes(attributeset, RRstyleable.RoundProgressBar);
        roundColor = typedarray.getColor(0, 0xffff0000);
        roundProgressColor = typedarray.getColor(1, 0xff00ff00);
        max = typedarray.getInteger(3, 100);
        style = typedarray.getInt(4, 0);
        typedarray.recycle();
    }

    private void init(Context context1)
    {
        context = context1;
        paint = new Paint();
        (new Thread(new Runnable() {

            public void run()
            {
                refreshHandler.sendEmptyMessage(1);
            }

           
        }
)).start();
    }

    private void refreshRemainMem()
    {
        long l = KillProcessManager.getAvailMemory(context);
        long l1 = KillProcessManager.getTotalMemory(context);
        setRemainMem(KillProcessManager.byteToString(l));
        setProgress((int)((l * 100L) / l1), true);
    }

    private void showFinish(int i, int j)
    {
        if(Math.abs(i - j) <= 1 && parent != null)
            parent.showFinish();
    }

    public int getCricleColor()
    {
        return roundColor;
    }

    public int getCricleProgressColor()
    {
        return roundProgressColor;
    }

    public int getMax()
    {
        synchronized(this){
            return max;
        }
        
    }

    public int getProgress()
    {
        synchronized(this){
            return progress;
        }
       
    }

    public String getRemainMem()
    {
        return remainMem;
    }

    public float getRoundWidth()
    {
        return roundWidth;
    }

    public void killProcessNow()
    {
        long l = 0L;
        stopRefresh();
        List list = KillProcessManager.getRunningProcess(context);
        int i = list.size();
        long l1 = KillProcessManager.getAvailMemory(context);
        KillProcessManager.killProcess(context, list);
        KillProcessManager.killProcess(context, list);
        long l2 = KillProcessManager.getAvailMemory(context);
        long l3 = KillProcessManager.getTotalMemory(context);
        setRemainMem(KillProcessManager.byteToString(l2));
        smothToProgress((int)((100L * l2) / l3));
        startRefresh();
        int j = KillProcessManager.getRunningProcess(context).size();
        long l4 = l2 - l1;
        int k = i - j;
        if(k < 0)
            k = 0;
        if(k == 0)
            l4 = l;
        if(l4 >= l)
            l = l4;
        if(l > 1024L)
            Toast.makeText(context, (new StringBuilder()).append("\u6E05\u7406\u4E86").append(j).append("\u4E2A\u8FDB\u7A0B\uFF0C\u91CA\u653E\u5185\u5B58").append(KillProcessManager.byteToString(l)).toString(), 1).show();
    }

    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        roundWidth = getWidth() / 10;
    }

    protected void onDraw(Canvas canvas)
    {
        RectF rectf;
        super.onDraw(canvas);
        roundWidth = getWidth() / 10;
        int i = getWidth() / 2;
        int j = (int)((float)i - roundWidth / 2.0F);
        paint.setColor(roundColor);
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(i, i, j, paint);
        paint.setStrokeWidth(2.0F + roundWidth);
        paint.setColor(roundProgressColor);
        rectf = new RectF(i - j, i - j, i + j, i + j);
        switch(style){
            case 0://L2_L2:
                paint.setStyle(android.graphics.Paint.Style.STROKE);
                canvas.drawArc(rectf, -90 - (180 * progress) / max, (360 * progress) / max, false, paint);
                break;
            case 1://L3_L3:
                paint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
                if(progress != 0)
                {
                    canvas.drawArc(rectf, -90 - (180 * progress) / max, (360 * progress) / max, true, paint);
                    return;
                }
                break;
        }
        
    }

    public void setCricleColor(int i)
    {
        roundColor = i;
        postInvalidate();
    }

    public void setCricleProgressColor(int i)
    {
        roundProgressColor = i;
        postInvalidate();
    }

    public void setMax(int i)
    {
        synchronized(this){
           if(i<0){
               throw new IllegalArgumentException("max not less than 0");
           }
           max=i;
        }
        
    }

    public void setParent(KillProcessView killprocessview)
    {
        parent = killprocessview;
    }

    public void setProgress(int i, boolean flag)
    {
        synchronized(this){
            if(i<0){
                throw new IllegalArgumentException("progress not less than 0");
            }
            if(i > max)
                i = max;
            if(i <= max)
            {
                progress = i;
                if(parent != null)
                    parent.progressTv.setText((new StringBuilder()).append(i).append("").toString());
                postInvalidate();
            }
            if(parent != null && flag) parent.changeColorAtProgress(i);
        }
        
    }

    public void setRemainMem(String s)
    {
        remainMem = s;
        if(parent != null)
            parent.remainMemTv.setText(s);
    }

    public void setRoundWidth(float f)
    {
        roundWidth = f;
    }

    public void smothToProgress(int i)
    {
        Message message = new Message();
        message.what = 0;
        message.obj = Integer.valueOf(i);
        handler.sendMessage(message);
    }

    public void startRefresh()
    {
        refreshHandler.sendEmptyMessage(1);
    }

    public void stopRefresh()
    {
        refreshHandler.sendEmptyMessage(2);
    }

    public static final int FILL = 1;
    public static final int STROKE=0;
    private final long animationStepTime;
    private final long animationTime;
    Context context;
    Handler handler = new Handler() {

        public void handleMessage(Message message)
        {
            if(message.what == 0)
            {
                parent.killBtn.setEnabled(false);
                int k1 = getProgress();
                Integer integer = (Integer)message.obj;
                Bundle bundle1 = new Bundle();
                bundle1.putFloat("step", (float)(0 + integer.intValue()) / 58F);
                bundle1.putInt("from", k1);
                bundle1.putInt("to", integer.intValue());
                bundle1.putInt("count", 1);
                Message message2 = new Message();
                message2.what = 1;
                message2.setData(bundle1);
                handler.sendMessage(message2);
            } else
            if(message.what == 1)
            {
                float f = message.getData().getFloat("step");
                int j = message.getData().getInt("from");
                int k = message.getData().getInt("to");
                int l = message.getData().getInt("count");
                int i1 = 0 + (int)(f * (float)l);
                if(i1 < 0)
                {
                    setProgress(0, true);
                    handler.removeMessages(0);
                    handler.removeMessages(1);
                    parent.killBtn.setEnabled(true);
                    showFinish(j, k);
                    return;
                }
                if(i1 > max)
                {
                    int j1 = max;
                    setProgress(j1, true);
                    handler.removeMessages(0);
                    handler.removeMessages(1);
                    parent.killBtn.setEnabled(true);
                    showFinish(j, k);
                    return;
                }
                if(i1 >= k)
                {
                    setProgress(k, true);
                    handler.removeMessages(0);
                    handler.removeMessages(1);
                    parent.killBtn.setEnabled(true);
                    showFinish(j, k);
                    return;
                } else
                {
                    setProgress(i1, false);
                    Bundle bundle = message.getData();
                    bundle.putInt("count", l + 1);
                    Message message1 = new Message();
                    message1.what = 1;
                    message1.setData(bundle);
                    handler.sendMessageDelayed(message1, 17L);
                    return;
                }
            }
        }

      
    }
;
    private int max;
    private Paint paint;
    private KillProcessView parent;
    private int progress;
    private Handler refreshHandler = new Handler() {

        public void handleMessage(Message message)
        {
            if(message.what == 1)
            {
                refreshRemainMem();
                sendEmptyMessageDelayed(1, 2000L);
            } else
            if(message.what == 2)
            {
                removeMessages(1);
                removeMessages(2);
                return;
            }
        }

        
    }
;
    private String remainMem;
    private int roundColor;
    private int roundProgressColor;
    private float roundWidth;
    private int style;





}
