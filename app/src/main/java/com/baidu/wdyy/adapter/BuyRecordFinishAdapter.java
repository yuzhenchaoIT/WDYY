package com.baidu.wdyy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.wdyy.Utils.DateUtils;
import com.baidu.wdyy.bean.BuyRecordBean;
import com.bw.movie.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购票记录（已完成）适配器
 *
 * @author lmx
 * @date 2019/2/13
 */
public class BuyRecordFinishAdapter extends RecyclerView.Adapter<BuyRecordFinishAdapter.VH> {

    private List<BuyRecordBean> mList = new ArrayList<>();
    private Context mContext;

    public BuyRecordFinishAdapter(Context mContext) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_buy_finish, viewGroup, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        BuyRecordBean buyRecordBean = mList.get(i);
        vh.title.setText(buyRecordBean.getMovieName());
        vh.time.setText(buyRecordBean.getBeginTime() + "-" + buyRecordBean.getEndTime());
        vh.orderId.setText("订单号： " + buyRecordBean.getOrderId());
        try {
            vh.orderTime.setText("下单时间： " + DateUtils.dateFormat(new Date(buyRecordBean.getCreateTime()), DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vh.cinema.setText("影院： " + buyRecordBean.getCinemaName());
        vh.place.setText("影厅： " + buyRecordBean.getScreeningHall());
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

        private TextView title, time, orderId, orderTime, cinema, place, count, money;


        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_buy_finish_title);
            time = itemView.findViewById(R.id.item_buy_finish_time);
            orderId = itemView.findViewById(R.id.item_buy_finish_orderId);
            orderTime = itemView.findViewById(R.id.item_buy_finish_order_time);
            cinema = itemView.findViewById(R.id.item_buy_finish_cinema);
            place = itemView.findViewById(R.id.item_buy_finish_place);
            count = itemView.findViewById(R.id.item_buy_finish_count);
            money = itemView.findViewById(R.id.item_buy_finish_money);
        }
    }


}
