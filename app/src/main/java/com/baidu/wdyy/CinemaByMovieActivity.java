package com.baidu.wdyy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.wdyy.adapter.PurchaseAdapter;
import com.baidu.wdyy.bean.PurchaseBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.PurchasePresenter;
import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crossoverone.statuslib.StatusUtil;

public class CinemaByMovieActivity extends AppCompatActivity implements View.OnClickListener {

    private PurchaseAdapter purchaseAdapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_by_movie);
        ButterKnife.bind(this);
        //传过来的电影id
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        PurchasePresenter purchasePresenter = new PurchasePresenter(new PurchaseCall());

        Button purchase_return = findViewById(R.id.purchase_return);
        purchase_return.setOnClickListener(this);

        TextView purchase_name = findViewById(R.id.purchase_name);
        purchase_name.setText(name);

        RecyclerView purchase_recycleview = findViewById(R.id.purchase_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        purchase_recycleview.setLayoutManager(linearLayoutManager);
        purchaseAdapter = new PurchaseAdapter(this);
        purchase_recycleview.setAdapter(purchaseAdapter);

        purchasePresenter.request(id);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.purchase_return:
                finish();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        finish();
    }

    class PurchaseCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<PurchaseBean> purchaseBeans = (List<PurchaseBean>) result.getResult();
                purchaseAdapter.addItem(purchaseBeans);
                purchaseAdapter.addId(id);
                purchaseAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
