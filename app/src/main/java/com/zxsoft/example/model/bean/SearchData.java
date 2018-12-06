package com.zxsoft.example.model.bean;

import java.util.List;

/**
 * @class describe:描述
 * @anthor chenyx
 * @time 2018/03/13 13:45
 * @chang 2018/03/13 13:45
 */
public class SearchData {


    private List<String> ids;
    private List<Poi> datas;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<Poi> getDatas() {
        return datas;
    }

    public void setDatas(List<Poi> datas) {
        this.datas = datas;
    }

    public SearchData(List<String> ids, List<Poi> datas) {
        this.ids = ids;
        this.datas = datas;
    }

    public SearchData() {
    }
}
