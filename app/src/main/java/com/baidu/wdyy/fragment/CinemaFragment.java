package com.baidu.wdyy.fragment;

import android.graphics.Color;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.wdyy.Utils.CacheManager;
import com.baidu.wdyy.adapter.CinemaAdapter;
import com.baidu.wdyy.bean.CinemaBean;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.CinemaMoviePresenter;
import com.baidu.wdyy.presenter.NearbyMoivePresenter;
import com.baidu.wdyy.presenter.my.CancelFollowCinemaPresenter;
import com.baidu.wdyy.presenter.my.FollowCinemaPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CinemaFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.cinemasdv)
    SimpleDraweeView cinemasdv;
    @BindView(R.id.cimema_text)
    TextView cimemaText;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.recommend)
    Button recommend;
    @BindView(R.id.nearby)
    Button nearby;
    @BindView(R.id.cinemarecycleview)
    RecyclerView cinemarecycleview;
    Unbinder unbinder;
    private CinemaAdapter cinemaAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CinemaMoviePresenter cinemaPresenter;
    private RecyclerView recycleView;
    private NearbyMoivePresenter nearbyMoivePresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //影院关注
    private FollowCinemaPresenter followCinemaPresenter = new FollowCinemaPresenter(new FollowCinemaDataCall());
    //影院取消关注
    private CancelFollowCinemaPresenter cancelFollowCinemaPresenter = new CancelFollowCinemaPresenter(new CancelFollowCinemaDataCall());
    //取出用户  userId    sessionId
    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");
    private View view;
    private int cinemaId1;
    private CacheManager cacheManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cacheManager = new CacheManager();
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);


        Button recommend = view.findViewById(R.id.recommend);
        Button nearby = view.findViewById(R.id.nearby);
        recommend.setOnClickListener(this);
        nearby.setOnClickListener(this);
        recycleView = view.findViewById(R.id.cinemarecycleview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);

        cinemaPresenter = new CinemaMoviePresenter(new CinemaCall());
        nearbyMoivePresenter = new NearbyMoivePresenter(new CinemaCall());

        //默认推荐影院
        cinemaAdapter = new CinemaAdapter(getActivity());
        recycleView.setAdapter(cinemaAdapter);
        cinemaPresenter.request(userId, sessionId, 1, 10);

        unbinder = ButterKnife.bind(this, view);
        //接口回调拿到 影院的Id
        cinemaAdapter.setOnItemClickListener(new CinemaAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int cinemaId, int isFollow) {
                cinemaId1 = cinemaId;
                if (isFollow == 1) {
                    //请求取消关注的接口
                    cancelFollowCinemaPresenter.request(userId, sessionId, cinemaId1);
                } else if (isFollow == 2) {
                    //请求关注的接口
                    followCinemaPresenter.request(userId, sessionId, cinemaId1);
                }
            }
        });

        recommend.setBackgroundResource(R.drawable.btn_gradient);
        recommend.setTextColor(Color.WHITE);
        nearby.setBackgroundResource(R.drawable.myborder);
        nearby.setTextColor(Color.BLACK);

        initData();
        return view;
    }

    private void initData() {
        mLocationClient = new LocationClient(getActivity());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }

    @Override
    public void onClick(View v) {
        //推荐影院
        if (v.getId() == R.id.recommend) {
            recommend.setBackgroundResource(R.drawable.btn_gradient2);
            recommend.setTextColor(Color.WHITE);
            nearby.setBackgroundResource(R.drawable.myborder);
            nearby.setTextColor(Color.BLACK);
            cinemaAdapter.remove();
            cinemaAdapter = new CinemaAdapter(getActivity());
            recycleView.setAdapter(cinemaAdapter);
            cinemaPresenter.request(userId, sessionId, 1, 10);
        }
        //附近影院
        if (v.getId() == R.id.nearby) {
            nearby.setBackgroundResource(R.drawable.btn_gradient2);
            nearby.setTextColor(Color.WHITE);
            recommend.setBackgroundResource(R.drawable.myborder);
            recommend.setTextColor(Color.BLACK);
            cinemaAdapter.remove();
            cinemaAdapter = new CinemaAdapter(getActivity());
            recycleView.setAdapter(cinemaAdapter);
            nearbyMoivePresenter.request(userId, sessionId, "116.30551391385724", "40.04571807462411", 1, 10);
            //接口回调拿到 影院的Id
            cinemaAdapter.setOnItemClickListener(new CinemaAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(int cinemaId, int isFollow) {
                    if (isFollow == 1) {
                        //请求取消关注的接口
                        cancelFollowCinemaPresenter.request(userId, sessionId, cinemaId1);
                    } else if (isFollow == 2) {
                        //请求关注的接口
                        followCinemaPresenter.request(userId, sessionId, cinemaId1);
                    }
                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
                     unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    /**
     * 影院关注
     */
    class FollowCinemaDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                cancelFollowCinemaPresenter.request(userId, sessionId, cinemaId1);
                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 影院取消关注
     */
    class CancelFollowCinemaDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                followCinemaPresenter.request(userId, sessionId, cinemaId1);
                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 请求影院数据的DataCall
     */
    class CinemaCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<CinemaBean> cinemaBeans = (List<CinemaBean>) result.getResult();
                cinemaAdapter.addItem(cinemaBeans);
                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 定位
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息
            Log.i("bb", "onReceiveLocation: " + addr);
            cimemaText.setText(locationDescribe + addr);

        }
    }
}
