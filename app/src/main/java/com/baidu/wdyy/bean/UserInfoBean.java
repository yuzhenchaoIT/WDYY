package com.baidu.wdyy.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 个人信息bean
 *
 * @author lmx
 * @date 2019/1/29
 */

public class UserInfoBean implements Serializable {
    /**
     * headPic : http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg
     * id : 1784
     * lastLoginTime : 1548333051000
     * nickName : 小小程序猿
     * phone : 16619785294
     * sex : 1
     */
    @DatabaseField
    private String headPic;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private long lastLoginTime;
    @DatabaseField
    private String nickName;
    @DatabaseField
    private String phone;
    @DatabaseField
    private int sex;

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
