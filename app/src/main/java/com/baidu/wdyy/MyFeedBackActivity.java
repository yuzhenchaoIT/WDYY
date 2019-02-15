package com.baidu.wdyy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.my.FeedBackPresenter;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

public class MyFeedBackActivity extends AppCompatActivity {

    @BindView(R.id.my_feed_back_content)
    EditText mMyFeedBackContent;
    @BindView(R.id.my_feed_submit)
    Button mMyFeedSubmit;
    @BindView(R.id.my_feed_back_back)
    ImageView mMyFeedBackBack;
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");
    private FeedBackPresenter feedBackPresenter = new FeedBackPresenter(new FeedBackDataCall());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back);
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
    @OnClick({R.id.my_feed_submit, R.id.my_feed_back_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_feed_submit:
                String content = mMyFeedBackContent.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(getBaseContext(), "亲，留下上帝的宝贵建议吧", Toast.LENGTH_SHORT).show();
                } else {
                    feedBackPresenter.request(userId, sessionId, content);
                }

                break;
            case R.id.my_feed_back_back:
                finish();
                break;
        }
    }

    class FeedBackDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyFeedBackActivity.this, MyFeedBackResultActivity.class));
                finish();
            } else {
                Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
