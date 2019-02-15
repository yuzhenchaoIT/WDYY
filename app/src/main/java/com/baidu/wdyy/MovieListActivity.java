package com.baidu.wdyy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.wdyy.adapter.MovieListAdapter;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.BeingMoviePresenter;
import com.baidu.wdyy.presenter.PopularMoviePresenter;
import com.baidu.wdyy.presenter.SoonMoviePresenter;
import com.baidu.wdyy.presenter.my.CancelFollowMoviePresenter;
import com.baidu.wdyy.presenter.my.FollowMoviePresenter;
import com.bw.movie.R;

import org.greenrobot.eventbus.EventBus;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 影片列表
 *
 * @author lmx
 * @date 2019/2/15
 */
public class MovieListActivity extends AppCompatActivity {

    @BindView(R.id.movie_list_hot)
    Button mMovieListHot;
    @BindView(R.id.movie_list_now)
    Button mMovieListNow;
    @BindView(R.id.movie_list_soon)
    Button mMovieListSoon;
    @BindView(R.id.movie_list_recy)
    RecyclerView mMovieListRecy;
    @BindView(R.id.movie_list_back)
    ImageView mMovieListBack;
    private PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(new PopularCall());
    private BeingMoviePresenter beingMoviePresenter = new BeingMoviePresenter(new BeingCall());
    private SoonMoviePresenter soonMoviePresenter = new SoonMoviePresenter(new SoonCall());
    //适配器
    private MovieListAdapter movieListAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
    private String type;
    private int id;
    private FollowMoviePresenter followMoviePresenter = new FollowMoviePresenter(new FollowDataCall());
    private CancelFollowMoviePresenter cancelFollowMoviePresenter = new CancelFollowMoviePresenter(new CancelCall());
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String select = intent.getStringExtra("select");


        //默认显示热门影院
        mMovieListRecy.setLayoutManager(linearLayoutManager);

        //适配器
        movieListAdapter = new MovieListAdapter(getBaseContext());
        mMovieListRecy.setAdapter(movieListAdapter);

        //p层请求
        popularMoviePresenter.request(0, "", 1, 10);


        //默认按钮颜色
        mMovieListHot.setBackgroundResource(R.drawable.btn_gradient);
        mMovieListHot.setTextColor(Color.WHITE);
        mMovieListNow.setBackgroundResource(R.drawable.myborder);
        mMovieListNow.setTextColor(Color.BLACK);
        mMovieListSoon.setBackgroundResource(R.drawable.myborder);
        mMovieListSoon.setTextColor(Color.BLACK);

