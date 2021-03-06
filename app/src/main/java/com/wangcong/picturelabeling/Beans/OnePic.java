package com.wangcong.picturelabeling.Beans;

/**
 * Created by 13307 on 2017/5/3.
 */

public class OnePic {

    private String id;//图片ID
    private String path;//图片路径
    private String recommends;//该图片的推荐标签

    public OnePic(String a, String b, String c) {
        id = a;
        path = b;
        recommends = c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRecommends() {
        return recommends;
    }

    public void setRecommends(String recommends) {
        this.recommends = recommends;
    }

}
