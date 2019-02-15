package com.baidu.wdyy;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.Utils.SpaceItemDecoration;
import com.baidu.wdyy.adapter.CinemaFlowAdapter;
import com.baidu.wdyy.adapter.CinemaRecycleAdapter;
import com.baidu.wdyy.bean.CinemaById;
import com.baidu.wdyy.bean.CinemaDetalisBean;
import com.baidu.wdyy.bean.CinemaRecy;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.CinemaByIdPresenter;
import com.baidu.wdyy.presenter.CinemaDetalisPresenter;
import com.baidu.wdyy.presenter.CinemaRecyPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDetalisActivity extends AppCompatActivity {
    @BindView(R.id.cinema_recy2)
    RecyclerView cinemaRecy;
    private RecyclerCoverFlow horse;
    private SimpleDraweeView cinema_detalis_sdvone;
    private TextView cinema_detalis_textviewone;
    private TextView cinema_detalis_textviewtwo;
    private CinemaByIdPresenter cinemaByIdPresenter;
    private CinemaFlowAdapter cinemaFlowAdapter;
    private CinemaRecyPresenter cinemaRecyPresenter;
    private CinemaRecycleAdapter cinemaRecycleAdapter;
    private List<CinemaById> cinemaByIds;
    private int p = 4;
    ;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detalis);
        ButterKnife.bind(this);
        cinema_detalis_sdvone = findViewById(R.id.cinema_detalis_sdvone);
        cinema_detalis_textviewone = findViewById(R.id.cinema_detalis_textviewone);
        cinema_detalis_textviewtwo = findViewById(R.id.cinema_detalis_textviewtwo);
        //获取传过来的电影ID
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        CinemaDetalisPresenter cinemaDetalisPresenter = new CinemaDetalisPresenter(new CinemaDetalisCall());
        cinemaDetalisPresenter.request(0, "", id);
        cinemaByIdPresenter = new CinemaByIdPresenter(new MyCall());
        cinemaFlowAdapter = new CinemaFlowAdapter(this);
        horse = findViewById(R.id.cinema_detalis_horse);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        horse.setAdapter(cinemaFlowAdapter);
        cinemaByIdPresenter.request(id);
        horse.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                p = cinemaByIds.get(position).getId();
                Log.d("id23", "onItemSelected: " + p);
                cinemaRecycleAdapter.clearList();
                cinemaRecyPresenter.request(id, p);
            }
        });
        cinemaRecycleAdapter = new CinemaRecycleAdapter(this);
        cinemaRecyPresenter = new CinemaRecyPresenter(new My());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        cinemaRecy.setLayoutManager(manager);
        cinemaRecy.setAdapter(cinemaRecycleAdapter);
        cinemaRecy.addItemDecoration(new SpaceItemDecoration(10));

    }

    class My implements DataCall<Result<List<CinemaRecy>>> {

        @Override
        public void success(Result<List<CinemaRecy>> result) {
            if (result.getStatus().equals("0000")) {
//                Toast.makeText(CinemaDetalisActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                List<CinemaRecy> result1 = result.getResult();
                cinemaRecycleAdapter.addList(result1);
                cinemaRecycleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class MyCall implements DataCall<Result<List<CinemaById>>> {

        @Override
        public void success(Result<List<CinemaById>> result) {
            if (result.getStatus().equals("0000")) {
                List<String> list = new ArrayList<>();
                cinemaByIds = result.getResult();
                p = cinemaByIds.get(0).getId();
                cinemaRecyPresenter.request(id, p);
                Log.d("id2", "success: " + p);
                cinemaFlowAdapter.addItem(cinemaByIds);
                cinemaFlowAdapter.notifyDataSetChanged();
                for (int i = 0; i < cinemaByIds.size(); i++) {
                    list.add(cinemaByIds.get(i).getName());
                }
                cinemaRecycleAdapter.addName(list);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    public boolean isBaseOnWidth() {
        return false;
    }


    public float getSizeInDp() {
        return 720;
    }


    //电影院信息
    class CinemaDetalisCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                CinemaDetalisBean cinemaDetalisBean = (CinemaDetalisBean) result.getResult();
                cinema_detalis_sdvone.setImageURI(Uri.parse(cinemaDetalisBean.getLogo()));
                cinema_detalis_textviewone.setText(cinemaDetalisBean.getName());
                cinema_detalis_textviewtwo.setText(cinemaDetalisBean.getAddress());
                Log.i("abc", "success: " + new Gson().toJson(result));

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
