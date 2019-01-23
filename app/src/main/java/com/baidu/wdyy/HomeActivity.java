package com.baidu.wdyy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_regirect_tiao)
    TextView mBtnRegirectTiao;

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

    @OnClick(R.id.btn_regirect_tiao)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regirect_tiao:
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                break;
        }
    }
}
