package com.baidu.wdyy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.wdyy.adapter.RemindAdapter;
import com.baidu.wdyy.bean.RemindBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.my.MyRemindPresenter;
import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

/**
 * 系统消息
 *
 * @author lmx
 * @date 2019/1/31
 */
public class MyRemindActivity extends AppCompatActivity {

    @BindView(R.id.my_remind_recycler)
    RecyclerView mMyRemindRecycler;
    @BindView(R.id.my_remind_back)
    ImageView mMyRemindBack;
    //p层
    private MyRemindPresenter myRemindPresenter = new MyRemindPresenter(new remindCall());
    //适配器
    private RemindAdapter mRemindAdapter;
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_remind);
        ButterKnife.bind(this);

        //线性
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMyRemindRecycler.setLayoutManager(linearLayoutManager);
        //适配器
        mRemindAdapter = new RemindAdapter(getBaseContext());
        mMyRemindRecycler.setAdapter(mRemindAdapter);
        //请求数据
        myRemindPresenter.request(userId, sessionId, 1, 100);
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

    @OnClick({R.id.my_remind_recycler, R.id.my_remind_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_remind_recycler:
                break;
            case R.id.my_remind_back:
                finish();
                break;
        }
    }

    class remindCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
//                Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                List<RemindBean> remindBeanList = (List<RemindBean>) data.getResult();
                mRemindAdapter.addItem(remindBeanList);
                mRemindAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }


}
