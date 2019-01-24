package com.baidu.wdyy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.baidu.wdyy.adapter.show_binner_adapter;
import com.bw.movie.R;

import recycler.coverflow.RecyclerCoverFlow;

public class MovieFragment extends Fragment implements show_binner_adapter.onItemClick {
    private RecyclerCoverFlow mList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mList = view.findViewById(R.id.list);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        mList.setAdapter(new show_binner_adapter(getContext(), this));
        return view;
    }
    private void initList() {

    }

    @Override
    public void clickItem(int pos) {
        mList.smoothScrollToPosition(pos);
    }
}
