package com.baidu.wdyy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfo;
import com.baidu.wdyy.bean.UserInfoBean;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.core.code.EncryptUtil;
import com.baidu.wdyy.core.db.DBDao;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.LoginPresenter;
import com.bw.movie.R;
import com.j256.ormlite.dao.Dao;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_regirect_tiao)
    TextView mBtnRegirectTiao;
    @BindView(R.id.ed_login_number)
    EditText mEdLoginNumber;
    @BindView(R.id.ed_login_password)
    EditText mEdLoginPassword;
    @BindView(R.id.btn_ying)
    ToggleButton mBtnYing;
    @BindView(R.id.save_pwd)
    CheckBox mSavePwd;
    @BindView(R.id.zd_login)
    CheckBox mZdLogin;
    @BindView(R.id.member_activity_login_layout)
    LinearLayout mMemberActivityLoginLayout;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.weixin_login)
    ImageView mWeixinLogin;
    //密码是否可见
    private boolean pwdVisibile = false;
    //登录p层
    private LoginPresenter loginPresenter = new LoginPresenter(new LoginDataCall());
    private IWXAPI mWxApi;
    private Dao<UserInfo, String> userDao = DBDao.getInstance(getBaseContext()).getUserDao();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setStatusColor();
        setSystemInvadeBlack();
        setRememberPwd();
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxb3852e6a6b7d9516");
    }

    /**
     * 记住密码
     */
    private void setRememberPwd() {
        //记住密码操作
        boolean remPas = WDYYApp.getShare().getBoolean("remPwd", true);
        if (remPas) {
            mSavePwd.setChecked(true);
            mEdLoginNumber.setText(WDYYApp.getShare().getString("phone", ""));
            mEdLoginPassword.setText(WDYYApp.getShare().getString("pwd", ""));
        }
    }

    protected void setStatusColor() {
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#00000000"));
    }

    protected void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, true, true);
    }

    @OnClick({R.id.btn_regirect_tiao, R.id.btn_ying, R.id.btn_login, R.id.weixin_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regirect_tiao:
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_ying:
                if (pwdVisibile) {//密码显示，则隐藏
                    mEdLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwdVisibile = false;
                } else {//密码隐藏则显示
                    mEdLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwdVisibile = true;
                }
                break;
            case R.id.btn_login:
                String phone = mEdLoginNumber.getText().toString();
                String pwd = mEdLoginPassword.getText().toString();
                String encryptPwd = EncryptUtil.encrypt(pwd);
                String decryptPwd = EncryptUtil.decrypt(encryptPwd);
                if (mSavePwd.isChecked()) {
                    WDYYApp.getShare().edit().putString("phone", phone)
                            .putString("pwd", decryptPwd).commit();
                }
                loginPresenter.request(phone, encryptPwd);
                break;
            case R.id.weixin_login:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                mWxApi.sendReq(req);
                break;
        }
    }


    class LoginDataCall implements DataCall<Result<UserInfo>> {
        @Override
        public void success(Result<UserInfo> data) {
            if (data.getStatus().equals("0000")) {
                UserInfo userInfo = data.getResult();
                //登录成功把userId  sessionId存入sp
                WDYYApp.getShare().edit().putInt("userId", userInfo.getUserId())
                        .putString("sessionId", userInfo.getSessionId()).commit();
                //登录成功  数据添加数据库
                try {
                    userDao.createOrUpdate(userInfo);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, ShowActivity.class));
                finish();
            } else {
                Toast.makeText(getBaseContext(), data.getStatus() + " " + data.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), "登录失败,请核实信息" + e, Toast.LENGTH_SHORT).show();
        }
    }

}
