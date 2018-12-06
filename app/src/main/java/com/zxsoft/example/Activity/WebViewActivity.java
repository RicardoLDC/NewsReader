package com.zxsoft.example.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zxsoft.example.R;
import com.zxsoft.example.SqliteDataBase.DataBaseHelper;
import com.zxsoft.example.model.bean.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
//import org.litepal.LitePal;
//import org.litepal.tablemanager.Connector;

import static com.zxsoft.example.HomeApplication.getContext;


public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private DataBaseHelper dbHelper=new DataBaseHelper(getContext(),"NewsBase.db",null,3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        webView=findViewById(R.id.web_view);
        EventBus.getDefault().register(this);

        //LitePal.initialize(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMessage(Message message) {
        String url = message.getNews().get(message.getPosition()).getUrl();
        //String title=message.getNews().get(message.getPosition()).getTitle();
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        //webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl(url);
        //   webView.clearHistory()
        //存储数据
        Log.d("WebViewActivity: ","首页fragment点击事件");
//            PostMessage news = new PostMessage();
//            news.setTitle(message.getNews().get(message.getPosition()).getTitle());
//            news.setAuthor_name(message.getNews().get(message.getPosition()).getAuthor_name());
//            news.setDate(message.getNews().get(message.getPosition()).getDate());
//            news.setCategory(message.getNews().get(message.getPosition()).getCategory());
//            news.setUrl(message.getNews().get(message.getPosition()).getUrl());
//            news.setThumbnail_pic_s(message.getNews().get(message.getPosition()).getThumbnail_pic_s());
//            news.setUniquekey(message.getNews().get(message.getPosition()).getUniquekey());
//            news.save();

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
        CookieSyncManager.createInstance(getContext());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
