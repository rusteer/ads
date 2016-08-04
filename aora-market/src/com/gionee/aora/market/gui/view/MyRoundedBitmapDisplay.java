// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.graphics.*;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

public class MyRoundedBitmapDisplay
    implements BitmapDisplayer
{

    public MyRoundedBitmapDisplay(int i)
    {
        roundPixels = i;
    }

    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int i, Rect rect, Rect rect1, int j, int k)
    {
        int l = j * 2;
        int i1 = k * 2;
        int j1 = i * 2;
        Bitmap bitmap1 = resizeBitmap(bitmap, 2, 2);
        Rect rect2 = new Rect(2 * rect.left, 2 * rect.top, 2 * rect.right, 2 * rect.bottom);
        Rect rect3 = new Rect(2 * rect1.left, 2 * rect1.top, 2 * rect1.right, 2 * rect1.bottom);
        Bitmap bitmap2 = Bitmap.createBitmap(l, i1, android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        RectF rectf = new RectF(rect3);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff000000);
        canvas.drawRoundRect(rectf, j1, j1, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap1, rect2, rectf, paint);
        return resizeBitmap(bitmap2, 1.0F / (float)2, 1.0F / (float)2);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, float f, float f1)
    {
        if(bitmap != null)
        {
            int i = bitmap.getWidth();
            int j = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(f, f1);
            return Bitmap.createBitmap(bitmap, 0, 0, i, j, matrix, true);
        } else
        {
            return null;
        }
    }

    public static Bitmap roundCorners(Bitmap bitmap, ImageView imageview, int i)
    { int j1; Rect rect;
    Rect rect1;
    int i2;
    Bitmap bitmap1;
    int k3;
    int l3;
    int i4;
    int j4;
    Rect rect3;
    int k4;
    Rect rect4;
    int k1;
    int l1;
    int j2;
    int k2;
    int l2;
    int i3;
    int j3;
    Rect rect2;
        int j;
        int k;
        int l;
        int i1;
        j = bitmap.getWidth();
        k = bitmap.getHeight();
        l = imageview.getWidth();
        i1 = imageview.getHeight();
        if(l <= 0)
            l = j;
        if(i1 <= 0)
            i1 = k;
       
        switch(imageview.getScaleType().ordinal()){
           
            case 0://L_L2_L2:
                if((float)l / (float)i1 > (float)j / (float)k)
                {
                    l3 = Math.min(i1, k);
                    k3 = (int)((float)j / ((float)k / (float)l3));
                } else
                {
                    k3 = Math.min(l, j);
                    l3 = (int)((float)k / ((float)j / (float)k3));
                }
                i4 = (l - k3) / 2;
                j4 = (i1 - l3) / 2;
                rect3 = new Rect(0, 0, j, k);
                rect1 = new Rect(i4, j4, k3 + i4, l3 + j4);
                rect = rect3;
                i2 = i1;
                break;                
            case 4://L_L3 _L3:
                if((float)l / (float)i1 > (float)j / (float)k)
                {
                    i3 = (int)((float)i1 * ((float)j / (float)l));
                    j3 = (k - i3) / 2;
                    k2 = 0;
                    l2 = j;
                } else
                {
                    j2 = (int)((float)l * ((float)k / (float)i1));
                    k2 = (j - j2) / 2;
                    l2 = j2;
                    i3 = k;
                    j3 = 0;
                }
                l = Math.min(l, j);
                i2 = Math.min(i1, k);
                rect2 = new Rect(k2, j3, k2 + l2, i3 + j3);
                rect1 = new Rect(0, 0, l, i2);
                rect = rect2;
                break;
            case 5://L_L4 _L4:
                rect = new Rect(0, 0, j, k);
                rect1 = new Rect(0, 0, l, i1);
                i2 = i1;
                break;
            case 6://L_L5 
            case 7://L_L5_L5:
                l = Math.min(l, j);
                j1 = Math.min(i1, k);
                k1 = (j - l) / 2;
                l1 = (k - j1) / 2;
                rect = new Rect(k1, l1, k1 + l, l1 + j1);
                rect1 = new Rect(0, 0, l, j1);
                i2 = j1;
                break;
            case 1://L_L1 
            case 2://L_L1 
            case 3://L_L1 
                default:
                    
                       
                        if((float)l / (float)i1 > (float)j / (float)k)
                        {
                            k4 = (int)((float)j / ((float)k / (float)i1));
                        } else
                        {
                            i1 = (int)((float)k / ((float)j / (float)l));
                            k4 = l;
                        }
                        rect4 = new Rect(0, 0, j, k);
                        rect1 = new Rect(0, 0, k4, i1);
                        i2 = i1;
                        l = k4;
                        rect = rect4;
                break;
        }
        

//_L7:
       
        try
        {
            bitmap1 = getRoundedCornerBitmap(bitmap, i, rect, rect1, l, i2);
        }
        catch(OutOfMemoryError outofmemoryerror)
        {
            L.e(outofmemoryerror, "Can't create bitmap with rounded corners. Not enough memory.", new Object[0]);
            return bitmap;
        }
        return bitmap1;




    }

    public Bitmap display(Bitmap bitmap, ImageView imageview)
    {
        Bitmap bitmap1 = roundCorners(bitmap, imageview, roundPixels);
        imageview.setImageBitmap(bitmap1);
        return bitmap1;
    }

    private final int roundPixels;
}
