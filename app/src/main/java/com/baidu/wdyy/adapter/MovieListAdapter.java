package com.baidu.wdyy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.DetailActivity;
import com.baidu.wdyy.bean.MoiveBean;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder1> {


    private List<MoiveBean> mList = new ArrayList<>();
    private Context mContext;

    public MovieListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addItem(List<MoiveBean> movieBeans) {
        if (movieBeans != null) {
            mList.addAll(movieBeans);
        }
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie_list, viewGroup, false);
        ViewHolder1 viewHolder1 = new ViewHolder1(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder1 viewHolder1, int i) {
        final MoiveBean moiveBean = mList.get(i);
        viewHolder1.sim.setImageURI(moiveBean.getImageUrl());
        viewHolder1.name.setText(moiveBean.getName());
        viewHolder1.title.setText("简介：" + moiveBean.getSummary());
        viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("id", moiveBean.getId() + "");
                mContext.startActivity(intent);
            }
        });

        final int isFllow = moiveBean.getFollowMovie();
        viewHolder1.guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, moiveBean.getId() + " " + isFllow, Toast.LENGTH_SHORT).show();
                onItemClickListener.onItemClick(moiveBean.getId(), isFllow);
            }
        });
        if (isFllow == 1) {
            viewHolder1.guan.setImageResource(R.drawable.com_icon_collection_selectet);
        } else if (isFllow == 2) {
            viewHolder1.guan.setImageResource(R.drawable.com_icon_collection_default_hdpi);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void remove() {
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 内部类
     */
    class ViewHolder1 extends RecyclerView.ViewHolder {
        private SimpleDraweeView sim;
        private TextView name, title;
        private ImageView guan;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            sim = itemView.findViewById(R.id.sims);
            name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.titles);
            guan = itemView.findViewById(R.id.guan);
        }
    }

    /**
     * 条目点击进入详情页面 接口回调
     */

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int moiveId, int isFollow);
    }

    //方法名
    private OnItemClickListener onItemClickListener;

    //set方法      设置点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
