package com.zxsoft.example.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxsoft.example.R;
import com.zxsoft.example.model.bean.News;

import java.util.List;

import static android.util.Base64.DEFAULT;
import static android.util.Base64.decode;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private List<News.DataBean> datas;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public RecyclerAdapter(List<News.DataBean> datas, OnItemClickListener clickListener) {
        this.datas = datas;
        this.mOnItemClickListener = clickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

//    private OnItemClickListener mOnItemClickListener = null;

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item2, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        mContext=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTitle.setText(datas.get(position).getTitle());
        holder.mAuthor.setText(datas.get(position).getAuthor_name());
        holder.mTime.setText(datas.get(position).getDate());
        holder.itemView.setTag(position);
        Glide.with(mContext).load(datas.get(position).getThumbnail_pic_s()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder,持有每个item的界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        public TextView mTitle;
        public TextView mAuthor;
        public TextView mTime;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            mTitle = itemView.findViewById(R.id.title2);
            mAuthor = itemView.findViewById(R.id.source2);
            mTime = itemView.findViewById(R.id.comment2);
            imageView=itemView.findViewById(R.id.image2);
        }

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mOnItemClickListener=listener;
//    }
}
