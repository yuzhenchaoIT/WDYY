package com.baidu.wdyy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.wdyy.ChooseActivity;
import com.baidu.wdyy.bean.CinemaRecy;
import com.bw.movie.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CinemaRecycleAdapter extends RecyclerView.Adapter<CinemaRecycleAdapter.MyViewHolder> {
    private List<CinemaRecy> list = new ArrayList<>();
    public void addList(List<CinemaRecy> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    public void clearList(){
        list.clear();
    }
    private Context context;

    public CinemaRecycleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_recy_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final CinemaRecy cinemaRecy = list.get(i);
        myViewHolder.cinema_name.setText(cinemaRecy.getScreeningHall());
        myViewHolder.cinema_start.setText(cinemaRecy.getBeginTime());
        myViewHolder.cinema_end.setText(cinemaRecy.getEndTime());
        myViewHolder.cinema_mongey.setText(cinemaRecy.getPrice()+"");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChooseActivity.class);
                intent.putExtra("cinemaname", cinemaRecy);
                intent.putExtra("name",mlist.get(i));
                context.startActivity(intent);
            }
        });

    }
    private List<String> mlist = new ArrayList<>();
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addName(List<String> list) {
        if(list!=null){
            mlist.addAll(list);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView cinema_start;
        private final TextView cinema_name;
        private final TextView cinema_end;
        private final TextView cinema_mongey;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cinema_name = itemView.findViewById(R.id.cinema_name);
            cinema_start = itemView.findViewById(R.id.cinema_start);
            cinema_end = itemView.findViewById(R.id.cinema_end);
            cinema_mongey = itemView.findViewById(R.id.cinema_mongey);
        }
    }
}
