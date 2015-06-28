package com.kenny.demo.entity;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * 新闻评论实体类
 * Created by kenny on 2015/6/23.
 */
public class Comment extends DataSupport{//实体类继承DataSupport后就会有CRUD操作的能力了
    private int id;
    private String content;
    private Date publishDate;
    //评论的新闻   (News与Comment实现一对多关系)，还需要在News类中引入Comment哦
    private News news;

    public String tostring(Comment comment){
        if (comment!=null){
            return "id="+comment.getId() + "\t content="+comment.getContent() +"\t publishData="+comment.getPublishDate();
        }else {
            return "对象为空";
        }
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
