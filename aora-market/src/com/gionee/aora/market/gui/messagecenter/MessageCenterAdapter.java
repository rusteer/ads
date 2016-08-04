// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.messagecenter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.database.MessageCenterDBHelper;
import com.gionee.aora.market.gui.lenjoy.LenjoyAppDetailActivity;
import com.gionee.aora.market.gui.lenjoy.LenjoyAppListDetailActivity;
import com.gionee.aora.market.gui.special.ExerciseInfomationActivity;
import com.gionee.aora.market.gui.special.SuperSpecialActivity;
import com.gionee.aora.market.gui.view.CircleCornerImageView;
import com.gionee.aora.market.module.MessageInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageCenterAdapter extends BaseAdapter
{
    private class ViewHolder
    {

        public void setUi(MessageInfo messageinfo, final int position)
        {
            TextView textview;
            final String messageId;
            final String lenjoyId;
            final String s2;
            try
            {
                Date date = new Date(1000L * Long.valueOf(messageinfo.getDate()).longValue());
                dateTv.setText(formater.format(date));
            }
            catch(Exception exception) { }
            if(messageinfo.getType() == 7 || messageinfo.getType() == 8)
            {
                String s = (new StringBuilder()).append(messageinfo.getNum()).append("").toString();
                String s1 = (new StringBuilder()).append("\u6536\u5230").append(s).append("\u4E2A\u8D5E").toString();
                if(messageinfo.isRead())
                {
                    leftIcon.setSrc(biz.AR.drawable.message_center_like_icon_);
                    textTv.setText(s1);
                } else
                {
                    leftIcon.setSrc(biz.AR.drawable.message_center_like_icon);
                    int i = s1.indexOf(s);
                    int j = i + s.length();
                    SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(s1);
                    spannablestringbuilder.setSpan(new ForegroundColorSpan(0xfff0874b), i, j, 34);
                    textTv.setText(spannablestringbuilder);
                }
            } else
            if(messageinfo.getType() == 5 || messageinfo.getType() == 6)
            {
                String s5 = (new StringBuilder()).append(messageinfo.getNum()).append("").toString();
                String s6 = (new StringBuilder()).append("\u6536\u5230").append(s5).append("\u4E2A\u8E29").toString();
                if(messageinfo.isRead())
                {
                    leftIcon.setSrc(biz.AR.drawable.message_center_unlike_icon_);
                    textTv.setText(s6);
                } else
                {
                    leftIcon.setSrc(biz.AR.drawable.message_center_unlike_icon);
                    int k = s6.indexOf(s5);
                    int l = k + s5.length();
                    SpannableStringBuilder spannablestringbuilder1 = new SpannableStringBuilder(s6);
                    spannablestringbuilder1.setSpan(new ForegroundColorSpan(0xfff0874b), k, l, 34);
                    textTv.setText(spannablestringbuilder1);
                }
            } else
            {
                leftIcon.displayImage(messageinfo.getUserIcon(), biz.AR.drawable.integral_main_usericon);
                if(commentUser != null)
                    commentUser.setText(messageinfo.getOpUser());
                textTv.setText(messageinfo.getComment());
            }
            if(rightTitle != null)
                rightTitle.setText(messageinfo.getTitle());
            if(appIcon != null)
                ImageLoaderManager.getInstance().displayImage(messageinfo.getAppIcon(), appIcon, ops);
            textview = (TextView)view.findViewById(biz.AR.id.comment_text_tv);
            if(messageinfo.isRead())
            {
                if(rightTitle != null)
                    rightTitle.setTextColor(0xff999999);
                if(textTv != null)
                    textTv.setTextColor(0xff999999);
                if(commentUser != null)
                    commentUser.setTextColor(0xff999999);
                if(textview != null)
                    textview.setTextColor(0xff999999);
            } else
            {
                if(rightTitle != null)
                    rightTitle.setTextColor(0xff8ac873);
                if(textTv != null)
                    textTv.setTextColor(0xff333333);
                if(commentUser != null)
                    commentUser.setTextColor(0xff666666);
                if(textview != null)
                    textview.setTextColor(0xff666666);
            }
            messageId = messageinfo.getMsgId();
            lenjoyId = messageinfo.getLejoyId();
            s2 = messageinfo.getCommenetId();
            switch(messageinfo.getType())
            {
            default:
                return;

            case 5: // '\005'
            case 7: // '\007'
                view.setOnClickListener(  new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        setMessageRead(amigo, messageId, position);
                        DataCollectInfo datacollectinfo = action.clone();
                        datacollectinfo.setType("8");
                        LenjoyAppDetailActivity.startLenjoyAppDetail(context, lenjoyId, null, datacollectinfo);
                    }

                    /*final ViewHolder this$1;
                    final String val$lenjoyId;
                    final String val$messageId;
                    final int val$position;

            
            {
                this$1 = final_viewholder;
                messageId = s;
                position = i;
                lenjoyId = String.this;
                Object();
            }*/
                }
);
                return;

            case 6: // '\006'
            case 8: // '\b'
                view.setOnClickListener(  new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        setMessageRead(amigo, messageId, position);
                        DataCollectInfo datacollectinfo = action.clone();
                        datacollectinfo.setType("8");
                        LenjoyAppListDetailActivity.startLenjoyAppListDetail(context, lenjoyId, false, datacollectinfo, 0);
                    }

                   /* final ViewHolder this$1;
                    final String val$lenjoyId;
                    final String val$messageId;
                    final int val$position;

            
            {
                this$1 = final_viewholder;
                messageId = s;
                position = i;
                lenjoyId = String.this;
                Object();
            }*/
                }
);
                return;

            case 4: // '\004'
                view.setOnClickListener(  new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        setMessageRead(amigo, messageId, position);
                        DataCollectInfo datacollectinfo = action.clone();
                        datacollectinfo.setType("10");
                        LenjoyAppDetailActivity.startLenjoyAppDetail(context, lenjoyId, s2, datacollectinfo);
                    }

                    /*final ViewHolder this$1;
                    final String val$commentId;
                    final String val$lenjoyId;
                    final String val$messageId;
                    final int val$position;

            
            {
                this$1 = final_viewholder;
                messageId = s;
                position = i;
                lenjoyId = s1;
                commentId = String.this;
                Object();
            }*/
                }
);
                return;

            case 3: // '\003'
                view.setOnClickListener(  new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        setMessageRead(amigo, messageId, position);
                        DataCollectInfo datacollectinfo = action.clone();
                        datacollectinfo.setType("10");
                        LenjoyAppListDetailActivity.startLenjoyAppDetail(context, lenjoyId, s2, datacollectinfo);
                    }

                    
                }
);
                return;

            case 2: // '\002'
                final String s4 = messageinfo.getLejoyId();
                final String url = messageinfo.getUrl();
                view.setOnClickListener(/*s4.*/ new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        setMessageRead(amigo, messageId, position);
                        DataCollectInfo datacollectinfo = action.clone();
                        datacollectinfo.setType("10");
                        Intent intent = new Intent(context,  SuperSpecialActivity.class);
                        intent.putExtra("skipUrl", url);
                        intent.putExtra("vid", s4);
                        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
                        context.startActivity(intent);
                    }

                     
                }
);
                return;

            case 1: // '\001'
                final String vid2 = messageinfo.getLejoyId();
                final String s3 = messageinfo.getTitle();
                final String url2 = messageinfo.getUrl();
                view.setOnClickListener(/*s3.*/ new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        setMessageRead(amigo, messageId, position);
                        DataCollectInfo datacollectinfo = action.clone();
                        datacollectinfo.setType("10");
                        Intent intent = new Intent(context,  ExerciseInfomationActivity.class);
                        intent.putExtra("skipUrl", url2);
                        intent.putExtra("vid", vid2);
                        intent.putExtra("NAME", s3);
                        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
                        context.startActivity(intent);
                    }

                     
                }
);
                return;
            }
        }

        ImageView appIcon;
        TextView commentUser;
        TextView dateTv;
        CircleCornerImageView leftIcon;
        TextView rightTitle;
        TextView textTv;
        View view;

        public ViewHolder(View view1)
        {
            //Object();
            view = view1;
            leftIcon = (CircleCornerImageView)view1.findViewById(biz.AR.id.left_icon);
            textTv = (TextView)view1.findViewById(biz.AR.id.text_tv);
            dateTv = (TextView)view1.findViewById(biz.AR.id.date_tv);
            View view2 = view1.findViewById(biz.AR.id.app_icon);
            if(view2 != null)
                appIcon = (ImageView)view2;
            View view3 = view1.findViewById(biz.AR.id.right_title);
            if(view3 != null)
                rightTitle = (TextView)view3;
            View view4 = view1.findViewById(biz.AR.id.comment_user);
            if(view4 != null)
                commentUser = (TextView)view4;
        }
    }


    public MessageCenterAdapter(Context context1, ArrayList arraylist, DataCollectInfo datacollectinfo)
    {
        action = null;
        amigo = "";
        formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ops = ImageLoaderManager.getInstance().getImageLoaderOptions();
        context = context1;
        data = arraylist;
        action = datacollectinfo;
        amigo = UserStorage.getDefaultUserInfo(context1).getUSER_NAME();
    }

    private void setMessageRead(String s, String s1, int i)
    {
        (new MessageCenterDBHelper(context)).setMessageReaded(s, s1, true);
        ((MessageInfo)data.get(i)).setRead(true);
        notifyDataSetChanged();
    }

    public int getCount()
    {
        if(data == null)
            return 0;
        else
            return data.size();
    }

    public MessageInfo getItem(int i)
    {
        return (MessageInfo)data.get(i);
    }

    

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getItemViewType(int i)
    {
        int j = getItem(i).getType();
        if(j >= 1 && j <= 3)
            return 3;
        if(j == 4)
            return 2;
        return j != 7 && j != 5 ? 1 : 0;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        int j;
        ViewHolder viewholder;
        j = getItemViewType(i);
        viewholder = null;
        switch(j){
            case 0://L2_L2:
                if(view == null)
                {
                    view = View.inflate(context, biz.AR.layout.message_center_list_item_like_app, null);
                    viewholder = new ViewHolder(view);
                    view.setTag(viewholder);
                } else
                {
                    viewholder = (ViewHolder)view.getTag();
                }
                break;
            case 1://L3_L3:
                if(view == null)
                {
                    view = View.inflate(context, biz.AR.layout.message_center_list_item_like_app_list, null);
                    viewholder = new ViewHolder(view);
                    view.setTag(viewholder);
                } else
                {
                    viewholder = (ViewHolder)view.getTag();
                }
                break;
            case 2://L4_L4:
                if(view == null)
                {
                    view = View.inflate(context, biz.AR.layout.message_center_list_item_comment_lejoy_app, null);
                    viewholder = new ViewHolder(view);
                    view.setTag(viewholder);
                } else
                {
                    viewholder = (ViewHolder)view.getTag();
                }
                break;
            case 3://L5_L5:
                if(view == null)
                {
                    view = View.inflate(context, biz.AR.layout.message_center_list_item_comment, null);
                    viewholder = new ViewHolder(view);
                    view.setTag(viewholder);
                } else
                {
                    viewholder = (ViewHolder)view.getTag();
                }
                break;
           
        }
        
        viewholder.setUi(getItem(i), i);
        return view;

        
    }

    public int getViewTypeCount()
    {
        return 4;
    }

    DataCollectInfo action;
    String amigo;
    Context context;
    ArrayList data;
    private SimpleDateFormat formater;
    DisplayImageOptions ops;


}
