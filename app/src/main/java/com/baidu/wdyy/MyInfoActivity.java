package com.baidu.wdyy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.wdyy.bean.UserInfo;
import com.baidu.wdyy.bean.UserInfoBean;
import com.baidu.wdyy.core.db.DBDao;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baidu.wdyy.core.app.WDYYApp.getContext;

/**
 * 我的信息页面
 *
 * @author lmx
 * @date 2019/2/13
 */
public class MyInfoActivity extends AppCompatActivity {

    @BindView(R.id.my_info_head_pic)
    SimpleDraweeView mMyInfoHeadPic;
    @BindView(R.id.my_info_nick_name)
    TextView mMyInfoNickName;
    @BindView(R.id.my_info_sex)
    TextView mMyInfoSex;
    @BindView(R.id.my_info_phone)
    TextView mMyInfoPhone;
    @BindView(R.id.my_info_mail)
    TextView mMyInfoMail;
    @BindView(R.id.my_info_pwd_restart)
    ImageView mMyInfoPwdRestart;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.my_info_birth)
    TextView mMyInfoBirth;
    private DBDao dbDao = DBDao.getInstance(getContext());
    //性别默认值
    private String sex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);

        try {
            Dao<UserInfo, String> userDao = dbDao.getUserDao();
            //查询用户
            List<UserInfo> userInfos = userDao.queryForAll();
            UserInfo userInfo = userInfos.get(0);
            UserInfoBean userInfoBean = userInfo.getUserInfo();
            mMyInfoHeadPic.setImageURI(Uri.parse(userInfoBean.getHeadPic()));
            mMyInfoNickName.setText(userInfoBean.getNickName());
            int userInfoSex = userInfoBean.getSex();
            //性别
            if (userInfoSex == 1) {
                sex.equals("男");
            } else if (userInfoSex == 2) {
                sex.equals("女");
            }
            mMyInfoSex.setText(sex);
            mMyInfoBirth.setText("1997-09-06");
            mMyInfoPhone.setText(userInfoBean.getPhone());
            mMyInfoMail.setText("2771905288@qq.com");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.my_info_pwd_restart, R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_pwd_restart:
                startActivity(new Intent(MyInfoActivity.this, ResetPwdActivity.class));
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
