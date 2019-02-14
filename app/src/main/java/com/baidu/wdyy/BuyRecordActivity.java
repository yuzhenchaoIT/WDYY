package com.baidu.wdyy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.wdyy.adapter.BuyRecordFinishAdapter;
import com.baidu.wdyy.adapter.BuyRecordPayAdapter;
import com.baidu.wdyy.bean.BuyRecordBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.BuyTicketRecordPresenter;
import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购票记录页面
 *
 * @author lmx
 * @date 2019/2/13
 */
public class BuyRecordActivity extends AppCompatActivity {

    @BindView(R.id.buy_record_wait_pay)
    Button mBuyRecordWaitPay;
    @BindView(R.id.buy_record_finish)
    Button mBuyRecordFinish;
    @BindView(R.id.buy_record_recy)
    RecyclerView mBuyRecordRecy;
    @BindView(R.id.buy_record_back)
    ImageView mBuyRecordBack;
    //取出用户  userId    sessionId
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");
    //p层
    private BuyTicketRecordPresenter buyRecordPresenter = new BuyTicketRecordPresenter(new BuyRecordCall());
    //适配器
    private BuyRecordPayAdapter buyRecordPayAdapter;
    private BuyRecordFinishAdapter buyRecordFinishAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_record);
        ButterKnife.bind(this);

        //默认显示待付款页面
        //布局管理器
        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        mBuyRecordRecy.setLayoutManager(linearLayoutManager);

        //适配器
        buyRecordPayAdapter = new BuyRecordPayAdapter(getBaseContext());
        buyRecordFinishAdapter = new BuyRecordFinishAdapter(getBaseContext());
        mBuyRecordRecy.setAdapter(buyRecordPayAdapter);


        //p层请求
        buyRecordPresenter.request(userId, sessionId, 1, 100, 1);

        //默认按钮颜色
        mBuyRecordWaitPay.setBackgroundResource(R.drawable.btn_gradient);
        mBuyRecordWaitPay.setTextColor(Color.WHITE);
        mBuyRecordFinish.setBackgroundResource(R.drawable.myborder);
        mBuyRecordFinish.setTextColor(Color.BLACK);

    }

    @OnClick({R.id.buy_record_wait_pay, R.id.buy_record_finish, R.id.buy_record_back})
    public void onClick(View v) {
        switch (v.getId()) {
            //待付款
            case R.id.buy_record_wait_pay:
                mBuyRecordWaitPay.setBackgroundResource(R.drawable.btn_gradient);
                mBuyRecordWaitPay.setTextColor(Color.WHITE);
                mBuyRecordFinish.setBackgroundResource(R.drawable.myborder);
                mBuyRecordFinish.setTextColor(Color.BLACK);
                buyRecordPayAdapter.remove();
                buyRecordPayAdapter = new BuyRecordPayAdapter(getBaseContext());
                mBuyRecordRecy.setAdapter(buyRecordPayAdapter);
                buyRecordPresenter.request(userId, sessionId, 1, 100, 1);
                break;
            //已完成
            case R.id.buy_record_finish:
                mBuyRecordFinish.setBackgroundResource(R.drawable.btn_gradient);
                mBuyRecordFinish.setTextColor(Color.WHITE);
                mBuyRecordWaitPay.setBackgroundResource(R.drawable.myborder);
                mBuyRecordWaitPay.setTextColor(Color.BLACK);
                buyRecordFinishAdapter.remove();
                buyRecordFinishAdapter = new BuyRecordFinishAdapter(getBaseContext());
                mBuyRecordRecy.setAdapter(buyRecordFinishAdapter);
                buyRecordPresenter.request(userId, sessionId, 1, 100, 1);
                break;
            case R.id.buy_record_back:
                finish();
                break;
        }
    }

    class BuyRecordCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                List<BuyRecordBean> buyRecordBeanList = (List<BuyRecordBean>) data.getResult();
                buyRecordPayAdapter.addItem(buyRecordBeanList);
                buyRecordPayAdapter.notifyDataSetChanged();
                buyRecordFinishAdapter.addItem(buyRecordBeanList);
                buyRecordFinishAdapter.notifyDataSetChanged();
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
