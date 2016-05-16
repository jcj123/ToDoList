package com.qs.jcj.addlistview.domain;

/**
 * Created by jcj on 16/4/26.
 * 待办事项的每一个条目
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

    public Item(int id, String name, int isCompleted, String createDate) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        this.createDate = createDate;
    }

    public Item(String name, int isCompleted, String createDate) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.createDate = createDate;
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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isCompleted=" + isCompleted +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
