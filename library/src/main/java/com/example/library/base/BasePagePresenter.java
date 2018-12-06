package com.example.library.base;

/**
 * @author chenyx
 * @date create 2017/9/11
 * @description
 */
public class BasePagePresenter {
    protected int pageSize = 10;
    private int pageIndex = 0;
    protected int totalPage = 10;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void addPageIndex() {
        pageIndex++;
    }

}
