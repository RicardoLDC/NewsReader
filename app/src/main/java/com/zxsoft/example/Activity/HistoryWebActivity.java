package com.zxsoft.example.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zxsoft.example.R;
import com.zxsoft.example.SqliteDataBase.DataBaseHelper;
import com.zxsoft.example.model.bean.MessageTwo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.zxsoft.example.HomeApplication.getContext;

public class HistoryWebActivity extends AppCompatActivity {
    private WebView webView;
    private DataBaseHelper dbHelper=new DataBaseHelper(getContext(),"NewsBase.db",null,3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        webView=findViewById(R.id.web_view);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true)
    public void onEventPost(MessageTwo message) {
        String url=message.getNews().get(message.getPosition()).getUrl();
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        // webView.clearHistory();
        Log.d("HistoryWebActivity: ","历史记录点击事件");

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",message.getNews().get(message.getPosition()).getTitle());
        values.put("uniqueKey",message.getNews().get(message.getPosition()).getUniquekey());
        values.put("author_name",message.getNews().get(message.getPosition()).getAuthor_name());
        values.put("category",message.getNews().get(message.getPosition()).getCategory());
        values.put("date",message.getNews().get(message.getPosition()).getDate());
        values.put("url",message.getNews().get(message.getPosition()).getUrl());
        values.put("thumbnail_pic_s",message.getNews().get(message.getPosition()).getThumbnail_pic_s());
        values.put("thumbnail_pic_s02",message.getNews().get(message.getPosition()).getThumbnail_pic_s02());
        values.put("thumbnail_pic_s03",message.getNews().get(message.getPosition()).getThumbnail_pic_s03());
        //if(db.query("PostMessage",values));
        //db.update("PostMessage",values,null,null);
        db.delete("PostMessage","uniquekey like ?",new String[]{message.getNews().get(message.getPosition()).getUniquekey()});
        db.replace("PostMessage",null,values);
        values.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
