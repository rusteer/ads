// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gionee.aora.market.module.Comment;

public class IntroductionCommentAdapter extends BaseAdapter {
    static class ViewHolder {
        public RatingBar comment_RatingBar;
        public TextView comment_content;
        public TextView comment_date;
        public TextView comment_reviewer;
        public TextView model_number;
        public ViewHolder(View view) {
            comment_reviewer = (TextView) view.findViewById(biz.AR.id.comment_reviewer);
            comment_date = (TextView) view.findViewById(biz.AR.id.comment_date);
            comment_content = (TextView) view.findViewById(biz.AR.id.comment_content);
            model_number = (TextView) view.findViewById(biz.AR.id.model_number);
            comment_RatingBar = (RatingBar) view.findViewById(biz.AR.id.comment_RatingBar);
        }
    }
    private List comments;
    private Context context;
    public IntroductionCommentAdapter(Context context1, List list) {
        comments = list;
        context = context1;
    }
    public List getComments() {
        return comments;
    }
    @Override
    public int getCount() {
        if (comments == null) return 0;
        else return comments.size();
    }
    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        ViewHolder viewholder;
        Comment comment;
        if (view == null) {
            RelativeLayout relativelayout = (RelativeLayout) View.inflate(context, biz.AR.layout.comments_item, null);
            viewholder = new ViewHolder(relativelayout);
            relativelayout.setTag(viewholder);
            view = relativelayout;
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        comment = (Comment) comments.get(i);
        viewholder.comment_reviewer.setText(comment.getReviewer());
        viewholder.comment_date.setText(comment.getDate());
        viewholder.comment_content.setText(comment.getContent());
        viewholder.model_number.setText(comment.getModel_number());
        viewholder.comment_RatingBar.setRating(comment.getRating());
        return view;
    }
    public void setComments(List list) {
        comments = list;
    }
}
