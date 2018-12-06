package com.zxsoft.example.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zxsoft.example.Adapter.RecyclerAdapter;
import com.zxsoft.example.HomeApplication;
import com.zxsoft.example.R;
import com.zxsoft.example.Activity.WebViewActivity;
import com.zxsoft.example.SqliteDataBase.DataBaseHelper;
import com.zxsoft.example.contract.HomeContract;
import com.zxsoft.example.model.bean.Message;
import com.zxsoft.example.model.bean.News;
import com.zxsoft.example.model.data.home.HomeLocalDataSource;
import com.zxsoft.example.model.data.home.HomeRemoteDataSource;
import com.zxsoft.example.model.data.home.HomeRepository;
import com.zxsoft.example.presenter.HomePresenter;

import org.greenrobot.eventbus.EventBus;
//import org.litepal.LitePal;

import java.util.List;

import static com.zxsoft.example.HomeApplication.getContext;

public class Fragment01 extends Fragment implements HomeContract.View {
    private View view;
    private HomePresenter homePresenter;
    private RecyclerAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    //private WebView webView=null;
    //private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState) {
        view=inflater.inflate(R.layout.recyclerview,container,false);
        recyclerView=view.findViewById(R.id.recyclerview);
        mLayoutManager=new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(mLayoutManager);
        //设置每个item的高度是固定的
        //homePresenter=new HomePresenter(getActivity(),)
        homePresenter = new HomePresenter(this, HomeRepository.getInstance(HomeRemoteDataSource.getInstance(), HomeLocalDataSource.getInstance(HomeApplication.getContext())));
        homePresenter.getNews(0);
        //下拉刷新
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Context context = getContext();
                Toast.makeText(context, "onRefresh there", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
                homePresenter.getNews(0);
            }
        });
        return view;
    }

    //怎么将数据导入数据库
    @Override
    public void getNewsSuccess(final List<News.DataBean> message, int id) {
        //insertData(message);
        mAdapter = new RecyclerAdapter(message, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().postSticky(new Message(message,position));
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                startActivity(intent);
            }

        });
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                EventBus.getDefault().postSticky(new Message(message,position));
//
//                Intent intent=new Intent(getActivity(), WebViewActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void error(String msg) {

    }

}
