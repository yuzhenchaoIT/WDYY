package com.baidu.wdyy.core.db;

import android.content.Context;

import com.baidu.wdyy.bean.UserInfoBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class DBDao {

    private Dao<UserInfoBean, String> dao;

    public DBDao(Context context) throws SQLException {
        DBHelper helper = new DBHelper(context);
        dao = helper.getDao(UserInfoBean.class);
    }

    // 创建数据
    public void insertStudent(UserInfoBean user) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.createOrUpdate(user);
    }

    public void batchInsert(List<UserInfoBean> users) throws SQLException {
        dao.create(users);
    }


    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<UserInfoBean> getUser() throws SQLException {
        List<UserInfoBean> list = dao.queryForAll();
        return list;
    }

    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<UserInfoBean> queryUser() throws SQLException {
        //Eq是equals的缩写
        //方法1
//        List<UserInfoBean> list = dao.queryForEq("nickName", "张飞");
        List<UserInfoBean> list = dao.queryForAll();
        return list;
    }

    /**
     * 删除数据
     *
     * @param user
     * @throws SQLException
     */
    public void deleteUser(UserInfoBean user) throws SQLException {
        //只看id
        dao.delete(user);
    }

    /**
     * 删除指定数据
     *
     * @throws SQLException
     */
    public void deleteGuanyu() throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("nickName", "关羽");
        deleteBuilder.delete();
    }

    /**
     * 修改数据
     *
     * @param user
     * @throws SQLException
     */
    public void updateUser(UserInfoBean user) throws SQLException {
        user.setNickName("关羽");
        dao.update(user);
    }


}
