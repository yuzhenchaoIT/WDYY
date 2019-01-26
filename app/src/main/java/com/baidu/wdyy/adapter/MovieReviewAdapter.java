package com.baidu.wdyy.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.wdyy.bean.IDMoiveDetalisOne;
import com.bumptech.glide.Glide;
import com.bw.movie.R;


import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewHolder>{
    private Context context;
    private JZVideoPlayerStandard videoplayer;
    private String name;
    private List<IDMoiveDetalisOne.ShortFilmListBean> list;

    public MovieReviewAdapter(Context context, List<IDMoiveDetalisOne.ShortFilmListBean> list, String name) {
        this.context = context;
        this.list = list;
        this.name=name;
    }

    @NonNull
    @Override
    public MovieReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_review, parent, false);
        MovieReviewHolder movieReviewHolder = new MovieReviewHolder(view);
        return movieReviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewHolder holder, int position) {
        videoplayer.thumbImageView.setImageURI(Uri.parse(list.get(position).getImageUrl()));
        videoplayer.setUp(list.get(position).getVideoUrl()
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,name);
        Glide.with(context).load(Uri.parse(list.get(position).getImageUrl())).into(videoplayer.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MovieReviewHolder extends RecyclerView.ViewHolder{

        public MovieReviewHolder(View itemView) {
            super(itemView);
            videoplayer = itemView.findViewById(R.id.videoplayer);
        }


    }

}
