package com.zxsoft.example.model.data.home;

import android.support.annotation.NonNull;

import com.zxsoft.example.contract.HomeContract;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class HomeRepository implements HomeContract.HomeDataSourc{

    private static HomeRepository INSTANCE = null;

    private final HomeContract.HomeDataSourc mRemoteDataSource;

    private final HomeContract.HomeDataSourc mLocalDataSource;

    private HomeRepository(@NonNull HomeContract.HomeDataSourc remoteDataSource,
                              @NonNull HomeContract.HomeDataSourc localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static HomeRepository getInstance(HomeContract.HomeDataSourc remoteDataSource,
                                                HomeContract.HomeDataSourc localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new HomeRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    /*
    @Override
    public void getWuZhen(String name, double lat, double lng,HomeCallback callback) {
        mRemoteDataSource.getWuZhen(name,lat,lng,callback);
    }
    */
    @Override
    public void getNews(HomeCallback callback,int id) {
        mRemoteDataSource.getNews(callback,id);
    }
}
