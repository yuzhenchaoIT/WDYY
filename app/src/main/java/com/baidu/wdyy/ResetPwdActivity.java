package com.baidu.wdyy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.core.code.EncryptUtil;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.my.ResetPwdPresenter;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码页面
 *
 * @author lmx
 * @date 2019/2/13
 */
public class ResetPwdActivity extends AppCompatActivity {

    @BindView(R.id.reset_pwd_now)
    EditText mResetPwdNow;
    @BindView(R.id.reset_pwd_new)
    EditText mResetPwdNew;
    @BindView(R.id.reset_pwd_confirm)
    EditText mResetPwdConfirm;
    @BindView(R.id.reset_pwd_back)
    ImageView mResetPwdBack;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");
    //重置密码p层
    private ResetPwdPresenter resetPwdPresenter = new ResetPwdPresenter(new ResetPwdDataCall());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.reset_pwd_back, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                String oldPwd = mResetPwdNow.getText().toString();
                String newPwd = mResetPwdNew.getText().toString();
                String newPwd2 = mResetPwdConfirm.getText().toString();
                String encryptPwdOld = EncryptUtil.encrypt(oldPwd);
                String encryptPwd = EncryptUtil.encrypt(newPwd);
                String encryptPwd2 = EncryptUtil.encrypt(newPwd2);
                resetPwdPresenter.request(userId, sessionId, encryptPwdOld, encryptPwd, encryptPwd2);
                break;
            case R.id.reset_pwd_back:
                finish();
                break;
        }
    }

    class ResetPwdDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

}
