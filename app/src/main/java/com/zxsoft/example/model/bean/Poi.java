package com.zxsoft.example.model.bean;

/**
 * @class describe:描述
 * @anthor chenyx
 * @time 2018/03/13 13:41
 * @chang 2018/03/13 13:41
 */
public class Poi {
    private String id;// 坐标表主键
    private String categoryId;// 分类id
    private String categoryName;//分类名称
    private String categoryUrl;//分类图片
    private String lon;//经度
    private String lat;//纬度
    private String name;//名称
    private String address;//地址
    private String imageUrl;//图片URL
    private int distance;//距离

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Poi(String id, String categoryId, String categoryName, String name, String address, String imageUrl, int distance) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.distance = distance;
    }

    public Poi() {
    }
}
