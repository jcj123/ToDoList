package com.qs.jcj.addlistview.domain;

/**
 * Created by jcj on 16/4/26.
 */
public class Item {
    private int id;
    private String name;
    private int isCompleted;//1为已完成，0为未完成
    private String createDate;//创建日期
    public Item() {
    }

    public Item(String name, int isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}
