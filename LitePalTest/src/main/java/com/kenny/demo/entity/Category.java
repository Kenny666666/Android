package com.kenny.demo.entity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻类别表
 * Created by kenny on 2015/6/23.
 */
public class Category extends DataSupport{//实体类继承DataSupport后就会有CRUD操作的能力了
    private int id;
    private String name;
    //(News与Category实现多对多关系)，还需要在News类中引入Category哦
    private List<News> newsList = new ArrayList<>();

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
