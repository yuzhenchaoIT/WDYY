package com.baidu.wdyy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.code.EncryptUtil;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.RegPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.ed_reg_nickName)
    EditText mEdRegNickName;
    @BindView(R.id.ed_reg_sex)
    EditText mEdRegSex;
    @BindView(R.id.ed_reg_birth_date)
    EditText mEdRegBirthDate;
    @BindView(R.id.ed_reg_phone)
    EditText mEdRegPhone;
    @BindView(R.id.ed_reg_mail)
    EditText mEdRegMail;
    @BindView(R.id.ed_reg_pwd)
    EditText mEdRegPwd;
    @BindView(R.id.member_activity_login_layout)
    LinearLayout mMemberActivityLoginLayout;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    //性别默认值
    private int sexInt = 1;
    private RegPresenter regPresenter = new RegPresenter(new RegDataCall());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setStatusColor();
        setSystemInvadeBlack();
    }


    private void setStatusColor() {
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#00000000"));
    }

    private void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, true, true);
    }


    @OnClick(R.id.btn_register)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String nickName = mEdRegNickName.getText().toString();
                String sex = mEdRegSex.getText().toString();
                //性别
                if (sex.equals("男")) {
                    sexInt = 1;
                } else if (sex.equals("女")) {
                    sexInt = 2;
                }
                String birthDate = mEdRegBirthDate.getText().toString();
                String phone = mEdRegPhone.getText().toString();
                String mail = mEdRegMail.getText().toString();
                String pwd = mEdRegPwd.getText().toString();
                //加密
                String encryptpwd = EncryptUtil.encrypt(pwd);
                regPresenter.request(nickName, phone, encryptpwd, encryptpwd,
                        sexInt, birthDate, "123456", "小米", "5.0", "android", mail);
                break;
        }
    }

    class RegDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(RegisterActivity.this,
                        data.getStatus() + "成功啦" + data.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this,
                        data.getStatus() + "失败啦" + data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(RegisterActivity.this, e + "", Toast.LENGTH_SHORT).show();
        }
    }

}
