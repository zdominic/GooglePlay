package com.dominic.googleplay.bean;

import java.util.List;

/**
 * Created by Dominic on 2017/2/16.
 */

public class CategoryBean {

    /**
     * title : 游戏
     * infos : [{"url1":"image/category_game_0.jpg","url2":"image/category_game_1.jpg","url3":"image/category_game_2.jpg","name1":"休闲","name2":"棋牌","name3":"益智"},{"url1":"image/category_game_3.jpg","url2":"image/category_game_4.jpg","url3":"image/category_game_5.jpg","name1":"射击","name2":"体育","name3":"儿童"},{"url1":"image/category_game_6.jpg","url2":"image/category_game_7.jpg","url3":"image/category_game_8.jpg","name1":"网游","name2":"角色","name3":"策略"},{"url1":"image/category_game_9.jpg","url2":"image/category_game_10.jpg","url3":"","name1":"经营","name2":"竞速","name3":""}]
     */

    public String title;
    public List<InfosBean> infos;

    public static class InfosBean {
        /**
         * url1 : image/category_game_0.jpg
         * url2 : image/category_game_1.jpg
         * url3 : image/category_game_2.jpg
         * name1 : 休闲
         * name2 : 棋牌
         * name3 : 益智
         */

        public String url1;
        public String url2;
        public String url3;
        public String name1;
        public String name2;
        public String name3;
    }
}
