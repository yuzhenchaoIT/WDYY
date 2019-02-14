package com.baidu.wdyy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.Utils.DateUtils;
import com.baidu.wdyy.bean.BuyRecordBean;
import com.bw.movie.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购票记录（待付款）适配器
 *
 * @author lmx
 * @date 2019/2/13
 */
public class BuyRecordPayAdapter extends RecyclerView.Adapter<BuyRecordPayAdapter.VH> {

    private List<BuyRecordBean> mList = new ArrayList<>();

    private Context mContext;

    public BuyRecordPayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addItem(List<BuyRecordBean> buyRecordBeans) {
        if (buyRecordBeans != null) {
            mList.addAll(buyRecordBeans);
        }
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_buy_record_pay, viewGroup, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        BuyRecordBean buyRecordBean = mList.get(i);
        vh.go_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "去付款", Toast.LENGTH_SHORT).show();
            }
        });
        vh.title.setText(buyRecordBean.getMovieName());
        vh.orderId.setText("订单号： " + buyRecordBean.getOrderId());
        vh.cinema.setText("影院： " + buyRecordBean.getCinemaName());
        vh.place.setText("影厅： " + buyRecordBean.getScreeningHall());
        try {
            vh.time.setText("时间： " + DateUtils.dateFormat(new Date(buyRecordBean.getCreateTime()), DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vh.count.setText("数量： " + buyRecordBean.getAmount() + "张");
        vh.money.setText("金额： " + buyRecordBean.getPrice() + "元");

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
    class VH extends RecyclerView.ViewHolder {

        private Button go_pay;
        private TextView title, orderId, cinema, place, time, count, money;


        public VH(@NonNull View itemView) {
            super(itemView);
            go_pay = itemView.findViewById(R.id.item_buy_record_go_pay);
            title = itemView.findViewById(R.id.item_buy_record_title);
            orderId = itemView.findViewById(R.id.item_buy_record_orderId);
            cinema = itemView.findViewById(R.id.item_buy_record_cinema);
            place = itemView.findViewById(R.id.item_buy_record_place);
            time = itemView.findViewById(R.id.item_buy_record_time);
            count = itemView.findViewById(R.id.item_buy_record_count);
            money = itemView.findViewById(R.id.item_buy_record_money);
        }
    }

}
