package com.zxsoft.example.model.bean;

import java.util.List;

public class Message {

    List<News.DataBean> news;
    int position;
    public Message(List<News.DataBean>news,int position) {
        this.news=news;
        this.position=position;
    }
    public Message() {

    }
    public List<News.DataBean> getNews() {
        return news;
    }
    public int getPosition() {
        return position;
    }
}
