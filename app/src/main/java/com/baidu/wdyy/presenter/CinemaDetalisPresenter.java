package com.baidu.wdyy.presenter;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import io.reactivex.Observable;

public class CinemaDetalisPresenter extends BasePresenter{
    public CinemaDetalisPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace iBaseView = NetWorkManager.getInstance().create(RequestInterFace.class);
        return iBaseView.CinemaDetalis((int) args[0],(String) args[1],(int) args[2]);
    }
}
