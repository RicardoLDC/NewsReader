package com.zxsoft.example.model.bean;

import java.util.List;

public class MessageTwo {
    List<MessageStore> news;
    int position;
    public MessageTwo(List<MessageStore>news,int position) {
        this.news=news;
        this.position=position;
    }
    public List<MessageStore> getNews() {
        return news;
    }
    public int getPosition() {
        return position;
    }
}
