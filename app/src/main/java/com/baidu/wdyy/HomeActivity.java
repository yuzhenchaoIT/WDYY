package com.baidu.wdyy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.code.EncryptUtil;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.LoginPresenter;

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
    private LoginPresenter loginPresenter = new LoginPresenter(new LoginDataCall());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setStatusColor();
        setSystemInvadeBlack();

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
                break;
            case R.id.btn_login:
                String phone = mEdLoginNumber.getText().toString();
                String pwd = mEdLoginPassword.getText().toString();
                String encryptPwd = EncryptUtil.encrypt(pwd);
//                String decryptPwd = EncryptUtil.decrypt(pwd);
                loginPresenter.request(phone, encryptPwd);
                break;
            case R.id.weixin_login:
                break;
        }
    }

    class LoginDataCall implements DataCall<Result<UserInfo>> {
        @Override
        public void success(Result<UserInfo> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getStatus() + " " + data.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            } else {
                Toast.makeText(getBaseContext(), data.getStatus() + " " + data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), "请求成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        }
    }

}
