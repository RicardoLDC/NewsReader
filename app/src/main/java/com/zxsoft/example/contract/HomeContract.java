package com.zxsoft.example.contract;

import com.example.library.base.BaseCallback;
import com.example.library.base.BasePresenter;
import com.example.library.base.BaseView;
import com.zxsoft.example.model.bean.News;

import java.util.List;

public class HomeContract {
    public interface View extends BaseView<Presenter>{

        void getNewsSuccess(List<News.DataBean> strings,int id);
    }

    public interface Presenter extends BasePresenter{

        void getNews(int id);
    }

    public interface HomeDataSourc{
        interface HomeCallback extends BaseCallback {

            void getNewsSuccess(List<News.DataBean> strings,int id);
        }
        void getNews(HomeCallback callback,int id);
    }

}
