package com.baidu.wdyy.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.wdyy.bean.MoiveBean;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FindMovieAdapter extends RecyclerView.Adapter<FindMovieAdapter.MyHolder> {

    private Context context;
    private List<MoiveBean> list = new ArrayList<>();

    public FindMovieAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<MoiveBean> moiveBeans) {
        if (moiveBeans != null) {
            list.addAll(moiveBeans);
        }
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_find_movie_adapter, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MoiveBean moiveBean = list.get(i);
        myHolder.img.setImageURI(Uri.parse(moiveBean.getImageUrl()));
        myHolder.name.setText(moiveBean.getName());
        myHolder.content.setText(moiveBean.getSummary());
        myHolder.time.setText(moiveBean.getReleaseTimeShow());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {
        list.clear();
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView img;
        private TextView name, content, time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.movie_img);
            name = itemView.findViewById(R.id.movie_name);
            content = itemView.findViewById(R.id.movie_context);
            time = itemView.findViewById(R.id.movie_time_show);
        }
    }


}
