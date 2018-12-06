package com.zxsoft.example.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.zxsoft.example.Activity.SearchViewActivity;
import com.zxsoft.example.Adapter.ViewPagerAdapter;
import com.zxsoft.example.HomeApplication;
import com.zxsoft.example.R;
import com.zxsoft.example.contract.HomeContract;
import com.zxsoft.example.model.bean.News;
import com.zxsoft.example.model.data.home.HomeLocalDataSource;
import com.zxsoft.example.model.data.home.HomeRemoteDataSource;
import com.zxsoft.example.model.data.home.HomeRepository;
import com.zxsoft.example.presenter.HomePresenter;
import com.zxsoft.example.ui.home.MainActivity;
import com.zxsoft.example.ui.home.MainAdapter;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends Fragment implements HomeContract.View{
    private View view;
    private TabLayout tab_essence;
    private ViewPager vp_essence;
    private ViewPagerAdapter mAdapter;
    private HomePresenter homePresenter;
    private List<Fragment> mFragments=new ArrayList<>();
    private LinearLayout LL2;
//    @BindView(R.id.sv_search_text)
////    SearchView search_text;
    private EditText input;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        initContentView(view);
        initViewPager();
       // homePresenter = new HomePresenter(this, HomeRepository.getInstance(HomeRemoteDataSource.getInstance(), HomeLocalDataSource.getInstance(HomeApplication.getContext())));
        return view;
    }

    private void initViewPager() {
        //初始化布局

        Fragment01 frag1=new Fragment01();
        Fragment02 frag2=new Fragment02();
        Fragment03 frag3=new Fragment03();
        Fragment04 frag4=new Fragment04();
        Fragment05 frag5=new Fragment05();
        Fragment06 frag6=new Fragment06();
        Fragment07 frag7=new Fragment07();
        Fragment08 frag8=new Fragment08();
        Fragment09 frag9=new Fragment09();
        Fragment10 frag10=new Fragment10();
        mFragments.add(frag1);mFragments.add(frag2);
        mFragments.add(frag3);mFragments.add(frag4);
        mFragments.add(frag5);mFragments.add(frag6);
        mFragments.add(frag7);mFragments.add(frag8);
        mFragments.add(frag9);mFragments.add(frag10);

        mAdapter=new ViewPagerAdapter(getChildFragmentManager(),mFragments);
        vp_essence.setAdapter(mAdapter);
        tab_essence.setupWithViewPager(this.vp_essence);
    }

    //初始化搜索框，实例化tablayout+viewpager
    public void initContentView(View viewContent) {
        input=view.findViewById(R.id.inputEditText);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SearchViewActivity.class);
                startActivity(intent);
            }
        });
        this.tab_essence=(TabLayout)view.findViewById(R.id.tab_essence);
        this.vp_essence=(ViewPager)view.findViewById(R.id.vp_essence);
    }

    @Override
    public void getNewsSuccess(List<News.DataBean> message,int id) {

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void error(String msg) {

    }
}
