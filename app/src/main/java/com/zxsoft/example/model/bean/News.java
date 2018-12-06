package com.zxsoft.example.model.bean;

import com.zxsoft.example.SetList;

//import org.litepal.LitePal;
//import org.litepal.crud.DataSupport;
//import org.litepal.crud.LitePalSupport;

import java.util.List;

public class News{

    /**
     * stat : 1
     * data : [{"uniquekey":"399652a4e39cf580a21c9bd6343281b7","title":"生活中的科学减压方式","date":"2018-07-06 08:47","category":"头条","author_name":"健康一线","url":"http://mini.eastday.com/mobile/180706084735246.html","thumbnail_pic_s":"http://01.imgmini.eastday.com/mobile/20180706/20180706_dcebd9551d8868a173008fb842b3f96a_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://01.imgmini.eastday.com/mobile/20180706/20180706_5944d95c34619c3cc7d145739ca8efbc_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://01.imgmini.eastday.com/mobile/20180706/20180706_78cd95b351b8d44e54b12262ab36e9fe_cover_mwpm_03200403.jpg"}]
     */

    private List<MessageStore> postMessages=new SetList<>();
    private String stat;
    private List<DataBean> data=new SetList<>();

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setPostMessages(List<MessageStore> data) {
        this.postMessages=data;
    }
    public List<DataBean> PostMessageToDataBean(List<MessageStore> data) {
        if(data.size()==0) {
            this.data.clear();
            return this.data;
        }
        this.data.clear();
        for(int i=0;i<data.size();i++) {
            this.data.get(i).setTitle(data.get(i).getTitle());
            this.data.get(i).setUniquekey(data.get(i).getUniquekey());
            this.data.get(i).setThumbnail_pic_s(data.get(i).getThumbnail_pic_s());
            this.data.get(i).setUrl(data.get(i).getUrl());
            this.data.get(i).setCategory(data.get(i).getCategory());
            this.data.get(i).setDate(data.get(i).getDate());
            this.data.get(i).setAuthor_name(data.get(i).getAuthor_name());

            this.data.get(i).setThumbnail_pic_s02(data.get(i).getThumbnail_pic_s02());
            this.data.get(i).setThumbnail_pic_s03(data.get(i).getThumbnail_pic_s03());
        }

        return this.data;
    }
    public List<MessageStore> getPostMessages() {
        return postMessages;
    }

    public static class DataBean {
        /**
         * uniquekey : 399652a4e39cf580a21c9bd6343281b7
         * title : 生活中的科学减压方式
         * date : 2018-07-06 08:47
         * category : 头条
         * author_name : 健康一线
         * url : http://mini.eastday.com/mobile/180706084735246.html
         * thumbnail_pic_s : http://01.imgmini.eastday.com/mobile/20180706/20180706_dcebd9551d8868a173008fb842b3f96a_cover_mwpm_03200403.jpg
         * thumbnail_pic_s02 : http://01.imgmini.eastday.com/mobile/20180706/20180706_5944d95c34619c3cc7d145739ca8efbc_cover_mwpm_03200403.jpg
         * thumbnail_pic_s03 : http://01.imgmini.eastday.com/mobile/20180706/20180706_78cd95b351b8d44e54b12262ab36e9fe_cover_mwpm_03200403.jpg
         */

        private String uniquekey;
        private String title;
        private String date;
        private String category;
        private String author_name;

        private String url;
        private String thumbnail_pic_s;
        private String thumbnail_pic_s02;
        private String thumbnail_pic_s03;
        public String getUniquekey() {
            return uniquekey;
        }

        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }

        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }
    }
}
