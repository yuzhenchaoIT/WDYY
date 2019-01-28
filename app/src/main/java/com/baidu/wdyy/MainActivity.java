package com.baidu.wdyy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import crossoverone.statuslib.StatusUtil;

public class MainActivity extends AppCompatActivity {
    private int time = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time != 0) {
                handler.sendEmptyMessageDelayed(time, 1000);
                time--;
            } else {
//                if (!SharedUtils.getInstance().getisLogin()) {
                startActivity(new Intent(MainActivity.this, LeadActivity.class));
                finish();
//                } else {
//                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessage(time);
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
