package com.baidu.wdyy.bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 个人信息bean
 *
 * @author lmx
 * @date 2019/1/29
 */
@DatabaseTable(tableName = "user")
public class UserInfo {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String sessionId;
    @DatabaseField
    private int userId;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
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
