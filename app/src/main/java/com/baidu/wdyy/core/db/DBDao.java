package com.baidu.wdyy.core.db;

import android.content.Context;

import com.baidu.wdyy.bean.UserInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * 数据库dao层
 *
 * @author lmx
 * @date 2019/1/29
 */
public class DBDao {

    private static DBDao mDBDao;
    private Dao<UserInfo, String> userDao;

    private DBDao(Context context) {
        DBHelper helper = new DBHelper(context);
        try {
            userDao = helper.getDao(UserInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBDao getInstance(Context context) {
        if (mDBDao == null) {
            mDBDao = new DBDao(context);
        }
        return mDBDao;
    }

    public Dao<UserInfo, String> getUserDao() {
        return userDao;
    }


}
