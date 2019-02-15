package com.baidu.wdyy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.wdyy.DetailActivity;
import com.baidu.wdyy.bean.MoiveBean;
import com.bw.movie.R;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class BeingAdapter extends RecyclerView.Adapter {

    private Context context;

    public BeingAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<MoiveBean> list = new ArrayList<>();

    public void addItem(List<MoiveBean> popularMovieBeans) {
        if (popularMovieBeans != null) {
            list.addAll(popularMovieBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_popularmovie, null);
        PopularVH popularVH = new PopularVH(view);
        return popularVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MoiveBean moiveBean = list.get(i);
        PopularVH popularVH = (PopularVH) viewHolder;
        popularVH.popularsdv.setImageURI(Uri.parse(moiveBean.getImageUrl()));
        popularVH.populartextview.setBackgroundColor(0x55000000);
        popularVH.populartextview.setText(moiveBean.getName());
        popularVH.populartextview.setBackgroundResource(R.drawable.bg_item_text);
        popularVH.itemView.setOnClickListener(new View.OnClickListener() {
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


    //创建ViewHolder
    class PopularVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView popularsdv;
        public TextView populartextview;

        public PopularVH(@NonNull View itemView) {
            super(itemView);
            popularsdv = itemView.findViewById(R.id.popularsdv);
            populartextview = itemView.findViewById(R.id.populartextview);
        }
    }
}
