package com.baidu.wdyy.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.wdyy.bean.UserInfo;
import com.baidu.wdyy.bean.UserInfoBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {


    public DBHelper(Context context) {
        super(context, "user", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            // 建表
            TableUtils.createTable(connectionSource,UserInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
