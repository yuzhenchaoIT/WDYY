package com.baidu.wdyy.bean;



public class UserInfo {

    /**
     * sessionId : 15482980027771784
     * userId : 1784
     * userInfo : {"headPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","id":1784,"lastLoginTime":1548297777000,"nickName":"小小程序猿","phone":"16619785294","sex":1}
     */

    private String sessionId;
    private int userId;
    private UserInfoBean userInfo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }


}
