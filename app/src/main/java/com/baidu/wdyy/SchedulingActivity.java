package com.baidu.wdyy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.wdyy.Utils.SpaceItemDecoration;
import com.baidu.wdyy.adapter.CinemaRecycleAdapter;
import com.baidu.wdyy.bean.CinemaById;
import com.baidu.wdyy.bean.CinemaRecy;
import com.baidu.wdyy.bean.IDMoiveDetalisTwo;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.CinemaByIdPresenter;
import com.baidu.wdyy.presenter.CinemaRecyPresenter;
import com.baidu.wdyy.presenter.IDMoiveDetalisoneTwoPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import crossoverone.statuslib.StatusUtil;

public class SchedulingActivity extends AppCompatActivity {
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");
    private CinemaRecycleAdapter cinemaRecycleAdapter;
    private SimpleDraweeView scheduling_sdv;
    private TextView scheduling_textviewone;
    private TextView scheduling_textviewtwo;
    private TextView scheduling_textviewthree;
    private TextView scheduling_textviewfour;
    private TextView scheduling_textviewfive;
    private IDMoiveDetalisTwo idMoiveDetalisTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);
        scheduling_sdv = findViewById(R.id.scheduling_sdv);
        scheduling_textviewone = findViewById(R.id.scheduling_textviewone);
        scheduling_textviewtwo = findViewById(R.id.scheduling_textviewtwo);
        scheduling_textviewthree = findViewById(R.id.scheduling_textviewthree);
        scheduling_textviewfour = findViewById(R.id.scheduling_textviewfour);
        scheduling_textviewfive = findViewById(R.id.scheduling_textviewfive);

        IDMoiveDetalisoneTwoPresenter idMoiveDetalisoneTwoPresenter = new IDMoiveDetalisoneTwoPresenter(new DetalisCinemaCall());

        CinemaRecyPresenter cinemaRecyPresenter = new CinemaRecyPresenter(new SchedulingCall());

        CinemaByIdPresenter cinemaByIdPresenter = new CinemaByIdPresenter(new MyCall());

        //获取传过来的影院ID/影院地址/电影id
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        int cinemaid = Integer.parseInt(getIntent().getStringExtra("id"));
        int filmid = Integer.parseInt(getIntent().getStringExtra("filmid"));

        TextView scheduling_name = findViewById(R.id.scheduling_name);
        scheduling_name.setText(name);
        TextView scheduling_address = findViewById(R.id.scheduling_address);
        scheduling_address.setText(address);
        //返回上级目录
        findViewById(R.id.scheduling_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView scheduling_recycleview = findViewById(R.id.scheduling_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        scheduling_recycleview.setLayoutManager(linearLayoutManager);
        cinemaRecycleAdapter = new CinemaRecycleAdapter(this);
        scheduling_recycleview.setAdapter(cinemaRecycleAdapter);
        scheduling_recycleview.addItemDecoration(new SpaceItemDecoration(10));
        cinemaRecyPresenter.request(cinemaid,filmid);
        idMoiveDetalisoneTwoPresenter.request(userId,sessionId,filmid);
        cinemaByIdPresenter.request(filmid);
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
    class SchedulingCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<CinemaRecy> cinemaRecies = (List<CinemaRecy>) result.getResult();
                cinemaRecycleAdapter.addList(cinemaRecies);
                cinemaRecycleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class DetalisCinemaCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                idMoiveDetalisTwo = (IDMoiveDetalisTwo) result.getResult();
                scheduling_sdv.setImageURI(idMoiveDetalisTwo.getImageUrl());
                scheduling_textviewone.setText(idMoiveDetalisTwo.getName());
                scheduling_textviewtwo.setText("类型："+ idMoiveDetalisTwo.getMovieTypes());
                scheduling_textviewthree.setText("导演："+ idMoiveDetalisTwo.getDirector());
                scheduling_textviewfour.setText("时长："+ idMoiveDetalisTwo.getDuration());
                scheduling_textviewfive.setText("产地："+ idMoiveDetalisTwo.getPlaceOrigin());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //获取名字
    class MyCall implements DataCall<Result<List<CinemaById>>> {

        @Override
        public void success(Result<List<CinemaById>> result) {
            if (result.getStatus().equals("0000")) {
                List<String> list = new ArrayList<>();
                List<CinemaById> cinemaByIds = result.getResult();
                for (int i = 0; i < cinemaByIds.size(); i++) {
                    String name = cinemaByIds.get(i).getName();
                    list.add(name);
                }
                cinemaRecycleAdapter.addName(list);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
