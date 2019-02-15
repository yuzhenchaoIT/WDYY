package com.baidu.wdyy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.baidu.wdyy.adapter.FindCinemaAdapter;
import com.baidu.wdyy.adapter.FindMovieAdapter;
import com.baidu.wdyy.bean.CinemaBean;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.my.FindCinemaPresenter;
import com.baidu.wdyy.presenter.my.FindMoviePresenter;
import com.bw.movie.R;

import java.util.List;

import crossoverone.statuslib.StatusUtil;

/**
 * 我的关注
 *
 * @date 2019/1/29
 */
public class MyAttentionActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 电影
     */
    private Button mMyAttentionMovie;
    /**
     * 影院
     */
    private Button mMyAttentionCinema;
    private RecyclerView mMyAttentionRecycleview;
    //电影p层
    private FindMoviePresenter findMoviePresenter = new FindMoviePresenter(new MovieDataCall());
    //影院p层
    private FindCinemaPresenter findCinemaPresenter = new FindCinemaPresenter(new CinemaDataCall());
    //电影适配器
    private FindMovieAdapter findMovieAdapter;
    //影院适配器
    private FindCinemaAdapter findCinemaAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initView();
        //布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        mMyAttentionRecycleview.setLayoutManager(linearLayoutManager);


        //默认添加电影
        findMovieAdapter = new FindMovieAdapter(getBaseContext());
        //添加影院
        findCinemaAdapter = new FindCinemaAdapter(getBaseContext());
        mMyAttentionRecycleview.setAdapter(findMovieAdapter);
        //p层请求数据
        findMoviePresenter.request(userId, sessionId, 1, 100);
        //按钮颜色切换
        mMyAttentionMovie.setBackgroundResource(R.drawable.btn_gradient);
        mMyAttentionMovie.setTextColor(Color.WHITE);
        mMyAttentionCinema.setBackgroundResource(R.drawable.myborder);
        mMyAttentionCinema.setTextColor(Color.BLACK);
        setStatusColor();
        setSystemInvadeBlack();
    }

    private void initView() {
        mMyAttentionMovie = findViewById(R.id.my_attention_movie);
        mMyAttentionMovie.setOnClickListener(this);
        mMyAttentionCinema = findViewById(R.id.my_attention_cinema);
        mMyAttentionCinema.setOnClickListener(this);
        mMyAttentionRecycleview = findViewById(R.id.my_attention_recycleview);
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
        if (v.getId() == R.id.my_attention_movie) {
            mMyAttentionMovie.setBackgroundResource(R.drawable.btn_gradient);
            mMyAttentionMovie.setTextColor(Color.WHITE);
            mMyAttentionCinema.setBackgroundResource(R.drawable.myborder);
            mMyAttentionCinema.setTextColor(Color.BLACK);
            findMovieAdapter = new FindMovieAdapter(getBaseContext());
            findMovieAdapter.remove();
            mMyAttentionRecycleview.setAdapter(findMovieAdapter);
            mMyAttentionRecycleview.setLayoutManager(linearLayoutManager);
            findMoviePresenter.request(userId, sessionId, 1, 100);
        }
        if (v.getId() == R.id.my_attention_cinema) {
            mMyAttentionCinema.setBackgroundResource(R.drawable.btn_gradient);
            mMyAttentionCinema.setTextColor(Color.WHITE);
            mMyAttentionMovie.setBackgroundResource(R.drawable.myborder);
            mMyAttentionMovie.setTextColor(Color.BLACK);
            findCinemaAdapter = new FindCinemaAdapter(getBaseContext());
            mMyAttentionRecycleview.setAdapter(findCinemaAdapter);
            mMyAttentionRecycleview.setLayoutManager(linearLayoutManager);
            findCinemaPresenter.request(userId, sessionId, 1, 100);
        }
    }

    class MovieDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeanList = (List<MoiveBean>) data.getResult();
                findMovieAdapter.addItem(moiveBeanList);
                findMovieAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class CinemaDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                List<CinemaBean> cinemaBeanList = (List<CinemaBean>) data.getResult();
                findCinemaAdapter.addItem(cinemaBeanList);
                findCinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
