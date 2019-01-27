package com.baidu.wdyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.bean.CinemaRecy;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfoBean;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.BuyMovieTicketPresenter;
import com.baidu.wdyy.presenter.BuyMovieresultPresenter;
import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    private TextView textname;
    private RelativeLayout rl_bot;
    private TextView txt_choose_price;
    private double price = 0.0;
    private CinemaRecy cinemaname;
    private ImageView img_confirm;
    private ImageView img_abandon;
    private BuyMovieTicketPresenter buyMovieTicketPresenter;

    private int count=0;

    private int userId = WDYYApp.getShare().getInt("userId", 0);
    private String sessionId = WDYYApp.getShare().getString("sessionId", "");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        SeatTable seat_view = (SeatTable) findViewById(R.id.seat_view);
        txt_choose_price = findViewById(R.id.txt_choose_price);
        img_confirm = findViewById(R.id.img_confirm);
        img_abandon = findViewById(R.id.img_abandon);
        textname = findViewById(R.id.txt_session);
        rl_bot = findViewById(R.id.rl_bot);
        Intent intent = getIntent();
        cinemaname = (CinemaRecy) intent.getSerializableExtra("cinemaname");
        String name = intent.getStringExtra("name");
        seat_view.setScreenName(cinemaname.getScreeningHall());//设置屏幕名称
        seat_view.setMaxSelected(2);//设置最多选中
        textname.setText(name);
        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new MyBuy());
        seat_view.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                rl_bot.setVisibility(View.VISIBLE);
                price+=cinemaname.getPrice();
                Log.d("a", "unCheck: "+price);
                txt_choose_price.setText(price+"");
                count++;
            }

            @Override
            public void unCheck(int row, int column) {
                price-=cinemaname.getPrice();
                Log.d("a", "unCheck: "+price);
                txt_choose_price.setText(price+"");
                if(price == 0.0){
                    rl_bot.setVisibility(View.GONE);
                }
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seat_view.setData(10,15);
        img_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String md5 = MD5(userId +""+ cinemaname.getId()+"" + count + "movie");

                Log.i("zf", "onClick: "+userId + cinemaname.getId() + count + "movie");
                buyMovieTicketPresenter.request(userId,sessionId, cinemaname.getId(),count,md5);
            }
        });
        img_abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_bot.setVisibility(View.GONE);
            }

        });
    }
    class MyBuy implements DataCall<Result> {

        @Override
        public void success(Result result) {
            Toast.makeText(ChooseActivity.this, result.getMessage()+"成功", Toast.LENGTH_SHORT).show();
            BuyMovieresultPresenter buyMovieresultPresenter=new BuyMovieresultPresenter(new MyResult());
            buyMovieresultPresenter.request(userId,sessionId,1,result.getOrderId());
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(ChooseActivity.this,e.getMessage()+"失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     *  MD5加密
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    private class MyResult implements DataCall<Result> {
        @Override
        public void success(Result data) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(ChooseActivity.this, null);

// 将该app注册到微信

            msgApi.registerApp("wxd930ea5d5a258f4f");
            PayReq request = new PayReq();
            request.appId = data.getAppId();
            request.partnerId = data.getPartnerId();
            request.prepayId= data.getPrepayId();
            request.packageValue = data.getPackageValue();
            request.nonceStr= data.getNonceStr();
            request.timeStamp= data.getTimeStamp();
            request.sign= data.getSign();
            msgApi.sendReq(request);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
