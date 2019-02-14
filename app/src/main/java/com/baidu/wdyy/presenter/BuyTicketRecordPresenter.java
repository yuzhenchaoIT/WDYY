package com.baidu.wdyy.presenter;

import com.baidu.wdyy.bean.BuyRecordBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import java.util.List;

import io.reactivex.Observable;

public class BuyTicketRecordPresenter extends BasePresenter {

    public BuyTicketRecordPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        Observable<Result<List<BuyRecordBean>>> buyTicketRecord = requestInterFace.buyTicketRecord((int) args[0], (String) args[1], (int) args[2], (int) args[3], (int) args[4]);
        return buyTicketRecord;
    }
}
