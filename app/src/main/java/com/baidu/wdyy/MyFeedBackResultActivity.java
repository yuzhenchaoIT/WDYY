package com.baidu.wdyy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFeedBackResultActivity extends AppCompatActivity {

    @BindView(R.id.my_feed_back_result)
    ImageView mMyFeedBackResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back_result);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.my_feed_back_result)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_feed_back_result:
                finish();
                break;
        }
    }
}
