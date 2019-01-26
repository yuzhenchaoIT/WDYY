package com.baidu.wdyy;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.adapter.MovieFlowAdapter;
import com.baidu.wdyy.bean.CinemaDetalisBean;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.CinemaDetalisPresenter;
import com.baidu.wdyy.presenter.PopularMoviePresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDetalisActivity extends AppCompatActivity {
    private RecyclerCoverFlow horse;
    private MovieFlowAdapter movieFlowAdapter;
    private SimpleDraweeView cinema_detalis_sdvone;
    private TextView cinema_detalis_textviewone;
    private TextView cinema_detalis_textviewtwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detalis);
        cinema_detalis_sdvone = findViewById(R.id.cinema_detalis_sdvone);
        cinema_detalis_textviewone = findViewById(R.id.cinema_detalis_textviewone);
        cinema_detalis_textviewtwo = findViewById(R.id.cinema_detalis_textviewtwo);

        PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(new PopularCall());

        //获取传过来的电影ID
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        CinemaDetalisPresenter cinemaDetalisPresenter = new CinemaDetalisPresenter(new CinemaDetalisCall());

        cinemaDetalisPresenter.request(0,"",id);

        horse = findViewById(R.id.cinema_detalis_horse);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        movieFlowAdapter = new MovieFlowAdapter(this);
        horse.setAdapter(movieFlowAdapter);
        horse.setOnItemSelectedListener( new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                Toast.makeText(CinemaDetalisActivity.this, ""+(position+1)+"/"+ horse.getLayoutManager().getItemCount(),Toast.LENGTH_SHORT).show();
            }
        });
        popularMoviePresenter.request(0,"",1,10);
    }

    public boolean isBaseOnWidth() {
        return false;
    }


    public float getSizeInDp() {
        return 720;
    }


    //旋转木马电影
    class PopularCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                movieFlowAdapter.addItem(moiveBeans);
                movieFlowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //电影院信息
    class CinemaDetalisCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                CinemaDetalisBean cinemaDetalisBean = (CinemaDetalisBean) result.getResult();
                cinema_detalis_sdvone.setImageURI(Uri.parse(cinemaDetalisBean.getLogo()));
                cinema_detalis_textviewone.setText(cinemaDetalisBean.getName());
                cinema_detalis_textviewtwo.setText(cinemaDetalisBean.getAddress());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
