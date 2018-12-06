package com.zxsoft.example.presenter;

import com.zxsoft.example.contract.HomeContract;
import com.zxsoft.example.model.bean.News;
import com.zxsoft.example.model.data.home.HomeRepository;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter{
    private HomeContract.View mView;
    private HomeRepository mRepository;

    public HomePresenter(HomeContract.View view, HomeRepository homeRepository) {
        this.mView = view;
        this.mRepository = homeRepository;
//        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getNews(final int id) {
        mRepository.getNews(new HomeContract.HomeDataSourc.HomeCallback() {
            /*
            @Override
            public void getWuzhenSuccess(List<String> strings) {

            }
            */
            @Override
            public void getNewsSuccess(List<News.DataBean> strings,int id) {
                mView.getNewsSuccess(strings,id);
            }

            @Override
            public void error(String msg) {
                mView.error(msg);
            }
        },id);
    }
}
