package com.kenny.demo.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2015/6/23.
 */
public class Introduction extends DataSupport {//实体类继承DataSupport后就会有CRUD操作的能力了
    private int id;
    //新闻导语
    private String guide;
    //新闻摘要

    private String digest;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
