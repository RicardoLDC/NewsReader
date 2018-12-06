package com.example.library.base;

/**
 * @author chenyx
 * @date create 2017/9/11
 * @description
 */
public interface BaseView<T> {
    void setPresenter(T presenter);

    void error(String msg);
}
