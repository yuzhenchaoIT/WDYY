package com.baidu.wdyy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.wdyy.DetailActivity;
import com.baidu.wdyy.bean.MoiveBean;
import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class show_binner_adapter extends RecyclerView.Adapter {

    private Context context;

    public show_binner_adapter(Context context) {
        this.context = context;
    }

    private ArrayList<MoiveBean> list = new ArrayList<>();

    public void addItem(List<MoiveBean> moiveBeans) {
        if (moiveBeans != null) {
            list.addAll(moiveBeans);
        }
    }

    private onItemClick clickCb;

    public show_binner_adapter(onItemClick clickCb) {
        this.clickCb = clickCb;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_item, null);
        MovieVH movieVH = new MovieVH(view);
        return movieVH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        final MoiveBean moiveBean = list.get(i);
        MovieVH movieVH = (MovieVH) viewHolder;
//        Glide.with(context).load(moiveBean.getImageUrl()).into(movieVH.img);
        movieVH.img.setImageURI(moiveBean.getImageUrl());
        movieVH.populartextviewone.setBackgroundColor(0x55000000);
        movieVH.populartextviewone.setText(moiveBean.getName());
        movieVH.populartextviewone.setBackgroundResource(R.drawable.bg_item_text);
        movieVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCb != null) {
                    clickCb.clickItem(i);
                }
            }
        });
        movieVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", moiveBean.getId() + "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MovieVH extends RecyclerView.ViewHolder {
        public SimpleDraweeView img;
        public TextView populartextviewone;

        public MovieVH(View itemView) {
            super(itemView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.img);
            populartextviewone = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public interface onItemClick {
        void clickItem(int position);
    }
}
