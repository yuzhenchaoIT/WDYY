package com.baidu.wdyy.presenter.my;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;
import com.baidu.wdyy.presenter.BasePresenter;

import io.reactivex.Observable;

public class CancelFollowCinemaPresenter extends BasePresenter {


    public CancelFollowCinemaPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        return requestInterFace.cancelFollowCinema((int) args[0], (String) args[1], (int) args[2]);
    }
}
