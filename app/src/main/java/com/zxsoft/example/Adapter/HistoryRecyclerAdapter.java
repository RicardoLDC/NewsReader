package com.zxsoft.example.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxsoft.example.R;
import com.zxsoft.example.model.bean.Message;
import com.zxsoft.example.model.bean.MessageStore;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> implements View.OnClickListener{
    private static List<MessageStore> mList;
    private Cursor cursor;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public HistoryRecyclerAdapter(List<MessageStore> data, OnItemClickListener clickListener) {
        this.mList=data;
        this.mOnItemClickListener=clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @NonNull
    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item2,parent,false);
        final ViewHolder vh=new ViewHolder(view);
        view.setOnClickListener(this);
        mContext=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.ViewHolder holder, int position) {
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mAuthor.setText(mList.get(position).getAuthor_name());
        holder.mTime.setText(mList.get(position).getDate());
        holder.itemView.setTag(position);
        Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        public TextView mTitle;
        public TextView mAuthor;
        public TextView mTime;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            newsView=itemView;
            mTitle=itemView.findViewById(R.id.title2);
            mAuthor=itemView.findViewById(R.id.source2);
            mTime=itemView.findViewById(R.id.comment2);
            imageView=itemView.findViewById(R.id.image2);
        }
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

}
