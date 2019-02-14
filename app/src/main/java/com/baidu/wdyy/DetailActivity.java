package com.baidu.wdyy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.Utils.SpaceItemDecoration;
import com.baidu.wdyy.adapter.DramaAdapter;
import com.baidu.wdyy.adapter.MovieReviewAdapter;
import com.baidu.wdyy.adapter.ReviewAdapter;
import com.baidu.wdyy.bean.FilmReviewBean;
import com.baidu.wdyy.bean.IDMoiveDetalisOne;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.FindAllMovieCommentPresenter;
import com.baidu.wdyy.presenter.IDMoiveDetalisonePresenter;
import com.baidu.wdyy.presenter.my.CancelFollowMoviePresenter;
import com.baidu.wdyy.presenter.my.FollowMoviePresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.btn_action)
    Button btnAction;
    @BindView(R.id.text_action)
    TextView textAction;
    @BindView(R.id.text_xiangname)
    TextView textXiangname;
    @BindView(R.id.simp_xiangview)
    SimpleDraweeView simpXiangview;
    @BindView(R.id.btn_xiangqing)
    Button btnXiangqing;
    @BindView(R.id.btn_yugao)
    Button btnYugao;
    @BindView(R.id.btn_photo)
    Button btnPhoto;
    @BindView(R.id.btn_ping)
    Button btnPing;
    @BindView(R.id.btn_fan)
    Button btnFan;
    @BindView(R.id.btn_gou)
    Button btnGou;
    @BindView(R.id.btn_heartxiangqing)
    ImageView mBtnHeartxiangqing;
    private SimpleDraweeView simp_pop_movie;
    //    private TextView tv_pop_title;
    private TextView tv_director;
    private TextView tv_length;
    private TextView tv_type;
    private TextView tv_placeoforigin;
    private TextView tv_jianjie;
    private TextView text_yanyuan;
    private String movieId;

    private Uri uri;
    private RecyclerView recycler_photo;
    private PopupWindow window;
    private View view;
    private PopupWindow window1;
    private View view3;
    private PopupWindow window2;
    private View view2;
    private PopupWindow window3;
    private View view1;
    private IDMoiveDetalisOne idMoiveDetalisOne;
    private FindAllMovieCommentPresenter findAllMovieCommentPresenter;
    private ReviewAdapter reviewAdapter;
    private RecyclerView recycler_moviecomment;
    private int id;
    private FollowMoviePresenter followMoviePresenter = new FollowMoviePresenter(new FollowDataCall());
    private CancelFollowMoviePresenter cancelFollowMoviePresenter = new CancelFollowMoviePresenter(new CancelCall());
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //得到电影id
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        IDMoiveDetalisonePresenter idMoiveDetalisonePresenter = new IDMoiveDetalisonePresenter(new IDMoiveDetalisOneCall());
        idMoiveDetalisonePresenter.request(userId, sessionId, id);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pup_detail, null);
        simp_pop_movie = view.findViewById(R.id.simp_pop_movie);
