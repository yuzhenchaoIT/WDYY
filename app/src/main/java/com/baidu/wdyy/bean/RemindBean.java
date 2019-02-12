package com.baidu.wdyy.bean;

public class RemindBean {

    private String content;

    private int id;

    private int pushTime;

    private int status;

    private String title;

    private int userId;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setPushTime(int pushTime) {
        this.pushTime = pushTime;
    }

    public int getPushTime() {
        return this.pushTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }
}