        if (select.equals("1")) {
            mMovieListHot.setBackgroundResource(R.drawable.btn_gradient);
            mMovieListHot.setTextColor(Color.WHITE);
            mMovieListNow.setBackgroundResource(R.drawable.myborder);
            mMovieListNow.setTextColor(Color.BLACK);
            mMovieListSoon.setBackgroundResource(R.drawable.myborder);
            mMovieListSoon.setTextColor(Color.BLACK);
            movieListAdapter.remove();
            movieListAdapter = new MovieListAdapter(getBaseContext());
            mMovieListRecy.setAdapter(movieListAdapter);
            popularMoviePresenter.request(0, "", 1, 10);

        } else if (select.equals("2")) {
            mMovieListHot.setBackgroundResource(R.drawable.myborder);
            mMovieListHot.setTextColor(Color.BLACK);
            mMovieListNow.setBackgroundResource(R.drawable.btn_gradient);
            mMovieListNow.setTextColor(Color.WHITE);
            mMovieListSoon.setBackgroundResource(R.drawable.myborder);
            mMovieListSoon.setTextColor(Color.BLACK);
            movieListAdapter.remove();
            movieListAdapter = new MovieListAdapter(getBaseContext());
            mMovieListRecy.setAdapter(movieListAdapter);
            soonMoviePresenter.request(0, "", 1, 10);

        } else {
            mMovieListHot.setBackgroundResource(R.drawable.myborder);
            mMovieListHot.setTextColor(Color.BLACK);
            mMovieListNow.setBackgroundResource(R.drawable.myborder);
            mMovieListNow.setTextColor(Color.BLACK);
            mMovieListSoon.setBackgroundResource(R.drawable.btn_gradient);
            mMovieListSoon.setTextColor(Color.WHITE);
            movieListAdapter.remove();
            movieListAdapter = new MovieListAdapter(getBaseContext());
            mMovieListRecy.setAdapter(movieListAdapter);
            beingMoviePresenter.request(0, "", 1, 10);
        }
        setStatusColor();
        setSystemInvadeBlack();
    }
    protected void setStatusColor() {
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#00000000"));

        movieListAdapter.setOnItemClickListener(new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int moiveId, int isFollow) {
                id = moiveId;
                if (mMovieListHot.isClickable()) {
                    if (isFollow == 1) {
                        cancelFollowMoviePresenter.request(userId, sessionId, moiveId);
                    } else if (isFollow == 2) {
                        followMoviePresenter.request(userId, sessionId, moiveId);

                    }
                } else if (mMovieListNow.isClickable()) {
                    if (isFollow == 1) {
                        cancelFollowMoviePresenter.request(userId, sessionId, moiveId);
                    } else if (isFollow == 2) {
                        followMoviePresenter.request(userId, sessionId, moiveId);

                    }
                } else if (mMovieListSoon.isClickable()) {
                    if (isFollow == 1) {
                        cancelFollowMoviePresenter.request(userId, sessionId, moiveId);
                    } else if (isFollow == 2) {
                        followMoviePresenter.request(userId, sessionId, moiveId);

                    }
                }

//                if (isFollow == 1) {
//                    //请求取消关注的接口
//                    cancelFollowMoviePresenter.request(userId, sessionId, id);
//                } else if (isFollow == 2) {
//                    //请求关注的接口
//                    followMoviePresenter.request(userId, sessionId, id);
//                }
            }
        });


    }

    protected void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, true, true);
    }
    @OnClick({R.id.movie_list_hot, R.id.movie_list_now, R.id.movie_list_soon, R.id.movie_list_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_list_hot:
                mMovieListHot.setBackgroundResource(R.drawable.btn_gradient);
                mMovieListHot.setTextColor(Color.WHITE);
                mMovieListNow.setBackgroundResource(R.drawable.myborder);
                mMovieListNow.setTextColor(Color.BLACK);
                mMovieListSoon.setBackgroundResource(R.drawable.myborder);
                mMovieListSoon.setTextColor(Color.BLACK);
                movieListAdapter.remove();
                movieListAdapter = new MovieListAdapter(getBaseContext());
                mMovieListRecy.setAdapter(movieListAdapter);
                popularMoviePresenter.request(0, "", 1, 10);
                break;
            case R.id.movie_list_now:
                mMovieListHot.setBackgroundResource(R.drawable.myborder);
                mMovieListHot.setTextColor(Color.BLACK);
                mMovieListNow.setBackgroundResource(R.drawable.btn_gradient);
                mMovieListNow.setTextColor(Color.WHITE);
                mMovieListSoon.setBackgroundResource(R.drawable.myborder);
                mMovieListSoon.setTextColor(Color.BLACK);
                movieListAdapter.remove();
                movieListAdapter = new MovieListAdapter(getBaseContext());
                mMovieListRecy.setAdapter(movieListAdapter);
                soonMoviePresenter.request(0, "", 1, 10);
                break;
            case R.id.movie_list_soon:
                mMovieListHot.setBackgroundResource(R.drawable.myborder);
                mMovieListHot.setTextColor(Color.BLACK);
                mMovieListNow.setBackgroundResource(R.drawable.myborder);
                mMovieListNow.setTextColor(Color.BLACK);
                mMovieListSoon.setBackgroundResource(R.drawable.btn_gradient);
                mMovieListSoon.setTextColor(Color.WHITE);
                movieListAdapter.remove();
                movieListAdapter = new MovieListAdapter(getBaseContext());
                mMovieListRecy.setAdapter(movieListAdapter);
                beingMoviePresenter.request(0, "", 1, 10);
                break;
            case R.id.movie_list_back:
                finish();
                break;
        }
    }


    //热门电影
    class PopularCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                movieListAdapter.addItem(moiveBeans);
                movieListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //正在上映
    class BeingCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                movieListAdapter.addItem(moiveBeans);
                movieListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //即将上映
    class SoonCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                movieListAdapter.addItem(moiveBeans);
                movieListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //影院关注
    class FollowDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
                if (mMovieListHot.isClickable()) {
                    popularMoviePresenter.request(0, "", 1, 10);
                } else if (mMovieListNow.isClickable()) {
                    soonMoviePresenter.request(0, "", 1, 10);
                } else if (mMovieListSoon.isClickable()) {
                    beingMoviePresenter.request(0, "", 1, 10);
                }
            } else {
                Toast.makeText(MovieListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                movieListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //影院取消关注
    class CancelCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                if (mMovieListHot.isClickable()) {
                    popularMoviePresenter.request(0, "", 1, 10);
                } else if (mMovieListNow.isClickable()) {
                    soonMoviePresenter.request(0, "", 1, 10);
                } else if (mMovieListSoon.isClickable()) {
                    beingMoviePresenter.request(0, "", 1, 10);
                }
            } else {
                Toast.makeText(MovieListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                movieListAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
