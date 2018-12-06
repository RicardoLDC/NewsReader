package com.zxsoft.example.fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxsoft.example.Adapter.HistoryRecyclerAdapter;
import com.zxsoft.example.Activity.HistoryWebActivity;
import com.zxsoft.example.R;
import com.zxsoft.example.SetList;
import com.zxsoft.example.SqliteDataBase.DataBaseHelper;
import com.zxsoft.example.Activity.WebViewActivity;
import com.zxsoft.example.model.bean.Message;
import com.zxsoft.example.model.bean.MessageStore;
import com.zxsoft.example.model.bean.MessageTwo;
import com.zxsoft.example.model.bean.News.DataBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
//import org.litepal.LitePal;
//import org.litepal.crud.LitePalSupport;
//import org.litepal.tablemanager.Connector;

import java.util.Collections;
import java.util.List;

public class HistoryFragment extends Fragment {
    private View view;
    private HistoryRecyclerAdapter mAdapter;
    private HistoryRecyclerAdapter hAdapter;
    private LinearLayoutManager mLayoutManager;
    RecyclerView reView;
    static List<MessageStore> list=new SetList<>();
    List<DataBean> mdata=new SetList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        EventBus.getDefault().register(this);
        Log.d("HistoryFragment点击事件","1");

        view=inflater.inflate(R.layout.frag_history,container,false);
        reView=view.findViewById(R.id.recordRecycler);
        mLayoutManager=new LinearLayoutManager(getActivity());
        reView.setLayoutManager(mLayoutManager);
        //删除历史记录
//        DataBaseHelper dataBaseHelper= new DataBaseHelper(getContext(),"NewsBase.db",null,3);
//        SQLiteDatabase db=dataBaseHelper.getReadableDatabase();
//          db.delete("PostMessage",null,null);
//        Cursor cursor=db.query("PostMessage",null,null,null,null,null,null);
//
//        cursor.moveToFirst();
//        if(cursor.getCount()!=0) {
//            do {
//                MessageStore ms = new MessageStore();
//                ms.setUniquekey(cursor.getString(cursor.getColumnIndex("uniquekey")));
//                ms.setTitle(cursor.getString(cursor.getColumnIndex("title")));
//                ms.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
//                ms.setCategory(cursor.getString(cursor.getColumnIndex("category")));
//                ms.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s")));
//                ms.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02")));
//                ms.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03")));
//                ms.setDate(cursor.getString(cursor.getColumnIndex("date")));
//                ms.setUrl(cursor.getString(cursor.getColumnIndex("url")));
//                list.add(ms);
//            } while (cursor.moveToNext());
//        }
//        hAdapter = new HistoryRecyclerAdapter(list, new HistoryRecyclerAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    Log.d("HistoryFragment","点击事件1");
//                    EventBus.getDefault().postSticky(new MessageTwo(list, position));
//                    Intent intent=new Intent(getActivity(), HistoryWebActivity.class);
//                    startActivity(intent);
//                }
//        });
//        //}
//        reView.setAdapter(hAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("HistoryFragment","onStart()");
        Log.d("HistoryFragment","加载数据");
        DataBaseHelper dataBaseHelper= new DataBaseHelper(getContext(),"NewsBase.db",null,3);
        SQLiteDatabase db=dataBaseHelper.getReadableDatabase();
        //Cursor cursor=db.query("PostMessage",null,null,null,null,null,null);
        Cursor cursor=db.query(true,"PostMessage",new String[]{"uniquekey","title","date","category","author_name","url","thumbnail_pic_s","thumbnail_pic_s02","thumbnail_pic_s03"},null,null,null,null,null,null);
        //cursor.moveToFirst();
        list.clear();
        if(cursor.getCount()!=0) {
            Log.d("CURSOR","NOT NULL");
        }
        while(cursor.moveToNext()) {
                MessageStore ms = new MessageStore();
                ms.setUniquekey(cursor.getString(cursor.getColumnIndex("uniquekey")));
                ms.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                ms.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                ms.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                ms.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s")));
                ms.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02")));
                ms.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03")));
                ms.setDate(cursor.getString(cursor.getColumnIndex("date")));
                ms.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                list.add(ms);
        }
        cursor.close();
        Collections.reverse(list);
        hAdapter = new HistoryRecyclerAdapter(list, new HistoryRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //list=LitePal.select("title","author_name","date","thumbnail_pic_s","url").order("date desc").find(PostMessage.class);
                Log.d("HistoryFragment","点击事件2");
                EventBus.getDefault().postSticky(new MessageTwo(list, position));
                Intent intent=new Intent(getActivity(), HistoryWebActivity.class);
                startActivity(intent);
            }
        });

        reView.setAdapter(hAdapter);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMessage(Message message) {
        Log.d("HistoryFragment","已接收");
        DataBean news=message.getNews().get(message.getPosition());
        //News a=new News();
        //list=LitePal.select("title","author_name","date","thumbnail_pic_s","url").order("date desc").find(PostMessage.class);
        Log.d("message",message.getNews().get(message.getPosition()).getUniquekey());
        DataBaseHelper dataBaseHelper= new DataBaseHelper(getContext(),"NewsBase.db",null,3);
        SQLiteDatabase db=dataBaseHelper.getReadableDatabase();

       // a.setPostMessages(list);
       // mdata=a.PostMessageToDataBean(list);
       // mdata.add(news);
        //mAdapter=new HistoryRecyclerAdapter(list);
        mAdapter=new HistoryRecyclerAdapter(list, new HistoryRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 给webActivity传递消息列表和点击的序号
                EventBus.getDefault().postSticky(new MessageTwo(list,position));
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                startActivity(intent);
            }
        });
        Cursor cursor=db.query(true,"PostMessage",new String[]{"uniquekey","title","date","category","author_name","url","thumbnail_pic_s","thumbnail_pic_s02","thumbnail_pic_s03"},null,null,null,null,null,null);
        if(cursor.getCount()==0)
            Log.d("cursor","NULL");
        else
            Log.d("cursor","Not NULL");
        while(cursor.moveToNext()) {
            MessageStore ms = new MessageStore();
            ms.setUniquekey(cursor.getString(cursor.getColumnIndex("uniquekey")));
            ms.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            ms.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
            ms.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            ms.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s")));
            ms.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02")));
            ms.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03")));
            ms.setDate(cursor.getString(cursor.getColumnIndex("date")));
            ms.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            list.add(ms);
        }
        cursor.close();

        reView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
