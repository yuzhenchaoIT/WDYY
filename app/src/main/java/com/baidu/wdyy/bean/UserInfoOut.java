package com.baidu.wdyy.bean;

public class UserInfoOut {

    private String sessionId;

    private int userId;

    private UserInfo userInfo;

    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }
    public String getSessionId(){
        return this.sessionId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
    }
    public UserInfo getUserInfo(){
        return this.userInfo;
    }
}
