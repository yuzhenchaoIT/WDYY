package com.baidu.wdyy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.wdyy.adapter.PurchaseAdapter;
import com.baidu.wdyy.bean.IDMoiveDetalisOne;
import com.baidu.wdyy.bean.PurchaseBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.IDMoiveDetalisonePresenter;
import com.baidu.wdyy.presenter.PurchasePresenter;
import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaByMovieActivity extends AppCompatActivity implements View.OnClickListener {

    private PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_by_movie);
        ButterKnife.bind(this);
        //传过来的电影id
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
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

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.purchase_return:
                finish();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        finish();
    }

    class PurchaseCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<PurchaseBean> purchaseBeans = (List<PurchaseBean>) result.getResult();
                purchaseAdapter.addItem(purchaseBeans);
                purchaseAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}