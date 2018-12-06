package com.zxsoft.example.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxsoft.example.model.bean.News;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter {
    private List<News.DataBean> messageList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView messageImage;
        TextView messageTitle;
        TextView messageText;
        public ViewHolder(View view) {
            super(view);

        }
    }
}
