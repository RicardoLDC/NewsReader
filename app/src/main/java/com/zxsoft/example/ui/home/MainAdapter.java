package com.zxsoft.example.ui.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxsoft.example.R;
import com.zxsoft.example.model.bean.News;

import java.util.List;

public class MainAdapter extends BaseQuickAdapter<News.DataBean, BaseViewHolder> {
    public MainAdapter(int layoutResId, List<News.DataBean> data) {
        super(layoutResId, data);

}

    @Override
    protected void convert(BaseViewHolder helper, News.DataBean item) {
        helper.setText(R.id.title1,item.getTitle());
        helper.setText(R.id.source,item.getAuthor_name());
        helper.setText(R.id.comment,item.getDate());
    }

}