package com.dominic.googleplay.bean;

import java.util.List;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppDetailBean {

    /**
     * id : 1631363
     * name : 黑马程序员
     * packageName : com.itheima.www
     * iconUrl : app/com.itheima.www/icon.jpg
     * stars : 5.0
     * downloadNum : 40万+
     * version : 1.1.0605.0
     * date : 2015-06-10
     * size : 91767
     * downloadUrl : app/com.itheima.www/com.itheima.www.apk
     * des : 黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员黑马程序员
     * author : 黑马程序员
     * screen : ["app/com.itheima.www/screen0.jpg","app/com.itheima.www/screen1.jpg","app/com.itheima.www/screen2.jpg","app/com.itheima.www/screen3.jpg","app/com.itheima.www/screen4.jpg"]
     * safe : [{"safeUrl":"app/com.itheima.www/safeIcon0.jpg","safeDesUrl":"app/com.itheima.www/safeDesUrl0.jpg","safeDes":"已通过安智市场安全检测，请放心使用","safeDesColor":0},{"safeUrl":"app/com.itheima.www/safeIcon1.jpg","safeDesUrl":"app/com.itheima.www/safeDesUrl1.jpg","safeDes":"无任何形式的广告","safeDesColor":0}]
     */

    public int id;
    public String name;
    public String packageName;
    public String iconUrl;
    public double stars;
    public String downloadNum;
    public String version;
    public String date;
    public int size;
    public String downloadUrl;
    public String des;
    public String author;
    public List<String> screen;
    public List<SafeBean> safe;

    public static class SafeBean {
        /**
         * safeUrl : app/com.itheima.www/safeIcon0.jpg
         * safeDesUrl : app/com.itheima.www/safeDesUrl0.jpg
         * safeDes : 已通过安智市场安全检测，请放心使用
         * safeDesColor : 0
         */

        public String safeUrl;
        public String safeDesUrl;
        public String safeDes;
        public int safeDesColor;
    }
}
