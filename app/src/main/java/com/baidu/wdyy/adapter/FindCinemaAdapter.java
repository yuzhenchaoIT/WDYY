package com.baidu.wdyy.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.wdyy.bean.CinemaBean;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FindCinemaAdapter extends RecyclerView.Adapter<FindCinemaAdapter.MyHolder> {

    private Context context;
    private List<CinemaBean> list = new ArrayList<>();

    public FindCinemaAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<CinemaBean> cinemaBeans) {
        if (cinemaBeans != null) {
            list.addAll(cinemaBeans);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_find_cinema_adapter, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        CinemaBean cinemaBean = list.get(i);
        myHolder.img.setImageURI(Uri.parse(cinemaBean.getLogo()));
        myHolder.name.setText(cinemaBean.getName());
        myHolder.address.setText(cinemaBean.getAddress());

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
        private TextView name, address;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cinema_img);
            name = itemView.findViewById(R.id.cinema_name);
            address = itemView.findViewById(R.id.cinema_address);
        }
    }

}
