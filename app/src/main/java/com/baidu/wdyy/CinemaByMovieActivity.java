package com.baidu.wdyy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.baidu.wdyy.bean.IDMoiveDetalisOne;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.IDMoiveDetalisonePresenter;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaByMovieActivity extends AppCompatActivity {
    @BindView(R.id.recycler_cinemaByMovie)
    RecyclerView recyclerCinemaByMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_by_movie);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movieId");

    }
}
