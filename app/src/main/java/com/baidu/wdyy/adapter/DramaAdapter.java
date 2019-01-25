package com.baidu.wdyy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.List;

/**
 * Created by YangYueXiang
 * on 2018/11/12
 */
public class DramaAdapter extends RecyclerView.Adapter<DramaAdapter.DramaHolder>{
    private Context context;
    private List<String> result;

    public DramaAdapter(Context context, List<String> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public DramaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_drama, parent, false);
        DramaHolder dramaHolder = new DramaHolder(view);
        return dramaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DramaHolder holder, int position) {
        Glide.with(context).load(result.get(position)).into(holder.simp_drama);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class DramaHolder extends RecyclerView.ViewHolder {

        private final ImageView simp_drama;

        public DramaHolder(View itemView) {
            super(itemView);
            simp_drama = itemView.findViewById(R.id.simp_drama);
        }
    }
}