//        tv_pop_title = view.findViewById(R.id.tv_pop_title);
        tv_director = view.findViewById(R.id.tv_director);
        tv_length = view.findViewById(R.id.tv_length);
        tv_type = view.findViewById(R.id.tv_type);
        text_yanyuan = view.findViewById(R.id.text_yanyuan);
        tv_placeoforigin = view.findViewById(R.id.tv_placeoforigin);
        tv_jianjie = view.findViewById(R.id.tv_jianjie);
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        window.setAnimationStyle(R.style.PopupAnimation);

        LayoutInflater inflater3 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.pup_review, null);
        window3 = new PopupWindow(view3,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window3.setFocusable(true);
        ColorDrawable dw3 = new ColorDrawable(0xffffffff);
        window3.setBackgroundDrawable(dw3);
        window3.setAnimationStyle(R.style.PopupAnimation);

        LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.pup_film_review, null);
        window1 = new PopupWindow(view1,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setFocusable(true);
        ColorDrawable dw1 = new ColorDrawable(0xffffffff);
        window1.setBackgroundDrawable(dw1);
        window1.setAnimationStyle(R.style.PopupAnimation);

        LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.pup_dram, null);
        window2 = new PopupWindow(view2,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window2.setFocusable(true);
        ColorDrawable dw2 = new ColorDrawable(0xffffffff);
        window2.setBackgroundDrawable(dw2);
        window2.setAnimationStyle(R.style.PopupAnimation);
        getShowReview(view3);
    }

    private void getShowReview(View view3) {
        findAllMovieCommentPresenter = new FindAllMovieCommentPresenter(new FindAllMovieComment());
        ImageView pupo_yp_back = view3.findViewById(R.id.pupo_yp_back);
        pupo_yp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window3.dismiss();
            }
        });
        RecyclerView review_movie_review = view3.findViewById(R.id.review_movie_review);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        review_movie_review.addItemDecoration(new SpaceItemDecoration(10));
        review_movie_review.setLayoutManager(linearLayoutManager);
        findAllMovieCommentPresenter.request(userId, sessionId, id, false, 10);
        reviewAdapter = new ReviewAdapter();
        review_movie_review.setAdapter(reviewAdapter);

    }

    @OnClick({R.id.btn_xiangqing, R.id.btn_yugao, R.id.btn_photo, R.id.btn_ping, R.id.btn_fan, R.id.btn_gou, R.id.btn_heartxiangqing})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_xiangqing:
                showPopwindow1();
                break;
            case R.id.btn_yugao:
                showPopwindow2();
                break;
            case R.id.btn_photo:
                showPopwindow3();
                break;
            case R.id.btn_ping:
                showPopwindow4();
                break;
            case R.id.btn_fan:
                finish();
                break;
            case R.id.btn_gou:
                Intent intent = new Intent(DetailActivity.this, CinemaByMovieActivity.class);
                intent.putExtra("id", id + "");
                intent.putExtra("name", idMoiveDetalisOne.getName());
                startActivity(intent);
                break;
            //电影关注
            case R.id.btn_heartxiangqing:
                //获取关注状态
                int followMovie = idMoiveDetalisOne.getFollowMovie();
                if (followMovie == 1) {
                    cancelFollowMoviePresenter.request(userId, sessionId, id);
                    mBtnHeartxiangqing.setBackgroundResource(R.drawable.ic_favorite_default);
                } else if (followMovie == 2) {
                    followMoviePresenter.request(userId, sessionId, id);
                    mBtnHeartxiangqing.setBackgroundResource(R.drawable.ic_favorite_selected);
                }
                break;
        }
    }

    private void showPopwindow1() {
        View parent = View.inflate(DetailActivity.this, R.layout.activity_detail, null);

        window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);


    }

    private void showPopwindow2() {
        View parent = View.inflate(DetailActivity.this, R.layout.activity_detail, null);

        window1.showAtLocation(parent,
                Gravity.BOTTOM, 0, 0);
        RecyclerView recycler_movie_review = view1.findViewById(R.id.recycler_movie_review);
        recycler_movie_review.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.VERTICAL, false));
        MovieReviewAdapter movieReviewAdapter = new MovieReviewAdapter(DetailActivity.this, idMoiveDetalisOne.getShortFilmList(), idMoiveDetalisOne.getName());
        Log.i("cc", "showPopwindow2: " + idMoiveDetalisOne.getShortFilmList().toString());
        recycler_movie_review.setAdapter(movieReviewAdapter);
        window1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                JZVideoPlayer.releaseAllVideos();
            }
        });
    }

    private void showPopwindow3() {

        window2.showAtLocation(DetailActivity.this.findViewById(R.id.btn_photo),
                Gravity.BOTTOM, 0, 0);
        recycler_photo = view2.findViewById(R.id.recycler_photo);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_photo.setLayoutManager(layoutManager);
        DramaAdapter dramaAdapter = new DramaAdapter(DetailActivity.this, idMoiveDetalisOne.getPosterList());
        recycler_photo.setAdapter(dramaAdapter);
    }

    private void showPopwindow4() {
        window3.showAtLocation(DetailActivity.this.findViewById(R.id.btn_ping),
                Gravity.BOTTOM, 0, 0);


//        recycler_moviecomment = view3.findViewById(R.id.recycler_moviecomment);
//         recycler_moviecomment.setLayoutManager(new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.VERTICAL,false));
//         ReviewAdapter movieCommentAdapter = new ReviewAdapter();
//         recycler_moviecomment.setAdapter(movieCommentAdapter);
    }


    class FollowDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
                mBtnHeartxiangqing.setBackgroundResource(R.drawable.ic_favorite_selected);
            } else {
                cancelFollowMoviePresenter.request(userId, sessionId, id);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class CancelCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
                mBtnHeartxiangqing.setBackgroundResource(R.drawable.ic_favorite_default);
            } else {
                followMoviePresenter.request(userId, sessionId, id);
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }


    class FindAllMovieComment implements DataCall<Result<List<FilmReviewBean>>> {

        @Override
        public void success(Result<List<FilmReviewBean>> data) {
            if (data.getStatus().equals("0000")) {
                reviewAdapter.setList(data.getResult());
                reviewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class IDMoiveDetalisOneCall implements DataCall<Result> {


        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                idMoiveDetalisOne = (IDMoiveDetalisOne) result.getResult();

                textXiangname.setText(idMoiveDetalisOne.getName());
                simpXiangview.setImageURI(Uri.parse(idMoiveDetalisOne.getImageUrl()));
                simp_pop_movie.setImageURI(Uri.parse(idMoiveDetalisOne.getImageUrl()));
//                tv_pop_title.setText(idMoiveDetalisOne.getName());
                tv_director.setText(idMoiveDetalisOne.getDirector());
                tv_type.setText(idMoiveDetalisOne.getMovieTypes());
                tv_length.setText(idMoiveDetalisOne.getDuration());
                tv_placeoforigin.setText(idMoiveDetalisOne.getPlaceOrigin());
                tv_jianjie.setText(idMoiveDetalisOne.getSummary());
                text_yanyuan.setText(idMoiveDetalisOne.getStarring());
                int followMovie = idMoiveDetalisOne.getFollowMovie();
                if (followMovie == 1) {
                    mBtnHeartxiangqing.setBackgroundResource(R.drawable.ic_favorite_selected);
                } else if (followMovie == 2) {
                    mBtnHeartxiangqing.setBackgroundResource(R.drawable.ic_favorite_default);
                }
                Log.i("cc", "showPopwindow2: " + idMoiveDetalisOne.getShortFilmList().toString());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
