package com.baidu.wdyy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.baidu.wdyy.adapter.BeingAdapter;
import com.baidu.wdyy.adapter.PopularAdapter;
import com.baidu.wdyy.adapter.SoonAdapter;
import com.baidu.wdyy.adapter.show_binner_adapter;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.BeingMoviePresenter;
import com.baidu.wdyy.presenter.PopularMoviePresenter;
import com.baidu.wdyy.presenter.SoonMoviePresenter;
import com.bw.movie.R;

import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class MovieFragment extends Fragment implements show_binner_adapter.onItemClick {
    private RecyclerCoverFlow mList;


    private PopularAdapter popularAdapter;
    private BeingAdapter beingAdapter;
    private SoonAdapter soonAdapter;
    private com.baidu.wdyy.adapter.show_binner_adapter show_binner_adapter;
    private RadioGroup homeRadioGroup;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mList = view.findViewById(R.id.list);
        homeRadioGroup = view.findViewById(R.id.home_radio_group);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变

        show_binner_adapter = new show_binner_adapter(getActivity());
        mList.setAdapter(show_binner_adapter);


        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                homeRadioGroup.check(homeRadioGroup.getChildAt(position).getId());
               // Toast.makeText(getActivity(), ""+(position+1)+"/"+movieflow.getLayoutManager().getItemCount(),Toast.LENGTH_SHORT).show();
            }
        });
        PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(new PopularCall());
        BeingMoviePresenter beingMoviePresenter = new BeingMoviePresenter(new BeingCall());
        SoonMoviePresenter soonMoviePresenter = new SoonMoviePresenter(new SoonCall());
        RecyclerView popularRecycleView = view.findViewById(R.id.recycler_hotmovie);
        RecyclerView beingRecycleView = view.findViewById(R.id.recycler_playingmovie);
        RecyclerView soonRecycleView = view.findViewById(R.id.recycler_sooning);


        //热门电影列表数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popularRecycleView.setLayoutManager(linearLayoutManager);
        popularAdapter = new PopularAdapter(getActivity());
        popularRecycleView.setAdapter(popularAdapter);
        //正在上映
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        beingRecycleView.setLayoutManager(linearLayoutManager2);
        beingAdapter = new BeingAdapter(getActivity());
        beingRecycleView.setAdapter(beingAdapter);
        //即将上映
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        soonRecycleView.setLayoutManager(linearLayoutManager3);
        soonAdapter = new SoonAdapter(getActivity());
        soonRecycleView.setAdapter(soonAdapter);

        popularMoviePresenter.request(0,"",1,10);
        beingMoviePresenter.request(0,"",1,10);
        soonMoviePresenter.request(0,"",1,10);
        return view;

    }

    public boolean isBaseOnWidth() {
        return false;
    }


    public float getSizeInDp() {
        return 720;
    }

    //热门电影
    class PopularCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                Log.i("aa", "success: "+moiveBeans.toString());
               show_binner_adapter.addItem(moiveBeans);

                popularAdapter.addItem(moiveBeans);
                popularAdapter.notifyDataSetChanged();
                show_binner_adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Log.i("aa", "失败: ");

        }
    }
    //正在上映
    class BeingCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                Log.i("aa", "success: "+moiveBeans.toString());
                soonAdapter.addItem(moiveBeans);
                soonAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //即将上映
    class SoonCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                beingAdapter.addItem(moiveBeans);
                beingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    @Override
    public void clickItem(int pos) {
        mList.smoothScrollToPosition(pos);
    }
}
