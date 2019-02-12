package com.baidu.wdyy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.wdyy.bean.RemindBean;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.MyViewHolder> {

    private List<RemindBean> mList = new ArrayList<>();

    private Context mContext;

    public RemindAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addItem(List<RemindBean> remindBeans) {
        if (remindBeans != null) {
            mList.addAll(remindBeans);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_remind, viewGroup, false);
        MyViewHolder myHolder = new MyViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        RemindBean remindBean = mList.get(i);
        myViewHolder.title.setText(remindBean.getTitle());
        myViewHolder.info.setText(remindBean.getContent());
        myViewHolder.time.setText(remindBean.getPushTime());
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, info, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_remind_title);
            info = itemView.findViewById(R.id.item_remind_info);
            time = itemView.findViewById(R.id.item_remind_time);
        }
    }

}
