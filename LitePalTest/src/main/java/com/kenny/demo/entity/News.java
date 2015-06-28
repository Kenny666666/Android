package com.kenny.demo.entity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 新闻实体类
 * Created by kenny on 2015/6/23.
 */
public class News extends DataSupport{//实体类继承DataSupport后就会有CRUD操作的能力了
    //其中id这个字段可写可不写，因为即使不写这个字段，LitePal也会在表中自动生成一个id列，毕竟每张表都一定要有主键的嘛。
    //这里我要特别说明一下，LitePal的映射规则是非常轻量级的，不像一些其它的数据库框架，需要为每个模型类单独配置一个映射关系的XML，LitePal的所有映射都是自动完成的。根据LitePal的数据类型支持，可以进行对象关系映射的数据类型一共有8种，int、short、long、float、double、boolean、String和Date。只要是声明成这8种数据类型的字段都会被自动映射到数据库表中，并不需要进行任何额外的配置。
    //那么有的朋友可能会问了，既然是自动映射的话，如果News类中有一个字符串字段我并不想让它映射到数据库表中，这该怎么办呢？对此，LitePal同样采用了一种极为轻量的解决方案，只有声明成private修饰符的字段才会被映射到数据库表中，如果你有某一个字段不想映射的话，只需要将它改成public、protected或default修饰符就可以了。

    private int id;
    private String title;
    private String content;
    private Date publishData;
    private int commentCount;
    //新闻的导语/摘要   (News与Introduction实现一对一关系)
    private Introduction introduction;
    //新闻的评论   (News与Comment实现一对多关系)，还需要在Comment类中引入News哦
    private List<Comment> commentList = new ArrayList<>();
    //新闻的类别   (News与Category实现多对多关系)，还需要在Category类中引入News哦
    private List<Category> categoryList = new ArrayList<>();

    public List<Comment> getComments() {
         return DataSupport.where("news_id = ?", String.valueOf(id)).find(Comment.class);
    }

    public String tostring(News news){
        if (news!=null){
            return "id="+news.getId() + "\t title="+news.getTitle() +"\t publishData="+news.getPublishData()+"\t commentcount="+news.getCommentCount();
        }else {
            return "对象为空";
        }
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Introduction getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Introduction introduction) {
        this.introduction = introduction;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishData() {
        return publishData;
    }

    public void setPublishData(Date publishData) {
        this.publishData = publishData;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
