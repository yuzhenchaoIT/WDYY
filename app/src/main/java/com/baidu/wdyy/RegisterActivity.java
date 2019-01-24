package com.baidu.wdyy;

import android.content.Intent;
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
import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @OnClick({R.id.btn_register,R.id.ed_reg_birth_date})
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
            case R.id.ed_reg_birth_date:
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mEdRegBirthDate.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //.isDialog(true)//是否显示为对话框样式
                        .build();

                pvTime.show();

                break;


        }
    }

    @OnClick(R.id.ed_reg_birth_date)
    public void onViewClicked() {
    }

    class RegDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(RegisterActivity.this,
                        "注册成功啦", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            } else {
                Toast.makeText(RegisterActivity.this,
                        data.getStatus() + "注册失败啦" + data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(RegisterActivity.this, e + "", Toast.LENGTH_SHORT).show();
        }
    }

}
